package ua.np.services.smsinfo;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class JdbcSmsServiceDaoImpl implements SmsServiceDao {

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private JmsQueueSender jmsQueueSender;

    public void setJmsQueueSender( JmsQueueSender jmsQueueSender ) {
        this.jmsQueueSender = jmsQueueSender;
    }

    public void setDataSource( DataSource dataSource ) {
        this.dataSource = dataSource;
    }

    @Override
    public List<SmsRequest> addRequests( final List<SmsRequest> requests ) {

        jdbcTemplate = new JdbcTemplate( dataSource );
        String insertQuery = "INSERT INTO SmsRequest (incomingId, messageText, phoneNumber, status, systemName, creationDate, updateDate) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String selectQuery = "SELECT smsRequestId, incomingId FROM SmsRequest WHERE incomingId IN (:idList)";

        insertSmsRequests( requests, insertQuery );

        // get generated id`s
        // key - incomingId , value - smsRequestId (generated id from database)
        Map<String, Object> idMap = getIdMap( requests, selectQuery );
        setGeneratedIds(requests, idMap);
        sendRequestsToJms(requests);
        return requests;
    }

    private void sendRequestsToJms( List<SmsRequest> smsRequests ) {
        for( SmsRequest smsRequest : smsRequests ) {
            jmsQueueSender.send( smsRequest );
        }
    }

    private void setGeneratedIds( List<SmsRequest> requests, Map<String,Object> idMap ) {
        for( SmsRequest smsRequest : requests ) {
            BigInteger id = (BigInteger) idMap.get( smsRequest.getIncomingId() );
            smsRequest.setSmsRequestId( id.longValue() );
        }
    }

    private void insertSmsRequests( final List<SmsRequest> requests,
                                    String insertSql ) {
        jdbcTemplate.batchUpdate( insertSql, new BatchPreparedStatementSetter() {

            public void setValues( PreparedStatement ps, int i ) throws SQLException {
                SmsRequest request = requests.get( i );
                ps.setString( 1, request.getIncomingId() );
                ps.setString( 2, request.getMessageText() );
                ps.setString( 3, request.getPhoneNumber() );
                ps.setString( 4, request.getStatus() );
                ps.setString( 5, request.getPhoneNumber() );
                ps.setTimestamp( 6, getTimeStampFromDate( request.getCreationDate() ) );
                ps.setTimestamp( 7, getTimeStampFromDate( request.getUpdateDate() ) );
            }

            public int getBatchSize() {
                return requests.size();
            }
        } );
    }

    private Map<String, Object> getIdMap( List<SmsRequest> requests, String selectQuery ) {
        Map<String, Object> result = new HashMap<>();

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate( jdbcTemplate.getDataSource() );
        MapSqlParameterSource parameters = new MapSqlParameterSource("idList", getIdArrayString( requests ));

        List<Map<String, Object>> list = namedParameterJdbcTemplate.queryForList( selectQuery, parameters );

        for( Map<String, Object> map : list ) {
            result.put( (String)map.get( "incomingId" ), map.get( "smsRequestId" ) );
        }

        return result;
    }


    private Set<String> getIdArrayString( List<SmsRequest> requests ) {
        Set<String> result = new HashSet<>(  );
        for( SmsRequest smsRequest : requests ) {
            result.add( smsRequest.getIncomingId() );
        }
        return result;
    }

    private static java.sql.Timestamp getTimeStampFromDate( java.util.Date someDate ) {
        return new java.sql.Timestamp( someDate.getTime() );
    }

}

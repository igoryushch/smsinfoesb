package ua.np.services.smsinfo;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Copyright (C) 2014 Nova Poshta. All rights reserved.
 * http://novaposhta.ua/
 * <p/>
 * for internal use only!
 * <p/>
 * User: yushchenko.i
 * email: yushchenko.i@novaposhta.ua
 * Date: 15.01.14
 */

public class JdbcOperatorDaoImpl implements OperatorDao {
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    public void setDataSource( DataSource dataSource ) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Operator> findAll() {

        jdbcTemplate = new JdbcTemplate( dataSource );
        String sql = "SELECT Operator.operatorId, name, phoneCodeMapping.phoneCodes FROM Operator left join phoneCodeMapping on Operator.operatorId=phoneCodeMapping.operatorId";
        List<Operator> result = new ArrayList<>(  );

        List<Map<String, Object>> rows = jdbcTemplate.queryForList( sql );
        Operator currentOperator = null;
        for (Map<String, Object> row : rows){
            if (currentOperator == null){
                currentOperator = new Operator( (String)row.get( "name" ) );
            }
            if( !currentOperator.getName().equals( (String)row.get( "name" ) ) ){
                result.add( currentOperator );
                currentOperator = new Operator( (String)row.get( "name" ) );
            }
            currentOperator.getPhoneCodeMaping().add( (String)row.get( "phoneCodes" ) );
        }

        result.add( currentOperator );

        return result;
    }

}

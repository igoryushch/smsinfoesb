package ua.np.services.smsinfo;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C) 2014 Nova Poshta. All rights reserved.
 * http://novaposhta.ua/
 * <p/>
 * for internal use only!
 * <p/>
 * User: yushchenko.i
 * email: yushchenko.i@novaposhta.ua
 * Date: 20.03.2014
 */
public class KyivstarResponseProcessor implements Processor {

    @Override
    public void process( Exchange exchange ) throws Exception {

        KyivstarAcceptanceResponse resultResponse = parseResponse( exchange.getIn().getBody(InputStream.class) );
        // update operator message id & statuses
        List<KyivstarAcceptanceStatus> statusList = resultResponse.getStatus();

        String requestXml = (String) exchange.getIn().getHeader( "requestMessage" );
        List<SmsRequest> smsRequestList = getSmsRequestFromXmlString(requestXml);
        for( KyivstarAcceptanceStatus acceptanceStatus : statusList ) {
            System.out.println( acceptanceStatus.getClid() + " status = " + acceptanceStatus.getValue() );
            for( SmsRequest smsRequest : smsRequestList ) {
                if( smsRequest.getSmsRequestId().equals( Long.valueOf( acceptanceStatus.getClid() ) ) ) {
                    smsRequest.setOperatorMessageId( acceptanceStatus.getMid() );
                    smsRequest.setStatus( acceptanceStatus.getValue() );
                    break;
                }
            }
        }
        exchange.getOut().setBody( smsRequestList );
    }

    private List<SmsRequest> getSmsRequestFromXmlString( String requestXml ) {

        JAXBContext jc = null;
        List<SmsRequest> result = new ArrayList<>(  );
        try {
            jc = JAXBContext.newInstance( SmsRequestListWrapper.class );
            Unmarshaller jaxbUnmarshaller = jc.createUnmarshaller();
            SmsRequestListWrapper requestListWrapper = (SmsRequestListWrapper) jaxbUnmarshaller.unmarshal( new StreamSource( new StringReader( requestXml ) ) );
            result = requestListWrapper.getRequestList();
        } catch( JAXBException e ) {
            e.printStackTrace();
        }

        return result;
    }

    private KyivstarAcceptanceResponse parseResponse( InputStream responseInputStream ) {
        KyivstarAcceptanceResponse resultResponse = null;
        JAXBContext jc = null;
        try {
            jc = JAXBContext.newInstance( KyivstarAcceptanceResponse.class, ObjectFactory.class );
            Unmarshaller jaxbUnmarshaller = jc.createUnmarshaller();
            JAXBElement<KyivstarAcceptanceResponse> jeResponse = (JAXBElement<KyivstarAcceptanceResponse>)jaxbUnmarshaller.unmarshal( responseInputStream );
            resultResponse = jeResponse.getValue();
            return resultResponse;
        } catch( JAXBException e ) {
            e.printStackTrace();
        }
        return null;
    }


}

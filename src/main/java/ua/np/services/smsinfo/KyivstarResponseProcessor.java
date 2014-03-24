package ua.np.services.smsinfo;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
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
        for( KyivstarAcceptanceStatus acceptanceStatus : statusList ) {
            System.out.println( acceptanceStatus.getClid() + " status = " + acceptanceStatus.getValue() );
//            for( SmsRequest smsRequest : smsRequestList ) {
//                if( smsRequest.getSmsRequestId().equals( Long.valueOf( acceptanceStatus.getClid() ) ) ) {
//                    smsRequest.setOperator( operator );
//                    smsRequest.setOperatorMessageId( acceptanceStatus.getMid() );
//                    smsRequest.setStatus( acceptanceStatus.getValue() );
//                }
//            }
        }

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

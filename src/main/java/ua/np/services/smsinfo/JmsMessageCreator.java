package ua.np.services.smsinfo;

import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

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
public class JmsMessageCreator implements MessageCreator {

    private SmsRequest request;

    public SmsRequest getRequest() {
        return request;
    }

    public void setRequest( SmsRequest request ) {
        this.request = request;
    }

    @Override
    public Message createMessage( Session session ) throws JMSException {
        if (request == null){
            return null;
        }
        return session.createObjectMessage( request );
    }
}

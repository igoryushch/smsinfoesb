package ua.np.services.smsinfo;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;

import org.springframework.jms.core.JmsTemplate;

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


public class JmsQueueSender {

    private final JmsTemplate jmsTemplate;

    public JmsQueueSender(final JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void send(SmsRequest smsRequest) {
        jmsTemplate.convertAndSend(smsRequest);
    }
}

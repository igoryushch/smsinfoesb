package ua.np.services.smsinfo;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

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
public class LifeResponseProcessor implements Processor {

    @Override
    public void process( Exchange exchange ) throws Exception {
        System.out.println( "IN BODY = " + exchange.getIn().getBody(String.class));

    }
}

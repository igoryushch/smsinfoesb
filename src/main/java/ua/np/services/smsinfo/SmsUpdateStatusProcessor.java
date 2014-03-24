package ua.np.services.smsinfo;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.List;

/**
 * Copyright (C) 2014 Nova Poshta. All rights reserved.
 * http://novaposhta.ua/
 * <p/>
 * for internal use only!
 * <p/>
 * User: yushchenko.i
 * email: yushchenko.i@novaposhta.ua
 * Date: 24.03.2014
 */
public class SmsUpdateStatusProcessor implements Processor {

    private SmsServiceDao smsServiceDao;

    public void setSmsServiceDao(SmsServiceDao smsServiceDao) {
        this.smsServiceDao = smsServiceDao;
    }

    @Override
    public void process( Exchange exchange ) throws Exception {
        List<SmsRequest> smsRequestList = exchange.getIn().getBody(List.class);
        smsServiceDao.updateStatuses( smsRequestList );
    }
}

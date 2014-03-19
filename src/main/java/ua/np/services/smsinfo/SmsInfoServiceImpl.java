package ua.np.services.smsinfo;

import java.util.List;

import javax.jws.WebService;

/**
 * Copyright (C) 2014 Nova Poshta. All rights reserved.
 * http://novaposhta.ua/
 * <p/>
 * for internal use only!
 * <p/>
 * User: yushchenko.i
 * email: yushchenko.i@novaposhta.ua
 * Date: 10.01.14
 */

@WebService(endpointInterface = "ua.np.services.smsinfo.SmsInfoService", portName = "SmsInfoPort")
public class SmsInfoServiceImpl implements SmsInfoService {
    
    private SmsServiceDao smsServiceDao;
    private SmsServiceUtils smsServiceUtils;
    
    public void setSmsServiceDao(SmsServiceDao smsServiceDao) {
        this.smsServiceDao = smsServiceDao;
    }
    
    public void setSmsServiceUtils(SmsServiceUtils smsServiceUtils) {
        this.smsServiceUtils = smsServiceUtils;
    }

    @Override
    public String sendMessages( String xml, String systemName ) {
	List<SmsRequest> smsRequests = smsServiceUtils.getRequestsFromXmlString( xml, systemName );
	smsServiceDao.addRequests(smsRequests);
	return smsServiceUtils.buildAcceptedResponse( smsRequests );
    }


    @Override
    public String reportDeliveryData( String systemName ) {
        return "Report Data";
    }

    @Override
    public void updateStatuses( SmsRequestListWrapper requestList ) {

    }
}
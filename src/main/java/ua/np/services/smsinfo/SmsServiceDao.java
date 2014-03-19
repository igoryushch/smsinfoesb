package ua.np.services.smsinfo;

import java.util.List;

public interface SmsServiceDao {

    public List<SmsRequest> addRequests(final List <SmsRequest> requests);
    
}

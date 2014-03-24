package ua.np.services.smsinfo;

import java.util.List;

public interface SmsServiceDao {

    List<SmsRequest> addRequests(final List <SmsRequest> requests);
    void updateStatuses(List<SmsRequest> requestList);
}

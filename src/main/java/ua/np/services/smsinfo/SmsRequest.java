package ua.np.services.smsinfo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 * Copyright (C) 2014 Nova Poshta. All rights reserved.
 * http://novaposhta.ua/
 * <p/>
 * for internal use only!
 * <p/>
 * User: yushchenko.i
 * email: yushchenko.i@novaposhta.ua
 * Date: 09.01.14
 */

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SmsRequest implements Serializable {

    private Long smsRequestId;
    private String incomingId;
    private String systemName;
    private String phoneNumber;
    private String messageText;
    private Date creationDate;
    private Date updateDate;
    private String status;
    private Operator operator;
    private String operatorMessageId;

    public SmsRequest() {
    }

    public SmsRequest( String incomingId, String systemName, String phoneNumber, String messageText, Date creationDate, Date updateDate, String status, Operator operator, String operatorMessageId ) {
        this.incomingId = incomingId;
        this.systemName = systemName;
        this.phoneNumber = phoneNumber;
        this.messageText = messageText;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
        this.status = status;
        this.operator = operator;
        this.operatorMessageId = operatorMessageId;
    }

    public Long getSmsRequestId() {
        return smsRequestId;
    }

    public void setSmsRequestId( Long id ) {
        this.smsRequestId = id;
    }

    public String getIncomingId() {
        return incomingId;
    }

    public void setIncomingId( String incomingId ) {
        this.incomingId = incomingId;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName( String systemName ) {
        this.systemName = systemName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber( String phoneNumber ) {
        if( phoneNumber.length() == 10 )
            this.phoneNumber = "38" + phoneNumber;
        else
            this.phoneNumber = phoneNumber;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText( String messageText ) {
        this.messageText = messageText;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate( Date creationDate ) {
        this.creationDate = creationDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate( Date updateDate ) {
        this.updateDate = updateDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus( String status ) {
        this.status = status;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator( Operator operator ) {
        this.operator = operator;
    }

    public String getOperatorMessageId() {
        return operatorMessageId;
    }

    public void setOperatorMessageId( String operatorMessageId ) {
        this.operatorMessageId = operatorMessageId;
    }
}
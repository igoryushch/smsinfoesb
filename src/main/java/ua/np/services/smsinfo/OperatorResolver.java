package ua.np.services.smsinfo;

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

public class OperatorResolver {

    private OperatorDao operatorDao;
    private List<Operator> operators;
    private Operator defaultOperator;
    private SmsServiceUtils smsServiceUtils;

    public Operator resolveOperator( String phoneCode ) {
        if( operators == null ) readOperators();

        for( Operator operator : operators ) {
            for(String code : operator.getPhoneCodeMaping()){
                if( code.equals( phoneCode ) )
                    return operator;
            }
        }
        return getDefaultOperator();
    }

    public Operator getDefaultOperator(){
        if( operators == null )
            readOperators();
        if( this.defaultOperator == null ) {

            for( Operator operator : operators ) {
                for( String code : operator.getPhoneCodeMaping() ) {
                    if( "000".equals( code ) )
                        this.defaultOperator = operator;
                        return operator;
                }
            }
        }
        return this.defaultOperator;
    }

    public void readOperators(){
        this.operators = this.operatorDao.findAll();
    }

    public void setOperatorDao( OperatorDao operatorDao ) {
        this.operatorDao = operatorDao;
    }

    public void setSmsServiceUtils(SmsServiceUtils smsServiceUtils) {
        this.smsServiceUtils = smsServiceUtils;
    }

    public SmsRequestListWrapper setOperator(List<SmsRequest> requestList){
        for(SmsRequest request : requestList){
            Operator operator = resolveOperator( smsServiceUtils.getPhoneCodeFromNumber( request.getPhoneNumber() ) );
            request.setOperator( operator );
        }

        SmsRequestListWrapper requestListWrapper = new SmsRequestListWrapper(  );
        requestListWrapper.setRequestList( requestList );

        return requestListWrapper;
    }

}

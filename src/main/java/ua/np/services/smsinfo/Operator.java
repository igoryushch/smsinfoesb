package ua.np.services.smsinfo;

import java.util.HashSet;
import java.util.Set;

/**
 * Copyright (C) 2013 Nova Poshta. All rights reserved.
 * http://novaposhta.ua/
 * <p/>
 * for internal use only!
 * <p/>
 * User: yushchenko.i
 * email: yushchenko.i@novaposhta.ua
 * Date: 26.12.13
 */

public class Operator {
    private Long operatorId;
    private String name;
    private Set<String> phoneCodes;

    public Operator() {
    }

    public Operator( String name, Set<String> phoneCodes ) {
        this.name = name;
        this.phoneCodes = phoneCodes;
    }

    public Operator( String name ) {
        this.name = name;
        this.phoneCodes = new HashSet<>(  );
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId( Long id ) {
        this.operatorId = id;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public Set<String> getPhoneCodeMaping() {
        return phoneCodes;
    }

    public void setPhoneCodeMaping( Set<String> phoneCodes ) {
        this.phoneCodes = phoneCodes;
    }

}

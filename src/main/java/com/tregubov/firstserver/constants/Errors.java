package com.tregubov.firstserver.constants;

public class Errors {

    // account _________________________________________________________________________________________________________

    public static final int ACCOUNT_ALREADY_EXISTS = 2001;
    public static final int ACCOUNT_NOT_EXISTS = 2002;
    public static final int ACCOUNT_REGISTRATION_FAILED = 2003;
    public static final int ACCOUNT_LOGGING_FAILED = 2004;
    public static final int INCORRECT_PASSWORD = 2005;

    // order ___________________________________________________________________________________________________________

    public static final int PRODUCT_NOT_EXISTS = 3001;
    public static final int PRODUCT_NOT_AVAILABLE = 3002;
    public static final int PAYMENT_FAILED = 3003;
    public static final int PROMOCODE_NOT_ACTIVE = 3004;
    public static final int PROMOCODE_NOT_EXISTS = 3005;
    public static final int PROMOCODE_ALREADY_USED = 3006;
    public static final int ORDER_STATUS_NOT_EXISTS = 3007;

}

package com.team17.bikeworld.common;

public class CoreConstant {

    public static final String API_EVENT = "/event";

    //API Response code
    public static final int STATUS_CODE_SERVER_ERROR = -1;
    public static final int STATUS_CODE_FAIL = 0;
    public static final int STATUS_CODE_SUCCESS = 1;
    public static final int STATUS_CODE_NO_RESULT = 2;

    public static final String MESSAGE_SUCCESS = "Success!";
    public static final String MESSAGE_FAIL = "Fail!";
    public static final String MESSAGE_SERVER_ERROR = "Server error!";
    public static final String MESSAGE_NO_RESULT = "No result!";

    //Event Status
    public static final int STATUS_EVENT_PENDING = 0;
    public static final int STATUS_EVENT_NOT_APPROVED =1;
    public static final int STATUS_EVENT_APPRROVED = 2;
    public static final int STATUS_EVENT_PROCESSING = 3;
    public static final int STATUS_EVENT_ONGOING = 4;
    public static final int STATUS_EVENT_CANCELED= 5;
    public static final int STATUS_EVENT_END = 6;
}

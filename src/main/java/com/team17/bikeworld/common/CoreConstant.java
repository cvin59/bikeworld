package com.team17.bikeworld.common;

public class CoreConstant {

    public static final String API_EVENT = "/api/event";
    public static final String API_PROPOSAL_EVENT = "/api/proposal-event";

    //API Response code
    public static final int STATUS_CODE_SERVER_ERROR = -1;
    public static final int STATUS_CODE_FAIL = 0;
    public static final int STATUS_CODE_SUCCESS = 1;
    public static final int STATUS_CODE_NO_RESULT = 2;

    public static final String MESSAGE_SUCCESS = "Success!";
    public static final String MESSAGE_FAIL = "Fail!";
    public static final String MESSAGE_SERVER_ERROR = "Server error!";
    public static final String MESSAGE_NO_RESULT = "No result!";

    //Proposal Event Status
    public static final int STATUS_PROPOSALEVENT_NOT_APPROVED =0;
    public static final int STATUS_PROPOSALEVENT_APPRROVED = 1;

    //Event Status
    public static final int STATUS_EVENT_PROCESSING = 1;
    public static final int STATUS_EVENT_ONGOING = 2;
    public static final int STATUS_EVENT_CANCELED= 3;
    public static final int STATUS_EVENT_END = 4;

    //thidu sua file gi do
}

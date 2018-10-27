package com.team17.bikeworld.common;

import java.nio.file.Path;
import java.nio.file.Paths;

public class CoreConstant {

    public static final String API_EVENT = "/api/event";
    public static final String API_PROPOSAL_EVENT = "/api/proposal-event";
    public static final String API_PRODUCT = "/api/product";
    public static final String API_CRAWL = "/api/crawl";
    public static final String API_EVENT_IMAGE = "/api/event-image";
    public static final String API_COMMON = "/api/common";

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
    public static final int STATUS_PROPOSALEVENT_NOT_APPROVED = 2;
    public static final int STATUS_PROPOSALEVENT_APPRROVED = 1;
    public static final int STATUS_PROPOSALEVENT_PENDING = 1;

    //Event Status
    public static final int STATUS_EVENT_PENDING = 1;
    public static final int STATUS_EVENT_REGISTERING = 2;
    public static final int STATUS_EVENT_ONGOING = 3;
    public static final int STATUS_EVENT_END = 4;
    public static final int STATUS_EVENT_CANCELED = 5;

    //Product Status
    public static final int STATUS_PRODUCT_AVAILABLE = 1;

    //Role
    public static final String MEMBER = "MEMBER";
    public static final String ADMIN = "ADMIN";

    public static final Path rootLocation = Paths.get("src/main/resources/static/images").toAbsolutePath().normalize();

}

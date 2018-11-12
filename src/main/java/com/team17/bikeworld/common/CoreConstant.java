package com.team17.bikeworld.common;

import java.nio.file.Path;
import java.nio.file.Paths;

public class CoreConstant {

    public static final String API_EVENT = "/api/event";
    public static final String API_PROPOSAL_EVENT = "/api/proposal-event";
    public static final String API_PRODUCT = "/api/product";
    public static final String API_CRAWL = "/api/crawl";
    public static final String API_CRAWL_IMAGE = "/api/crawl-image";
    public static final String API_EVENT_IMAGE = "/api/event-image";
    public static final String API_PROPOSAL_EVENT_IMAGE = "/api/proposal-event-image";
    public static final String API_COMMON = "/api/common";
    public static final String API_PARTICIPANT = "/api/participant";
    public static final String API_EVENT_RATING = "/api/event-rating";
    public static final String API_ACCOUNT = "/api/account";
    public static final String API_ORDER = "/api/order";

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
    public static final int STATUS_PROPOSALEVENT_NOT_APPROVED = 3;
    public static final int STATUS_PROPOSALEVENT_APPRROVED = 2;
    public static final int STATUS_PROPOSALEVENT_PENDING = 1;

    //Event Status
    public static final int STATUS_EVENT_INACTIVE = 1;
    public static final int STATUS_EVENT_ACTIVE = 2;
    public static final int STATUS_EVENT_FINISH = 3;
    public static final int STATUS_EVENT_CANCELED = 4;

    //Product Status
    public static final int STATUS_PRODUCT_AVAILABLE = 1;
    public static final int STATUS_PRODUCT_SOLDOUT = 2;
    public static final int STATUS_PRODUCT_HIDDEN = 3;
    public static final int STATUS_PRODUCT_BANNED = 4;

    //Order Status
    public static final int STATUS_ORDER_PENDING = 1;
    public static final int STATUS_ORDER_SUCCESS = 2;
    public static final int STATUS_ORDER_CANCEL = 3;
    public static final int STATUS_ORDER_REJECT = 4;

    //Role
    public static final String MEMBER = "MEMBER";
    public static final String ADMIN = "ADMIN";

    public static final Path rootLocation = Paths.get("src/main/resources/static/images").toAbsolutePath().normalize();

    // Data for search nearby
    public static final double MIN_RADIUS = 0.0005;
    public static final double MAX_RADIUS = 0.5;
    public static final double LIMIT_SHOPS = 10;

    //Relevant Data
    public static final int MAX_RELEVANT_PRODUCT = 4;
}

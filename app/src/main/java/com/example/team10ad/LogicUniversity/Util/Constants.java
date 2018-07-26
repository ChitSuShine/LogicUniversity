package com.example.team10ad.LogicUniversity.Util;

public class Constants {
    // Constants for API Requests
    public static final String GRANT_TYPE = "password";
    public static final String API_BASE_URL = "http://lussisadteam10api.azurewebsites.net/api/";
    public static final String BEARER = "bearer ";
    public static final String KEY_ACCESS_TOKEN = "access_token";
    public static final String AUTHORIZATION = "Authorization";
    public static final String USER_GSON = "user";
    public static final String REJECT_GSON = "reject";
    public static final String OK = "OK";
    public static final int TEN = 10;

    // Constants for user roles
    public static final int CLERK_ROLE = 1;
    public static final int HOD_ROLE = 4;
    public static final int EMP_ROLE = 5;
    public static final int DEP_REP_ROLE = 6;
    public static final int TEMP_HOD = 7;

    // Constants for Error Messages
    public static final String INVALID_INFO = "Invalid username and password!";
    public static final String NETWORK_ERROR_MSG = "Cannot connect the network!";
    public static final String REQ_NO_SUCCESS = "Request is not successful!";

    // Constants for LoginActivity
    public static final String WARNING_MSG = "Warning Message";

    // Constants for AssignDepRepFragment
    public static final String ASSIGN_DEP_REP = "Assign Department Representative";
    public static final String ASSIGN_WARNING_MSG = "This is current representative and you cannot assign.";
    public static final String ASSIGN_CONFIRM_MSG = "Are you sure to assign authority?";
    public static final String ASSIGN_SUCCESS_MSG = "Your assign is successful.";

    // Constants for DelegateAuthorityFragment
    public static final String DELEGATE_AUTHORITY = "Delegate Authority";
    public static final String DELEGATE_CONFIRM_MSG = "Are you sure to remove authority?";
    public static final String DELEGATE_WARNING_MSG = "There is no employee to cancel authority.";
    public static final String DELEGATE_SUCCESS_MSG = "Your delegation is successful.";
    public static final String NO_DELEGATION = "There is no previous delegation.";
    public static final String DELEGATED_USER ="delegatedUser";

    // Constants for RepScanQRFragment
    public static final String REP_RES_NOT_FOUND = "Result Not Found!";
    public static final String REP_SCAN_ERROR_MSG = "Scan Error ";
    public static final String REP_COLLECTED_MSG = "Delivery is collected.";
    public static final String REP_MSG = "Collect Delivery";
    public static final String REP_OUTSTANDING_MSG = "Delivery is partially collected.";
    public static final String REP_COMPLETE_MSG = "Delivery is already collected.";
    public static final int REP_DELIVER = 4;
    public static final int REP_OUTSTANDING = 5;
    public static final int REP_COMPLETE = 6;

    //Constants for InventoryFragment
    public static final String INVENTORY_SUCCESS_MSG = "You have successfully updated inventory.";
    public static final String[] STATUS = {
            "Pending",
            "Approved",
            "Request Pending",
            "Preparing",
            "Delivered",
            "Outstanding",
            "Complete",
            "Reject"
    };
}

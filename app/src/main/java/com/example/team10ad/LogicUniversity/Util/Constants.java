package com.example.team10ad.LogicUniversity.Util;

public class Constants {
    // Constants for Requests
    public static final String GRANT_TYPE = "password";
    public static final String API_BASE_URL = "http://lussisadteam10api.azurewebsites.net/api/";
    public static final String BEARER = "bearer ";
    public static final String KEY_ACCESS_TOKEN = "access_token";
    public static final String AUTHORIZATION = "Authorization";
    public static final String USER_GSON = "user";
    public static final String OK = "OK";
    public static final int TEN = 10;

    // Constants for user roles
    public static final int CLERK_ROLE = 1;
    public static final int HOD_ROLE = 4;

    // Constants for Error Messages
    public static final String INVALID_INFO = "Invalid username and password!";
    public static final String NETWORK_ERROR_MSG = "Cannot connect the network!";
    public static final String REQ_NO_SUCCESS = "Request is not successful!";

    // Constants for LoginActivity
    public static final String WARNING_MSG = "Warning Message";

    // Constants for DelegateAuthorityFragment
    public static final String DELEGATE_AUTHORITY = "Delegate Authority";
    public static final String DELEGATE_CONFIRM_MSG = "Are you sure to remove authority?";
    public static final String DELEGATE_WARNING_MSG = "There is no employee to cancel authority.";
    public static final String NO_DELEGATION = "There is no previous delegation.";
    public static final String DELEGATED_USER ="delegatedUser";

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

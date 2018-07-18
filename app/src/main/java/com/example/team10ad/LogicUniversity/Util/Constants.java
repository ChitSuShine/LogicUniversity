package com.example.team10ad.LogicUniversity.Util;

public class Constants {

    // Chit Su Shine
    // Constants for Requests
    public static final String GRANT_TYPE = "password";
    public static final String API_BASE_URL = "http://lussisadteam10api.azurewebsites.net/api/";
    public static final String BEARER = "bearer ";
    public static final String KEY_ACCESS_TOKEN = "access_token";
    public static final String AUTHORIZATION = "Authorization";
    // Constants for Error Messages
    public static final String NETWORK_ERROR_MSG = "Cannot connect the network!";
    public static final String INVALID_INFO = "Invalid username and password!";
    public static final String REQ_NO_SUCCESS = "Request is not successful!";

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

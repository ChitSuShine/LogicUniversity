package com.example.team10ad.LogicUniversity.Service;

import com.example.team10ad.LogicUniversity.Util.Constants;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;

public class AuthenticationInterceptor implements Interceptor{
    private String authToken;

    public AuthenticationInterceptor(String token) {
        this.authToken = token;
    }

    @Override
    public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
        Request original = chain.request();

        Request.Builder builder = original.newBuilder()
                .header(Constants.AUTHORIZATION, authToken);

        Request request = builder.build();
        return chain.proceed(request);
    }
}

package com.nusiss.team10ad.LogicUniversity.Common;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.nusiss.team10ad.LogicUniversity.Model.AccessToken;
import com.nusiss.team10ad.LogicUniversity.Model.User;
import com.nusiss.team10ad.LogicUniversity.Service.AccessTokenService;
import com.nusiss.team10ad.LogicUniversity.Util.Constants;
import com.nusiss.team10ad.LogicUniversity.Util.MyApp;
import com.nusiss.team10ad.team10ad.R;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// Author: Chit Su Shine
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Getting current user's info & store in shared preferences
        Gson gson = new Gson();
        String json = MyApp.getInstance().getPreferenceManager().getString(Constants.USER_GSON);
        final User user = gson.fromJson(json, User.class);
        if(user!=null)
        {
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
        }

        AppCompatButton btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                EditText userName = findViewById(R.id.input_username);
                EditText password = findViewById(R.id.input_password);
                // To get Access Token for each user
                getAccessToken(userName.getText().toString(), password.getText().toString());
            }
        });
    }

    // Method for getting access token
    private void getAccessToken(String userName, String password) {
        // Retrofit Setup for AccessTokenService
        Retrofit.Builder builder = new Retrofit.Builder().
                baseUrl(Constants.API_BASE_URL).
                addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        AccessTokenService accessTokenService = retrofit.create(AccessTokenService.class);

        Call<AccessToken> call = accessTokenService.getAccessToken(userName, password, Constants.GRANT_TYPE);
        call.enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                if (response.isSuccessful()) {
                    MyApp.getInstance().getPreferenceManager().putString(Constants.KEY_ACCESS_TOKEN, response.body().getAccessToken());
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);
                } else {
                    new android.support.v7.app.AlertDialog.Builder(LoginActivity.this)
                            .setTitle(Constants.WARNING_MSG)
                            .setMessage(Constants.INVALID_INFO)
                            .setPositiveButton(Constants.OK, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).setIcon(R.drawable.alert)
                            .show();
                }
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {
                Toast.makeText(LoginActivity.this, Constants.NETWORK_ERROR_MSG, Toast.LENGTH_SHORT).show();
            }
        });
    }
}

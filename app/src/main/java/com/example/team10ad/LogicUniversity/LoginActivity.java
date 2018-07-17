package com.example.team10ad.LogicUniversity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.team10ad.LogicUniversity.Model.AccessToken;
import com.example.team10ad.LogicUniversity.Service.UserService;
import com.example.team10ad.LogicUniversity.Util.Constants;
import com.example.team10ad.team10ad.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {


    // Retrofit Setup
    Retrofit.Builder builder = new Retrofit.Builder().
            baseUrl("http://lussisadteam10api.azurewebsites.net/api/").
            addConverterFactory(GsonConverterFactory.create());
    Retrofit retrofit = builder.build();
    UserService userService = retrofit.create(UserService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        AppCompatButton btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                EditText userName = findViewById(R.id.input_username);
                EditText password = findViewById(R.id.input_password);

                // To get Access Token for each user
                getAccessToken(userName.getText().toString(),password.getText().toString());


                Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    // Method for getting access token
    private void getAccessToken(String userName, String password)
    {
        Call<AccessToken> call = userService.getAccessToken(userName, password, Constants.GRANT_TYPE);
        call.enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, response.body().getAccessToken().toString(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Invalid username and password!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "FAILURE!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

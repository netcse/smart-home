package com.example.shuvo.smarthome;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.changeToLogin){
            startActivity(new Intent(SignupActivity.this,MainActivity.class));
        }
    }

    public void signupUser(View view){
        TextView mobile = findViewById(R.id.mobileEditText);
        TextView email = findViewById(R.id.emailEditText);
        TextView password = findViewById(R.id.passwordEditText);
        Spinner userTypes = findViewById(R.id.userTypeSpinner);
        //Toast.makeText(this,"User Type"+userTypes.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();


        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        Log.v("Email: ",email.getText().toString());
        Log.v("phone_no: ",mobile.getText().toString());
        Log.v("password: ",password.getText().toString());
        Log.v("userType: ",userTypes.getSelectedItem().toString().toLowerCase());
        RequestBody body = RequestBody.create(mediaType, "{\n\t\"email\":\""+email.getText().toString()+"\",\n\t\"phone_no\":\""+mobile.getText().toString()+"\",\n\t\"password\":\""+password.getText().toString()+"\",\n\t\"role\":\""+userTypes.getSelectedItem().toString().toLowerCase()+"\"\n}");
        Request request = new Request.Builder()
                .url("http://45.125.222.120:5000/user")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Postman-Token", "85ae3696-4eac-4cc5-abe0-b6ebcba90440")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();

                    SignupActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                                Log.v("response",myResponse);
                                if(myResponse.equals("1")){
                                    Toast.makeText(SignupActivity.this,"User Saved",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(SignupActivity.this,MainActivity.class));
                                }
                                else
                                {
                                    Toast.makeText(SignupActivity.this,"User is not Saved",Toast.LENGTH_SHORT).show();
                                }
                                /*JSONObject obj = new JSONObject(myResponse);
                                Log.v("response",""+obj.getJSONObject("result").getString("status"));
                                if(obj.getJSONObject("result").getString("status").equals("success")) {
                                    Log.v("response","success");
                                    startActivity(new Intent(SignupActivity.this,MainActivity.class));
                                }*/
                        }
                    });
                }
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Spinner dropdown = findViewById(R.id.userTypeSpinner);
        String[] items = new String[]{"User","Admin"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        TextView changeToLogin = (TextView) findViewById(R.id.changeToLogin);
        changeToLogin.setOnClickListener(this);
    }
}

package com.example.shuvo.smarthome;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.EditText;
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

import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.changeToSignup) {
            startActivity(new Intent(MainActivity.this,SignupActivity.class));
        }
    }

    public void userLogin(View view){
        TextView mobileEditText = (TextView) findViewById(R.id.mobileEditText);
        String mobile = mobileEditText.getText().toString();
        TextView passwordEditText = (TextView) findViewById(R.id.passwordEditText);
        String password = passwordEditText.getText().toString();

        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\n\t\"username\":\""+mobile+"\",\n\t\"password\":\""+password+"\"\n}");
        Request request = new Request.Builder()
                .url("http://45.125.222.120:5000/auth/login")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Postman-Token", "e18ffd03-a5da-47fe-99ae-13b0544e2283")
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

                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Log.v("response",myResponse);
                                JSONObject obj = new JSONObject(myResponse);
                                Log.v("response",""+obj.getJSONObject("result").getString("user_id"));
                                if(obj.getJSONObject("result").getString("status").equals("success")) {
                                    String loginInfo = "loginInfo";
                                    SharedPreferences sharedpreferences = getSharedPreferences(loginInfo, Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                    editor.putString("role",obj.getJSONObject("result").getString("role"));
                                    editor.putString("user_id",obj.getJSONObject("result").getString("user_id"));
                                    editor.commit();
                                    MqttHelper helper = MqttHelper.getInstance(getApplicationContext()).connect();

                                    startActivity(new Intent(MainActivity.this,home.class));
                                    finish();

                                }
                                else{
                                    Toast.makeText(getApplicationContext(),"Wrong username or password",Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String loginInfo = "loginInfo";


        SharedPreferences settings = getSharedPreferences(loginInfo, Context.MODE_PRIVATE);
        String user_id=settings.getString("user_id", "-1");

        TextView changeToSignup = (TextView) findViewById(R.id.changeToSignup);
        changeToSignup.setOnClickListener(this);
        Toast.makeText(this,user_id,Toast.LENGTH_SHORT).show();
        if(!user_id.equals("-1"))
        {
            MqttHelper helper = MqttHelper.getInstance(getApplicationContext()).connect();
            startActivity(new Intent(MainActivity.this,home.class));
        }
    }
}

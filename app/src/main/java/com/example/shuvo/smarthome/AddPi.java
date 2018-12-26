package com.example.shuvo.smarthome;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
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

public class AddPi extends AppCompatActivity {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;

    public void addRasberryPi(View view){

        TextView piMacEditText = findViewById(R.id.piMacEditText);
        TextView piIpEditText = findViewById(R.id.piIpEditText);

        String loginInfo = "loginInfo";


        SharedPreferences settings = getSharedPreferences(loginInfo, Context.MODE_PRIVATE);
        String user_id=settings.getString("user_id", "-1");

        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\n\t\"mac\":\""+piMacEditText.getText().toString()+"\",\n\t\"ip\":\""+piIpEditText.getText().toString()+"\",\n\t\"user_id\":\""+user_id+"\"\n}");
        Request request = new Request.Builder()
                .url("http://45.125.222.120:5000/raspberrypi")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Postman-Token", "27c76455-9ff8-4680-9179-7e5422e7bc26")
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

                    Toast.makeText(AddPi.this,response.body().string(),Toast.LENGTH_SHORT).show();
                    Log.v("response",response.body().string());
                    AddPi.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                                Log.v("response",myResponse);

                                if(myResponse.equals("1")) {
                                    Toast.makeText(AddPi.this,"Raspberry pi saved",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(AddPi.this,home.class));
                                }
                                else{
                                    Toast.makeText(AddPi.this,"Error Occured!!",Toast.LENGTH_SHORT).show();
                                }

                        }
                    });
                }
            }
        });


    }

    private void setUpToolbar(){
        drawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        actionBarDrawerToggle.syncState();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pi);
        setUpToolbar();
        navigationView = (NavigationView)findViewById(R.id.navigation_menu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_dashboard:
                        startActivity(new Intent(AddPi.this,home.class));
                        break;
                    case R.id.nav_add_pi:
                        startActivity(new Intent(AddPi.this,AddPi.class));
                        break;
                    case R.id.nav_appliances:
                        startActivity(new Intent(AddPi.this,Appliances.class));
                        break;
                    case R.id.nav_logout:
                        SharedPreferences sharedPreferences = AddPi.this.getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.commit();
                        startActivity(new Intent(AddPi.this,MainActivity.class));
                        break;

                }
                return false;
            }
        });
    }
}

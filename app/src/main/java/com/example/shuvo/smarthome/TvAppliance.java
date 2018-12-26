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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

public class TvAppliance extends AppCompatActivity {

    DrawerLayout drawerLayout;
    String typeApp;
    String pin;
    String mac;
    String remote;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    JSONArray objApp = null;

    private void setUpToolbar(){
        drawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        actionBarDrawerToggle.syncState();
    }

    public void powerButton(View view){
        String payload = "{\"applianceType\": \"" + typeApp + "\" , \"operation\": \"POWER\", \"pin\": " + pin + " ,\"signalType\":\"IR\" , \"data\": "+getData("POWER")+"}\n";
        MqttHelper helper = MqttHelper.getInstance(getApplicationContext()).connect();
        Log.v("response", payload+mac);
        helper.publish(mac, payload);
    }
    public void keyOneButton(View view){
        String payload = "{\"applianceType\": \"" + typeApp + "\" , \"operation\": \"1\", \"pin\": " + pin + " ,\"signalType\":\"IR\" , \"data\": "+getData("1")+"}\n";
        MqttHelper helper = MqttHelper.getInstance(getApplicationContext()).connect();
        Log.v("response", payload+mac);
        helper.publish(mac, payload);
    }
    public void keyTwoButton(View view){
        String payload = "{\"applianceType\": \"" + typeApp + "\" , \"operation\": \"2\", \"pin\": " + pin + " ,\"signalType\":\"IR\" , \"data\": "+getData("2")+"}\n";
        MqttHelper helper = MqttHelper.getInstance(getApplicationContext()).connect();
        Log.v("response", payload+mac);
        helper.publish(mac, payload);
    }
    public void keyThreeButton(View view){
        String payload = "{\"applianceType\": \"" + typeApp + "\" , \"operation\": \"3\", \"pin\": " + pin + " ,\"signalType\":\"IR\" , \"data\": "+getData("3")+"}\n";
        MqttHelper helper = MqttHelper.getInstance(getApplicationContext()).connect();
        Log.v("response", payload+mac);
        helper.publish(mac, payload);
    }
    public void keyFourButton(View view){
        String payload = "{\"applianceType\": \"" + typeApp + "\" , \"operation\": \"4\", \"pin\": " + pin + " ,\"signalType\":\"IR\" , \"data\": "+getData("4")+"}\n";
        MqttHelper helper = MqttHelper.getInstance(getApplicationContext()).connect();
        Log.v("response", payload+mac);
        helper.publish(mac, payload);
    }
    public void keyFiveButton(View view){
        String payload = "{\"applianceType\": \"" + typeApp + "\" , \"operation\": \"5\", \"pin\": " + pin + " ,\"signalType\":\"IR\" , \"data\": "+getData("5")+"}\n";
        MqttHelper helper = MqttHelper.getInstance(getApplicationContext()).connect();
        Log.v("response", payload+mac);
        helper.publish(mac, payload);
    }
    public void keySixButton(View view){
        String payload = "{\"applianceType\": \"" + typeApp + "\" , \"operation\": \"6\", \"pin\": " + pin + " ,\"signalType\":\"IR\" , \"data\": "+getData("6")+"}\n";
        MqttHelper helper = MqttHelper.getInstance(getApplicationContext()).connect();
        Log.v("response", payload+mac);
        helper.publish(mac, payload);
    }
    public void keySevenButton(View view){
        String payload = "{\"applianceType\": \"" + typeApp + "\" , \"operation\": \"7\", \"pin\": " + pin + " ,\"signalType\":\"IR\" , \"data\": "+getData("7")+"}\n";
        MqttHelper helper = MqttHelper.getInstance(getApplicationContext()).connect();
        Log.v("response", payload+mac);
        helper.publish(mac, payload);
    }
    public void keyEightButton(View view){
        String payload = "{\"applianceType\": \"" + typeApp + "\" , \"operation\": \"8\", \"pin\": " + pin + " ,\"signalType\":\"IR\" , \"data\": "+getData("8")+"}\n";
        MqttHelper helper = MqttHelper.getInstance(getApplicationContext()).connect();
        Log.v("response", payload+mac);
        helper.publish(mac, payload);
    }
    public void keyNineButton(View view){
        String payload = "{\"applianceType\": \"" + typeApp + "\" , \"operation\": \"9\", \"pin\": " + pin + " ,\"signalType\":\"IR\" , \"data\": "+getData("9")+"}\n";
        MqttHelper helper = MqttHelper.getInstance(getApplicationContext()).connect();
        Log.v("response", payload+mac);
        helper.publish(mac, payload);
    }
    public void keyZeroButton(View view){
        String payload = "{\"applianceType\": \"" + typeApp + "\" , \"operation\": \"0\", \"pin\": " + pin + " ,\"signalType\":\"IR\" , \"data\": "+getData("0")+"}\n";
        MqttHelper helper = MqttHelper.getInstance(getApplicationContext()).connect();
        Log.v("response", payload+mac);
        helper.publish(mac, payload);
    }
    public void volumeUpButton(View view){
        String payload = "{\"applianceType\": \"" + typeApp + "\" , \"operation\": \"VOLUME_UP\", \"pin\": " + pin + " ,\"signalType\":\"IR\" , \"data\": "+getData("VOLUME_UP")+"}\n";
        MqttHelper helper = MqttHelper.getInstance(getApplicationContext()).connect();
        Log.v("response", payload+mac);
        helper.publish(mac, payload);
    }
    public void volumeDownButton(View view){
        String payload = "{\"applianceType\": \"" + typeApp + "\" , \"operation\": \"VOLUME_DOWN\", \"pin\": " + pin + " ,\"signalType\":\"IR\" , \"data\": "+getData("VOLUME_DOWN")+"}\n";
        MqttHelper helper = MqttHelper.getInstance(getApplicationContext()).connect();
        Log.v("response", payload+mac);
        helper.publish(mac, payload);
    }
    public void channelUpButton(View view){
        String payload = "{\"applianceType\": \"" + typeApp + "\" , \"operation\": \"CHANNEL_UP\", \"pin\": " + pin + " ,\"signalType\":\"IR\" , \"data\": "+getData("CHANNEL_UP")+"}\n";
        MqttHelper helper = MqttHelper.getInstance(getApplicationContext()).connect();
        Log.v("response", payload+mac);
        helper.publish(mac, payload);
    }
    public void channelDownButton(View view){
        String payload = "{\"applianceType\": \"" + typeApp + "\" , \"operation\": \"CHANNEL_DOWN\", \"pin\": " + pin + " ,\"signalType\":\"IR\" , \"data\": "+getData("CHANNEL_DOWN")+"}\n";
        MqttHelper helper = MqttHelper.getInstance(getApplicationContext()).connect();
        Log.v("response", payload+mac);
        helper.publish(mac, payload);
    }
    public void muteButton(View view){
        String payload = "{\"applianceType\": \"" + typeApp + "\" , \"operation\": \"MUTE\", \"pin\": " + pin + " ,\"signalType\":\"IR\" , \"data\": "+getData("MUTE")+"}\n";
        MqttHelper helper = MqttHelper.getInstance(getApplicationContext()).connect();
        Log.v("response", payload+mac);
        helper.publish(mac, payload);
    }





    public String getData(String key)  {

        for(int i =0;i<objApp.length();i++)
        {
            try {
                if(objApp.getJSONObject(i).getString("key").equals(key))
                {
                    return objApp.getJSONObject(i).getString("value");

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_appliance);

        typeApp = getIntent().getStringExtra("type");
        mac = getIntent().getStringExtra("mac");
        pin = getIntent().getStringExtra("pin");
        remote = getIntent().getStringExtra("remote");

        try {
            objApp = new JSONArray(remote);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        setUpToolbar();
        String loginInfo = "loginInfo";


        SharedPreferences settings = getSharedPreferences(loginInfo, Context.MODE_PRIVATE);
        String user_id=settings.getString("user_id", "-1");

        navigationView = (NavigationView)findViewById(R.id.navigation_menu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_dashboard:
                        startActivity(new Intent(TvAppliance.this,home.class));
                        break;

                    case R.id.nav_add_pi:
                        startActivity(new Intent(TvAppliance.this,AddPi.class));
                        break;
                    case R.id.nav_add_appliance:
                        startActivity(new Intent(TvAppliance.this,AddAppliances.class));
                        break;
                    case R.id.nav_appliances:
                        startActivity(new Intent(TvAppliance.this,Appliances.class));
                        break;
                    case R.id.nav_logout:
                        SharedPreferences sharedPreferences = TvAppliance.this.getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.commit();
                        startActivity(new Intent(TvAppliance.this,MainActivity.class));
                        break;

                }
                return false;
            }
        });
    }
}

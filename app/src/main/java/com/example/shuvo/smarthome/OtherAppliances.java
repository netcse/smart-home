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

public class OtherAppliances extends AppCompatActivity {
    String typeApp;
    String pin;
    String mac;
    String remote;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;

    private void setUpToolbar(){
        drawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        actionBarDrawerToggle.syncState();
    }
    public void otherAppOn(View view){
        String payload = "{\"applianceType\": \"" + typeApp + "\" , \"operation\": \"On\", \"pin\": " + pin + " ,\"signalType\":\"NoIR\" , \"data\": []}\n";
        MqttHelper helper = MqttHelper.getInstance(getApplicationContext()).connect();
        Log.v("response", payload+mac);
        helper.publish(mac, payload);
    }
    public void otherAppOff(View view){
        String payload = "{\"applianceType\": \"" + typeApp + "\" , \"operation\": \"Off\", \"pin\": " + pin + " ,\"signalType\":\"NoIR\" , \"data\": []}\n";
        MqttHelper helper = MqttHelper.getInstance(getApplicationContext()).connect();
        Log.v("response", payload+mac);
        helper.publish(mac, payload);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_appliances);

        typeApp = getIntent().getStringExtra("type");
        mac = getIntent().getStringExtra("mac");
        pin = getIntent().getStringExtra("pin");
        remote = getIntent().getStringExtra("remote");

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
                        startActivity(new Intent(OtherAppliances.this,home.class));
                        break;

                    case R.id.nav_add_pi:
                        startActivity(new Intent(OtherAppliances.this,AddPi.class));
                        break;
                    case R.id.nav_add_appliance:
                        startActivity(new Intent(OtherAppliances.this,AddAppliances.class));
                        break;
                    case R.id.nav_appliances:
                        startActivity(new Intent(OtherAppliances.this,Appliances.class));
                        break;
                    case R.id.nav_logout:
                        SharedPreferences sharedPreferences = OtherAppliances.this.getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.commit();
                        startActivity(new Intent(OtherAppliances.this,MainActivity.class));
                        break;

                }
                return false;
            }
        });
    }
}

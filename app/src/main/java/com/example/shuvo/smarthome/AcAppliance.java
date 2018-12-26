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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.w3c.dom.Text;

public class AcAppliance extends AppCompatActivity {

    int Temperature = 18;
    String typeApp;
    String pin;
    String mac;
    String remote;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    JSONArray objApp = null;

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

    private void setUpToolbar(){
        drawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        actionBarDrawerToggle.syncState();
    }

    public void sleepButton(View view){
        String payload = "{\"applianceType\": \"" + typeApp + "\" , \"operation\": \"SLEEP\", \"pin\": " + pin + " ,\"signalType\":\"IR\" , \"data\": "+getData("SLEEP")+"}\n";
        MqttHelper helper = MqttHelper.getInstance(getApplicationContext()).connect();
        Log.v("response", payload+mac);
        helper.publish(mac, payload);

    }
    public void onButton(View view){
        String payload = "{\"applianceType\": \"" + typeApp + "\" , \"operation\": \"ON\", \"pin\": " + pin + " ,\"signalType\":\"IR\" , \"data\": "+getData("ON")+"}\n";
        MqttHelper helper = MqttHelper.getInstance(getApplicationContext()).connect();
        Log.v("response", payload+mac);
        helper.publish(mac, payload);

    }
    public void offButton(View view){
        String payload = "{\"applianceType\": \"" + typeApp + "\" , \"operation\": \"OFF\", \"pin\": " + pin + " ,\"signalType\":\"IR\" , \"data\": "+getData("OFF")+"}\n";
        MqttHelper helper = MqttHelper.getInstance(getApplicationContext()).connect();
        Log.v("response", payload+mac);
        helper.publish(mac, payload);
    }
    public void horSwingButton(View view){
        String payload = "{\"applianceType\": \"" + typeApp + "\" , \"operation\": \"HORSWING\", \"pin\": " + pin + " ,\"signalType\":\"IR\" , \"data\": "+getData("HOR_SWING")+"}\n";
        MqttHelper helper = MqttHelper.getInstance(getApplicationContext()).connect();
        Log.v("response", payload+mac);
        helper.publish(mac, payload);
    }
    public void verSwingButton(View view){
        String payload = "{\"applianceType\": \"" + typeApp + "\" , \"operation\": \"VERSWING\", \"pin\": " + pin + " ,\"signalType\":\"IR\" , \"data\": "+getData("VER_SWING")+"}\n";
        MqttHelper helper = MqttHelper.getInstance(getApplicationContext()).connect();
        Log.v("response", payload+mac);
        helper.publish(mac, payload);
    }
    public void coolButton(View view){
        String payload = "{\"applianceType\": \"" + typeApp + "\" , \"operation\": \"MODE_COOL\", \"pin\": " + pin + " ,\"signalType\":\"IR\" , \"data\": "+getData("MODE_COOL")+"}\n";
        MqttHelper helper = MqttHelper.getInstance(getApplicationContext()).connect();
        Log.v("response", payload+mac);
        helper.publish(mac, payload);
    }
    public void dryButton(View view){
        String payload = "{\"applianceType\": \"" + typeApp + "\" , \"operation\": \"MODE_DRY\", \"pin\": " + pin + " ,\"signalType\":\"IR\" , \"data\": "+getData("MODE_DRY")+"}\n";
        MqttHelper helper = MqttHelper.getInstance(getApplicationContext()).connect();
        Log.v("response", payload+mac);
        helper.publish(mac, payload);
    }
    public void fanButton(View view){
        String payload = "{\"applianceType\": \"" + typeApp + "\" , \"operation\": \"MODE_FAN\", \"pin\": " + pin + " ,\"signalType\":\"IR\" , \"data\": "+getData("MODE_FAN")+"}\n";
        MqttHelper helper = MqttHelper.getInstance(getApplicationContext()).connect();
        Log.v("response", payload+mac);
        helper.publish(mac, payload);
    }
    public void fanHighButton(View view){
        String payload = "{\"applianceType\": \"" + typeApp + "\" , \"operation\": \"FAN_HIGH\", \"pin\": " + pin + " ,\"signalType\":\"IR\" , \"data\": "+getData("FAN_HIGH")+"}\n";
        MqttHelper helper = MqttHelper.getInstance(getApplicationContext()).connect();
        Log.v("response", payload+mac);
        helper.publish(mac, payload);
    }
    public void fanMediumButton(View view){
        String payload = "{\"applianceType\": \"" + typeApp + "\" , \"operation\": \"FAN_MID\", \"pin\": " + pin + " ,\"signalType\":\"IR\" , \"data\": "+getData("FAN_MID")+"}\n";
        MqttHelper helper = MqttHelper.getInstance(getApplicationContext()).connect();
        Log.v("response", payload+mac);
        helper.publish(mac, payload);
    }
    public void fanLowButton(View view){
        String payload = "{\"applianceType\": \"" + typeApp + "\" , \"operation\": \"FAN_LOW\", \"pin\": " + pin + " ,\"signalType\":\"IR\" , \"data\": "+getData("FAN_LOW")+"}\n";
        MqttHelper helper = MqttHelper.getInstance(getApplicationContext()).connect();
        Log.v("response", payload+mac);
        helper.publish(mac, payload);
    }
    public void fanQuetButton(View view){
        String payload = "{\"applianceType\": \"" + typeApp + "\" , \"operation\": \"FAN_QUIET\", \"pin\": " + pin + " ,\"signalType\":\"IR\" , \"data\": "+getData("FAN_QUIET")+"}\n";
        MqttHelper helper = MqttHelper.getInstance(getApplicationContext()).connect();
        Log.v("response", payload+mac);
        helper.publish(mac, payload);
    }
    public void fanAutoButton(View view){
        String payload = "{\"applianceType\": \"" + typeApp + "\" , \"operation\": \"FAN_AUTO\", \"pin\": " + pin + " ,\"signalType\":\"IR\" , \"data\": "+getData("FAN_AUTO")+"}\n";
        MqttHelper helper = MqttHelper.getInstance(getApplicationContext()).connect();
        Log.v("response", payload+mac);
        helper.publish(mac, payload);
    }

    public void tempPlusButton(View view){
        if(Temperature< 30)
            Temperature++;
        String payload = "{\"applianceType\": \"" + typeApp + "\" , \"operation\": \"TEMP_"+Temperature+"\", \"pin\": " + pin +" ,\"signalType\":\"IR\" , \"data\": "+getData("TEMP_"+Temperature)+"}\n";
        MqttHelper helper = MqttHelper.getInstance(getApplicationContext()).connect();
        Log.v("response", payload+mac);
        helper.publish(mac, payload);
        EditText temp = findViewById(R.id.tempTextField);
        temp.setText(""+Temperature);
        Log.v("temp",""+Temperature);
    }

    public void tempMinusButton(View view){
        if(Temperature > 18)
            Temperature--;
        String payload = "{\"applianceType\": \"" + typeApp + "\" , \"operation\": \"TEMP_"+Temperature+"\", \"pin\": " + pin + " ,\"signalType\":\"IR\" , \"data\": \""+getData("ON")+"\"}\n";
        MqttHelper helper = MqttHelper.getInstance(getApplicationContext()).connect();
        Log.v("response", payload+mac);
        helper.publish(mac, payload);
        EditText temp = findViewById(R.id.tempTextField);
        temp.setText(""+Temperature);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ac_appliance);

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
                        startActivity(new Intent(AcAppliance.this,home.class));
                        break;

                    case R.id.nav_add_pi:
                        startActivity(new Intent(AcAppliance.this,AddPi.class));
                        break;
                    case R.id.nav_add_appliance:
                        startActivity(new Intent(AcAppliance.this,AddAppliances.class));
                        break;
                    case R.id.nav_appliances:
                        startActivity(new Intent(AcAppliance.this,Appliances.class));
                        break;
                    case R.id.nav_logout:
                        SharedPreferences sharedPreferences = AcAppliance.this.getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.commit();
                        startActivity(new Intent(AcAppliance.this,MainActivity.class));
                        break;

                }
                return false;
            }
        });
    }
}

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddAppliances extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;

    public void addAppliance(View view){
        Spinner piListSpinner = findViewById(R.id.piListAppSpinner);
        Spinner applianceTypeSpinner = findViewById(R.id.applianceTypeSpinner);
        Spinner applianceBrandSpinner = findViewById(R.id.applianceBrandSpinner);
        TextView applianceNameEditText = findViewById(R.id.applianceNameEditText);
        TextView pinNoEditText = findViewById(R.id.pinNoEditText);
        TextView streamURLEditText = findViewById(R.id.streamURLEditText);

        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\n\t\"raspberrypi_id\":\""+piListSpinner.getSelectedItem().toString()+"\",\n\t\"appliance\":\""+applianceNameEditText.getText().toString()+"\",\n\t\"type\":\""+applianceTypeSpinner.getSelectedItem().toString()+"\",\n\t\"pin\":\""+pinNoEditText.getText().toString()+"\",\n\t\"url\":\""+streamURLEditText.getText().toString()+"\",\n\t\"brand\":\""+applianceBrandSpinner.getSelectedItem().toString()+"\"\n}");
        Request request = new Request.Builder()
                .url("http://45.125.222.120:5000/appliance")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("cache-control", "no-cache")
                .addHeader("Postman-Token", "60b09ead-0306-4221-87f4-ecd05627878e")
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

                    AddAppliances.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.v("response",myResponse);
                            if(myResponse.equals("1")){
                                Toast.makeText(getApplicationContext(),"Appliance Saved",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(AddAppliances.this,home.class));
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
        setContentView(R.layout.activity_add_appliances);
        setUpToolbar();

        String loginInfo = "loginInfo";

        navigationView = (NavigationView)findViewById(R.id.navigation_menu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_dashboard:
                        startActivity(new Intent(AddAppliances.this,home.class));
                        break;

                    case R.id.nav_add_pi:
                        startActivity(new Intent( AddAppliances.this,AddPi.class));
                        break;
                    case R.id.nav_add_appliance:
                        startActivity(new Intent(AddAppliances.this,AddAppliances.class));
                        break;
                    case R.id.nav_appliances:
                        startActivity(new Intent(AddAppliances.this,Appliances.class));
                        break;
                    case R.id.nav_logout:
                        SharedPreferences sharedPreferences = AddAppliances.this.getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.commit();
                        startActivity(new Intent(AddAppliances.this,MainActivity.class));
                        break;

                }
                return false;
            }
        });

        SharedPreferences settings = getSharedPreferences(loginInfo, Context.MODE_PRIVATE);
        String user_id=settings.getString("user_id", "-1");

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://45.125.222.120:5000/raspberrypi/userId/"+user_id)
                .get()
                .addHeader("cache-control", "no-cache")
                .addHeader("Postman-Token", "0430b79f-1446-4559-beb2-86407c8c51b7")
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

                    AddAppliances.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Log.v("response",myResponse);
                                JSONArray obj = new JSONArray(myResponse);

                                Spinner dropdown = findViewById(R.id.piListAppSpinner);
                                List<String> piItemsArray = new ArrayList<String>();

                                Log.v("response",""+obj.length());
                                for(int i = 0;i<obj.length();i++)
                                {
                                    piItemsArray.add(obj.getJSONObject(i).getString("raspberrypi_id"));
                                }
                                String[] piItem = new String[piItemsArray.size()];
                                piItem = piItemsArray.toArray(piItem);
                                ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, piItem);
                                dropdown.setAdapter(adapter);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
        Spinner appliancTypeDropDown = findViewById(R.id.applianceTypeSpinner);
        String[] appliancTypeItems = new String[]{"TV","AC","Camera","DOOR","Others"};
        ArrayAdapter<String> appliancTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, appliancTypeItems);
        appliancTypeDropDown.setAdapter(appliancTypeAdapter);
        appliancTypeDropDown.setOnItemSelectedListener(this);

        Spinner applianceBrandDropDown = findViewById(R.id.applianceBrandSpinner);
        String[] applianceBrandItems = new String[]{"SAMSUNG","GENERAL","Others"};
        ArrayAdapter<String> applianceBrandItemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, applianceBrandItems);
        applianceBrandDropDown.setAdapter(applianceBrandItemsAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selected = parent.getItemAtPosition(position).toString();
        if(selected.equals("Camera")){
            TextView streamURLEditText = findViewById(R.id.streamURLEditText);
            streamURLEditText.setVisibility(TextView.VISIBLE);
        }
        else{
            TextView streamURLEditText = findViewById(R.id.streamURLEditText);
            streamURLEditText.setVisibility(TextView.INVISIBLE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

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

public class Appliances extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    Boolean bool = false;
    int piListPos = 0;

    private void setUpToolbar() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        actionBarDrawerToggle.syncState();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (bool == true) {
            //startActivity(new Intent(Appliances.this, Appliances.class));
            //finish();
            Spinner spinnerObject = findViewById(R.id.applianceListSpinner);
            spinnerObject.setSelection(0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appliances);
        setUpToolbar();

        String loginInfo = "loginInfo";

        bool = true;


        navigationView = (NavigationView) findViewById(R.id.navigation_menu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_dashboard:
                        startActivity(new Intent(Appliances.this, home.class));
                        break;

                    case R.id.nav_add_pi:
                        startActivity(new Intent(Appliances.this, AddPi.class));
                        break;
                    case R.id.nav_add_appliance:
                        startActivity(new Intent(Appliances.this, AddAppliances.class));
                        break;
                    case R.id.nav_logout:
                        SharedPreferences sharedPreferences = Appliances.this.getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.commit();
                        startActivity(new Intent(Appliances.this, MainActivity.class));
                        break;

                }
                return false;
            }
        });

        SharedPreferences settings = getSharedPreferences(loginInfo, Context.MODE_PRIVATE);
        String user_id = settings.getString("user_id", "-1");

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://45.125.222.120:5000/raspberrypi/userId/" + user_id)
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

                    Appliances.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String piList = "piList";
                                SharedPreferences sharedpreferences = getSharedPreferences(piList, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString("piList", myResponse);
                                editor.commit();

                                Log.v("response", myResponse);
                                JSONArray obj = new JSONArray(myResponse);

                                Spinner dropdown = findViewById(R.id.piListAppSpinner);
                                List<String> piItemsArray = new ArrayList<String>();

                                Log.v("response", "" + obj.length());
                                for (int i = 0; i < obj.length(); i++) {
                                    piItemsArray.add(obj.getJSONObject(i).getString("raspberrypi_id"));
                                }
                                String[] piItem = new String[piItemsArray.size()];
                                piItem = piItemsArray.toArray(piItem);
                                ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, piItem);
                                dropdown.setAdapter(adapter);
                                dropdown.setOnItemSelectedListener(Appliances.this);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });

        String piList = "piList";

        SharedPreferences piListPref = getSharedPreferences(piList, Context.MODE_PRIVATE);
        String piListStr = piListPref.getString("piList", "-1");
        if (!piListStr.equals(-1)) {
            JSONArray obj = null;
            try {
                obj = new JSONArray(piListStr);

                Request requestAppList = new Request.Builder()
                        .url("http://45.125.222.120:5000/appliance/" + obj.getJSONObject(0).getString("raspberrypi_id"))
                        .get()
                        .addHeader("cache-control", "no-cache")
                        .addHeader("Postman-Token", "083f870c-ff61-4e3e-8fed-f88691a96c00")
                        .build();


                client.newCall(requestAppList).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            final String myResponse = response.body().string();

                            Appliances.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        String applianceList = "applianceList";
                                        SharedPreferences sharedpreferences = getSharedPreferences(applianceList, Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedpreferences.edit();
                                        editor.putString("applianceList", myResponse);
                                        editor.commit();
                                        Log.v("response", myResponse);
                                        JSONArray obj = new JSONArray(myResponse);

                                        Spinner dropdown = findViewById(R.id.applianceListSpinner);
                                        List<String> piItemsArray = new ArrayList<String>();

                                        piItemsArray.add("Select Item");
                                        Log.v("response", "" + obj.length());
                                        for (int i = 1; i < obj.length(); i++) {
                                            piItemsArray.add(obj.getJSONObject(i).getString("appliance"));
                                        }
                                        String[] applianceItem = new String[piItemsArray.size()];
                                        applianceItem = piItemsArray.toArray(applianceItem);
                                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, applianceItem);
                                        dropdown.setAdapter(adapter);
                                        dropdown.setOnItemSelectedListener(Appliances.this);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selected = parent.getItemAtPosition(position).toString();

        Spinner spinner = (Spinner) parent;
        String piList = "piList";
        SharedPreferences piListPref = getSharedPreferences(piList, Context.MODE_PRIVATE);
        String piListStr = piListPref.getString("piList", "-1");
        JSONArray obj = null;
        try {
            obj = new JSONArray(piListStr);


            if (spinner.getId() == R.id.piListAppSpinner) {

                OkHttpClient client = new OkHttpClient();
                Request requestAppList = new Request.Builder()
                        .url("http://45.125.222.120:5000/appliance/" + obj.getJSONObject(position).getString("raspberrypi_id"))
                        .get()
                        .addHeader("cache-control", "no-cache")
                        .addHeader("Postman-Token", "083f870c-ff61-4e3e-8fed-f88691a96c00")
                        .build();


                client.newCall(requestAppList).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            final String myResponse = response.body().string();

                            Appliances.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        String applianceList = "applianceList";
                                        SharedPreferences sharedpreferences = getSharedPreferences(applianceList, Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedpreferences.edit();
                                        editor.putString("applianceList", myResponse);
                                        editor.commit();
                                        Log.v("response", myResponse);
                                        JSONArray obj = new JSONArray(myResponse);

                                        Spinner dropdown = findViewById(R.id.applianceListSpinner);
                                        List<String> piItemsArray = new ArrayList<String>();
                                        piItemsArray.add("Select Item");

                                        Log.v("response", "" + obj.length());
                                        for (int i = 0; i < obj.length(); i++) {
                                            piItemsArray.add(obj.getJSONObject(i).getString("appliance"));
                                        }
                                        String[] applianceItem = new String[piItemsArray.size()];
                                        applianceItem = piItemsArray.toArray(applianceItem);
                                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, applianceItem);
                                        dropdown.setAdapter(adapter);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    }
                });

            } else if (spinner.getId() == R.id.applianceListSpinner) {
                //Toast.makeText(getApplicationContext(),start,Toast.LENGTH_SHORT).show();


                Spinner piListSpinner = findViewById(R.id.piListAppSpinner);
                int piPos = -1;
                Log.v("response", "pi no" + piListSpinner.getSelectedItem().toString());
                String applianceList = "applianceList";
                for (int i = 0; i < obj.length(); i++) {
                    Log.v("response", "rasp_id" + obj.getJSONObject(i).getString("raspberrypi_id"));
                    if (obj.getJSONObject(i).getString("raspberrypi_id").equals(piListSpinner.getSelectedItem().toString())) {
                        piPos = i;
                        break;
                    }
                }

                position = position - 1;

                SharedPreferences piListAppPref = getSharedPreferences(applianceList, Context.MODE_PRIVATE);
                String applianceListStr = piListAppPref.getString("applianceList", "-1");

                JSONArray objApp = new JSONArray(applianceListStr);

                if (objApp.getJSONObject(position).getString("type").equals("CAMERA")) {
                    Intent camera = new Intent(Appliances.this, Camera.class);
                    camera.putExtra("url", objApp.getJSONObject(position).getString("url"));
                    startActivity(camera);
                } else if (objApp.getJSONObject(position).getString("type").equals("DOOR")) {

                    String payload = "{\"applianceType\": \"" + objApp.getJSONObject(position).getString("type") + "\" , \"operation\": \"Off\", \"pin\": " + objApp.getJSONObject(position).getString("pin") + " ,\"signalType\":\"NoIR\" , \"data\": []}\n";
                    Intent door = new Intent(Appliances.this, Door.class);
                    door.putExtra("mac", obj.getJSONObject(piPos).getString("mac"));
                    door.putExtra("payload", payload);
                    startActivity(door);
                } else if (objApp.getJSONObject(position).getString("type").equals("Others")) {

                    Intent otherApp = new Intent(Appliances.this, OtherAppliances.class);
                    otherApp.putExtra("type", objApp.getJSONObject(position).getString("type"));
                    otherApp.putExtra("pin", objApp.getJSONObject(position).getString("pin"));
                    otherApp.putExtra("mac", obj.getJSONObject(piPos).getString("mac"));
                    otherApp.putExtra("remote", "");
                    startActivity(otherApp);

                } else if (objApp.getJSONObject(position).getString("type").equals("TV")) {

                    OkHttpClient client = new OkHttpClient();

                    MediaType mediaType = MediaType.parse("application/json");
                    RequestBody body = RequestBody.create(mediaType, "{\n\t\"brand\":\"" + objApp.getJSONObject(position).getString("brand") + "\",\n\t\"type\":\"" + objApp.getJSONObject(position).getString("type") + "\"\n}");
                    Request requestRemoteInfo = new Request.Builder()
                            .url("http://45.125.222.120:5000/remoteInfo")
                            .post(body)
                            .addHeader("Content-Type", "application/json")
                            .addHeader("cache-control", "no-cache")
                            .addHeader("Postman-Token", "e4054ec1-8714-4c5d-9d27-cadd81cff28f")
                            .build();

                    client.newCall(requestRemoteInfo).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            if (response.isSuccessful()) {
                                final String myResponse = response.body().string();

                                Appliances.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        String remoteInfo = "remoteInfo";
                                        SharedPreferences sharedpreferences = getSharedPreferences(remoteInfo, Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedpreferences.edit();
                                        editor.putString("remoteInfo", myResponse);
                                        editor.commit();

                                    }
                                });
                            }
                        }
                    });

                    SharedPreferences remoteInfo = getSharedPreferences("remoteInfo", Context.MODE_PRIVATE);
                    String remoteInfoStr = remoteInfo.getString("remoteInfo", "-1");

                    Intent tv = new Intent(Appliances.this, TvAppliance.class);
                    tv.putExtra("type", objApp.getJSONObject(position).getString("type"));
                    tv.putExtra("pin", objApp.getJSONObject(position).getString("pin"));
                    tv.putExtra("mac", obj.getJSONObject(piPos).getString("mac"));
                    tv.putExtra("remote", remoteInfoStr);
                    startActivity(tv);

                } else if (objApp.getJSONObject(position).getString("type").equals("AC")) {

                    OkHttpClient client = new OkHttpClient();

                    MediaType mediaType = MediaType.parse("application/json");
                    RequestBody body = RequestBody.create(mediaType, "{\n\t\"brand\":\"" + objApp.getJSONObject(position).getString("brand") + "\",\n\t\"type\":\"" + objApp.getJSONObject(position).getString("type") + "\"\n}");
                    Request requestRemoteInfo = new Request.Builder()
                            .url("http://45.125.222.120:5000/remoteInfo")
                            .post(body)
                            .addHeader("Content-Type", "application/json")
                            .addHeader("cache-control", "no-cache")
                            .addHeader("Postman-Token", "e4054ec1-8714-4c5d-9d27-cadd81cff28f")
                            .build();

                    client.newCall(requestRemoteInfo).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            if (response.isSuccessful()) {
                                final String myResponse = response.body().string();

                                Appliances.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        String remoteInfo = "remoteInfo";
                                        SharedPreferences sharedpreferences = getSharedPreferences(remoteInfo, Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedpreferences.edit();
                                        editor.putString("remoteInfo", myResponse);
                                        editor.commit();

                                    }
                                });
                            }
                        }
                    });

                    SharedPreferences remoteInfo = getSharedPreferences("remoteInfo", Context.MODE_PRIVATE);
                    String remoteInfoStr = remoteInfo.getString("remoteInfo", "-1");


                    Intent ac = new Intent(Appliances.this, AcAppliance.class);
                    ac.putExtra("type", objApp.getJSONObject(position).getString("type"));
                    ac.putExtra("pin", objApp.getJSONObject(position).getString("pin"));
                    ac.putExtra("mac", obj.getJSONObject(piPos).getString("mac"));
                    ac.putExtra("remote", remoteInfoStr);
                    startActivity(ac);
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(getApplicationContext(), "life sucks", Toast.LENGTH_SHORT).show();
    }
}

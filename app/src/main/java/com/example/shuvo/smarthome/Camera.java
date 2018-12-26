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
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class Camera extends AppCompatActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

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
                        startActivity(new Intent(Camera.this,home.class));
                        break;

                    case R.id.nav_add_pi:
                        startActivity(new Intent(Camera.this,AddPi.class));
                        break;
                    case R.id.nav_add_appliance:
                        startActivity(new Intent(Camera.this,AddAppliances.class));
                        break;
                    case R.id.nav_appliances:
                        startActivity(new Intent(Camera.this,Appliances.class));
                        break;
                    case R.id.nav_logout:
                        SharedPreferences sharedPreferences = Camera.this.getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.commit();
                        startActivity(new Intent(Camera.this,MainActivity.class));
                        break;

                }
                return false;
            }
        });

        VideoView videoView = findViewById(R.id.videoView);
        String url= getIntent().getStringExtra("url");
        videoView.setVideoPath(url);
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.start();
    }
}

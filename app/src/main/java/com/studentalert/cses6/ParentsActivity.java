package com.studentalert.cses6;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class ParentsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parents);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(" Parents Corner ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.INVISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","Rahul", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "REGARDING CONTENT OF CSE BETA App");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));


            }
        });
        setupCollapsingToolbar();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        WebView myWebView = (WebView) findViewById(R.id.web_view);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.loadUrl("http://www.rajagiritech.ac.in/stud/parent/Index.asp");
        myWebView.setWebViewClient(new WebViewClient());
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setupCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(
                R.id.collapse_toolbar);

        collapsingToolbar.setTitleEnabled(true);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_maths) {
            Global.subject = "Design and Analysis of algorithms";
            Global.subjectCode = "daa";
            Intent subject = new Intent(getApplicationContext(), OopActivity.class);
            startActivity(subject);
            finish();
        } else if (id == R.id.nav_pom) {
            Global.subject = "Internet Computing";
            Global.subjectCode = "IC";
            Intent subject = new Intent(getApplicationContext(), OopActivity.class);
            startActivity(subject);
            finish();
        } else if (id == R.id.nav_amp) {
            Global.subject = "Elective";
            Global.subjectCode = "el";
            Intent subject = new Intent(getApplicationContext(), OopActivity.class);
            startActivity(subject);
            finish();
        } else if (id == R.id.nav_dms) {
            Global.subject = "System Software";
            Global.subjectCode = "ss";
            Intent subject = new Intent(getApplicationContext(), OopActivity.class);
            startActivity(subject);
            finish();
        } else if (id == R.id.nav_dsp) {
            Global.subject = "Computer Networks";
            Global.subjectCode = "cn";
            Intent subject = new Intent(getApplicationContext(), OopActivity.class);
            startActivity(subject);
            finish();
        } else if (id == R.id.nav_os) {
            Global.subject = "Software Engineering";
            Global.subjectCode = "se";
            Intent subject = new Intent(getApplicationContext(), OopActivity.class);
            startActivity(subject);
            finish();
        } else if (id == R.id.nav_notif) {
            Intent notif = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(notif);
            finish();
        } else if (id == R.id.nav_databaselab) {
            Global.subject = "OS Lab";
            Global.subjectCode = "oslab";
            Intent subject = new Intent(getApplicationContext(), OopActivity.class);
            startActivity(subject);
            finish();
        }else if (id == R.id.nav_parent) {
            Intent parent = new Intent(getApplicationContext(), ParentsActivity.class);
            startActivity(parent);
            finish();
        } else if (id == R.id.nav_mplab) {
            Global.subject = "miniproject lab";
            Global.subjectCode = "mplab";
            Intent subject = new Intent(getApplicationContext(), OopActivity.class);
            startActivity(subject);
            finish();
        }else if (id == R.id.nav_exam) {
            Global.subject = "Question Papers";
            Global.subjectCode = "qp";
            Intent subject = new Intent(getApplicationContext(), OopActivity.class);
            startActivity(subject);
            finish();
        }else if (id == R.id.nav_misc) {
            Global.subject = "Miscellaneous";
            Global.subjectCode = "misc";
            Intent subject = new Intent(getApplicationContext(), OopActivity.class);
            startActivity(subject);
            finish();
        } else if (id == R.id.nav_share) {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.AppShare));
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
        } else if (id == R.id.nav_send) {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(Intent.EXTRA_TEXT, Global.AppShare);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

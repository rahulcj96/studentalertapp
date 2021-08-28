package com.studentalert.cses6;



import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.Volley;
/*import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;*/

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static com.studentalert.cses6.Global.*;

public class OopActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private RecyclerView mRecyclerView;
    private CardView Emptyview;
    private final Context context = this;
    private ProgressDialog pDialog;
    private List<subjectContent> listData;
    // TAGS
    private static final String TAG_NAME = "name";
    private static final String TAG_DESC = "description";
    private static final String TAG_AUTHOR = "author";
    private static final String TAG_TYPE = "type";
    private static final String TAG_DOWNLOAD = "download";
    private static final String TAG_SUBJECT = "subject";
    private static final String TAG_DATE = "date";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_subj);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(Global.subject!=null) getSupportActionBar().setTitle(Global.subject);
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
        /*AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest.Builder request = new AdRequest.Builder();
        request.addTestDevice(AdRequest.DEVICE_ID_EMULATOR);
        mAdView.loadAd(request.build()); */

        setupCollapsingToolbar();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view_subject);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        Emptyview = (CardView) findViewById(R.id.card_view_sub);
        Global.orientation = getResources().getConfiguration().orientation;
        switch (Global.orientation)
        {
            case Configuration.ORIENTATION_UNDEFINED: Global.Orientation = "Undefined"; break;
            case Configuration.ORIENTATION_LANDSCAPE: Global.Orientation = "Landscape"; break;
            case Configuration.ORIENTATION_PORTRAIT:  Global.Orientation = "Portrait"; break;
            default: Global.Orientation = "Square";break;
        }
        StaggeredGridLayoutManager mSGLM;
        if(Global.Orientation.equals("Portrait")) {

            mSGLM = new StaggeredGridLayoutManager(1,1);

        } else {

            mSGLM = new StaggeredGridLayoutManager(2,1);
        }
        mRecyclerView.setLayoutManager(mSGLM);
        listData = new ArrayList<>();
        jsonFetch();
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setupCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(
                R.id.collapse_toolbar);

        collapsingToolbar.setTitleEnabled(true);
    }

   private void jsonFetch() {
            pDialog = new ProgressDialog(OopActivity.this);
            pDialog.setTitle(getString(R.string.loading));
            pDialog.setMessage(getString(R.string.loading));
            pDialog.setCancelable(false);
            pDialog.show();
            String SUB_LINK = getString(R.string.sub_link);
            Volley.newRequestQueue(OopActivity.this).
                    add(new CustomJsonRequestMain(SUB_LINK, null,
                                new Response.Listener<JSONArray>() {
                                    @Override
                                    public void onResponse(JSONArray response) {
                                        hidePDialog();
                                        SharedPreferences sharedPreferences= context.getSharedPreferences("Prefs", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor= sharedPreferences.edit();
                                        editor.putString("subjects",response.toString());
                                        editor.apply();
                                        jsonparse();

                                    }
                                }

                                , new Response.ErrorListener()

                        {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                VolleyLog.d("Error: " + error.getMessage());
                                //error.printStackTrace();
                                jsonparse();
                                hidePDialog();
                            }
                        }

                        )

                        {


                            @Override
                            protected Response<JSONArray> parseNetworkResponse(
                                    NetworkResponse response) {
                                try {
                                    String jsonString = new String(response.data,
                                            HttpHeaderParser
                                                    .parseCharset(response.headers));
                                    return Response.success(new JSONArray(jsonString),
                                            HttpHeaderParser
                                                    .parseCacheHeaders(response));
                                } catch (UnsupportedEncodingException e) {
                                    return Response.error(new ParseError(e));
                                } catch (JSONException je) {
                                    return Response.error(new ParseError(je));
                                }
                            }
                        }

                    );
        }

    private void jsonparse()
    {
        JSONArray notifications=new JSONArray();
        SharedPreferences sharedPreferences= context.getSharedPreferences("Prefs", Context.MODE_PRIVATE);
        try {
            Object obj=sharedPreferences.getString("subjects","0");
            notifications=new JSONArray(obj.toString());
        }catch(JSONException e)
        {
            Toast.makeText(context,"Please Connect to Internet and Try again..!!",Toast.LENGTH_LONG).show();
        }
        try{

            for (int i=0;i<notifications.length();i++)
            {
                subjectContent subjectContent = new subjectContent();
                JSONObject obj=notifications.getJSONObject(i);
                subjectContent.setName(obj.getString(TAG_NAME));
                subjectContent.setAuthor(obj.getString(TAG_AUTHOR));
                subjectContent.setDescription(obj.getString(TAG_DESC));
                subjectContent.setType(obj.getString(TAG_TYPE));
                subjectContent.setSubject(obj.getString(TAG_TYPE));
                subjectContent.setDate(obj.getString(TAG_DATE));
                subjectContent.setDownload_link(obj.getString(TAG_DOWNLOAD));
                subjectContent.setSubject(obj.getString(TAG_SUBJECT));
                if(subjectContent.getSubject().equals(subjectCode))
                    listData.add(subjectContent);
            }
            if(listData.isEmpty()) {
                Log.i("LOG :", "EMPTY RECYCLERVIEW");
                mRecyclerView.setVisibility(View.GONE);
                Emptyview.setVisibility(View.VISIBLE);
            }
            else {
                mRecyclerView.setVisibility(View.VISIBLE);
                Emptyview.setVisibility(View.GONE);
            }
            subjectCardAdapter mAdapter = new subjectCardAdapter(listData, getApplicationContext());
            mRecyclerView.setAdapter(mAdapter);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
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
            Global.subject = "Design and Algorithm";
            Global.subjectCode = "daa";
            Intent subject = new Intent(getApplicationContext(), OopActivity.class);
            startActivity(subject);
            finish();
        } else if (id == R.id.nav_pom) {
            Global.subject = "Internet Computing";
            Global.subjectCode = "ic";
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
            Global.subject = "Computer networks";
            Global.subjectCode = "cn";
            Intent subject = new Intent(getApplicationContext(), OopActivity.class);
            startActivity(subject);
            finish();
        } else if (id == R.id.nav_os) {
            Global.subject = "Software Engineering" ;
            Global.subjectCode = "se";
            Intent subject = new Intent(getApplicationContext(), OopActivity.class);
            startActivity(subject);
            finish();
        } else if (id == R.id.nav_notif) {
            Intent notif = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(notif);
            finish();
        } else if (id == R.id.nav_databaselab) {
            Global.subject = "Operating system lab";
            Global.subjectCode = "oslab";
            Intent subject = new Intent(getApplicationContext(), OopActivity.class);
            startActivity(subject);
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
        } else if (id == R.id.nav_parent) {
            Intent parent = new Intent(getApplicationContext(), ParentsActivity.class);
            startActivity(parent);
            finish();
        }else if (id == R.id.nav_share) {
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

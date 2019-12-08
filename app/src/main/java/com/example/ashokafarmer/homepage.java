package com.example.ashokafarmer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class homepage extends AppCompatActivity implements pooladapter.onitemclicklistener{
    private Toolbar mtoolbar;

    public static final String EXTRA_URL="imageurl";
    public static final String EXTRA_NAME="user";
    public static final String EXTRA_LOCATION="views";
    //added extra datas for manipulating later(report is not initialised currently)
    public static final String EXTRA_REPORT="imageurl";
    public  static final String EXTRA_AREA="imageHeight";
    private RecyclerView mRecyclerView;
    private pooladapter mpoolAdapter;
    private ArrayList<poolitems> mpoollist;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        mtoolbar=(Toolbar)findViewById(R.id.main_page_toolbar);
        mRecyclerView=findViewById(R.id.joinownpoolsrecycler);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mpoollist=new ArrayList<>();
        mRequestQueue= Volley.newRequestQueue(this);

        parseJSON();


        //NAVIGATION BAR DIRECTLY IMPORTED FROM MIKEPENZ
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.gardenland)
                .addProfiles(
                        new ProfileDrawerItem().withName("Utsav Majhi").withEmail("deadsnipper@gmail.com").withIcon(getResources().getDrawable(R.drawable.profile_photo))
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();



        //if you want to update the items at a later time it is recommended to keep it in a variable
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName("Profile");
        PrimaryDrawerItem item2=new PrimaryDrawerItem().withIdentifier(2).withName("Logout");
        PrimaryDrawerItem item3=new PrimaryDrawerItem().withIdentifier(3).withName("Join Pool");
        PrimaryDrawerItem item4=new PrimaryDrawerItem().withIdentifier(4).withName("Create Pool");
        PrimaryDrawerItem item5=new PrimaryDrawerItem().withIdentifier(5).withName("Digitize Land");



        //create the drawer and remember the `Drawer` result object
        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(mtoolbar)
                .withTranslucentStatusBar(false)
                .withActionBarDrawerToggle(true)
                .withSavedInstance(savedInstanceState)
                .withDisplayBelowStatusBar(false)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        item1, item2,item3 , item4, item5

                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D

                        switch (position)
                        {
                            case 1:
                                //pass token for getting user details
                                Intent p1=new Intent(homepage.this,profileactivity.class);
                                //pass the token or required details
                                startActivity(p1);
                                break;
                            case 2:
                                Toast.makeText(homepage.this, "Logout", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(homepage.this,MainActivity.class));
                                finish();
                                break;

                            case 3:
                                startActivity(new Intent(homepage.this,joinpoolactivity.class));
                                break;

                            case 4:
                                startActivity(new Intent(homepage.this,createpoolactivity.class));
                                break;

                            case 5:
                                startActivity(new Intent(homepage.this,digitiselandactivity.class));
                                break;

                        }
                        return true;
                    }
                })
                .build();
        //NAVIGATION DRAWER ENDS



        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("Ashoka Farmer");
        result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);



    }

    private void parseJSON() {



        //JSON URL (NOW ITS A DUMMY)
        String url="https://pixabay.com/api/?key=5303976-fd6581ad4ac165d1b75cc15b3&q=kitten&image_type=photo&pretty=true";;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //getting values from fake json file
                        try {
                            JSONArray jsonArray=response.getJSONArray("hits");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject hit = jsonArray.getJSONObject(i);
                                String poolname = hit.getString("user");
                                String mImageurl= hit.getString("tags");
                                String area = hit.getString("imageHeight");
                                String location = hit.getString("views");
                                String Report=hit.getString("userImageURL");

                                //remember maintain the same order as in poolitemslist.java
                                mpoollist.add(new poolitems(mImageurl,poolname,area,location));
                            }
                            mpoolAdapter = new pooladapter(homepage.this, mpoollist);
                            mRecyclerView.setAdapter(mpoolAdapter);
                            mpoolAdapter.setOnItemClickListener(homepage.this);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mRequestQueue.add(request);

    }


    //digitize land button click
    public void digilandclick(View view) {
        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        startActivity(new Intent(homepage.this,digitiselandactivity.class));


    }
    //digitise land activity ends

    //create pool activity clicking
    public void createpclick(View view) {
        startActivity(new Intent(homepage.this,createpoolactivity.class));

    }
    //create pool activity ends


    //join pools activity clicking
    public void joinpoolclick(View view) {

            startActivity(new Intent(homepage.this,joinpoolactivity.class));


    }


    @Override
    public void onItemClick(int position) {
        Intent detailIntent=new Intent(homepage.this,joinpoolexpand.class);
        poolitems clickedItem=mpoollist.get(position);
        detailIntent.putExtra(EXTRA_URL,clickedItem.getmImageurl());
        detailIntent.putExtra(EXTRA_NAME,clickedItem.getPoolname());
        detailIntent.putExtra(EXTRA_AREA,clickedItem.getArea());
        detailIntent.putExtra(EXTRA_LOCATION,clickedItem.getLocation());

        startActivity(detailIntent);

    }
}

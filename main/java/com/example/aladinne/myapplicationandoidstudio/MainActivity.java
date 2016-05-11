package com.example.aladinne.myapplicationandoidstudio;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aladinne.myapplicationandoidstudio.handler.GetDescriptionFromUrl;
import com.example.aladinne.myapplicationandoidstudio.handler.GetXmlFromUrl;
import com.example.aladinne.myapplicationandoidstudio.models.MangaUpdates;
import com.orm.SugarContext;

import org.w3c.dom.Document;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String WIFI = "Wi-Fi";
    public static final String ANY = "Any";

    // Whether there is a Wi-Fi connection.
    private static boolean wifiConnected = false;
    // Whether there is a mobile connection.
    private static boolean mobileConnected = false;
    // Whether the display should be refreshed.
    public static boolean refreshDisplay = true;

    // The user's current network preference setting.
    public static String sPref = null;

    // The BroadcastReceiver that tracks network connectivity changes.
    private NetworkReceiver receiver = new NetworkReceiver();
    TextView tv_sourceName;
    ImageView img_source;
    ListView lv;
    ArrayList<String> listMangaUpdate;
    ArrayList<String> listshanaproject;


    ArrayList<String> linksMangaUpdate;
    ArrayList<String> linksshanaproject;
    TextView tv2;


    ProgressDialog progressBar;
    private int progressBarStatus = 0;
    private Handler progressBarHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //tv_sourceName = (TextView) findViewById(R.id.feed) ;
        img_source = (ImageView) findViewById(R.id.sourceImage);
        lv = (ListView) findViewById(R.id.listView);

        listMangaUpdate = new ArrayList<String>();
        /*adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listMangaUpdate);
        lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();*/
        tv2 = (TextView) findViewById(R.id.textView2);
        linksMangaUpdate = new ArrayList<String>();
        linksshanaproject = new ArrayList<String>();
        listshanaproject = new ArrayList<String>();
        SugarContext.init(this);



        if(!isOnline())
        {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this);
            alertDialogBuilder.setTitle("No Connectivity");

            // set dialog message
            alertDialogBuilder
                    .setMessage("try again later!")
                    .setCancelable(false)
                    .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MainActivity.this.finish();
                        }
                    });






            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {


            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

            linksMangaUpdate.clear();
            listMangaUpdate.clear();
            tv2.setText("Fetching from : www.mangaupdates.com/rss.php");
            lv.setAdapter(null);
            if (android.os.Build.VERSION.SDK_INT > 9)
            {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }

                GetXmlFromUrl getXmlFromUrl = new GetXmlFromUrl();
                Document doc = getXmlFromUrl.getDoc("http://www.mangaupdates.com/rss.php");
                String link = "";
                for (int i = 1; i <= doc.getElementsByTagName("item").getLength(); i++) {
                    listMangaUpdate.add(doc.getElementsByTagName("item").item(i - 1).getChildNodes().item(1).getTextContent());
                    MangaUpdates manga = new MangaUpdates();
                    //manga.setTitle(listMangaUpdate.get(i-1));


                    link = doc.getElementsByTagName("item").item(i - 1).getChildNodes().item(3).getTextContent();
                    link = link.substring(25, link.length());
                    link = link.substring(0, link.indexOf("\""));
                    linksMangaUpdate.add(link);

                   // manga.setLink(linksMangaUpdate.get(i - 1));
                    //manga.save();

                }


                final ArrayAdapter<String> adapterMangaUpdate = new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, android.R.id.text1, listMangaUpdate);

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(MainActivity.this, test2Activity.class);
                        intent.putExtra("manga_title", adapterMangaUpdate.getItem(position));
                        intent.putExtra("manga_link", (String) linksMangaUpdate.get(position));
                        GetDescriptionFromUrl getDescriptionFromUrl = new GetDescriptionFromUrl();
                        String description = getDescriptionFromUrl.getDescription(linksMangaUpdate.get(position));
                        startActivity(intent);
                    }
                });
                // Assign adapter to ListView
                lv.setAdapter(adapterMangaUpdate);

            } else if (id == R.id.nav_slideshow) {
                Toast.makeText(this, "shanaproject", Toast.LENGTH_SHORT).show();
                listshanaproject.clear();
                linksshanaproject.clear();
                lv.setAdapter(null);
                tv2.setText("Fetching from : shanaproject.com/feeds/site");
                GetXmlFromUrl getXmlFromUrl = new GetXmlFromUrl();
                Document doc = getXmlFromUrl.getDoc("http://www.shanaproject.com/feeds/site/");
                // String linkshanaproject ="";
                for (int i = 1; i <= doc.getElementsByTagName("item").getLength(); i++) {

                    listshanaproject.add(doc.getElementsByTagName("item").item(i - 1).getChildNodes().item(0).getTextContent());


                    // link=doc.getElementsByTagName("item").item(i-1).getChildNodes().item(3).getTextContent();
                    // link= link.substring(25, link.length());
                    //  link= link.substring(0,link.indexOf("\""));
                    linksshanaproject.add("");


                }


                final ArrayAdapter<String> adaptershanaproject = new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, android.R.id.text1, listshanaproject);

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(MainActivity.this, test2Activity.class);
                        intent.putExtra("manga_title", adaptershanaproject.getItem(position));
                        intent.putExtra("manga_link", "");
                        // GetDescriptionFromUrl getDescriptionFromUrl = new GetDescriptionFromUrl();
                        //  String description =getDescriptionFromUrl.getDescription(linksshanaproject.get(position));
                        startActivity(intent);
                    }
                });
                // Assign adapter to ListView
                lv.setAdapter(adaptershanaproject);



            } else if (id == R.id.nav_manage) {


            List<MangaUpdates> mangas = new ArrayList<MangaUpdates>() ;
            mangas=MangaUpdates.listAll(MangaUpdates.class);
            Toast.makeText(this,mangas.size(), Toast.LENGTH_SHORT).show();

            }


         else if (id == R.id.nav_share) {

            //  String t = headlines.get(0) ;
        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public class NetworkReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connMgr =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

            // Checks the user prefs and the network connection. Based on the result, decides
            // whether
            // to refresh the display or keep the current display.
            // If the userpref is Wi-Fi only, checks to see if the device has a Wi-Fi connection.
            if (WIFI.equals(sPref) && networkInfo != null
                    && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                // If device has its Wi-Fi connection, sets refreshDisplay
                // to true. This causes the display to be refreshed when the user
                // returns to the app.
                refreshDisplay = true;
                Toast.makeText(context, R.string.wifi_connected, Toast.LENGTH_SHORT).show();

                // If the setting is ANY network and there is a network connection
                // (which by process of elimination would be mobile), sets refreshDisplay to true.
            } else if (ANY.equals(sPref) && networkInfo != null) {
                refreshDisplay = true;

                // Otherwise, the app can't download content--either because there is no network
                // connection (mobile or Wi-Fi), or because the pref setting is WIFI, and there
                // is no Wi-Fi connection.
                // Sets refreshDisplay to false.
            } else {
                refreshDisplay = false;
                Toast.makeText(context, R.string.lost_connection, Toast.LENGTH_SHORT).show();
            }
        }


    }


    // Given a string representation of a URL, sets up a connection and gets
    // an input stream.
    private InputStream downloadUrl(String urlString) throws IOException {
        java.net.URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        // Starts the query
        conn.connect();
        InputStream stream = conn.getInputStream();
        return stream;
    }


    public InputStream getInputStream(URL url) {
        try {
            return url.openConnection().getInputStream();
        } catch (IOException e) {
            return null;
        }
    }




    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}




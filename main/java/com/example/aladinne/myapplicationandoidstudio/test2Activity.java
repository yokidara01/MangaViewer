package com.example.aladinne.myapplicationandoidstudio;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.aladinne.myapplicationandoidstudio.handler.GetDescriptionFromUrl;

public class test2Activity extends AppCompatActivity {
    TextView tv,tv2 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
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

        tv=(TextView) findViewById(R.id.textView3);
        tv2=(TextView) findViewById(R.id.textView4);
        Intent intent = getIntent();
        tv.setText(intent.getStringExtra("manga_title"));
        GetDescriptionFromUrl getDescriptionFromUrl = new GetDescriptionFromUrl() ;
        String description = getDescriptionFromUrl.getDescription(intent.getStringExtra("manga_link")) ;
        tv2.setText(description);



    }

}

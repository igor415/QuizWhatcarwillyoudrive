package com.varivoda.igor.quiz_whatcarwillyoudrive;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import butterknife.BindView;

public class AnalyzeAnimation extends AppCompatActivity {
    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyze_animation);
        setSupportActionBar(toolbar);
        Handler handler = new Handler();
        handler.postDelayed(AnalyzeAnimation.this::finish, 1000);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.ostali_menu,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.podijeli){
            Intent in = new Intent(Intent.ACTION_SEND);
            in.setType("text/plain");
            in.putExtra(Intent.EXTRA_SUBJECT, "Car predictor - 2019");
            String message = "\nhttps://play.google.com/store/apps/details?id=com.varivoda.igor.quiz_whatcarwillyoudrive\n\n";

            in.putExtra(Intent.EXTRA_TEXT, message);
            startActivity(Intent.createChooser(in, "Choose where you want to share:"));
        }else if(item.getItemId()==R.id.programer){
            Intent i = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/developer?id=Igor+Varivoda"));
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

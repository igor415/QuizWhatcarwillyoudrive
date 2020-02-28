package com.varivoda.igor.quiz_whatcarwillyoudrive;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Vibrator;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DieselPetrol extends AppCompatActivity {
    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    public static String[] pitanjaDieselPetrol = new String[5];
    public static String[][] choicesDieselPetrol = new String[5][2];
    @BindView(R.id.prvi)
    protected Button prvi;
    @BindView(R.id.drugi)
    protected Button drugi;
    @BindView(R.id.question)
    protected TextView question;
    private int brojac = 0, brojacPrvi = 0, brojacDrugi = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diesel_petrol);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.dieselPetrolTitle));
        }


        prvi.setOnClickListener(v -> ButtonFunction(1));
        drugi.setOnClickListener(v -> ButtonFunction(2));
        updateQuestion();
    }

    private void ButtonFunction(int br) {
        if (br == 1) {
            brojacPrvi++;
        } else {
            brojacDrugi++;
        }
        if (brojac < 5) {
            updateQuestion();
        } else {
            Intent intent = new Intent(DieselPetrol.this,
                    AnalyzeAnimation.class);
            startActivity(intent);
            Handler handler = new Handler();
            handler.postDelayed(this::krajKviza, 1000);
        }
    }


    private void krajKviza() {
        Vibrator v = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(400);
        TextView naslovrez;
        Button gumbDialog;
        ImageView zatvori;
        if (brojacPrvi > brojacDrugi) {


            AlertDialog.Builder builder = new AlertDialog.Builder(DieselPetrol.this);
            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.diesel_petrol_popup, new LinearLayout(DieselPetrol.this), false);

            builder.setView(dialogView);
            gumbDialog = dialogView.findViewById(R.id.gumbDialog);
            naslovrez = dialogView.findViewById(R.id.naslovrez);
            naslovrez.setText(getResources().getString(R.string.DieselPetrolResult1));
            zatvori = dialogView.findViewById(R.id.zatvori);
            final AlertDialog dialog = builder.create();
            gumbDialog.setOnClickListener(v14 -> {
                dialog.cancel();
                finish();
            });

            zatvori.setOnClickListener(v13 -> {
                dialog.cancel();
                finish();
            });
            if (dialog.getWindow() != null) {
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
            dialog.show();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(DieselPetrol.this);
            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.diesel_petrol_popup, new LinearLayout(DieselPetrol.this), false);
            builder.setView(dialogView);
            gumbDialog = dialogView.findViewById(R.id.gumbDialog);

            naslovrez = dialogView.findViewById(R.id.naslovrez);
            naslovrez.setText(getResources().getString(R.string.DieselPetrolResult2));
            zatvori = dialogView.findViewById(R.id.zatvori);
            final AlertDialog dialog = builder.create();
            gumbDialog.setOnClickListener(v12 -> {
                dialog.cancel();
                finish();
            });

            zatvori.setOnClickListener(v1 -> {
                dialog.cancel();
                finish();
            });
            if (dialog.getWindow() != null) {
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
            dialog.show();
        }

    }

    public void updateQuestion() {
        question.setText(pitanjaDieselPetrol[brojac]);
        prvi.setText(choicesDieselPetrol[brojac][0]);
        drugi.setText(choicesDieselPetrol[brojac][1]);
        brojac++;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.ostali_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.podijeli) {
            Intent in = new Intent(Intent.ACTION_SEND);
            in.setType("text/plain");
            in.putExtra(Intent.EXTRA_SUBJECT, "Car predictor - 2019");
            String message = "\nhttps://play.google.com/store/apps/details?id=com.varivoda.igor.quiz_whatcarwillyoudrive\n\n";

            in.putExtra(Intent.EXTRA_TEXT, message);
            startActivity(Intent.createChooser(in, "Choose where you want to share:"));
            return true;
        } else if (item.getItemId() == R.id.programer) {
            Intent i = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/developer?id=Igor+Varivoda"));
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

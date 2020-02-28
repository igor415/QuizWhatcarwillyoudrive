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



import java.util.ArrayList;
import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;

public class KindActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    protected Toolbar toolbar;
    @BindView(R.id.answer1)
    protected Button answer1;
    @BindView(R.id.answer2)
    protected Button answer2;
    @BindView(R.id.answer3)
    protected Button answer3;
    @BindView(R.id.pitanje)
    protected TextView pitanje;
    public static List<String> listaPitanja = new ArrayList<>();
    private int brojac = 0, brojacPrvi = 0, brojacDrugi = 0, brojactreci = 0;
    public static String[][] choices = new String[7][3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kind);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("What kind of driver are you?(II)");
        }

        answer1.setOnClickListener(v -> {
            brojacPrvi++;
            updateQuestion();
        });
        answer2.setOnClickListener(v -> {
            brojacDrugi++;
            updateQuestion();
        });
        answer3.setOnClickListener(v -> {
            brojactreci++;
            updateQuestion();
        });
        updateQuestion();

    }




    public void updateQuestion() {

        if (brojac == 7) {
            Intent intent = new Intent(KindActivity.this,
                    AnalyzeAnimation.class);
            startActivity(intent);
            Handler handler = new Handler();
            handler.postDelayed(this::gameOver, 1000);


        } else {
            pitanje.setText(listaPitanja.get(brojac));
            answer1.setText(choices[brojac][0]);
            answer2.setText(choices[brojac][1]);
            answer3.setText(choices[brojac][2]);
            brojac++;
        }
    }

    public void gameOver() {


        Vibrator v = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(400);
        int[] lista = {brojacPrvi, brojacDrugi, brojactreci};
        int max = lista[0];
        int idx = 0;
        int q = 0;
        for (int i = 1; i < lista.length; i++) {
            if (max < lista[i]) {
                max = lista[i];
                idx = i;
            }

        }
        for (int value : lista) {
            if (max == value) {
                q++;
            }
        }
        //
        AlertDialog.Builder builder = new AlertDialog.Builder(KindActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.popup_kind_driver, new LinearLayout(KindActivity.this),false);
        builder.setView(dialogView);
        Button gumbDialog = dialogView.findViewById(R.id.gumbDialog);
        TextView naslovrez = dialogView.findViewById(R.id.naslovrez);
        ImageView zatvori = dialogView.findViewById(R.id.zatvori);
        final AlertDialog dialog = builder.create();
        gumbDialog.setOnClickListener(v12 -> {
            dialog.cancel();
            finish();
        });

        zatvori.setOnClickListener(v1 -> {
            dialog.cancel();
            finish();
        });
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        if (q > 1) {
            naslovrez.setText(getString(R.string.kindResult1));
            if (dialog.getWindow() != null) {
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
            dialog.show();
        } else {
            if (idx == 0) {
                naslovrez.setText(getString(R.string.kindResult2));
                if (dialog.getWindow() != null) {
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }
                dialog.show();
            }
            if (idx == 1) {
                naslovrez.setText(getString(R.string.kindResult3));
                if (dialog.getWindow() != null) {
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }
                dialog.show();
            }
            if (idx == 2) {
                naslovrez.setText(getString(R.string.kindResult4));
                if (dialog.getWindow() != null) {
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }
                dialog.show();
            }

        }

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
        }
        if (item.getItemId() == R.id.programer) {
            Intent i = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/developer?id=Igor+Varivoda"));
            startActivity(i);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

}

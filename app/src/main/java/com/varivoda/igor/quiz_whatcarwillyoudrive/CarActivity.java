package com.varivoda.igor.quiz_whatcarwillyoudrive;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Vibrator;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CarActivity extends AppCompatActivity {
    @BindView(R.id.odgovor1)
    protected Button answer1;
    @BindView(R.id.odgovor2)
    protected Button answer2;
    @BindView(R.id.odgovor3)
    protected Button answer3;
    @BindView(R.id.odgovor4)
    protected Button answer4;
    @BindView(R.id.brojPitanja)
    protected TextView brojPitanja;
    @BindView(R.id.skore)
    protected TextView score;
    @BindView(R.id.toggleButton2)
    protected ToggleButton toggleButton2;
    @BindView(R.id.logo)
    protected ImageView logo;
    @BindView(R.id.toolbar)
    protected Toolbar toolbar;
    private int brojac = 0, skor = 0;
    public static String[] ids = new String[15];
    public static String[][] choicesEasyCar = new String[15][4];
    public static String[] correctEasyCar = new String[15];
    public static String[] idsDifficultCar = new String[15];
    public static String[][] choicesDifficultCar = new String[15][4];
    public static String[] correctDifficultCar = new String[15];
    private String answer;
    private int version;
    private MediaPlayer correct,falsee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        correct = MediaPlayer.create(CarActivity.this,R.raw.truee);
        falsee = MediaPlayer.create(CarActivity.this,R.raw.falsee);
        Intent i = getIntent();
        version = i.getIntExtra(HomeActivity.KLJUC,0);
        if(version==0){
            getSupportActionBar().setTitle(getString(R.string.easyTitleCar));
        }else{
            getSupportActionBar().setTitle(getString(R.string.difficultTitleCar));
        }

        answer1.setOnClickListener(v -> {
            if (answer.equals(answer1.getText().toString())) {
                skor++;
                score.setText(getString(R.string.prikaz_skora,String.valueOf(skor)));
                correct.start();
                if (brojac == 15) {
                    gameOver();
                } else {
                    updateQuestion();
                }
            } else {
                skor--;
                score.setText(getString(R.string.prikaz_skora,String.valueOf(skor)));
                falsee.start();
                if (brojac == 15) {
                    gameOver();
                } else {
                    updateQuestion();
                }
            }
        });
        answer2.setOnClickListener(v -> {
            if (answer.equals(answer2.getText().toString())) {
                skor++;
                score.setText(getString(R.string.prikaz_skora,String.valueOf(skor)));
                correct.start();
                if (brojac == 15) {
                    gameOver();
                } else {
                    updateQuestion();
                }
            } else {
                skor--;
                score.setText(getString(R.string.prikaz_skora,String.valueOf(skor)));
                falsee.start();
                if (brojac == 15) {
                    gameOver();
                } else {
                    updateQuestion();
                }
            }
        });
        answer3.setOnClickListener(v -> {
            if (answer.equals(answer3.getText().toString())) {
                skor++;
                score.setText(getString(R.string.prikaz_skora,String.valueOf(skor)));
                correct.start();
                if (brojac == 15 ){
                    gameOver();
                }
                else {
                    updateQuestion();
                }
            } else {
                skor--;
                score.setText(getString(R.string.prikaz_skora,String.valueOf(skor)));
                falsee.start();
                if (brojac == 15) {
                    gameOver();
                } else {
                    updateQuestion();
                }
            }
        });
        answer4.setOnClickListener(v -> {
            if (answer.equals(answer4.getText().toString())) {
                skor++;
                score.setText(getString(R.string.prikaz_skora,String.valueOf(skor)));
                correct.start();
                if (brojac == 15) {
                    gameOver();
                } else {
                    updateQuestion();
                }
            } else {
                skor--;
                score.setText(getString(R.string.prikaz_skora,String.valueOf(skor)));
                falsee.start();
                if (brojac == 15) {
                    gameOver();
                } else {
                    updateQuestion();
                }
            }
        });
        toggleButton2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                Toast.makeText(CarActivity.this,"Correct answer is: "+answer,Toast.LENGTH_LONG).show();
                toggleButton2.setEnabled(false);
            }
        });
        updateQuestion();

    }

    public void gameOver() {
        Vibrator v = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(400);

        ImageView zatvori;
        TextView naslovrez;
        TextView prikazRezultata;

        if(skor==15){
            AlertDialog.Builder builder = new AlertDialog.Builder(CarActivity.this);
            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.logo_popup_positive,new LinearLayout(CarActivity.this),false);
            builder.setView(dialogView);

            Button gumbDialog = dialogView.findViewById(R.id.gumbDialog);
            gumbDialog.setText(getString(R.string.finish_quiz_res));
            naslovrez = dialogView.findViewById(R.id.naslovrez);
            naslovrez.setText(getString(R.string.excellent_res));
            prikazRezultata = dialogView.findViewById(R.id.prikazRezultata);
            prikazRezultata.setText(getString(R.string.final_score,String.valueOf(skor)));
            zatvori = dialogView.findViewById(R.id.zatvori);
            final AlertDialog dialog = builder.create();
            gumbDialog.setOnClickListener(v16 -> {
                dialog.cancel();
                finish();
            });

            zatvori.setOnClickListener(v15 -> {
                dialog.cancel();
                finish();
            });
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);

            dialog.show();
        }
        else if(skor<15 && skor>6){

            AlertDialog.Builder builder = new AlertDialog.Builder(CarActivity.this);
            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.logo_popup_positive,new LinearLayout(CarActivity.this),false);
            builder.setView(dialogView);

            Button gumbDialog = dialogView.findViewById(R.id.gumbDialog);
            gumbDialog.setText(getString(R.string.improve_better_res));
            naslovrez = dialogView.findViewById(R.id.naslovrez);
            naslovrez.setText(getString(R.string.pretty_good_res));
            prikazRezultata = dialogView.findViewById(R.id.prikazRezultata);
            prikazRezultata.setText(getString(R.string.final_score,String.valueOf(skor)));
            zatvori = dialogView.findViewById(R.id.zatvori);
            final AlertDialog dialog = builder.create();
            gumbDialog.setOnClickListener(v14 -> {
                dialog.cancel();
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            });

            zatvori.setOnClickListener(v13 -> {
                dialog.cancel();
                finish();
            });
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);

            dialog.show();
        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(CarActivity.this);
            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.logo_popup_negative,new LinearLayout(CarActivity.this),false);

            builder.setView(dialogView);

            Button gumbDialog = dialogView.findViewById(R.id.gumbDialog);
            gumbDialog.setText(getString(R.string.try_again_res));
            naslovrez = dialogView.findViewById(R.id.naslovrez);
            naslovrez.setText(getString(R.string.improve_score_res));
            prikazRezultata = dialogView.findViewById(R.id.prikazRezultata);
            prikazRezultata.setText(getString(R.string.final_score,String.valueOf(skor)));
            zatvori = dialogView.findViewById(R.id.zatvori);
            final AlertDialog dialog = builder.create();
            gumbDialog.setOnClickListener(v12 -> {
                dialog.cancel();
                Intent intent = getIntent();
                finish();
                startActivity(intent);

            });

            zatvori.setOnClickListener(v1 -> {
                dialog.cancel();
                finish();
            });
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);

            dialog.show();



        }

    }

    public void updateQuestion() {
        switch (version){
            case 0:
                Picasso.get().load(ids[brojac]).into(logo);
                answer1.setText(choicesEasyCar[brojac][0]);
                answer2.setText(choicesEasyCar[brojac][1]);
                answer3.setText(choicesEasyCar[brojac][2]);
                answer4.setText(choicesEasyCar[brojac][3]);
                answer = correctEasyCar[brojac];
                brojac++;
                brojPitanja.setText(getString(R.string.prikazBrojaca,String.valueOf(brojac)));
                break;
            case 1:
                Picasso.get().load(idsDifficultCar[brojac]).into(logo);
                answer1.setText(choicesDifficultCar[brojac][0]);
                answer2.setText(choicesDifficultCar[brojac][1]);
                answer3.setText(choicesDifficultCar[brojac][2]);
                answer4.setText(choicesDifficultCar[brojac][3]);
                answer = correctDifficultCar[brojac];
                brojac++;
                brojPitanja.setText(getString(R.string.prikazBrojaca,String.valueOf(brojac)));
                break;
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
        if(item.getItemId()==R.id.podijeli){
            Intent in = new Intent(Intent.ACTION_SEND);
            in.setType("text/plain");
            in.putExtra(Intent.EXTRA_SUBJECT, "Car predictor - 2019");
            String message = "\nhttps://play.google.com/store/apps/details?id=com.varivoda.igor.quiz_whatcarwillyoudrive\n\n";
            in.putExtra(Intent.EXTRA_TEXT, message);
            startActivity(Intent.createChooser(in, "Choose where you want to share:"));
            return true;
        }else if(item.getItemId()==R.id.programer){
            Intent i = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/developer?id=Igor+Varivoda"));
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

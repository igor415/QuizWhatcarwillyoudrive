package com.varivoda.igor.quiz_whatcarwillyoudrive;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Vibrator;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
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


import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SharedActivity extends AppCompatActivity {
    public static List<String> listaWhat = new ArrayList<>();
    public static List<String> listaWhich = new ArrayList<>();
    public static List<String> listaKind = new ArrayList<>();
    public static Map<String, String> mapaWhatSlike = new HashMap<>();
    public static Map<String, String> mapaBuySlike = new HashMap<>();
    public static Map<String, String> mapaWhichSlike = new HashMap<>();
    public static List<String> listaBuy = new ArrayList<>();
    public static String[][] choicesKind = new String[7][4];
    public static String[][] choicesBuy = new String[7][4];
    public static String[][] choicesWhat = new String[7][4];
    public static String[][] choicesWhich = new String[7][4];

    private TextView naslovrez;
    private Button gumbDialog;
    private ImageView zatvori, glavnaSlika;
    @BindView(R.id.toolbar)
    protected Toolbar toolbar;
    @BindView(R.id.answer1)
    protected Button answer1;
    @BindView(R.id.answer2)
    protected Button answer2;
    @BindView(R.id.answer3)
    protected Button answer3;
    @BindView(R.id.answer4)
    protected Button answer4;
    @BindView(R.id.naslov)
    protected TextView naslov;
    @BindView(R.id.pitanje)
    protected TextView pitanje;
    @BindView(R.id.layout)
    protected ConstraintLayout layout;
    private int brojac = 0, brojacPrvi = 0, brojacDrugi = 0, brojactreci = 0, brojacCetvrti = 0;
    private int switchValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        Intent i = getIntent();
        switchValue = i.getIntExtra(HomeActivity.KLJUC, 0);
        switch (switchValue) {
            case 0:
                layout.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.slikica_p));
                getSupportActionBar().setTitle(getString(R.string.what_car_will_you_drive_title));
                break;
            case 1:
                layout.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.sl));
                getSupportActionBar().setTitle(getString(R.string.what_kind_of_driver_are_you_title));
                break;
            case 2:
                layout.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.shape_buy_should));
                getSupportActionBar().setTitle(getString(R.string.what_car_should_you_buy_title));
                break;
            case 3:
                layout.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.shape_what_car_you));
                getSupportActionBar().setTitle(getString(R.string.what_car_are_you_title));
                break;
        }

        List<String> listaNaslova = Arrays.asList(getResources().getStringArray(R.array.listaNaslova));
        naslov.setText(listaNaslova.get(switchValue));

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
        answer4.setOnClickListener(v -> {
            brojacCetvrti++;
            updateQuestion();
        });
        updateQuestion();
    }

    public void updateQuestion() {

        switch (switchValue) {
            case 0:
                if (brojac == 7) {
                    Intent intent = new Intent(SharedActivity.this,
                            AnalyzeAnimation.class);
                    startActivity(intent);
                    Handler handler = new Handler();
                    handler.postDelayed(SharedActivity.this::gameOver, 1000);

                } else {
                    pitanje.setText(listaWhich.get(brojac));
                    answer1.setText(choicesWhich[brojac][0]);
                    answer2.setText(choicesWhich[brojac][1]);
                    answer3.setText(choicesWhich[brojac][2]);
                    answer4.setText(choicesWhich[brojac][3]);
                    brojac++;
                }
                break;
            case 1:
                if (brojac == 7) {
                    Intent intent = new Intent(SharedActivity.this,
                            AnalyzeAnimation.class);
                    startActivity(intent);
                    Handler handler = new Handler();
                    handler.postDelayed(this::gameOver, 1000);
                    break;
                } else {
                    pitanje.setText(listaKind.get(brojac));
                    answer1.setText(choicesKind[brojac][0]);
                    answer2.setText(choicesKind[brojac][1]);
                    answer3.setText(choicesKind[brojac][2]);
                    answer4.setText(choicesKind[brojac][3]);
                    brojac++;
                }

                break;
            case 2:
                if (brojac == 7) {


                    Intent intent = new Intent(SharedActivity.this,
                            AnalyzeAnimation.class);
                    startActivity(intent);
                    Handler handler = new Handler();
                    handler.postDelayed(SharedActivity.this::gameOver, 1000);
                    break;
                } else {
                    pitanje.setText(listaBuy.get(brojac));
                    answer1.setText(choicesBuy[brojac][0]);
                    answer2.setText(choicesBuy[brojac][1]);
                    answer3.setText(choicesBuy[brojac][2]);
                    answer4.setText(choicesBuy[brojac][3]);
                    brojac++;
                }

                break;
            case 3:
                if (brojac == 7) {
                    Intent intent = new Intent(SharedActivity.this,
                            AnalyzeAnimation.class);
                    startActivity(intent);
                    Handler handler = new Handler();
                    handler.postDelayed(SharedActivity.this::gameOver, 1000);
                } else {
                    pitanje.setText(listaWhat.get(brojac));
                    answer1.setText(choicesWhat[brojac][0]);
                    answer2.setText(choicesWhat[brojac][1]);
                    answer3.setText(choicesWhat[brojac][2]);
                    answer4.setText(choicesWhat[brojac][3]);
                    brojac++;
                }

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

        if (item.getItemId() == R.id.podijeli) {
            Intent in = new Intent(Intent.ACTION_SEND);
            in.setType("text/plain");
            in.putExtra(Intent.EXTRA_SUBJECT, "Car predictor - 2019");
            String message = "\nhttps://play.google.com/store/apps/details?id=com.varivoda.igor.quiz_whatcarwillyoudrive\n\n";
            in.putExtra(Intent.EXTRA_TEXT, message);
            startActivity(Intent.createChooser(in, "Choose where you want to share:"));
        } else if (item.getItemId() == R.id.programer) {
            Intent i = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/developer?id=Igor+Varivoda"));
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void gameOver() {
        Vibrator v = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(400);

        switch (switchValue) {
            case 0:
                driveCarDialog();
                break;
            case 1:
                kindCarDialog();
                break;
            case 2:
                buyCarDialog();
                break;
            case 3:
                whatCarDialog();
                break;
        }

    }

    public void whatCarDialog() {
        int[] lista = {brojacPrvi, brojacDrugi, brojactreci, brojacCetvrti};
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
        AlertDialog.Builder builder = new AlertDialog.Builder(SharedActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.popup_what_buy, new LinearLayout(SharedActivity.this), false);
        dialogView.setBackgroundResource(R.drawable.which_drive_dialog_shape);
        builder.setView(dialogView);


        gumbDialog = dialogView.findViewById(R.id.gumbDialog);
        gumbDialog.setText(getString(R.string.finish_quiz_res));
        naslovrez = dialogView.findViewById(R.id.naslovrez);
        naslovrez.setText(getString(R.string.this_is_you_res));
        glavnaSlika = dialogView.findViewById(R.id.glavnaSlika);
        zatvori = dialogView.findViewById(R.id.zatvori);
        final AlertDialog dialog = builder.create();
        gumbDialog.setOnClickListener(v -> {
            dialog.cancel();
            finish();
        });

        zatvori.setOnClickListener(v -> {
            dialog.cancel();
            finish();
        });
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        //
        if (q > 1) {
            Picasso.get().load(mapaWhatSlike.get("cla")).into(glavnaSlika);
            dialog.show();
        } else {
            if (idx == 0) {
                Picasso.get().load(mapaWhatSlike.get("bycicle")).into(glavnaSlika);
                dialog.show();
            }
            if (idx == 1) {

                Picasso.get().load(mapaWhatSlike.get("aveo")).into(glavnaSlika);
                dialog.show();
            }
            if (idx == 2) {

                Picasso.get().load(mapaWhatSlike.get("malibu")).into(glavnaSlika);
                dialog.show();
            }
            if (idx == 3) {
                Picasso.get().load(mapaWhatSlike.get("gulia")).into(glavnaSlika);
                dialog.show();
            }
        }
        //
    }

    public void driveCarDialog() {
        int[] lista = {brojacPrvi, brojacDrugi, brojactreci, brojacCetvrti};
        int max = lista[0];
        int idx = 0;
        int q = 0;
        for (int i = 1; i < lista.length; i++) {
            if (max < lista[i]) {
                max = lista[i];
                idx = i;
            }

        }
        for(int val : lista) {
            if (max == val) {
                q++;
            }
        }
        //
        AlertDialog.Builder builder = new AlertDialog.Builder(SharedActivity.this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.popup_what_buy, new LinearLayout(SharedActivity.this),false);
        dialogView.setBackgroundResource(R.drawable.which_drive_dialog_shape);
        builder.setView(dialogView);
        gumbDialog = dialogView.findViewById(R.id.gumbDialog);
        naslovrez = dialogView.findViewById(R.id.naslovrez);
        glavnaSlika = dialogView.findViewById(R.id.glavnaSlika);
        zatvori = dialogView.findViewById(R.id.zatvori);
        final AlertDialog dialog = builder.create();
        gumbDialog.setOnClickListener(v -> {
                dialog.cancel();
                Intent intent = getIntent();
                finish();
                startActivity(intent);

        });

        zatvori.setOnClickListener(v -> {
                dialog.cancel();
                finish();

        });
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        //
        if (q > 1) {
            Picasso.get().load(mapaWhichSlike.get("fordEscape")).into(glavnaSlika);
            dialog.show();
        } else {
            if (idx == 0) {
                Picasso.get().load(mapaWhichSlike.get("spark")).into(glavnaSlika);
                dialog.show();
            }
            if (idx == 1) {
                Picasso.get().load(mapaWhichSlike.get("civic")).into(glavnaSlika);
                dialog.show();
            }
            if (idx == 2) {
                Picasso.get().load(mapaWhichSlike.get("corvette")).into(glavnaSlika);
                dialog.show();
            }
            if (idx == 3) {
                Picasso.get().load(mapaWhichSlike.get("lambo")).into(glavnaSlika);
                dialog.show();
            }
        }
    }

    public void kindCarDialog() {
        int[] lista = {brojacPrvi, brojacDrugi, brojactreci, brojacCetvrti};
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
        AlertDialog.Builder builder = new AlertDialog.Builder(SharedActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.popup_kind_driver, new LinearLayout(SharedActivity.this), false);
        builder.setView(dialogView);
        gumbDialog = dialogView.findViewById(R.id.gumbDialog);
        naslovrez = dialogView.findViewById(R.id.naslovrez);
        zatvori = dialogView.findViewById(R.id.zatvori);
        final AlertDialog dialog = builder.create();
        gumbDialog.setOnClickListener(v -> {
            dialog.cancel();
            finish();
        });

        zatvori.setOnClickListener(v -> {
            dialog.cancel();
            finish();
        });
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        if (q > 1) {
            naslovrez.setText(getString(R.string.SharedKindResult1));
            if (dialog.getWindow() != null) {
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
            dialog.show();
        } else {
            if (idx == 0) {
                naslovrez.setText(getString(R.string.SharedKindResult2));
                if (dialog.getWindow() != null) {
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }
                dialog.show();
            }
            if (idx == 1) {
                naslovrez.setText(getString(R.string.SharedKindResult3));
                if (dialog.getWindow() != null) {
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }
                dialog.show();
            }
            if (idx == 2) {
                naslovrez.setText(getString(R.string.SharedKindResult4));
                if (dialog.getWindow() != null) {
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }
                dialog.show();
            }
            if (idx == 3) {
                naslovrez.setText(getString(R.string.SharedKindResult5));
                if (dialog.getWindow() != null) {
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }
                dialog.show();
            }
        }

    }

    public void buyCarDialog() {
        int[] lista = {brojacPrvi, brojacDrugi, brojactreci, brojacCetvrti};
        int max = lista[0];
        int idx = 0;
        int q = 0;
        for (int i = 1; i < lista.length; i++) {
            if (max < lista[i]) {
                max = lista[i];
                idx = i;
            }

        }
        for (int val : lista) {
            if (max == val) {
                q++;
            }
        }
        //
        AlertDialog.Builder builder = new AlertDialog.Builder(SharedActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.popup_what_buy, new LinearLayout(SharedActivity.this),false);
        builder.setView(dialogView);
        gumbDialog = dialogView.findViewById(R.id.gumbDialog);
        naslovrez = dialogView.findViewById(R.id.naslovrez);
        glavnaSlika = dialogView.findViewById(R.id.glavnaSlika);
        zatvori = dialogView.findViewById(R.id.zatvori);
        final AlertDialog dialog = builder.create();
        gumbDialog.setOnClickListener(v -> {
            dialog.cancel();
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        });

        zatvori.setOnClickListener(v -> {
            dialog.cancel();
            finish();
        });
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        //
        if (q > 1) {
            Picasso.get().load(mapaBuySlike.get("golf")).into(glavnaSlika);
            dialog.show();
        } else {
            if (idx == 0) {
                Picasso.get().load(mapaBuySlike.get("fiesta")).into(glavnaSlika);
                dialog.show();
            }
            if (idx == 1) {
                Picasso.get().load(mapaBuySlike.get("mazda3")).into(glavnaSlika);
                dialog.show();
            }
            if (idx == 2) {
                Picasso.get().load(mapaBuySlike.get("audiq3")).into(glavnaSlika);
                dialog.show();
            }
            if (idx == 3) {
                Picasso.get().load(mapaBuySlike.get("camaro")).into(glavnaSlika);
                dialog.show();
            }
        }


    }
}

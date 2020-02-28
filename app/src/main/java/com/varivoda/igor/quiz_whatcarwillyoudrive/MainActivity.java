package com.varivoda.igor.quiz_whatcarwillyoudrive;

import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.varivoda.igor.quiz_whatcarwillyoudrive.DbFlowBaza.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.varivoda.igor.quiz_whatcarwillyoudrive.CarActivity.choicesDifficultCar;
import static com.varivoda.igor.quiz_whatcarwillyoudrive.CarActivity.choicesEasyCar;
import static com.varivoda.igor.quiz_whatcarwillyoudrive.CarActivity.correctDifficultCar;
import static com.varivoda.igor.quiz_whatcarwillyoudrive.CarActivity.correctEasyCar;
import static com.varivoda.igor.quiz_whatcarwillyoudrive.CarActivity.ids;
import static com.varivoda.igor.quiz_whatcarwillyoudrive.CarActivity.idsDifficultCar;
import static com.varivoda.igor.quiz_whatcarwillyoudrive.DieselPetrol.choicesDieselPetrol;
import static com.varivoda.igor.quiz_whatcarwillyoudrive.DieselPetrol.pitanjaDieselPetrol;
import static com.varivoda.igor.quiz_whatcarwillyoudrive.KindActivity.choices;
import static com.varivoda.igor.quiz_whatcarwillyoudrive.KindActivity.listaPitanja;
import static com.varivoda.igor.quiz_whatcarwillyoudrive.SharedActivity.choicesBuy;
import static com.varivoda.igor.quiz_whatcarwillyoudrive.SharedActivity.choicesKind;
import static com.varivoda.igor.quiz_whatcarwillyoudrive.SharedActivity.choicesWhat;
import static com.varivoda.igor.quiz_whatcarwillyoudrive.SharedActivity.choicesWhich;
import static com.varivoda.igor.quiz_whatcarwillyoudrive.SharedActivity.listaBuy;
import static com.varivoda.igor.quiz_whatcarwillyoudrive.SharedActivity.listaKind;
import static com.varivoda.igor.quiz_whatcarwillyoudrive.SharedActivity.listaWhat;
import static com.varivoda.igor.quiz_whatcarwillyoudrive.SharedActivity.listaWhich;
import static com.varivoda.igor.quiz_whatcarwillyoudrive.SharedActivity.mapaBuySlike;
import static com.varivoda.igor.quiz_whatcarwillyoudrive.SharedActivity.mapaWhatSlike;
import static com.varivoda.igor.quiz_whatcarwillyoudrive.SharedActivity.mapaWhichSlike;


public class MainActivity extends AppCompatActivity {
    @BindView(R.id.progress_bar)
    protected ProgressBar progressBar;
    @BindView(R.id.postotak_ucit)
    protected TextView textView;
    @BindView(R.id.toolbar)
    protected Toolbar toolbar;
    private static final String TAG = "MainActivity";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setCredentials();
        auth = FirebaseAuth.getInstance();
        signIn();
        progressBar.setMax(100);
        progressBar.setScaleY(3f);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.car_predictor_res));
        }

        Thread welcomeThread = new Thread() {

            @Override
            public void run() {
                try {
                    super.run();
                    progressAnimation();
                    sleep(2000);

                } catch (Exception e) {
                    Log.d(TAG, e.getMessage());
                } finally {

                    Intent i = new Intent(MainActivity.this,
                            HomeActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        };
        welcomeThread.start();

    }


    private void signIn(){
        String email="",pass="";
        List<User> users = SQLite.select()
                .from(User.class)
                .queryList();
        for(User u : users){
            if(u.getId()==1){
                email = u.getEmail();
                pass = u.getPassword();
            }
        }
        auth.signInWithEmailAndPassword(email,pass)
                .addOnCompleteListener(this, task -> {
                    if(task.isSuccessful()){
                        FirebaseUser user = auth.getCurrentUser();
                        Log.d(TAG, user.getEmail()+"  "+user.getUid());
                    }
                });
    }

    public void progressAnimation() {
        Animacija a = new Animacija(this,progressBar, textView, 0f, 100f);
        a.setDuration(2000);
        progressBar.setAnimation(a);
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
    private void loadDataKindActivity() {
        CollectionReference collectionReference = db.collection("KindActivity");
        Query query = collectionReference.orderBy("id", Query.Direction.ASCENDING);
        query.get().addOnSuccessListener(queryDocumentSnapshots -> {
            for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                KindOpcija opcija = documentSnapshot.toObject(KindOpcija.class);
                listaPitanja.add(opcija.getPitanje());
                choices[opcija.getId()][0] = opcija.getOdgovor1();
                choices[opcija.getId()][1] = opcija.getOdgovor2();
                choices[opcija.getId()][2] = opcija.getOdgovor3();
            }
        });
    }
    private void loadDataDieselPetrol(){
        CollectionReference collectionReference = db.collection("DieselPetrol");
        Query query = collectionReference.orderBy("id",Query.Direction.ASCENDING);
        query.get().addOnSuccessListener(queryDocumentSnapshots -> {
            for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                DieselPetrolOpcija opcija = documentSnapshot.toObject(DieselPetrolOpcija.class);
                pitanjaDieselPetrol[opcija.getId()] = opcija.getPitanje();
                choicesDieselPetrol[opcija.getId()][0] = opcija.getOdgovor1();
                choicesDieselPetrol[opcija.getId()][1] = opcija.getOdgovor2();
            }
        });
    }
    private void loadDataEasyCar(){
        CollectionReference collectionReference = db.collection("EasyCar");
        Query query = collectionReference.orderBy("id",Query.Direction.ASCENDING);
        query.get().addOnSuccessListener(queryDocumentSnapshots -> {
            for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                EasyCarOpcija opcija = documentSnapshot.toObject(EasyCarOpcija.class);
                ids[opcija.getId()] = opcija.getUrl();
                correctEasyCar[opcija.getId()] = opcija.getTocanOdgovor();
                choicesEasyCar[opcija.getId()][0] = opcija.getOdgovor1();
                choicesEasyCar[opcija.getId()][1] = opcija.getOdgovor2();
                choicesEasyCar[opcija.getId()][2] = opcija.getOdgovor3();
                choicesEasyCar[opcija.getId()][3] = opcija.getOdgovor4();
            }
        });
    }
    private void loadDataDifficultCar(){
        CollectionReference collectionReference = db.collection("DifficultCar");
        Query query = collectionReference.orderBy("id",Query.Direction.ASCENDING);
        query.get().addOnSuccessListener(queryDocumentSnapshots -> {
            for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                DifficultCarOpcija opcija = documentSnapshot.toObject(DifficultCarOpcija.class);
                idsDifficultCar[opcija.getId()] = opcija.getUrl();
                choicesDifficultCar[opcija.getId()][0] = opcija.getOdgovor1();
                choicesDifficultCar[opcija.getId()][1] = opcija.getOdgovor2();
                choicesDifficultCar[opcija.getId()][2] = opcija.getOdgovor3();
                choicesDifficultCar[opcija.getId()][3] = opcija.getOdgovor4();
                correctDifficultCar[opcija.getId()] = opcija.getTocanOdgovor();
            }
        });
    }

    private void loadDataSharedKind(){
        CollectionReference collectionReference = db.collection("SharedKind");
        Query query = collectionReference.orderBy("id",Query.Direction.ASCENDING);
        query.get().addOnSuccessListener(queryDocumentSnapshots -> {
            for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                SharedKindOpcija opcija = documentSnapshot.toObject(SharedKindOpcija.class);
                listaKind.add(opcija.getPitanje());
                choicesKind[opcija.getId()][0] = opcija.getOdgovor1();
                choicesKind[opcija.getId()][1] = opcija.getOdgovor2();
                choicesKind[opcija.getId()][2] = opcija.getOdgovor3();
                choicesKind[opcija.getId()][3] = opcija.getOdgovor4();
            }
        });
    }
    private void loadDataSharedWhat(){
        CollectionReference collectionReference = db.collection("SharedWhat");
        Query query = collectionReference.orderBy("id",Query.Direction.ASCENDING);
        query.get().addOnSuccessListener(queryDocumentSnapshots -> {
            for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                SharedKindOpcija opcija = documentSnapshot.toObject(SharedKindOpcija.class);
                listaWhat.add(opcija.getPitanje());
                choicesWhat[opcija.getId()][0] = opcija.getOdgovor1();
                choicesWhat[opcija.getId()][1] = opcija.getOdgovor2();
                choicesWhat[opcija.getId()][2] = opcija.getOdgovor3();
                choicesWhat[opcija.getId()][3] = opcija.getOdgovor4();
            }
        });
    }
    private void loadDataSharedKindSlike(){
        CollectionReference collectionReference = db.collection("slike");
        Query query = collectionReference.whereEqualTo("id",0);
        query.get().addOnSuccessListener(queryDocumentSnapshots -> {
            for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                SlikaOpcija opcija = documentSnapshot.toObject(SlikaOpcija.class);
                mapaWhatSlike.put(opcija.getName(),opcija.getUrl());
            }
        });
    }
    private void loadDataSharedBuy(){
        CollectionReference collectionReference = db.collection("SharedBuy");
        Query query = collectionReference.orderBy("id",Query.Direction.ASCENDING);
        query.get().addOnSuccessListener(queryDocumentSnapshots -> {
            for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                SharedKindOpcija opcija = documentSnapshot.toObject(SharedKindOpcija.class);
                listaBuy.add(opcija.getPitanje());
                choicesBuy[opcija.getId()][0] = opcija.getOdgovor1();
                choicesBuy[opcija.getId()][1] = opcija.getOdgovor2();
                choicesBuy[opcija.getId()][2] = opcija.getOdgovor3();
                choicesBuy[opcija.getId()][3] = opcija.getOdgovor4();
            }
        });
    }
    private void loadDataSharedBuySlike(){
        CollectionReference collectionReference = db.collection("slike");
        Query query = collectionReference.whereEqualTo("id",1);
        query.get().addOnSuccessListener(queryDocumentSnapshots -> {
            for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                SlikaOpcija opcija = documentSnapshot.toObject(SlikaOpcija.class);
                mapaBuySlike.put(opcija.getName(),opcija.getUrl());
            }
        });
    }
    private void loadDataSharedWhich(){
        CollectionReference collectionReference = db.collection("SharedWhich");
        Query query = collectionReference.orderBy("id",Query.Direction.ASCENDING);
        query.get().addOnSuccessListener(queryDocumentSnapshots -> {
            for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                SharedKindOpcija opcija = documentSnapshot.toObject(SharedKindOpcija.class);
                listaWhich.add(opcija.getPitanje());
                choicesWhich[opcija.getId()][0] = opcija.getOdgovor1();
                choicesWhich[opcija.getId()][1] = opcija.getOdgovor2();
                choicesWhich[opcija.getId()][2] = opcija.getOdgovor3();
                choicesWhich[opcija.getId()][3] = opcija.getOdgovor4();
            }
        });
    }
    private void loadDataSharedWhichSlike(){
        CollectionReference collectionReference = db.collection("slike");
        Query query = collectionReference.whereEqualTo("id",2);
        query.get().addOnSuccessListener(queryDocumentSnapshots -> {
            for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                SlikaOpcija opcija = documentSnapshot.toObject(SlikaOpcija.class);
                mapaWhichSlike.put(opcija.getName(),opcija.getUrl());
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
       loadDataKindActivity();
        loadDataDieselPetrol();
        loadDataEasyCar();
        loadDataDifficultCar();
        loadDataSharedKind();
        loadDataSharedWhat();
        loadDataSharedKindSlike();
        loadDataSharedBuy();
        loadDataSharedBuySlike();
        loadDataSharedWhich();
        loadDataSharedWhichSlike();
    }

    private void setCredentials() {
        User user = new User();
        user.setId(1);
        user.setEmail("igor.varivodaa@gmail.com");
        user.setPassword("10451045");
        user.save();
    }

}



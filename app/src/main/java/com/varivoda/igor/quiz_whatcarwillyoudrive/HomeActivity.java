package com.varivoda.igor.quiz_whatcarwillyoudrive;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.varivoda.igor.quiz_whatcarwillyoudrive.CarActivity.correctDifficultCar;
import static com.varivoda.igor.quiz_whatcarwillyoudrive.CarActivity.correctEasyCar;
import static com.varivoda.igor.quiz_whatcarwillyoudrive.KindActivity.listaPitanja;


public class HomeActivity extends AppCompatActivity {

    public static final String KLJUC = "com.varivoda.igor.quiz_whatcarwillyoudrive.kljuc";
    private long time;
    private Menu menu;
    private Toast toast;
    @BindView(R.id.toolbar)
    protected Toolbar toolbar;
    @BindView(R.id.recycler_view)
    protected RecyclerView recyclerView;

    public final static String PREFS = "PrefsFile";
    private SharedPreferences settings = null;
    private SharedPreferences.Editor editor = null;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference homeRef = db.collection("home");
    private HomeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.car_predictor_res));
        }

        settings = getSharedPreferences(PREFS, MODE_PRIVATE);
        editor = settings.edit();

        // First time running app
        if (!settings.contains("lastRun"))
            enableNotification();
        else
            recordRunTime();


        setUpRecyclerView();

    }

    private void setUpRecyclerView() {
        Query query = homeRef.orderBy("id",Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Opcija> options = new FirestoreRecyclerOptions.Builder<Opcija>()
                .setQuery(query,Opcija.class).build();
        adapter = new HomeAdapter(options);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener((documentSnapshot, position) -> {
            switch (position) {
                case 0:
                    callActivity(SharedActivity.class,0);
                    break;
                case 1:
                    callActivity(SharedActivity.class,1);
                    break;
                case 2:
                    callActivity(SharedActivity.class,2);
                    break;
                case 3:
                    callActivity(SharedActivity.class,3);
                    break;
                case 4:
                    callActivity(DieselPetrol.class,-1);
                    break;
                case 5:
                    if(correctEasyCar.length<1){
                        ToastMsg();
                    }else{
                        callActivity(CarActivity.class,0);
                    }
                    break;
                case 6:
                    if(correctDifficultCar.length<1){
                        ToastMsg();
                    }else{
                        callActivity(CarActivity.class,1);
                    }
                    break;
                case 7:
                    if(listaPitanja.size()<1){
                        ToastMsg();
                    }else{
                        callActivity(KindActivity.class,-1);
                    }

                    break;
            }
        });
    }

    private void ToastMsg(){
        Toast.makeText(HomeActivity.this, "Data couldn't be loaded. Check your internet connection!", Toast.LENGTH_LONG).show();
    }

    private void callActivity(Class c,int num){
        Intent i = new Intent(HomeActivity.this,c);
        if(num!=-1) {
            i.putExtra(KLJUC, num);
        }
        startActivity(i);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }


    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }



    @Override
    public void onBackPressed() {

        if (time + 3200 > System.currentTimeMillis()) {
            toast.cancel();
            super.onBackPressed();
            return;
        } else {
            toast = Toast.makeText(this, "Press back again to exit!", Toast.LENGTH_LONG);
            toast.show();
        }
        time = System.currentTimeMillis();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        this.menu = menu;
        if (settings.contains("enabled")) {
            boolean b = settings.getBoolean("enabled", true);
            if (b) {
                menu.findItem(R.id.enable_notification).setIcon(R.drawable.ic_check_black_24dp);
                menu.findItem(R.id.disable_notification).setIcon(R.drawable.ic_check_white);
            } else {
                menu.findItem(R.id.disable_notification).setIcon(R.drawable.ic_check_black_24dp);
                menu.findItem(R.id.enable_notification).setIcon(R.drawable.ic_check_white);
            }

        }
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
        }else if (item.getItemId() == R.id.disable_notification) {
            disableNotification();
            menu.findItem(R.id.disable_notification).setIcon(R.drawable.ic_check_black_24dp);
            menu.findItem(R.id.enable_notification).setIcon(R.drawable.ic_check_white);
            return true;

        }else if (item.getItemId() == R.id.enable_notification) {
            enableNotification();
            menu.findItem(R.id.enable_notification).setIcon(R.drawable.ic_check_black_24dp);
            menu.findItem(R.id.disable_notification).setIcon(R.drawable.ic_check_white);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void recordRunTime() {
        editor.putLong("lastRun", System.currentTimeMillis());
        editor.commit();
    }

    public void enableNotification() {
        editor.putLong("lastRun", System.currentTimeMillis());
        editor.putBoolean("enabled", true);
        editor.commit();

    }

    public void disableNotification() {
        editor.putBoolean("enabled", false);
        editor.commit();

    }
}

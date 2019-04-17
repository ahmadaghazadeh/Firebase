package com.github.ahmadaghazadeh.firebase.old;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ahmadaghazadeh.firebase.R;
import com.github.ahmadaghazadeh.firebase.data.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public class MainActivity extends AppCompatActivity {
//    private FirebaseAnalytics mFirebaseAnalytics;
//    private FirebaseAuth mAuth;
//    private FirebaseRemoteConfig mFirebaseRemoteConfig;
//TextView txtEmail;
//TextView txtPassword;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        try {
//            FirebaseApp app= FirebaseApp.initializeApp(this);
//
//            mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
//            mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
//            mFirebaseRemoteConfig.setDefaults(R.xml.remote_config_defaults);
//            mAuth = FirebaseAuth.getInstance();
//            txtEmail=findViewById(R.id.txtEmail);
//            txtPassword=findViewById(R.id.txtPassword);
//
//
//            setContentView(R.layout.activity_main);
//            Toolbar toolbar = findViewById(R.id.toolbar);
//            setSupportActionBar(toolbar);
//
//            FloatingActionButton fab = findViewById(R.id.fab);
//            fab.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    writeData(); //OK
//                   // setEvent();
//
//                    //getRemoteConfig(); //OK
//                    FirebaseCrash.log("Activity created");
//                    FirebaseCrash.logcat(Log.ERROR, "", "NPE caught");
//
//                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
//                }
//            });
//            Button btnLogin=findViewById(R.id.btnLogin);
//            Button btnSignin=findViewById(R.id.btnSignin);
//            Button btnRead=findViewById(R.id.btnRead);
//
//            btnSignin.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    signin(txtEmail.getText().toString(),txtPassword.getText().toString());
//                }
//            });
//
//        } catch (Exception ex) {
//            FirebaseCrash.logcat(Log.ERROR, "Error", "NPE caught");
//            FirebaseCrash.report(ex);
//        }
//
//    }
//
//
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        try {
//            updateUI(currentUser);
//        } catch (Exception ex) {
//            FirebaseCrash.logcat(Log.ERROR, "Error", "NPE caught");
//            FirebaseCrash.report(ex);
//        }
//
//    }
//
//    private void updateUI(FirebaseUser currentUser) {
//        if(currentUser!=null){
//            Toast.makeText(MainActivity.this, currentUser.getDisplayName() +
//                            currentUser.getPhoneNumber(),
//                    Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    private void setEvent() {
//        Bundle bundle = new Bundle();
//        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "1");
//        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Ahmad");
//        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
//        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
//    }
//
//    private void signin(String user, String password) {
//
//        mAuth.createUserWithEmailAndPassword(user, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d("test", "createUserWithEmail:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
//
//                            updateUI(user);
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w("test", "createUserWithEmail:failure", task.getException());
//                            Toast.makeText(MainActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
//                        }
//
//                        // ...
//                    }
//                });
//    }
//    private void login(String user, String password) {
//
//        mAuth.signInWithEmailAndPassword(user, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d("test", "createUserWithEmail:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w("test", "createUserWithEmail:failure", task.getException());
//                            Toast.makeText(MainActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
//                        }
//
//                        // ...
//                    }
//                });
//    }
//
//    private void initNewUser(){
//        // Write a message to the database
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("users");
//        String userId = myRef.push().getKey();
//        if(userId!=null){
//            myRef.child(userId).setValue("Hello, World!");
//        }
//    }
//
//
//    private void writeData(){
//        // Write a message to the database
//        User user=new User();
//
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("users");
//        String userId = myRef.push().getKey();
//        // Save in pref userid
//        if(userId!=null){
//            myRef.child(userId).setValue(user);
//        }
//    }
//
//    private void getRemoteConfig(){
//        // cacheExpirationSeconds is set to cacheExpiration here, indicating the next fetch request
//// will use fetch data from the Remote Config service, rather than cached parameter values,
//// if cached parameter values are more than cacheExpiration seconds old.
//// See Best Practices in the README for more information.
//        mFirebaseRemoteConfig.fetch(60)
//                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            Toast.makeText(MainActivity.this, "Fetch Succeeded",
//                                    Toast.LENGTH_SHORT).show();
//                            String welcomeMessage = mFirebaseRemoteConfig.getString("enable_adad");
//                            // After config data is successfully fetched, it must be activated before newly fetched
//                            // values are returned.
//                            mFirebaseRemoteConfig.activateFetched();
//                        } else {
//                            Toast.makeText(MainActivity.this, "Fetch Failed",
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                        //displayWelcomeMessage();
//                    }
//                });
//    }
}

package com.github.ahmadaghazadeh.firebase.ui.login;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.Toast;

import com.github.ahmadaghazadeh.firebase.R;
import com.github.ahmadaghazadeh.firebase.app.C;
import com.github.ahmadaghazadeh.firebase.databinding.ActivityLoginBinding;
import com.github.ahmadaghazadeh.firebase.utils.base.activity.BaseActivity;
import com.github.ahmadaghazadeh.firebase.utils.exception.ApiException;
import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import javax.inject.Inject;

public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel>
        implements ILoginNavigator  {
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    @Inject
    ViewModelProvider.Factory factory;
    int RC_SIGN_IN=1001;

    int counter=1;

    public static Intent newInstance(Context context) {
        return new Intent(context, LoginActivity.class);
    }


    @Override
    public LoginViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(LoginViewModel.class);
    }

    @Override
    public int getBindingVariable() {
        return 1;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mViewModel.setNavigator(this);


            mViewModel.init();
          GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        FirebaseApp userInfoFireBase = FirebaseApp.getInstance("base-userinfo");
        mAuth = FirebaseAuth.getInstance(userInfoFireBase);
    }
    private void revokeAccess() {
        // Firebase sign out
        mAuth.signOut();

        // Google revoke access
        mGoogleSignInClient.revokeAccess().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                       // updateUI(null);
                        mViewModel.onClickClear();
                    }
                });
    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        mViewModel.SignInGoogleAuto(currentUser);
    }

    @Override
    public void googleSignIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void signOutGoogle() {
        revokeAccess();
    }

    @Override
    public void FirebaseInvites() {
//        Intent intent = new AppInviteInvitation.IntentBuilder(getString(R.string.invitation_title))
//                .setMessage(getString(R.string.invitation_message))
//                .setDeepLink(Uri.parse(getString(R.string.invitation_deep_link)))
//                .setCustomImage(Uri.parse(getString(R.string.invitation_custom_image)))
//                .setCallToActionText(getString(R.string.invitation_cta))
//                .build();
//        startActivityForResult(intent, REQUEST_INVITE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("", "Google sign in failed", e);
                // ...
            }
        }
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d("", "firebaseAuthWithGoogle:" + acct.getId());
        // [START_EXCLUDE silent]
       showProgress(true);
        // [END_EXCLUDE]

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            if(user!=null){
                                mViewModel.checkNewUser(user.getEmail(),user.getDisplayName());
                            }

                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("", "signInWithCredential:failure", task.getException());

                            //updateUI(null);
                        }

                        // [START_EXCLUDE]
                       hideProgress();
                        // [END_EXCLUDE]
                    }
                });
    }
    public void registerFCM(){
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();


                        Toast.makeText(LoginActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });

        counter++;
        Bundle bundle = new Bundle();
        bundle.putString("UserName", "Aghazadeh");
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "counter");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, ""+counter);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

    }
}

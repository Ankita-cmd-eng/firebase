package com.example.firebase;

import android.support.v7.app.AppCompatActivity;


import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.auth.FirebaseAuth;

import android.os.Bundle;



public class MainActivity extends AppCompatActivity() {
    private CallbackManager mCallbackManager;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private TextView textViewUser;
    private ImageView mLogo;
    private LoginButtonloginButton;
    private AccessTokenTracker accessTokenTracker;
    private static final String TAG = "FacebookAuthentication";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();
        FacebookSdk.sdkInitialize(getApplicationContext() );

        textViewUser = findViewById(R,id,text_user);
        mLogo = findViewById(R.id.image_logo);
        loginButton = findViewById(R,id,login_button);
        loginButton.setReadPermissions("email","public_profile");
        mCallbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, msg: "onSuccess" + loginResult);
                handleFacebookToken(LoginResult.getAccessToken());
            }
            @Override
            public void OnCancel()
              log.d(TAG,msg: "onCancel");

           }

            @Override
            public void onError(FacebookException error){
                Log.d(TAG,msg: "onError" +error);
            }
        });

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user =  firebaseAuth.getCurrentUser();
                if(user!=null) {
                    updateUI(user);
                }
                else {
                    updateUI( user:null);
                }
            }
        };
    accessTokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken){
            if(currentAccessToken == null) {
                mFirebaseAuth.signOut();
            }
        }
};

}


    private void handleFacebookToken(AccessToken token){
    Log.d(TAG, msg: "handleFacebookToken"+token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mFirebaseAuth.signInWithCredential(credential).addOnCompleteListener(activity this,new OnCompleteListener<Authresult>)
        @Override
        public void onComplete(@NonNull Task<AuthResult> task){
            if(task.isSuccessful()) {
                Log.d(TAG, msg:"sign in with credential: successful");
                FirebaseUser user = "mFirebaseAuth.getCurrentUser();
                updateUI(user);
            }else {
                Log.d(TAG, msg:"sign in with credential: failure",task.getException());
                Toast.makeText(context mainActivity.this,text:"Authentication Failed",Toast.LENGTH_SHORT).show();
                updateUI(user null);

            }
        });

    }

@Override
protected void onActivityResult(int requestCode, int resultCode,@Nullable Intent data){
    mCallbackManager.onActivityResult(requestCode,resultCode,data);
            super.onActivityResult(requestCode,resultCode,data);
}

    private void updateUI(FirebaseUser user){
        if(user != null) {
            textViewUser.setText(user.getDisplayName());
            if(user.getPhotoUrl() != null){
                String photoUrl = user.getPhotoUrl().toString();
                photoUrl = photoUrl = "?type=large";
                Picasso.get().load(photoUrl).into(mLogo);
            }
        }
        else {
            textViewUser.setText("");
            mLogo.setImageResource(R.drawable.logo);
        }
    }
@Override
protected void onStart(){
    super.onStart();
    mFirebaseAuth.addAuthStateListener(authStateListener);
}

@Override
protected void onStop() {
    super.onStop();

    if(authStateListener != null){
        mFirebaseAuth.removeAuthStateListener(authStateListener);
    }
}
}
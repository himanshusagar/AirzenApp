package iiitd.airzentest2.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import iiitd.airzentest2.R;


public class SignInActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener ,

        GoogleApiClient.ConnectionCallbacks
{


    public  static  final String EXTRA_EMAIL = "iiitd.airzentest2.ui.EMAIL";
    public  static  final String EXTRA_LOGOUT_STATUS = "iiitd.airzentest2.ui.LOGOUT_STATUS";

    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;

    private GoogleApiClient mGoogleApiClient;
    private TextView mStatusTextView;
    private ProgressDialog mProgressDialog;

    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // Views
        mStatusTextView = (TextView) findViewById(R.id.status);

        // Button listeners
        findViewById(R.id.sign_in_button).setOnClickListener(this);

        // [START configure_signin]
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // [END configure_signin]

        // [START build_client]
        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        // [END build_client]


        // [START customize_button]
        // Customize sign-in button. The sign-in button can be displayed in
        // multiple sizes and color schemes. It can also be contextually
        // rendered based on the requested scopes. For example. a red button may
        // be displayed when Google+ scopes are requested, but a white button
        // may be displayed when only basic profile is requested. Try adding the
        // Scopes.PLUS_LOGIN scope to the GoogleSignInOptions to see the
        // difference.
        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setScopes(gso.getScopeArray());
        // [END customize_button]
    }

    @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);


       final boolean logoutStatus = this.getIntent().getBooleanExtra(EXTRA_LOGOUT_STATUS , false);

        Log.d(TAG ," OnStart Logout Status" + logoutStatus + opr.isDone() + "isconnected"+mGoogleApiClient.isConnected());


        if (opr.isDone())
        {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.

            Log.d(TAG, "Got cached sign-in");
            if(logoutStatus)
            {
                mGoogleApiClient.connect();
               TimeWaste();
            }
            else
            {
                GoogleSignInResult result = opr.get();
                handleSignInResult(result);

            }
        }
        else
        {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.

            showProgressDialog();

            opr.setResultCallback(new ResultCallback<GoogleSignInResult>()
            {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult)
                {
                    hideProgressDialog();
                    Log.d(TAG , "Trying");
                    handleSignInResult(googleSignInResult);

                }
            });
        }
    }

    // [START onActivityResult]
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN)
        {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }
    // [END onActivityResult]

    // [START handleSignInResult]
    private void handleSignInResult(GoogleSignInResult result)
    {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());

        if (result.isSuccess())
        {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            userEmail = acct.getEmail();

            mStatusTextView.setText( getString(R.string.signed_in_fmt, acct.getDisplayName()) + " " +acct.getEmail()  );
           // updateUI(true);

           // onStop();

            startAirzenIntent();



        }
        else
        {
            // Signed out, show unauthenticated UI.
            updateUI(false);
        }
    }
    // [END handleSignInResult]

    // [START signIn]
    private void signIn()
    {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    // [END signIn]

    // [START signOut]
    private void signOut()
    {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        updateUI(false);
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END signOut]

    // [START revokeAccess]
    private void revokeAccess()
    {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        updateUI(false);
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END revokeAccess]

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult)
    {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Toast.makeText(getBaseContext(), "Network Error", Toast.LENGTH_LONG).show();

        Log.d(TAG, "onConnectionFailed:" + connectionResult);

        updateUI(false);


    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    private void updateUI(boolean signedIn) {

        if (signedIn)
        {
    //        findViewById(R.id.sign_in_button).setVisibility(View.GONE);
      //      findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);
        } else
        {
            mStatusTextView.setText(R.string.signed_out);

//            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
  //          findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
        }
    }


    private void startAirzenIntent()
    {
        Intent intent = new Intent(getApplicationContext(), AirzenLogin.class);
        intent.putExtra(EXTRA_EMAIL , userEmail);
        startActivity(intent);

    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.sign_in_button:
                signIn();
                break;

        }
    }



    @Override
    public void onStop()
    {
        super.onStop();
        Log.d(TAG , "-----------------------------------DIsmiss-------------------");

    }

    @Override
    public void onConnected(@Nullable Bundle bundle)
    {
        final boolean logoutStatus = this.getIntent().getBooleanExtra(EXTRA_LOGOUT_STATUS , false);

        Log.d(TAG , "Onconnected" + "LogoutStatus" + logoutStatus);

        if(logoutStatus)
        {
            Toast.makeText(getBaseContext(), "Signing Out", Toast.LENGTH_LONG).show();

            signOut();
        }



    }

    @Override
    public void onConnectionSuspended(int i)
    {

    }

    private void TimeWaste()
    {
        Log.d(TAG , "Is conecting" + mGoogleApiClient.isConnecting() + mGoogleApiClient.isConnected() + "Wait");


     /*   final ProgressDialog progressDialog = new ProgressDialog(SignInActivity.this,
                R.style.AppTheme_Dark_Dialog);

        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Logging Out...");
        progressDialog.show();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success

                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 6000);
*/
    }
}
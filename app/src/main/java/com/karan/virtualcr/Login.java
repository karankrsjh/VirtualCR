package com.karan.virtualcr;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.concurrent.TimeUnit;

public class Login extends AppCompatActivity implements View.OnClickListener{
    private  String mobile_No="";
    private FirebaseAuth mAuth;
    private boolean SigninProgress=false;
    private TextView PhoneNumber;
    private TextView Otp_digit;
    private TextView Resend;
    private TextView change_mobile;
    private TextView time;
    private TextView OtpSentMsg;
    private Button SendOTPButton;
    private Button LOGInButton;
    private CheckBox valid_ph_checkbox;
    private CheckBox otp_sent_checkbox;
    private String mVerificationId;
    private static final int STATE_INITIALIZED = 1;
    private static final int STATE_CODE_SENT = 2;
    private static final int STATE_VERIFY_FAILED = 3;
    private static final int STATE_VERIFY_SUCCESS = 4;
    private static final int STATE_SIGNIN_FAILED = 5;
    private static final int STATE_SIGNIN_SUCCESS = 6;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;  //it will handle the result of otp

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
        mobile_No=PhoneNumber.getText().toString();

        if (SigninProgress && validatePhoneNumber())
        {
            startPhoneNumberVerfification(mobile_No);

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        }
        setContentView(R.layout.activity_login);
        Resend =findViewById(R.id.Resend);
        Resend.setOnClickListener(this);
        Otp_digit=findViewById(R.id.OTP_digit);
        valid_ph_checkbox=findViewById(R.id.Valid_mobile_checkbox);
        otp_sent_checkbox=findViewById(R.id.OTP_sent_checkbox);
        time=findViewById(R.id.textView19);
        PhoneNumber = findViewById(R.id.Mobile_number);
        SendOTPButton = findViewById(R.id.Send_otp);
        SendOTPButton.setOnClickListener(this);
        LOGInButton=findViewById(R.id.LogIn);
        LOGInButton.setOnClickListener(this);
        change_mobile=findViewById(R.id.mobile_change);
        mAuth = FirebaseAuth.getInstance();
        OtpSentMsg=findViewById(R.id.MessageOTP);
        mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                SigninProgress = false;
                updateUI(STATE_VERIFY_SUCCESS, phoneAuthCredential);
                signInWithPhoneAuthCredential(phoneAuthCredential);
                Snackbar.make(findViewById(android.R.id.content), "otp verified" ,
                        Snackbar.LENGTH_SHORT).show();
                Log.i("logging in with", phoneAuthCredential.toString());
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                Toast.makeText(getApplicationContext(), "OTP sent" + s, Toast.LENGTH_LONG).show();
                mVerificationId = s;
                mResendToken = forceResendingToken;
                updateUI(STATE_CODE_SENT);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Log.i("varification failed", "true");
                SigninProgress = false;
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // [START_EXCLUDE]
                    PhoneNumber.setError("Invalid phone number.");
                    // [END_EXCLUDE]
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // [START_EXCLUDE]
                    Snackbar.make(findViewById(android.R.id.content), "Quota exceeded.",
                            Snackbar.LENGTH_SHORT).show();
                    //update the ui for failed status
                    updateUI(STATE_VERIFY_FAILED);
                }

            }
        };

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("state", SigninProgress);
        outState.putString("mobile_no",mobile_No);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        SigninProgress = savedInstanceState.getBoolean("state");
        mobile_No=savedInstanceState.getString("mobile_no");
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            updateUI(STATE_SIGNIN_SUCCESS, user);
                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(getBaseContext(),"login failed",Toast.LENGTH_LONG).show();
                            }
                            updateUI(STATE_SIGNIN_FAILED);
                        }
                    }
                });
    }



    private void startPhoneNumberVerfification(String phone_no){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phone_no, 60, TimeUnit.SECONDS, this, mCallback);
        SigninProgress=true;
    }

    private void verifyPhoneNumberWithCode(String verificationID,String Code)
    {
      PhoneAuthCredential credential=PhoneAuthProvider.getCredential(verificationID,Code);
        signInWithPhoneAuthCredential(credential);
    }

    private void resendVerificationCode(String phoneNumber,PhoneAuthProvider.ForceResendingToken token){

        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber,60,TimeUnit.SECONDS,this,mCallback,token);
    }


    private boolean validatePhoneNumber(){
        String phoneNumber=PhoneNumber.getText().toString();
        if(TextUtils.isEmpty(phoneNumber ) || phoneNumber.length()<10)
        {
            PhoneNumber.setError("Invalid phone number");
            return false;
        }
        valid_ph_checkbox.setChecked(true);
        return true;
    }

    @Override
    public void onClick(View view){
        if(view.getId()==R.id.Send_otp){
            if(!validatePhoneNumber()){
                return;
            }
            mobile_No=PhoneNumber.getText().toString();
            startPhoneNumberVerfification(mobile_No);
        }
         if(view.getId()==R.id.LogIn)
        {
            verifyPhoneNumberWithCode(mVerificationId,Otp_digit.getText().toString());
        }
        if(view.getId()==R.id.Resend)
        {
            resendVerificationCode(mobile_No,mResendToken);
        }

    }

    private void updateUI(int uiState) {
        updateUI(uiState, mAuth.getCurrentUser(), null);
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            updateUI(STATE_SIGNIN_SUCCESS, user);
        } else {
            updateUI(STATE_INITIALIZED);
        }
    }
    private void updateUI(int uiState, FirebaseUser user) {
        updateUI(uiState, user, null);
    }

    private void updateUI(int uiState, PhoneAuthCredential cred) {
        updateUI(uiState, null, cred);
    }

    private void updateUI(int uistate,FirebaseUser user,PhoneAuthCredential cred)
    {
        ContentLoadingProgressBar progressBar;

     switch (uistate){
         case STATE_INITIALIZED:
             {
                 enableViews(PhoneNumber);
                 disableViews(Otp_digit,Resend,time,change_mobile);
                 break;
                                }
         case STATE_CODE_SENT:
         {
             enableViews(Otp_digit,Resend,time,change_mobile,OtpSentMsg,LOGInButton);
             disableViews(PhoneNumber,SendOTPButton);
             OtpSentMsg.setText("OTP has been sent to "+mobile_No);
             otp_sent_checkbox.setChecked(true);
             break;
         }
         case STATE_VERIFY_FAILED:
         {
             enableViews(Otp_digit,Resend,time,change_mobile,LOGInButton);
             disableViews(PhoneNumber,SendOTPButton);
             break;
         }
         case STATE_VERIFY_SUCCESS:
         {
             progressBar=new ContentLoadingProgressBar(this);
             progressBar.show();

             break;
         }
         case STATE_SIGNIN_FAILED:
         {
             Otp_digit.setError("Invalid Otp");
             Toast.makeText(this,"sigin failed",Toast.LENGTH_LONG).show();
             break;
         }
         case  STATE_SIGNIN_SUCCESS:
         {

             Intent intent= new Intent(this,StudentProfile.class);
             SharedPreferences Mobile_prefrence=getSharedPreferences("User Mobile_no", Context.MODE_PRIVATE);
             SharedPreferences.Editor ed = Mobile_prefrence.edit();
             ed.putString("Mobile_no",mobile_No);
             ed.apply();
             startActivity(intent);
             finish();
             break;
         }
     }
    }
    private void enableViews(View... views) {
        for (View v : views) {
            v.setVisibility(View.VISIBLE);
        }
    }

    private void disableViews(View... views) {
        for (View v : views) {
            v.setVisibility(View.INVISIBLE);
        }
    }
}

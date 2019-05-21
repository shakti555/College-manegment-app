package aurai.polytechnic.government.GPA;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneNumber extends AppCompatActivity {
    private Button sendverifactioncode,verifybutton;
    EditText inputphonenumber,inputvercode;
    String phonenumb,phonenumbe;
    ProgressDialog loadingbar;
    FirebaseAuth mauth;
    FirebaseAuth.AuthStateListener authStateListener;
    private  PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private  String  mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
 private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number);
      inputphonenumber=findViewById(R.id.phonenumber);
      inputvercode=findViewById(R.id.Otp);
      mAuth=FirebaseAuth.getInstance();
      loadingbar=new ProgressDialog(this);
      sendverifactioncode=findViewById(R.id.sendvericode);
      verifybutton=findViewById(R.id.vericode);
        authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null)
                {
                    Intent i=new Intent(PhoneNumber.this,MainActivity.class);
                    startActivity(i);

                }
            }
        };


        sendverifactioncode.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {


              phonenumb=inputphonenumber.getText().toString().trim();
              if(phonenumb.isEmpty())
              {
                  inputphonenumber.setError("Enter the number");
              }
              else
              {

                  loadingbar.setMessage("Sending Code on Your Number");
                  loadingbar.setCanceledOnTouchOutside(false);
                  loadingbar.show();
                     phonenumbe="+91"+phonenumb;
                  PhoneAuthProvider.getInstance().verifyPhoneNumber(
                          phonenumbe,
                          60,
                          TimeUnit.SECONDS,
                         PhoneNumber.this,
                          callbacks);
              }

          }
      });
      verifybutton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              String varcode=inputvercode.getText().toString();

              if(varcode.isEmpty())
              {
                  inputvercode.setError("Enter The Verification Code");
              }
              else
              {

                  loadingbar.setMessage("Verifying Your Number.. ");
                  loadingbar.setCanceledOnTouchOutside(false);
                  loadingbar.show();
                  PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId,varcode );

                 signInWithPhoneAuthCredential(credential);

              }
          }
      });

      callbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
          @Override
          public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential)
          {
                        signInWithPhoneAuthCredential(phoneAuthCredential);
          }

          @Override
          public void onVerificationFailed(FirebaseException e) {
              inputphonenumber.setVisibility(View.VISIBLE);
              sendverifactioncode .setVisibility(View.VISIBLE);
              inputvercode.setVisibility(View.INVISIBLE);
              verifybutton.setVisibility(View.INVISIBLE);

              Toast.makeText(PhoneNumber.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
               loadingbar.dismiss();
          }


          public void onCodeSent(String verificationId,
                                 PhoneAuthProvider.ForceResendingToken token) {
              inputphonenumber.setVisibility(View.INVISIBLE);
              sendverifactioncode .setVisibility(View.INVISIBLE);
              inputvercode.setVisibility(View.VISIBLE);
              loadingbar.dismiss();
              verifybutton.setVisibility(View.VISIBLE);
              mVerificationId = verificationId;
              mResendToken = token;
              Toast.makeText(PhoneNumber.this, "Verification Code has Been Sent On Your Number", Toast.LENGTH_SHORT).show();

          }

      };

      }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {


                           loadingbar.dismiss();
                            Toast.makeText(PhoneNumber.this, "Verification Completed", Toast.LENGTH_SHORT).show();

                            Intent i=new Intent(PhoneNumber.this,userregistration.class);
                            startActivity(i);
                                                  } else {


                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);

    }


}

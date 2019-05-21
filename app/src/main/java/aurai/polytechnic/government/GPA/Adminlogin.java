package aurai.polytechnic.government.GPA;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Adminlogin extends AppCompatActivity {
    EditText username,password;
    String email="gpauraibhadohi@gmail.com";

    FirebaseAuth firebaseAuth;
    TextInputLayout emaillayout,passwordlayout;
    FirebaseAuth.AuthStateListener authStateListener;
   ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminlogin);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        emaillayout=findViewById(R.id.usernameWrapper);
        passwordlayout=findViewById(R.id.pawWrapperWrapper);
        Alretbuilder();
        firebaseAuth= FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);


        }

    private void Alretbuilder() {
        AlertDialog.Builder mbuilder=new AlertDialog.Builder(this);
        mbuilder.setTitle("Warning");
        mbuilder.setMessage("Only Admin Can Login Here Make Sure You are a Admin");
        mbuilder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();

            }
        });
        mbuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent=new Intent(Adminlogin.this, MainActivity.class);
                startActivity(intent);
            }
        });

        AlertDialog alert=mbuilder.create();
        alert.show();
    }
    public void forget(View v)
    {



      progressDialog.setMessage("Please Wait..");
      progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);


       firebaseAuth.sendPasswordResetEmail(email)
               .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            Toast.makeText(Adminlogin.this, "We have sent you instructions to reset your password!", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(Adminlogin.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                        }


                    }
                });

    }





    public void Adminlogin(View v)
    {
       String myusername=username.getText().toString().trim();
       String mypassword=password.getText().toString().trim();
       boolean value=true;
       if(myusername.isEmpty())
       {
           emaillayout.setError("Please Filled Username");
           username.requestFocus();
           value=false;
       }
       else
       {
           emaillayout.setErrorEnabled(false);

       }
       if(mypassword.isEmpty())
       {
         passwordlayout.setError("Please Filled Username");
         passwordlayout.requestFocus();
           value=false;
       }

       else
       {
          passwordlayout.setErrorEnabled(false);
       }
       if(value)
       {

            progressDialog.setMessage("Logging...");
            progressDialog.show();
            progressDialog.setCanceledOnTouchOutside(false);
           firebaseAuth.signInWithEmailAndPassword(myusername,mypassword).
                   addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {

                           if(task.isSuccessful())
                           {
                               Toast.makeText(Adminlogin.this, "Login SucessFully", Toast.LENGTH_SHORT).show();
                                 Intent i=new Intent(Adminlogin.this,myadminlogin.class);
                                 startActivity(i);
                                 finish();

                           }
                           else
                           {
                               progressDialog.dismiss();
                               Toast.makeText(Adminlogin.this, "Wrong Email or Password", Toast.LENGTH_SHORT).show();
                           }


                       }
                   });


       }
       }

}

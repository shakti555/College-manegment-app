package aurai.polytechnic.government.GPA;

import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class changeadminapp extends AppCompatActivity {
    EditText newpassword, confirmpassword, oldpassword;
    Button button;
    FirebaseAuth auth;

    ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changeadminapp);
        oldpassword = findViewById(R.id.old_pass);
        newpassword = findViewById(R.id.et_new_password);
        confirmpassword = findViewById(R.id.et_confirm_password);
        progressDialog = new ProgressDialog(this);

        button = findViewById(R.id.btn_change_password);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String npass = newpassword.getText().toString();
                String cpass = confirmpassword.getText().toString();
                String oldpass = oldpassword.getText().toString();


                progressDialog.setMessage("Processing..");
                progressDialog.show();
                if (npass.isEmpty() || cpass.isEmpty()) {
                    progressDialog.dismiss();
                    Toast.makeText(changeadminapp.this, "Fill Both Fields", Toast.LENGTH_SHORT).show();
                } else if (npass.equals(cpass)) {


                    final FirebaseUser user;
                    user = FirebaseAuth.getInstance().getCurrentUser();
                    final String email = user.getEmail();
                    AuthCredential credential = EmailAuthProvider.getCredential(email,oldpass);
                    user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                user.updatePassword(npass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if(task.isSuccessful())

                                        {
                                            progressDialog.dismiss();
                                            Toast.makeText(changeadminapp.this, "Password Changed Sucessfully", Toast.LENGTH_SHORT).show();
                                        }
                                        else
                                        {    progressDialog.dismiss();
                                            Toast.makeText(changeadminapp.this, "Something Went wong", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });
                            }
                            else
                            {
                                Toast.makeText(changeadminapp.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                            }


                        }
                    });

















                }



                else

            {
                Toast.makeText(changeadminapp.this, "Password Do not matched", Toast.LENGTH_SHORT).show();
            }

            }
        });
    }
        }


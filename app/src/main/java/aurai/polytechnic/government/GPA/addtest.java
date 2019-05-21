package aurai.polytechnic.government.GPA;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import aurai.polytechnic.government.GPA.R;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class addtest extends AppCompatActivity {
    EditText testitle, testlink;
    Button posttest;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    FirebaseAuth mauth;
    DatabaseReference postref;
    String Title, Link, saveCurrentDate, saveCurrentTime, postRandomName, current_user_id, downloadUrl;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtest);
        testitle = findViewById(R.id.edittext_tt);
        mauth = FirebaseAuth.getInstance();
        current_user_id = mauth.getCurrentUser().getUid();
        progressDialog = new ProgressDialog(this);
        postref = db.getReference().child("Online Test Record");
        testlink = findViewById(R.id.edittext_link);
        posttest = findViewById(R.id.post_test);
        posttest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Title = testitle.getText().toString();
                Link = testlink.getText().toString();
                if (Title.isEmpty()) {
                    testitle.setError("Please Filled this");
                } else if (Link.isEmpty()) {
                    testlink.setError("Please Filled this");
                } else {
                    progressDialog.setMessage("Uploading...");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    savedatainDb();
                }

            }
        });
    }



    private void savedatainDb() {





        Calendar calFordDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
        saveCurrentDate = currentDate.format(calFordDate.getTime());

        Calendar calFordTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
        saveCurrentTime = currentTime.format(calFordTime.getTime());

        postRandomName = saveCurrentDate + saveCurrentTime;


        final String Postid = postref.push().getKey();


                 HashMap postsMap = new HashMap();
                    postsMap.put("Link", Link);
                    postsMap.put("Title", Title);
                    postsMap.put("Uniqueid",Postid);
                    postsMap.put("Date", saveCurrentDate);

        postref.child(Postid).updateChildren(postsMap).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {

                if(task.isSuccessful())
                {
                    progressDialog.dismiss();
                    Toast.makeText(addtest.this, "New Exam Record Added", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(addtest.this,campus.class);
                    startActivity(i);

                }
                else
                {
                    progressDialog.dismiss();
                    Toast.makeText(addtest.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }

            }

        });





       /* postref.child(Postid).child("Link").setValue(Link);
         postref.child(Postid).child("Title").setValue(Title);
         postref.child(Postid).child("Date").setValue(saveCurrentDate);
         postref.child(Postid).child("Unique id").setValue(Postid);
        postref.child(Postid).child("Counter").setValue(count);
         progressDialog.dismiss();
         */
     //   Toast.makeText(this, "Upation Sucessfully", Toast.LENGTH_SHORT).show();


    }
}

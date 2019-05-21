package aurai.polytechnic.government.GPA;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;


public class newpost extends AppCompatActivity {

    ImageView imageView;
    Button post_btn;
    EditText discription;
    String dicrip;
    final static int pick_img = 1;
    private Uri imageuri;
    ProgressDialog progressDialog;
    FirebaseDatabase db= FirebaseDatabase.getInstance();
    DatabaseReference postref;
    StorageReference imagestoreref= FirebaseStorage.getInstance().getReference();
    private String saveCurrentDate, saveCurrentTime, postRandomName, downloadUrl, current_user_id;
        Uri duri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newpost);
        imageView=findViewById(R.id.nimageview);
        post_btn=findViewById(R.id.post_btn);
        discription=findViewById(R.id.edittext_dis);
        progressDialog=new ProgressDialog(this);
        postref=db.getReference().child("Newsfeed");


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        post_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckValidate();
            }
        });
        }

    private void CheckValidate() {

           dicrip=discription.getText().toString().trim();
          if(dicrip.isEmpty()) {

              discription.setError("Please Fill this");
          }
          else if(imageuri==null)
          {
              Toast.makeText(this, "Select a Image", Toast.LENGTH_SHORT).show();

                   }
                   else
          {

             progressDialog.setMessage("Uploading Data...");
             progressDialog.show();
             progressDialog.setCanceledOnTouchOutside(false);
              storagerefrance();
          }
    }
    private void storagerefrance() {

       Calendar calFordDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
        saveCurrentDate = currentDate.format(calFordDate.getTime());

        Calendar calFordTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
        saveCurrentTime = currentTime.format(calFordTime.getTime());

        postRandomName = saveCurrentDate + saveCurrentTime;
       final StorageReference filepath=imagestoreref.child("Newsfeed Images").child(imageuri.getLastPathSegment()+postRandomName+".jpg");
        filepath.putFile(imageuri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful())
                {


                    filepath.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadUrl=String.valueOf(uri);
                                    SavingPostIndb();
                                   // Log.d(TAG, "onSuccess: uri= "+ uri.toString());
                                }
                            });
                        }
                    });



                  // downloadUrl=task.getResult().getStorage().getDownloadUrl().toString();




                }
                else
                {
                    Toast.makeText(newpost.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });

    //Toast.makeText(this, "Yiu clicked on button", Toast.LENGTH_SHORT).show();

    }

    private void SavingPostIndb() {

         final String Postid=postref.push().getKey();

        postref.child(postRandomName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final HashMap postmap=new HashMap();
                postmap.put("Time",saveCurrentTime);
                postmap.put("Date",saveCurrentDate);
                postmap.put("Discription",dicrip);
                postmap.put("Postimg",downloadUrl);
                postmap.put("Uniqueid",Postid);
                postref.child(Postid).updateChildren(postmap).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful())
                        {
                            progressDialog.dismiss();

                            Toast.makeText(newpost.this, "Post Updated Sucessfully", Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(newpost.this,Newsfeed.class);
                            startActivity(i);
                        }
                        else
                        {
                             progressDialog.dismiss();
                            Toast.makeText(newpost.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(newpost.this, "Something is wrong from db", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==pick_img&& resultCode==RESULT_OK && data!=null)
        {
           imageuri = data.getData();
            imageView.setImageURI(imageuri);
        }

    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, pick_img);



    }

}


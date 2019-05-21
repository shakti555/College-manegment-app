package aurai.polytechnic.government.GPA;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class uploadpdf extends AppCompatActivity {
     EditText pdftext;
     Button pdfbtn;
    final static int pick_img = 1;

    FirebaseDatabase db= FirebaseDatabase.getInstance();
    DatabaseReference postref;
    ProgressDialog progressDialog;
    private Uri imageuri;
    String dicrip;
    StorageReference pdftoreref= FirebaseStorage.getInstance().getReference();
    private String saveCurrentDate, saveCurrentTime, postRandomName, downloadUrl, current_user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadpdf);
        pdftext=findViewById(R.id.edittext_pdf);
        pdfbtn=findViewById(R.id.post_pdf);
        postref=db.getReference().child("Pdf list record");
        progressDialog=new ProgressDialog(this);
        pdfbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dicrip=pdftext.getText().toString().trim();
                if(dicrip.isEmpty())
                {
                    pdftext.setError("Please Fill this");
                }
                else {
                    openpdf();
                }


            }
        });
        
        
    }

    private void openpdf() {

        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, pick_img);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
              progressDialog.setMessage("Uploading pdf...");
              progressDialog.show();
              progressDialog.setCanceledOnTouchOutside(false);
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==pick_img&& resultCode==RESULT_OK && data!=null)
        {
            imageuri = data.getData();

        }
        Calendar calFordDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
        saveCurrentDate = currentDate.format(calFordDate.getTime());

        Calendar calFordTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
        saveCurrentTime = currentTime.format(calFordTime.getTime());

        postRandomName = saveCurrentDate + saveCurrentTime;
        final StorageReference filepath=pdftoreref.child("PDf Files").child(imageuri.getLastPathSegment()+postRandomName+".pdf");
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
                    Toast.makeText(uploadpdf.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void SavingPostIndb() {
        final String Postid=postref.push().getKey();

        postref.child(postRandomName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final HashMap postmap=new HashMap();
                postmap.put("Pdftxt",dicrip);
                postmap.put("pdfurl",downloadUrl);
                postmap.put("Date",saveCurrentDate);
                postmap.put("Uniqueid",Postid);
                postref.child(Postid).updateChildren(postmap).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful())
                        {
                            progressDialog.dismiss();

                            Toast.makeText(uploadpdf.this, "Post Updated Sucessfully", Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(uploadpdf.this,campus.class);
                            startActivity(i);
                        }
                        else
                        {
                            progressDialog.dismiss();
                            Toast.makeText(uploadpdf.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(uploadpdf.this, "Something is wrong from db", Toast.LENGTH_SHORT).show();
            }
        });


    }

}

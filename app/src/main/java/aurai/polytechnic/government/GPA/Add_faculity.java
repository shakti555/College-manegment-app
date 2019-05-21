package aurai.polytechnic.government.GPA;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

public class Add_faculity extends AppCompatActivity {

      EditText myname,myemail;
    String fname,femail;
      Button submit;
      ProgressDialog progressDialog;
      Spinner branch;
      Uri resulturi;
    byte[] finalimg;

    private String saveCurrentDate, saveCurrentTime, postRandomName, downloadUrl, currentuser_id;
      CircleImageView myimg;
    FirebaseDatabase db= FirebaseDatabase.getInstance();
    DatabaseReference postref;
    StorageReference imagestoreref= FirebaseStorage.getInstance().getReference();

    private Uri imageuri;
    String [] data={"Select A Department","Computer Science and Engineering","Information Technology","Electronics Engineering","Applied Faculty","Non Teaching Staff"};
    ArrayAdapter<String> ad;
    final static int pick_img = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_faculity);
        myname=findViewById(R.id.edittext_name);
        myemail=findViewById(R.id.edittext_email);
        branch=findViewById(R.id.branch);
        Checkvalue();

        submit=findViewById(R.id.Add_dep);
         myimg=findViewById(R.id.nimageview);

        progressDialog=new ProgressDialog(this);

   ad=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,data);
   branch.setAdapter(ad);
        myimg.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 openimage();
             }
         });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                  fname=myname.getText().toString().trim();
                femail=myemail.getText().toString().trim();
                if(fname.isEmpty())
                {
                    myname.setError("Enter The Name");
                }
                else  if(femail.isEmpty())
                {
                    myemail.setError("Enter the Email");
                }
                else if(imageuri==null)
                {
                    Toast.makeText(Add_faculity.this, "Please select a image", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    storagerefrance();
                    progressDialog.setMessage("Please wait...");
                    progressDialog.show();

                }
            }
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==pick_img&& resultCode==RESULT_OK && data!=null)
        {
            imageuri = data.getData();

            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);

        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if(resultCode == RESULT_OK)
            {

                resulturi=result.getUri();

                File actualImage=new File(resulturi.getPath());

                try {
                    Bitmap compressedImage = new Compressor(this)
                            .setMaxWidth(1000)
                            .setMaxHeight(1000)
                            .setQuality(50)
                            .compressToBitmap(actualImage);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    compressedImage.compress(Bitmap.CompressFormat.JPEG, 50, baos);
                    finalimg = baos.toByteArray();


                } catch (IOException e) {
                    e.printStackTrace();
                }


                myimg.setImageURI(resulturi);
            }

        }

    }






    private void openimage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, pick_img);
    }

   public void Checkvalue()
    {
        branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Object item = parent.getItemAtPosition(position);

                if(item.toString().equals("Select A Department")) {


                } else if(item.toString().equals("Computer Science and Engineering")) {
                    postref=db.getReference().child("Department").child("CSE");


                } else if(item.toString().equals("Information Technology")) {

                    postref=db.getReference().child("Department").child("IT");


                } else if(item.toString().equals("Electronics Engineering")) {
                    postref=db.getReference().child("Department").child("Elex");


                }
                else if(item.toString().equals("Applied Faculty")) {
                    postref=db.getReference().child("Department").child("AppliedFac");


                }
                else if(item.toString().equals("Non Teaching Staff")) {
                    postref=db.getReference().child("Department").child("NonTech");


                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void SavingPostIndb() {

        final String Postid=postref.push().getKey();

        postref.child(postRandomName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final HashMap postmap=new HashMap();


                postmap.put("Name",fname);
                postmap.put("Email",femail);
                postmap.put("Tpics",downloadUrl);
                postmap.put("Uniqueid",Postid);


                postref.child(Postid).updateChildren(postmap).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful())
                        {
                            progressDialog.dismiss();

                            Toast.makeText(Add_faculity.this, "Post Updated Sucessfully", Toast.LENGTH_SHORT).show();

                        }
                        else
                        {
                            progressDialog.dismiss();
                            Toast.makeText(Add_faculity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Add_faculity.this, "Something is wrong from db", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void storagerefrance() {


        Calendar calFordDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
        saveCurrentDate = currentDate.format(calFordDate.getTime());

        Calendar calFordTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
        saveCurrentTime = currentTime.format(calFordTime.getTime());

        postRandomName = saveCurrentDate + saveCurrentTime;

        final StorageReference filepath=imagestoreref.child("Teachers Image").child(imageuri.getLastPathSegment()+postRandomName+".jpg");
        final UploadTask uploadTask=filepath.putBytes(finalimg);
       uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful())
                {


                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
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
                    Toast.makeText(Add_faculity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Toast.makeText(this, "Yiu clicked on button", Toast.LENGTH_SHORT).show();


    }
}

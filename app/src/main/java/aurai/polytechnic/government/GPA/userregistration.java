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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

public class userregistration extends AppCompatActivity {
      EditText usrname;
      Spinner category;
      FirebaseAuth mauth;
      Button btn;
      String downloadUrl;
    final static int pick_img = 1;
    ProgressDialog progressDialog;

    FirebaseDatabase db= FirebaseDatabase.getInstance();
    DatabaseReference postref;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
       String mobno=user.getPhoneNumber();
    StorageReference imagestoreref= FirebaseStorage.getInstance().getReference();
    Uri imageuri;
    Uri resulturi;
    private String saveCurrentDate, saveCurrentTime, postRandomName, current_user_id;
      CircleImageView circleImageView;
          String [] data={"Select a Category","Faculty","Student","Guest"};
          String mycategory;
    ArrayAdapter<String> ad;
    byte[] finalimg;
    FirebaseAuth.AuthStateListener authStateListener;

    String myurname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userregistration);
      usrname=findViewById(R.id.user_name);
       category=findViewById(R.id.category);
        btn=findViewById(R.id.register);
        progressDialog=new ProgressDialog(this);
        postref=db.getReference().child("User Data");
        Checkvalue();
        mauth=FirebaseAuth.getInstance();
       current_user_id=mauth.getCurrentUser().getUid();
       circleImageView=findViewById(R.id.profileimg);
        ad=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,data);
        category.setAdapter(ad);
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openimage();
            }
        });


    btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            myurname=usrname.getText().toString();
            if(myurname.isEmpty())
            {
                usrname.setError("Please Enter The Username");
            }
            else if(imageuri==null)
            {
                Toast.makeText(userregistration.this, "Please select a image", Toast.LENGTH_SHORT).show();

            }
            else if(mycategory.equals(null))
            {
                Toast.makeText(userregistration.this, "Please Select a Category", Toast.LENGTH_SHORT).show();
            }
            else
            {
                storagerefrance();
                progressDialog.setMessage("Loading...");
                progressDialog.show();
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setTitle("Please Wait");

            }
        }
    });



    }

    private void Checkvalue() {
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Object item = parent.getItemAtPosition(position);

                if(item.toString().equals("Select a Category")) {


                    mycategory=null;

                } else if(item.toString().equals("Faculty")) {


                    mycategory="Faculty";


                } else if(item.toString().equals("Student")) {
                mycategory ="Student";



                } else if(item.toString().equals("Guest")) {

                  mycategory="Guest";
                }





            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void openimage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, pick_img);
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
                  Bitmap  compressedImage = new Compressor(this)
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


              circleImageView.setImageURI(resulturi);
          }

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

        final StorageReference filepath=imagestoreref.child("User Profile Image").child(imageuri.getLastPathSegment()+postRandomName+".jpg");
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
                    Toast.makeText(userregistration.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Toast.makeText(this, "Yiu clicked on button", Toast.LENGTH_SHORT).show();


    }
    private void SavingPostIndb() {







        postref.child(postRandomName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                final HashMap postmap=new HashMap();
                postmap.put("Name",myurname);;
                postmap.put("Profileimg",downloadUrl);
                postmap.put("Category",mycategory);
                postmap.put("Mobileno",mobno);

                postref.child(current_user_id).updateChildren(postmap).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful())
                        {
                            progressDialog.dismiss();

                            Toast.makeText(userregistration.this, "Registration Successfully", Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(userregistration.this,MainActivity.class);
                            startActivity(i);
                            finish();

                        }
                        else
                        {
                            progressDialog.dismiss();
                            Toast.makeText(userregistration.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(userregistration.this, "Something is wrong from db", Toast.LENGTH_SHORT).show();
            }
        });

    }



    }



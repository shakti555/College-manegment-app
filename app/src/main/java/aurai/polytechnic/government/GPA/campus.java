
package aurai.polytechnic.government.GPA;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class campus extends AppCompatActivity {

    private RecyclerView myrecyclerView2;
    FirebaseDatabase mdb2= FirebaseDatabase.getInstance();
    DatabaseReference mpostref2;
    private List<testdata> mexamplelist2 =new ArrayList<>();




    SliderLayout sliderLayout;
    TextView aicte,bteup,upted,irtdup,scholarship;
    Button btn;


    private RecyclerView myrecyclerView;
    FirebaseDatabase mdb= FirebaseDatabase.getInstance();
    DatabaseReference mpostref;
    private List<pdfdata> mexamplelist =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campus);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

     btn=findViewById(R.id.btnsitjeecup);
       aicte=findViewById(R.id.aictesite);

        bteup=findViewById(R.id.bteup);

        upted=findViewById(R.id.upted);

        irtdup=findViewById(R.id.irdsite);

       scholarship=findViewById(R.id.schsite);
        myrecyclerView=findViewById(R.id.pdflist);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mpostref=mdb.getReference().child("Pdf list record");
        myrecyclerView.setHasFixedSize(true);
        myrecyclerView.setLayoutManager(new LinearLayoutManager(this));


        myrecyclerView2=findViewById(R.id.testlist);

        mpostref2=mdb2.getReference().child("Online Test Record");
        myrecyclerView2.setHasFixedSize(true);
        myrecyclerView2.setLayoutManager(new LinearLayoutManager(this));

    DataMethod2();

    DataMethod();




        sliderLayout = findViewById(R.id.studentlider);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sliderLayout.setIndicatorAnimation(IndicatorAnimations.FILL);
        sliderLayout.setScrollTimeInSec(5);



        setSliderViews();

           btn.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent intent = new Intent();
                   intent.setAction(Intent.ACTION_VIEW);
                   intent.addCategory(Intent.CATEGORY_BROWSABLE);
                   intent.setData(Uri.parse("https://jeecup.nic.in/cms/public/home.aspx"));
                   startActivity(intent);


               }
           });
        bteup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://bteup.ac.in/webapp/default.aspx"));
                startActivity(intent);


            }
        });
       aicte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.aicte-india.org/"));
                startActivity(intent);


            }
        });
        upted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://upted.gov.in/hi"));
                startActivity(intent);


            }
        });
        irtdup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://irdtup.in/"));
                startActivity(intent);


            }
        });
       scholarship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://scholarship.up.nic.in/"));
                startActivity(intent);


            }
        });

    }

    private void DataMethod2() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        myrecyclerView2.setLayoutManager(layoutManager);


        mpostref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot newssnapshot : dataSnapshot.getChildren() )
                {
                   testdata  data=newssnapshot.getValue(testdata.class);
                    mexamplelist2.add(data);
                }
                testAdapter adpter=new testAdapter(campus.this,mexamplelist2);
                myrecyclerView2.setAdapter(adpter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(campus.this, "Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });



    }

    private void DataMethod() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
       myrecyclerView.setLayoutManager(layoutManager);

        mpostref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot newssnapshot : dataSnapshot.getChildren() )
                {
                   pdfdata  data=newssnapshot.getValue(pdfdata.class);
                    mexamplelist.add(data);
                }
                pafAdapter adpter=new pafAdapter(campus.this,mexamplelist);
                myrecyclerView.setAdapter(adpter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(campus.this, "Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });



    }

    private void setSliderViews() {

        for (int i = 0; i <= 5; i++) {

            DefaultSliderView sliderView1 = new DefaultSliderView(this);

            switch (i) {
                case 0:
                    sliderView1.setImageUrl("https://firebasestorage.googleapis.com/v0/b/gpa-campus.appspot.com/o/Student%20pic%2FIMG_20190512_202103.jpg?alt=media&token=869606e5-a66d-4592-b9ff-9dbddbd37ff7");
                    sliderView1.setDescription("Cse Batch Passout 2017");


                   // sliderView1.setDescription("Working as Field Manager In Discovery Mankind Chhattisgarh Head Quater  ");
                    break;
                case 1:
                    sliderView1.setImageUrl("https://firebasestorage.googleapis.com/v0/b/gpa-campus.appspot.com/o/Student%20pic%2FIMG_20190512_202132.jpg?alt=media&token=90d89eba-1d51-453b-b28d-10e9975437ba");

                    sliderView1.setDescription("CSE Batch 2017 Passout");
                    break;
                case 2:
                    sliderView1.setImageUrl("https://firebasestorage.googleapis.com/v0/b/gpa-campus.appspot.com/o/Student%20pic%2FIMG_20190512_203310.jpg?alt=media&token=dc97b685-d64d-4a3e-8df1-f2d1f667e7f7");
                    sliderView1.setDescription("CSE Batch 2017 Passout");
                    break;
                case 3:
                    sliderView1.setImageUrl("https://firebasestorage.googleapis.com/v0/b/gpa-campus.appspot.com/o/Student%20pic%2FIMG_20190512_204147.jpg?alt=media&token=0dfab5bc-da3c-4be6-a5ef-e111f3713d15");
                    sliderView1.setDescription("CSE Batch 2017 Passout ");
                    break;
                case 4:
                    sliderView1.setImageUrl("https://firebasestorage.googleapis.com/v0/b/gpa-campus.appspot.com/o/Student%20pic%2FIMG_20190512_204853.jpg?alt=media&token=baca1ff1-052f-4f57-be5d-df205edbfa0e");
                    sliderView1.setDescription("CSE Batch 2016 Passout");
                    break;
                case 5:
                    sliderView1.setImageUrl("https://firebasestorage.googleapis.com/v0/b/gpa-campus.appspot.com/o/Student%20pic%2FIMG_20190512_205418.jpg?alt=media&token=d457bc45-6f1a-40a6-aa73-529c04ba2bd9");
                    sliderView1.setDescription("CSE Batch 2017 Passout");
                    break;



            }

            sliderView1.setImageScaleType(ImageView.ScaleType.CENTER_CROP);

            final int finalI = i;
            sliderView1.setOnSliderClickListener(new SliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(SliderView sliderView) {

                }
            });

            //at last add this view in your layout :
            sliderLayout.addSliderView(sliderView1);

        }


    }


}

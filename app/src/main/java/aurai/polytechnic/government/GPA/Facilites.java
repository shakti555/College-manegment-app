package aurai.polytechnic.government.GPA;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;

public class Facilites extends AppCompatActivity {
    SliderLayout sliderLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facilites);

        sliderLayout = findViewById(R.id.camusslider);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sliderLayout.setIndicatorAnimation(IndicatorAnimations.FILL);
        sliderLayout.setScrollTimeInSec(2);



        setSliderViews();



    }


    private void setSliderViews() {

        for (int i = 0; i <= 11; i++) {

            DefaultSliderView sliderView1 = new DefaultSliderView(this);

            switch (i) {
                case 0:
                    sliderView1.setImageUrl("https://firebasestorage.googleapis.com/v0/b/gpa-campus.appspot.com/o/Facility%20image%2F2.png?alt=media&token=4026bc19-a389-4fb5-8497-7a9bc34a14f0");
                    sliderView1.setDescription("Main Campus");
                    break;
                case 1:
                    sliderView1.setImageUrl("https://firebasestorage.googleapis.com/v0/b/gpa-campus.appspot.com/o/Facility%20image%2F3.png?alt=media&token=3b316398-817d-40bc-beb2-bf080fc57b5e");

                    sliderView1.setDescription("Girls Hostel");
                    break;
                case 2:
                    sliderView1.setImageUrl("https://firebasestorage.googleapis.com/v0/b/gpa-campus.appspot.com/o/Facility%20image%2F4.png?alt=media&token=6e8b9cea-7f0f-4daa-8b8c-45c0e1ff2549");
                    sliderView1.setDescription("Boys Hostel");
                    break;
                case 3:
                    sliderView1.setImageUrl("https://firebasestorage.googleapis.com/v0/b/gpa-campus.appspot.com/o/Facility%20image%2F7.png?alt=media&token=56981af3-cda5-420d-82fe-06606af7edb8");
                    sliderView1.setDescription("Biometric Attendance ");
                    break;
                case 4:
                    sliderView1.setImageUrl("https://firebasestorage.googleapis.com/v0/b/gpa-campus.appspot.com/o/Facility%20image%2Ffac.jpg?alt=media&token=68a7feab-439f-46e7-8aee-7b7fa93bb92c");

                    sliderView1.setDescription("Our GPA Faculty");
                    break;
                case 5:
                    sliderView1.setImageUrl("https://firebasestorage.googleapis.com/v0/b/gpa-campus.appspot.com/o/Facility%20image%2F8.png?alt=media&token=0447a556-8b05-41aa-9583-c9c58f2c1435");
                    sliderView1.setDescription("Our Computer Lab");
                    break;
                case 6:
                    sliderView1.setImageUrl("https://firebasestorage.googleapis.com/v0/b/gpa-campus.appspot.com/o/Facility%20image%2FDSC03006.JPG?alt=media&token=d8f0ad2b-57d7-413b-9a93-b3af99e4377d");
                    sliderView1.setDescription("GPA Games");
                    break;
                case 7:
                    sliderView1.setImageUrl("https://firebasestorage.googleapis.com/v0/b/gpa-campus.appspot.com/o/Facility%20image%2F36.png?alt=media&token=c2d7db0f-25c7-4558-9746-a3b82e4f1ba3");
                    sliderView1.setDescription("Celebrating Independent Day");
                    break;
                case 8:
                    sliderView1.setImageUrl("https://firebasestorage.googleapis.com/v0/b/gpa-campus.appspot.com/o/Facility%20image%2F10.png?alt=media&token=7aa75de1-dc79-4deb-981c-f118e5a02721");
                    sliderView1.setDescription("Workshop");
                    break;
                case 9:
                    sliderView1.setImageUrl("https://firebasestorage.googleapis.com/v0/b/gpa-campus.appspot.com/o/Facility%20image%2FDSC03086.JPG?alt=media&token=014e8597-3475-444b-9e2b-a27d1f2789d6");
                    sliderView1.setDescription("Annual function ");
                    break;
                case 10:
                    sliderView1.setImageUrl("https://firebasestorage.googleapis.com/v0/b/gpa-campus.appspot.com/o/Facility%20image%2FDSC01575.JPG?alt=media&token=883ce6f7-ebd6-4ee5-8136-51f185939359");
                    sliderView1.setDescription("Cultural Program");
                    break;
                case 11:
                    sliderView1.setImageUrl("https://firebasestorage.googleapis.com/v0/b/gpa-campus.appspot.com/o/Facility%20image%2Fhelth.png?alt=media&token=392364f4-769a-472a-86f1-68812d5463be");
                    sliderView1.setDescription("Health care Camp");
                    break;



            }

            sliderView1.setImageScaleType(ImageView.ScaleType.CENTER_CROP);

            final int finalI = i;
            sliderView1.setOnSliderClickListener(new SliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(SliderView sliderView) {
                    Toast.makeText(Facilites.this, "This is slider " + (finalI + 1), Toast.LENGTH_SHORT).show();
                }
            });

            //at last add this view in your layout :
            sliderLayout.addSliderView(sliderView1);

        }


    }
}


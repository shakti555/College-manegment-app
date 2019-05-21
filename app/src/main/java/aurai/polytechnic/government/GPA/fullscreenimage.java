package aurai.polytechnic.government.GPA;

import androidx.appcompat.app.AppCompatActivity;
import aurai.polytechnic.government.GPA.R;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

public class fullscreenimage extends AppCompatActivity {
            PhotoView imageView;
             TextView textView;
             String imglink,mytext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreenimage);
        imageView=findViewById(R.id.fullimg);
        textView=findViewById(R.id.fulltext);
        imglink=getIntent().getStringExtra("Link");
        mytext=getIntent().getStringExtra("Text");
        Picasso.get().load(imglink).into(imageView);
        textView.setText(mytext);

    }
}

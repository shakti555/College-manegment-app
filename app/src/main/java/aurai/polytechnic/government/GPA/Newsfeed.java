package aurai.polytechnic.government.GPA;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Newsfeed extends AppCompatActivity {

    private RecyclerView recyclerView;
         ProgressBar pg;

    FirebaseDatabase db= FirebaseDatabase.getInstance();
    DatabaseReference postref;

    private List<Post>  examplelist =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsfeed);
        recyclerView=findViewById(R.id.all_post_list);
        pg=findViewById(R.id.pgbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       postref=db.getReference().child("Newsfeed");
       recyclerView.setHasFixedSize(true);
       recyclerView.setLayoutManager(new LinearLayoutManager(this));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
       recyclerView.smoothScrollToPosition(0);

        DataMethod();
    }



    private void DataMethod() {




    postref.addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {
              pg.setVisibility(View.GONE);
              for(DataSnapshot newssnapshot : dataSnapshot.getChildren() )
              {
                  Post post=newssnapshot.getValue(Post.class);
                  examplelist.add(post);
              }
              newsAdpter adpter=new newsAdpter(Newsfeed.this,examplelist);
              recyclerView.setAdapter(adpter);
          }

          @Override
          public void onCancelled(DatabaseError databaseError) {
              Toast.makeText(Newsfeed.this, "Something is wrong", Toast.LENGTH_SHORT).show();
          }
      });

    }


}


/*
        */
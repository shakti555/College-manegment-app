package aurai.polytechnic.government.GPA;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class It_Department extends AppCompatActivity {
    private RecyclerView myrecyclerView;
    FirebaseDatabase mdb= FirebaseDatabase.getInstance();
    DatabaseReference mpostref;
    private List<techersData> mexamplelist =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_it__department);
        myrecyclerView=findViewById(R.id.It_teachers);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mpostref=mdb.getReference().child("Department").child("IT");;
        myrecyclerView.setHasFixedSize(true);
        myrecyclerView.setLayoutManager(new LinearLayoutManager(this));

        DataMethod();
    }



    private void DataMethod() {

        mpostref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot newssnapshot : dataSnapshot.getChildren() )
                {
                    techersData  data=newssnapshot.getValue(techersData.class);
                    mexamplelist.add(data);
                }
                TechersAdapter adpter=new TechersAdapter(It_Department.this,mexamplelist);
                myrecyclerView.setAdapter(adpter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(It_Department.this, "Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }


}


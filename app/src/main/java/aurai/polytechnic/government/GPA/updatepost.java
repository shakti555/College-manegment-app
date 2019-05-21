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

public class updatepost extends AppCompatActivity {
    private RecyclerView myrecyclerView;

    private  RecyclerView.LayoutManager mylayoutManager;
    FirebaseDatabase mdb= FirebaseDatabase.getInstance();
    DatabaseReference mpostref;

    private List<Updatepostdata> mexamplelist =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatepost);
        myrecyclerView=findViewById(R.id.all_update_list);

        mpostref=mdb.getReference().child("Newsfeed");
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
                      Updatepostdata data=newssnapshot.getValue(Updatepostdata.class);
                      mexamplelist.add(data);
                }
                UpdatepostAdpter adpter=new UpdatepostAdpter(updatepost.this,mexamplelist);
                myrecyclerView.setAdapter(adpter);
                adpter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(updatepost.this, "Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }


}

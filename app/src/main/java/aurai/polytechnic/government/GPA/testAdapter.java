package aurai.polytechnic.government.GPA;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class testAdapter extends RecyclerView.Adapter<testAdapter.ExampleViewholder> {
    FirebaseAuth mauth;
    String current_user_id;
    public List<testdata> mExamplelist;
    public Context mcontext;


    public static class ExampleViewholder extends RecyclerView.ViewHolder {

        public TextView title, mdate;
        LinearLayout webact;
         ImageView testdel;

        public ExampleViewholder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.tsttt);
            mdate = itemView.findViewById(R.id.ttdate);
            webact=itemView.findViewById(R.id.webviewact);
            testdel=itemView.findViewById(R.id.testdel);

        }
    }

    public testAdapter(Context context, List<testdata> examlelist) {

        mcontext = context;
        mExamplelist = examlelist;


    }

    @NonNull
    @Override
    public testAdapter.ExampleViewholder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custon_test_layout, parent, false);
        testAdapter.ExampleViewholder exampleViewholder = new testAdapter.ExampleViewholder(view);
        return exampleViewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewholder holder, int position) {
        final testdata currentitem=mExamplelist.get(position);
        holder.title.setText(currentitem.getTitle());
        holder.mdate.setText(currentitem.getDate());



     final String  myid=currentitem.getUniqueid();
      final   String  mylink=currentitem.getLink();

        holder.webact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(mcontext,onlinetest.class);
                intent.putExtra("Link",mylink);
                mcontext.startActivity(intent);
                }
        });

        FirebaseDatabase mdb= FirebaseDatabase.getInstance();
        final DatabaseReference mpostref2=mdb.getReference().child("Online Test Record");

        mauth=FirebaseAuth.getInstance();
        current_user_id=mauth.getCurrentUser().getUid();


        if(current_user_id.equals("bvAkid3xC7XX5roZeyX4Q1nUBM12")||current_user_id.equals("7UdKXyTLy3Ncd4cROGkm5WHr8qI3"))
        {
            holder.testdel.setVisibility(View.VISIBLE);

        }

        holder.testdel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mpostref2.child(myid).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(mcontext, "Post Deleted Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent= new Intent(mcontext,campus.class);
                        mcontext.startActivity(intent);
                        ((Activity)mcontext).finish();
                    }
                });
            }
        });






    }


    @Override
    public int getItemCount() {
        return mExamplelist.size();
    }
}




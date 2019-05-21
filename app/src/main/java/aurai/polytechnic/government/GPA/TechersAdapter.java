package aurai.polytechnic.government.GPA;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TechersAdapter extends RecyclerView.Adapter<TechersAdapter.ExampleViewholder> {
      FirebaseAuth mauth;
      String current_user_id;
    public List<techersData> mExamplelist;
    public Context mcontext;

    public static class ExampleViewholder extends RecyclerView.ViewHolder{

        public CircleImageView imageView;
        public TextView techer_name,techer_email;
        ImageView fdel;


        public ExampleViewholder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.techerpic);
           techer_name=itemView.findViewById(R.id.techername);
           techer_email=itemView.findViewById(R.id.techeremail);
          fdel=itemView.findViewById(R.id.deltbtn);

        }
    }
    public TechersAdapter(Context context, List<techersData> examlelist)
    {

        mcontext = context;
        mExamplelist = examlelist;



    }

    @NonNull
    @Override
    public ExampleViewholder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_techer_layout,parent,false);
      ExampleViewholder exampleViewholder=new ExampleViewholder(view);
        return  exampleViewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewholder exampleViewholder, int i) {




  techersData currentItem=mExamplelist.get(i);

        exampleViewholder.techer_name.setText(currentItem.getName());
        exampleViewholder.techer_email.setText(currentItem.getEmail());


        Picasso.get().load(currentItem.getTpics()).into(exampleViewholder.imageView);

        FirebaseDatabase mdb= FirebaseDatabase.getInstance();
        final DatabaseReference mpostref;
        mauth=FirebaseAuth.getInstance();
        current_user_id=mauth.getCurrentUser().getUid();

        if(current_user_id.equals("bvAkid3xC7XX5roZeyX4Q1nUBM12")||current_user_id.equals("7UdKXyTLy3Ncd4cROGkm5WHr8qI3"))
        {
            exampleViewholder.fdel.setVisibility(View.VISIBLE);

        }


        mpostref=mdb.getReference().child("Department");

        final String id=currentItem.getUniqueid();


        exampleViewholder.fdel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mpostref.child("CSE").child(id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        Toast.makeText(mcontext, "Post Deleted Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent= new Intent(mcontext,cse_department.class);
                        mcontext.startActivity(intent);
                        ((Activity)mcontext).finish();

                        }
                });
                mpostref.child("IT").child(id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(mcontext, "Post Deleted Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent= new Intent(mcontext,It_Department.class);
                        mcontext.startActivity(intent);
                        ((Activity)mcontext).finish();

                    }
                });
                mpostref.child("Elex").child(id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(mcontext, "Post Deleted Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent= new Intent(mcontext,Elex_Department.class);
                        mcontext.startActivity(intent);
                        ((Activity)mcontext).finish();

                    }
                });
                mpostref.child("AppliedFac").child(id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(mcontext, "Post Deleted Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent= new Intent(mcontext,Applieddep.class);
                        mcontext.startActivity(intent);
                        ((Activity)mcontext).finish();

                    }
                });

                mpostref.child("NonTech").child(id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(mcontext, "Post Deleted Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent= new Intent(mcontext,nonteching.class);
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



package aurai.polytechnic.government.GPA;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class pafAdapter extends RecyclerView.Adapter<pafAdapter.ExampleViewholder> {
    FirebaseAuth mauth;
    String current_user_id;
    public List<pdfdata> mExamplelist;
    public Context mcontext;

    public static class ExampleViewholder extends RecyclerView.ViewHolder{

        public ImageView imageView;
        public TextView Discription,date;
        ImageView fdel;


        public ExampleViewholder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.pdfbtnimg);
            Discription=itemView.findViewById(R.id.pdfdicri);
           date=itemView.findViewById(R.id.pdfdate);
            fdel=itemView.findViewById(R.id.pdfdel);

        }
    }
    public pafAdapter(Context context, List<pdfdata> examlelist)
    {

        mcontext = context;
        mExamplelist = examlelist;



    }

    @NonNull
    @Override
    public ExampleViewholder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.cutom_pdf,parent,false);
        ExampleViewholder exampleViewholder=new ExampleViewholder(view);
        return  exampleViewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewholder exampleViewholder, int i) {




        pdfdata currentItem=mExamplelist.get(i);

        exampleViewholder.date.setText(currentItem.getDate());
        exampleViewholder.Discription.setText(currentItem.getPdftxt());
    final String id=currentItem.getUniqueid();
    final String url=currentItem.getPdfurl();
        FirebaseDatabase mdb= FirebaseDatabase.getInstance();
        final DatabaseReference mpostref=mdb.getReference().child("Pdf list record");
        mauth=FirebaseAuth.getInstance();
        current_user_id=mauth.getCurrentUser().getUid();


        if(current_user_id.equals("bvAkid3xC7XX5roZeyX4Q1nUBM12")||current_user_id.equals("7UdKXyTLy3Ncd4cROGkm5WHr8qI3"))
    {
        exampleViewholder.fdel.setVisibility(View.VISIBLE);

    }

        exampleViewholder.fdel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              mpostref.child(id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
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
      exampleViewholder.imageView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = new Intent();
              intent.setAction(Intent.ACTION_VIEW);
              intent.addCategory(Intent.CATEGORY_BROWSABLE);
              intent.setData(Uri.parse(url));
             mcontext.startActivity(intent);
          }
      });
        }

    @Override
    public int getItemCount() {
        return mExamplelist.size();
    }


}
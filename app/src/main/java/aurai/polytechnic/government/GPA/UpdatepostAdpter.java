package aurai.polytechnic.government.GPA;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UpdatepostAdpter extends RecyclerView.Adapter<UpdatepostAdpter.ExampleViewholder> {

    public List<Updatepostdata> mExamplelist;
    public Context mcontext;
    Updatepostdata currentItem;
    public static class ExampleViewholder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView tv_dicrip;
        public Button delbtn,updatebtn;
        EditText updatetext;


        public ExampleViewholder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.update_image);
            tv_dicrip = itemView.findViewById(R.id.update_descri);
            delbtn = itemView.findViewById(R.id.delbtn);
            updatebtn=itemView.findViewById(R.id.updatepstbtn);
            updatetext=itemView.findViewById(R.id.updatemydis);
        }
    }

    public UpdatepostAdpter(Context context, List<Updatepostdata> examlelist) {

        mcontext = context;
        mExamplelist = examlelist;


    }


    @NonNull
    @Override
    public ExampleViewholder onCreateViewHolder(@NonNull ViewGroup parent, int i) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_update, parent, false);
        ExampleViewholder exampleViewholder = new ExampleViewholder(view);
        return exampleViewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ExampleViewholder exampleViewholder, int i) {
        FirebaseDatabase mdb= FirebaseDatabase.getInstance();
        final DatabaseReference mpostref;
        mpostref=mdb.getReference().child("Newsfeed");

        currentItem = mExamplelist.get(i);
             final String id=currentItem.getUniqueid();

        exampleViewholder.tv_dicrip.setText(currentItem.getDiscription());
        Picasso.get().load(currentItem.getPostimg()).into(exampleViewholder.imageView);

        exampleViewholder.delbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mpostref.child(id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                   @Override
                   public void onComplete(@NonNull Task<Void> task) {
                       Toast.makeText(mcontext, "Post Deleted Successfully", Toast.LENGTH_SHORT).show();
                       Intent intent= new Intent(mcontext,updatepost.class);
                       mcontext.startActivity(intent);
                       ((Activity)mcontext).finish();
                   }
               });
            }
        });
        exampleViewholder.updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updatedata=exampleViewholder.updatetext.getText().toString();
                if(updatedata.isEmpty())
                {
                    exampleViewholder.updatetext.setError("Please Fill this");
                }
                else
                {

                    mpostref.child(id).child("Discription").setValue(updatedata).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            Toast.makeText(mcontext, "Post Updated Successfully", Toast.LENGTH_SHORT).show();
                            exampleViewholder.tv_dicrip.setText(currentItem.getDiscription());
                            Intent intent= new Intent(mcontext,updatepost.class);
                        mcontext.startActivity(intent);
                            ((updatepost)mcontext).finish();
                        }
                    });

                    }

            }
        });





    }


    @Override
    public int getItemCount() {
        return mExamplelist.size();
    }
}































































































































































































































































































































































































































































































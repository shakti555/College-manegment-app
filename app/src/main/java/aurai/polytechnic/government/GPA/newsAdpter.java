package aurai.polytechnic.government.GPA;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class newsAdpter extends RecyclerView.Adapter<newsAdpter.ExampleViewholder> {

   public   List<Post>  mExamplelist;
    public  Context mcontext;
    public static class ExampleViewholder extends RecyclerView.ViewHolder{

        public ImageView imageView;
        public TextView tv_dicrip;
        public  TextView tv_time;
        public TextView tv_date;



        public ExampleViewholder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.post_img);
            tv_dicrip=itemView.findViewById(R.id.post_Dicription);
            tv_time=itemView.findViewById(R.id.post_time);
            tv_date=itemView.findViewById(R.id.post_date);
        }
    }
    public newsAdpter(Context context, List<Post> examlelist)
    {

        mcontext = context;
        mExamplelist = examlelist;



    }

    @NonNull
    @Override
    public ExampleViewholder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_news,parent,false);
        ExampleViewholder exampleViewholder=new ExampleViewholder(view);
        return  exampleViewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewholder exampleViewholder, int i) {
       Post currentItem=mExamplelist.get(i);

        exampleViewholder.tv_dicrip.setText(currentItem.getDiscription());
        exampleViewholder.tv_date.setText(currentItem.getDate());
        exampleViewholder.tv_time.setText(currentItem.getTime());
        Picasso.get().load(currentItem.getPostimg()).placeholder(R.drawable.imagepl).error(R.drawable.imgnotfo).into(exampleViewholder.imageView);
        final String mylink=currentItem.getPostimg();
        final String mytext=currentItem.getDiscription();

         exampleViewholder.imageView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent= new Intent(mcontext,fullscreenimage.class);
                 intent.putExtra("Link",mylink);
                 intent.putExtra("Text",mytext);
                 mcontext.startActivity(intent);
             }
         });

    }

    @Override
    public int getItemCount() {
        return mExamplelist.size();
    }


}

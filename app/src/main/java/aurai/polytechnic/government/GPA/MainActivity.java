package aurai.polytechnic.government.GPA;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference ref;
    String current_user_id;
    FirebaseAuth mauth;
    TextView myusername;
    CircleImageView myprofileimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mauth = FirebaseAuth.getInstance();
        current_user_id = mauth.getCurrentUser().getUid();
       Appudatechecker appUpdateChecker=new Appudatechecker(this);
        appUpdateChecker.checkForUpdate(false);

        ref = FirebaseDatabase.getInstance().getReference().child("User Data").child(current_user_id);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
      //  Toast.makeText(this, ""+current_user_id, Toast.LENGTH_SHORT).show();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        myusername=findViewById(R.id.myusername);
        myprofileimg=findViewById(R.id.myprofileimg);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists())
                {

                      String myuser=dataSnapshot.child("Name").getValue().toString();
                      String mypic=dataSnapshot.child("Profileimg").getValue().toString();
                    myusername.setText(myuser);
                 //   Picasso.get().load(mypic).placeholder(R.drawable.imagepl).error(R.drawable.imgnotfo).into(myprofileimg);
                    Picasso.get().load(mypic).into(myprofileimg);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


            }
            @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                          finishAffinity();
                          finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            FirebaseAuth.getInstance().signOut();
            finish();
            Intent i = new Intent(MainActivity.this,PhoneNumber.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.Admin) {

            Intent intent=new Intent(MainActivity.this,Adminlogin.class);
            startActivity(intent);


        } else if (id == R.id.share) {


            try {
                Intent obj = new Intent(Intent.ACTION_SEND);
                obj.setType("text/plain");
                obj.putExtra(Intent.EXTRA_TEXT, "Click this Link for download Government Polytechnic Aurai, Bhadohi Official android app https://play.google.com/store/apps/details?id=aurai.polytechnic.government.mygpa");
                obj.setPackage("com.whatsapp");
                startActivity(obj);
            }
            catch (ActivityNotFoundException e)
            {
                Toast.makeText(MainActivity.this, "Please Install the whatsapp", Toast.LENGTH_SHORT).show();
            }

        } else if (id == R.id.contactus) {



            Intent intent=new Intent(MainActivity.this,devlopers.class);
            startActivity(intent);


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void newsFeed(View view)
    {

        Intent i=new Intent(MainActivity.this,Newsfeed.class);
        startActivity(i);

    }

    public void ourCampus(View view)
    {
        Intent i=new Intent(MainActivity.this,campus.class);
        startActivity(i);
        }

   public void aboutUs(View view)
   {
       Intent i=new Intent(MainActivity.this, aboutus.class);
       startActivity(i);
  }
  public void achivements(View view)
  {
      Intent i=new Intent(MainActivity.this,achivment.class);
      startActivity(i);
  }
  public  void facilities(View view)
  {
      Intent i=new Intent(MainActivity.this,Facilites.class);
      startActivity(i);
  }
  public  void Department(View view)
  {

      Intent i=new Intent(MainActivity.this,Faculity.class);
      startActivity(i);
  }
  public void videoCourse(View v)
  {
      Intent i=new Intent(MainActivity.this,achivment.class);
      startActivity(i);

  }


}


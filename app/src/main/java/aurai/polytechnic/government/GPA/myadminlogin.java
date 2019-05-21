package aurai.polytechnic.government.GPA;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

public class myadminlogin extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myadminlogin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }






    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.logout) {


            Intent i = new Intent(myadminlogin.this, Adminlogin.class);
            startActivity(i);
            finish();
        }
             if(id== R.id.changepassword)
            {

                //Toast.makeText(this, "Password will change soon..", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(myadminlogin.this, changeadminapp.class);
                startActivity(i);
            }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void addpost(View v)
    {
         Intent i=new Intent(myadminlogin.this, newpost.class);
         startActivity(i);
    }
    public  void Updatepost(View v)
    {
        Intent i=new Intent(myadminlogin.this,updatepost.class);
        startActivity(i);
    }
    public  void Addfaculity(View v)
    {

        Intent i=new Intent(myadminlogin.this,Add_faculity.class);
        startActivity(i);
    }
    public  void ChangeF(View view)
    {
        Intent i=new Intent(myadminlogin.this,Faculity.class);
        startActivity(i);

        }
  public  void Uploadpdf(View v)
  {

      Intent i=new Intent(myadminlogin.this,uploadpdf.class);
      startActivity(i);
  }
  public  void addtest(View v)
  {
      Intent i=new Intent(myadminlogin.this,addtest.class);
      startActivity(i);
  }
}

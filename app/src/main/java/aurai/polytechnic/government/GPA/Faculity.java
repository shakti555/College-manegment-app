package aurai.polytechnic.government.GPA;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

public class Faculity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }




    public void elexDep(View v)
    {
        Intent i=new Intent(Faculity.this,Elex_Department.class);
        startActivity(i);

    }
    public  void  cseDep(View v)
    {

        Intent i=new Intent(Faculity.this,cse_department.class);
        startActivity(i);
    }
    public  void itDep(View v)
    {
        Intent i=new Intent(Faculity.this,It_Department.class);
        startActivity(i);

    }

    public  void Applied(View v)
    {
        Intent i=new Intent(Faculity.this,Applieddep.class);
        startActivity(i);
    }


    public  void nontech(View view)
    {

        Intent i=new Intent(Faculity.this,nonteching.class);
        startActivity(i);

    }
}

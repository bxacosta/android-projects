package ec.edu.uce.controlador;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ec.edu.uce.R;
import ec.edu.uce.modelo.Vehiculo;
import ec.edu.uce.vista.DataAdapter;

public class WelcomeActivity extends AppCompatActivity {

    private List<Vehiculo> vehiculos = new ArrayList<>();
    private RecyclerView recyclerStdudents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initVehiculos();
        recyclerStdudents = findViewById(R.id.RecyclerID);
        recyclerStdudents.setLayoutManager(new LinearLayoutManager(this));

        DataAdapter adapter = new DataAdapter(vehiculos);
        recyclerStdudents.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_persist) {
            return true;
        } else if (id == R.id.action_close) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void initVehiculos() {
        vehiculos.add(new Vehiculo("ydf-1123", "patito", new Date(), 3212.4, true, "verde"));
        vehiculos.add(new Vehiculo("fsd-7523", "perrito", new Date(), 232316.4, true, "rojo"));
        vehiculos.add(new Vehiculo("jde-4523", "raton", new Date(), 223230.4, true, "azul"));
    }

}

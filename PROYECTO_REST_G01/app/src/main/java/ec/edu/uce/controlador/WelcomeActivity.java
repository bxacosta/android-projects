package ec.edu.uce.controlador;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ec.edu.uce.R;
import ec.edu.uce.modelo.Vehiculo;
import ec.edu.uce.servicios.VehiculoService;
import ec.edu.uce.servicios.VehiculoServiceRest;
import ec.edu.uce.vista.DataAdapter;
import ec.edu.uce.vista.ItemClickListener;

public class WelcomeActivity extends AppCompatActivity {

    private VehiculoService vehiculoService = new VehiculoServiceRest();

    public static List<Vehiculo> vehiculos = new ArrayList<>();
    public static DataAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initData();
        initRecyclerView();

        // Define el ClickListener en el RecyclerView
        adapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, final int position, boolean isLongClick) {
                if (isLongClick) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(WelcomeActivity.this);
                    builder.setTitle("Seleccione una opción");
                    String[] options = {"Editar", "Eliminar"};
                    builder.setItems(options, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int item) {
                            switch (item) {
                                case 0: // Editar
                                    Intent intent = new Intent(WelcomeActivity.this, FormActivity.class);
                                    intent.putExtra("position", position);
                                    startActivity(intent);
                                    break;
                                case 1: // Eliminar
                                    vehiculos.remove(position);
                                    updateView();
                                    break;
                            }
                        }
                    });
                    builder.show();
                } else {
                    Toast.makeText(WelcomeActivity.this, "Manten presionado para ver las opciones", Toast.LENGTH_SHORT).show();
                }
            }
        });
        updateView();
    }

    public void initData() {
        vehiculos = vehiculoService.findAll();
    }

    public void initRecyclerView() {
        RecyclerView recyclerStdudents = findViewById(R.id.RecyclerID);
        recyclerStdudents.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DataAdapter(vehiculos, this);
        recyclerStdudents.setAdapter(adapter);
    }

    public void updateView() {
        adapter.notifyDataSetChanged();
    }

    public void redirectFormActivity(View view) {
        Intent intent = new Intent(this, FormActivity.class);
        startActivity(intent);
    }

    public void persistData() {
        Toast.makeText(this, "Persistiendo datos", Toast.LENGTH_SHORT).show();
        vehiculoService.saveAll(vehiculos);
    }

    public void onDestroy() {
        persistData();
//        mDbHelper.close();
        super.onDestroy();
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
            persistData();
            return true;
        } else if (id == R.id.action_close) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

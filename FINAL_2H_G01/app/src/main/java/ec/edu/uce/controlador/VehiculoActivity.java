package ec.edu.uce.controlador;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import ec.edu.uce.R;
import ec.edu.uce.componentes.CustomException;
import ec.edu.uce.modelo.Vehiculo;
import ec.edu.uce.servicios.VehiculoServicio;
import ec.edu.uce.vista.ItemClickListener;
import ec.edu.uce.vista.VehiculoAdapter;

public class VehiculoActivity extends AppCompatActivity {

    private VehiculoAdapter adapter;
    private VehiculoServicio vehiculoServicio = new VehiculoServicio(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiculo);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(floatingButtonListener);

        initRecyclerView();
    }

    private View.OnClickListener floatingButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), VehiculoFormActivity.class);
            startActivity(intent);
            finish();
        }
    };

    private void initRecyclerView() {
        RecyclerView recyclerVehiculo = findViewById(R.id.rvVehiculo);
        recyclerVehiculo.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new VehiculoAdapter(vehiculoServicio.listarTodos());
        adapter.setItemClickListener(btnOpcionesListener);
        recyclerVehiculo.setAdapter(adapter);
    }

    private ItemClickListener btnOpcionesListener = new ItemClickListener() {
        @Override
        public void onClick(final View view, final int position) {
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext(), R.style.AlertDialogCustom);
            String[] options = {"Editar", "Eliminar"};
            builder.setTitle("Seleccione una opción");
            builder.setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    switch (item) {
                        case 0: // Editar
                            Toast.makeText(view.getContext(), "Editar Posicion: " + position, Toast.LENGTH_SHORT).show();
                            break;
                        case 1: // Eliminar
                            final Vehiculo vehiculo = adapter.obtenerVehiculo(position);
                            adapter.eliminarVehiculo(position);

                            Snackbar snackbar = Snackbar.make(view, "Vehiculo con la placa " + vehiculo.getPlaca() + " eliminado de la lista", Snackbar.LENGTH_LONG);
                            snackbar.setAction("DESHACER", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    adapter.restaurarVehiculo(vehiculo, position);
                                }
                            });
                            snackbar.addCallback(new Snackbar.Callback() {
                                @Override
                                public void onDismissed(Snackbar transientBottomBar, int event) {
                                    if (event == DISMISS_EVENT_TIMEOUT) {
                                        System.out.println("BORRA");
                                        try {
                                            vehiculoServicio.borrar(vehiculo);
                                        } catch (CustomException e) {
                                            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }
                            });
                            snackbar.setActionTextColor(getResources().getColor(R.color.colorAccent));
                            snackbar.show();
                            break;
                    }
                }
            });
            builder.show();
        }
    };

    @Override
    protected void onDestroy() {
        vehiculoServicio.close();
        super.onDestroy();
    }
}

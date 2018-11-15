package ec.edu.uce.controlador;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ec.edu.uce.R;
import ec.edu.uce.componentes.CustomException;
import ec.edu.uce.modelo.Vehiculo;
import ec.edu.uce.servicio.VehiculoService;
import ec.edu.uce.vista.DatePickerFragment;

public class FormActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private VehiculoService vehiculoService = new VehiculoService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void save(View view) {
        System.out.println("Entra prro");
        EditText txtPlaca = view.findViewById(R.id.id_placa);
        EditText txtMarca = view.findViewById(R.id.id_marca);
        EditText txtCosto = view.findViewById(R.id.id_costo);
        System.out.println("Entra prro 1");
        EditText txtColor = view.findViewById(R.id.id_color);
        Switch wsEnrollment = view.findViewById(R.id.sw_enrollment);
        TextView txtDate = view.findViewById(R.id.id_date);

        System.out.println("Entra prro 2");
        String datePattern = "dd MMMM yyyy";
        System.out.println("Entra prro 3");
        SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
        System.out.println("Entra prro 4");

        //System.out.println("Precios: " + txtCosto.getText().toString());
        //Double cost = Double.parseDouble(txtCosto.getText().toString());
        //Boolean enrollment = wsEnrollment.isChecked();
        Date date = new Date();
//        try {
//            date = sdf.parse(txtDate.getText().toString());
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setPlaca(txtPlaca.getText().toString());
        vehiculo.setMarca(txtMarca.getText().toString());
        vehiculo.setCosto(121.123);
        vehiculo.setColor(txtColor.getText().toString());
        vehiculo.setMatriculado(true);
        vehiculo.setFechaFabricacion(new Date());

        try {
            vehiculoService.initResources(this);
            vehiculoService.save(vehiculo);
        }catch (CustomException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void showDataPicker(View view) {
        DialogFragment datePicker = new DatePickerFragment();
        datePicker.show(getSupportFragmentManager(), "date picker");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String datePattern = "dd MMMM yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(datePattern);

        String date = sdf.format(c.getTime());
        TextView txtFecha = findViewById(R.id.id_date);
        txtFecha.setText(date);
    }
}

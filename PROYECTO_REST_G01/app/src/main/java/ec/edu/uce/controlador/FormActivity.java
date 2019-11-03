package ec.edu.uce.controlador;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import ec.edu.uce.R;
import ec.edu.uce.componentes.CustomException;
import ec.edu.uce.modelo.Vehiculo;
import ec.edu.uce.vista.DatePickerFragment;

public class FormActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    private EditText txtPlaca;
    private EditText txtMarca;
    private EditText txtCosto;
    private EditText txtColor;
    private TextView txtDate;
    private Switch wsEnrollment;

    private Vehiculo vehiculo;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtPlaca = findViewById(R.id.txt_license);
        txtMarca = findViewById(R.id.txt_brand);
        txtCosto = findViewById(R.id.txt_costo);
        txtColor = findViewById(R.id.txt_color);
        txtDate = findViewById(R.id.txt_date);
        wsEnrollment = findViewById(R.id.sw_enrollment);

        position = getIntent().getIntExtra("position", -1);
        if (position == -1) {
            vehiculo = new Vehiculo();
        } else {
            vehiculo = WelcomeActivity.vehiculos.get(position);
            fillData();
        }
    }

    public void save(View view) {
        Boolean isEnrollment = wsEnrollment.isChecked();
        Double cost = Double.parseDouble(txtCosto.getText().toString());
        Date fabricationDate = new Date();
        try {
            fabricationDate = sdf.parse(txtDate.getText().toString());
        } catch (ParseException e) {
            Toast.makeText(this, "Error al convertir la fecha", Toast.LENGTH_SHORT).show();
        }

        boolean guardar = true;
        // Validar fechas
        Date max = new Date();
        Date min = new GregorianCalendar(1995, 01, 01).getTime();
        if (fabricationDate.after(max) || fabricationDate.before(min)) {
            guardar = false;
            Toast.makeText(this, "La fecha debe estar entre: " + sdf.format(min) + " y " + sdf.format(max), Toast.LENGTH_LONG).show();
        }
        // Validar Placa
        if (!txtPlaca.getText().toString().matches("([A-Za-z]{3}-[0-9]{3,4})")) {
            guardar = false;
            txtPlaca.setError("La palca debe tener el siguiente formato: AAA-1234");
        }
        // Validar campos vacios
        if (TextUtils.isEmpty(txtMarca.getText()) || TextUtils.isEmpty(txtColor.getText())) {
            guardar = false;
            txtMarca.setError("Este campo es obligatorio");
            txtColor.setError("Este campo es obligatorio");
        }

        vehiculo.setPlaca(txtPlaca.getText().toString());
        vehiculo.setMarca(txtMarca.getText().toString());
        vehiculo.setCosto(cost);
        vehiculo.setColor(txtColor.getText().toString());
        vehiculo.setMatriculado(isEnrollment);
        vehiculo.setFechaFabricacion(fabricationDate);

        if (guardar) {
            try {
                if (position == -1) {
                    // Es un nuevo vehiculo
                    if (!WelcomeActivity.vehiculos.contains(vehiculo)) {
                        WelcomeActivity.vehiculos.add(vehiculo);
                        Toast.makeText(this, "Vehiculo con la placa " + vehiculo.getPlaca().toUpperCase() + " agregado correctamente", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(this, "Vehiculo con la placa " + vehiculo.getPlaca().toUpperCase() + " ya existe ingrese otro", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Ya existe un vehiculo
                    WelcomeActivity.vehiculos.set(position, vehiculo);
                    Toast.makeText(this, "Vehiculo con la placa " + vehiculo.getPlaca().toUpperCase() + " se edito correctamente", Toast.LENGTH_SHORT).show();
                    finish();
                }
            } catch (CustomException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * Si ya existe un vehiculo llena sus datos en la vista
     */
    public void fillData() {
        txtPlaca.setEnabled(false);
        txtPlaca.setText(vehiculo.getPlaca());
        txtMarca.setText(vehiculo.getMarca());
        txtCosto.setText(String.valueOf(vehiculo.getCosto()));
        txtColor.setText(vehiculo.getColor());
        txtDate.setText(sdf.format(vehiculo.getFechaFabricacion()));
        wsEnrollment.setChecked(vehiculo.getMatriculado());
    }

    public void showDataPicker(View view) {
        DialogFragment datePicker = new DatePickerFragment();
        datePicker.show(getSupportFragmentManager(), "date-picker");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String date = sdf.format(c.getTime());
        TextView txtFecha = findViewById(R.id.txt_date);
        txtFecha.setText(date);
    }
}

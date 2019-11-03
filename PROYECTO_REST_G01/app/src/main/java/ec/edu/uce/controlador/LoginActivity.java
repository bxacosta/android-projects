package ec.edu.uce.controlador;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ec.edu.uce.R;
import ec.edu.uce.componentes.CustomException;
import ec.edu.uce.servicios.UsuarioService;
import ec.edu.uce.servicios.UsuarioServiceRest;

public class LoginActivity extends AppCompatActivity {

    private static final int CODIGO_DE_SOLICITUD_DE_INTERNET = 1;

    private UsuarioService userService = new UsuarioServiceRest();

    private FloatingActionButton fab;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = findViewById(R.id.fab);
        btnLogin = findViewById(R.id.btnLogin);

        // Desabilita los botones por defecto
        //enableButtons(false);
        checkPermissions();
    }

    public void login(View view) {
        EditText txtUsername = findViewById(R.id.txtUsername);
        EditText txtPassword = findViewById(R.id.txtPassword);

        try {
            boolean succsess = userService.login(txtUsername.getText().toString(), txtPassword.getText().toString());
            if (succsess) {
                Intent intent = new Intent(this, WelcomeActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Usuario o contraseña incorrectos.", Toast.LENGTH_LONG).show();
            }
        } catch (CustomException e) {
            e.printStackTrace();
        }
    }

    public void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, CODIGO_DE_SOLICITUD_DE_INTERNET);
        } else {
            Toast.makeText(this, "Permisos de Internet Obtenidos", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            enableButtons(true);
        } else {
            enableButtons(false);
            Toast.makeText(this, "Para usar esta aplicacion es necesario aceptar los permisos solicitados", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Cambia el estado de los botones
     *
     * @param status true o false para activar los botones
     */
    public void enableButtons(boolean status) {
        fab.setEnabled(status);
        btnLogin.setEnabled(status);
    }


    public void redirectToRegisterActivity(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

}

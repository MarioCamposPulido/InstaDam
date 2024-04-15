package com.example.instadam.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.instadam.R;
import com.example.instadam.base_datos.BaseDatosHelper;

/**
 * Activity Login. Iniciarán sesión todos los usuarios aqui
 */
public class Login extends AppCompatActivity {

    private TextView logoInstadam;

    private TextView emailEditText;

    private TextView passwordEditText;

    private Button inicioSesionButton;

    private Button registroButton;

    private BaseDatosHelper dbHelper;

    /**
     * Abre el MainActivity
     */
    private void openMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * Abre el Registro
     */
    private void openRegistro(){
        Intent intent = new Intent(this, Registro.class);
        startActivity(intent);
    }

    /**
     * Bloquea que se pueda retroceder en este Activity
     * Para evitar que el usuario vea toda la sesión del anterior usuario
     */
    @Override
    public void onBackPressed() {
        // No hacer nada (ignorar el botón de retroceso)
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Cambiar la barra de color de arriba en negro
        getWindow().setStatusBarColor(Color.BLACK);

        logoInstadam = findViewById(R.id.logoInstaDam);
        // Para subrrayar texto, en este caso "InstaDam"
        logoInstadam.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        inicioSesionButton = findViewById(R.id.registrarseButton);
        registroButton = findViewById(R.id.registroButton);

        inicioSesionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper = new BaseDatosHelper(getBaseContext());
                // Comprueba si el usuario existe en la base de datos, si es cierto iniciará sesión
                if (dbHelper.checkUserLogin(emailEditText.getText().toString(), passwordEditText.getText().toString())){
                    openMainActivity();
                }else{
                    Toast.makeText(Login.this, getResources().getString(R.string.errorUsuarioInexiste), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Cambia de vista al Registro
        registroButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegistro();
            }
        });
    }

    /**
     * Cuando la aplicacion se destruya se cerrará la base de datos
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbHelper != null) {
            dbHelper.close(); // Cierre de la instancia de BaseDatosHelper si no es nula
        }
    }
    
}
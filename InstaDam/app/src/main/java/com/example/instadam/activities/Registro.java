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
 * Activity Registro. Se registrarán todos los usuarios
 */
public class Registro extends AppCompatActivity {

    private TextView bienvenidaRegistro;

    private TextView emailRegistro;

    private TextView userNameRegistro;

    private TextView passwordRegistro;

    private TextView checkPasswrdRegistro;

    private Button registrarseButton;

    private BaseDatosHelper dbHelper;

    /**
     * Abre el Login
     */
    private void openLogin(){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    /**
     * Comprueba si el email pertenece a algún usuario, si no es cierto,
     * insertará un nuevo usuario
     * @param email
     * @param passw
     * @param userName
     * @return false, si no existe el email
     */
    private boolean insertarNuevoUsuario(String email, String passw, String userName) {
        boolean exits = false;
        dbHelper = new BaseDatosHelper(getBaseContext());

        if (!dbHelper.checkEmail(userName)) {
            // Insertar nueva fila indicando nombre de la tabla
            dbHelper.insertNewUserRegistro(email, passw, userName);
            Toast.makeText(Registro.this, getResources().getString(R.string.exitoRegistro), Toast.LENGTH_SHORT).show();
        } else {
            exits = true;
            // mostrar algún tipo de error
            Toast.makeText(getBaseContext(), getResources().getString(R.string.errorUsuarioExiste), Toast.LENGTH_SHORT).show();
        }
        return exits;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        // Cambiar la barra de color de arriba en negro
        getWindow().setStatusBarColor(Color.BLACK);

        bienvenidaRegistro = findViewById(R.id.bienvenidaRegistro);
        // Subrraya el texto
        bienvenidaRegistro.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        emailRegistro = findViewById(R.id.emailRegistro);
        userNameRegistro = findViewById(R.id.userNameRegistro);
        passwordRegistro = findViewById(R.id.passwordRegistro);
        checkPasswrdRegistro = findViewById(R.id.checkPasswrdRegistro);
        registrarseButton = findViewById(R.id.registrarseButton);

        /*
            Comprueba si están los campos vacíos y si la contraseña repetida es la misma,
            intentará insertar un nuevo usuario, si se cumple, cambiará al Login
         */
        registrarseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!emailRegistro.getText().toString().isEmpty() && !userNameRegistro.getText().toString().isEmpty() &&
                        !passwordRegistro.getText().toString().isEmpty() && !checkPasswrdRegistro.getText().toString().isEmpty()) {
                    if (!(checkPasswrdRegistro.getText().toString().equals(passwordRegistro.getText().toString()))){
                        Toast.makeText(Registro.this, getResources().getString(R.string.errorPassword), Toast.LENGTH_SHORT).show();
                    }else{
                        if (!insertarNuevoUsuario(emailRegistro.getText().toString(), passwordRegistro.getText().toString(), userNameRegistro.getText().toString())){
                            openLogin();
                        }
                    }
                }else{
                    Toast.makeText(Registro.this, getResources().getString(R.string.errorCamposVacios), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbHelper != null) {
            dbHelper.close(); // Cierre de la instancia de BaseDatosHelper si no es nula
        }
    }
}
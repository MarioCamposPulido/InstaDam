package com.example.instadam.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;

import com.example.instadam.fragments.PerfilFragment;
import com.example.instadam.R;
import com.example.instadam.java.Usuario;
import com.example.instadam.fragments.BuscarFragment;
import com.example.instadam.fragments.PublicarFragment;
import com.example.instadam.fragments.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * Activity MainActivity. Muestra todos los fragmentos y navega por
 * las diferentes opciones del usuario
 */
public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private Fragment fragment;

    // ¡¡¡¡¡ IMPORTANTE !!!!!!
    // Este usuario se utliza para saber los datos del Usuario que inició sesión
    public static Usuario usuario = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Cambiar la barra de color de arriba en negro
        getWindow().setStatusBarColor(Color.BLACK);

        bottomNavigationView = findViewById(R.id.panelabajo);
        // Selecciona por defecto la opción "home" y la muestra al crear el MainActivity
        bottomNavigationView.setSelectedItemId(R.id.home);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new HomeFragment()).commit();

        // Cambia el fragmento dependiendo de la opción que selecciones en el bottomNavigationView
        bottomNavigationView.setOnItemSelectedListener(item -> {

            int idItemn = item.getItemId();

            if (idItemn == R.id.home) {
                fragment = new HomeFragment();
            }
            if (idItemn == R.id.buscar) {
                fragment = new BuscarFragment();
            }
            if (idItemn == R.id.chat) {
                fragment = new PublicarFragment();
            }
            if (idItemn == R.id.perfil) {
                fragment = new PerfilFragment();
            }

            if (fragment != null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, fragment).commit();
            }

            return true;
        });
    }

    /**
     * Infla el menu de opciones
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_menu, menu);
        return true;
    }
}
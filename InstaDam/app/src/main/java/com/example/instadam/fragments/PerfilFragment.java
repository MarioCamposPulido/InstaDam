package com.example.instadam.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.instadam.R;
import com.example.instadam.activities.Login;
import com.example.instadam.activities.MainActivity;
import com.example.instadam.base_datos.BaseDatosHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

/**
 * Fragmento Perfil, cambia los datos del Usuario, puedes ver tu información y cierra sesión
 */
public class PerfilFragment extends Fragment {
    private Button cerrarSesion;
    private Button editarPerfil;
    private TextView userName;
    private TextView generoPerfil;
    private ImageView fotoPerfil;
    private TextView descripcionPerfil;
    private TextView numPublicaciones;
    private TextView numSeguidores;
    private TextView numSeguidos;
    private BaseDatosHelper dbHelper;

    /**
     * Abre el Login
     */
    public void openLogin() {
        Intent intent = new Intent(getContext(), Login.class);
        startActivity(intent);
    }

    public PerfilFragment() {

    }

    // Cambia todos los datos visibles del perfil
    private void asignarDatosPerfil(String username, String genero, String descripcion, int numpublicaciones, int numseguidores, int numseguidos, Bitmap fotoperfilParam) {
        // Asignamos toda la información actual del Usuario al Perfil
        userName.setText(username);
        if (!Objects.isNull(genero)){
            generoPerfil.setText(genero);
        }else{
            generoPerfil.setText(getResources().getString(R.string.noDefinido));
        }
        if (!Objects.isNull(descripcion)){
            descripcionPerfil.setText(descripcion);
        }
        numPublicaciones.setText(String.valueOf(numpublicaciones));
        numSeguidores.setText(String.valueOf(numseguidores));
        numSeguidos.setText(String.valueOf(numseguidos));
        if (Objects.isNull(fotoperfilParam)) {
            fotoPerfil.setImageResource(R.drawable.icon_face);
        } else {
            fotoPerfil.setImageBitmap(MainActivity.usuario.getImagenPerfil());
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        cerrarSesion = view.findViewById(R.id.btnCerrarSesion);
        editarPerfil = view.findViewById(R.id.editarPerfil);
        fotoPerfil = view.findViewById(R.id.fotoperfil);
        generoPerfil = view.findViewById(R.id.generoPerfil);
        descripcionPerfil = view.findViewById(R.id.descripcionPerfil);
        numPublicaciones = view.findViewById(R.id.numPublicaciones);
        numSeguidores = view.findViewById(R.id.numSeguidores);
        numSeguidos = view.findViewById(R.id.numSeguidos);
        userName = view.findViewById(R.id.userName);

        asignarDatosPerfil(MainActivity.usuario.getUsername(), MainActivity.usuario.getGenero(), MainActivity.usuario.getDescripcion(), MainActivity.usuario.getNumPublicaciones(),
                MainActivity.usuario.getNumSeguidores(), MainActivity.usuario.getNumSeguidos(), MainActivity.usuario.getImagenPerfil());

        // Cambia el usuario Iniciado a null y abre el Login
        cerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.usuario = null;
                openLogin();
            }
        });

        // Abre el fragmento EditarPerfil
        editarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new EditarPerfilFragment()).commit();
            }
        });

        // Botón flotante
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Abrimos la camara con este método
                camaraLauncher.launch(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
            }
        });

        return view;
    }

    // Método para abrir la cámara y subir la imagen en la base de datos, tambien cambia la imagen del usuario que inició sesión
    private final ActivityResultLauncher<Intent> camaraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Bundle extras = result.getData().getExtras();
                        Bitmap imgBitmap = (Bitmap) extras.get("data");
                        dbHelper = new BaseDatosHelper(getContext());
                        dbHelper.upgradeFotoPerfil(imgBitmap);
                        fotoPerfil.setImageBitmap(imgBitmap);
                        MainActivity.usuario.setImagenPerfil(imgBitmap);
                    }
                }
            });

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (dbHelper != null) {
            dbHelper.close(); // Cierre de la instancia de BaseDatosHelper si no es nula
        }
    }
}
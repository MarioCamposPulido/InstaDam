package com.example.instadam.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.instadam.activities.MainActivity;
import com.example.instadam.R;

/**
 * Fragmento Home, muestra un mensaje para saludar al Usuario
 */
public class HomeFragment extends Fragment {

    private ImageView imagenBienvenida;

    private TextView mensajeBienvenida;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        imagenBienvenida = view.findViewById(R.id.imagenBienvenida);
        mensajeBienvenida = view.findViewById(R.id.mensajeBienvenida);

        // Cambia el mensaje de bienvenida al userName del Usuario que inició sesión
        mensajeBienvenida.setText("* " + getResources().getString(R.string.bienvenida) + " " + MainActivity.usuario.getUsername() + " *");

        return view;
    }
}
package com.example.instadam.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.instadam.R;
import com.example.instadam.activities.MainActivity;
import com.example.instadam.base_datos.BaseDatosHelper;
import com.example.instadam.java.Usuario;
import com.example.instadam.adapter.AdapterBuscarRecyclerView;

import java.util.LinkedList;

/**
 * Fragmento Buscar, busca usuarios para seguirlos
 */
public class BuscarFragment extends Fragment {

    private RecyclerView recyclerViewBuscar;

    private TextView editTextBuscar;

    private ImageButton buscarButton;

    private BaseDatosHelper dbHelper;

    public BuscarFragment() {

    }

    /**
     * Devuelve una lista de Usuarios sin el usuario que inició sesión
     * (para que no se siga a sí mismo)
     * @return LikedList de Usuarios
     */
    public LinkedList<Usuario> getUsuariosSinUsuarioActivo() {

        LinkedList<Usuario> todosUsuariosSinUsuarioActivo = new LinkedList<>();

        for (int i = 0; i < dbHelper.getAllUsers().size(); i++) {
            if (!dbHelper.getAllUsers().get(i).getEmail().equals(MainActivity.usuario.getEmail())) {
                todosUsuariosSinUsuarioActivo.add(dbHelper.getAllUsers().get(i));
            }
        }

        return todosUsuariosSinUsuarioActivo;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buscar_instadam, container, false);


        buscarButton = view.findViewById(R.id.buscarButton);
        recyclerViewBuscar = view.findViewById(R.id.recyclerViewBuscar);
        editTextBuscar = view.findViewById(R.id.editTextBuscar);
        dbHelper = new BaseDatosHelper(getContext());

        /*
            Busca si el Nombre de usuario introducido contiene la palabra introducida,
            si no introduce nada devolverá todos los usuarios menos el usuarios que entró,
            esto actualizará el recycler de Buscar
         */
        buscarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinkedList<Usuario> usuariosFiltrados = new LinkedList<>();
                for (int i = 0; i < getUsuariosSinUsuarioActivo().size(); i++) {
                    if (!editTextBuscar.getText().toString().equals("")){
                        if (getUsuariosSinUsuarioActivo().get(i).getUsername().contains(editTextBuscar.getText().toString())) {
                            usuariosFiltrados.add(getUsuariosSinUsuarioActivo().get(i));
                        }
                    }else {
                        usuariosFiltrados = getUsuariosSinUsuarioActivo();
                        Toast.makeText(view.getContext(), view.getContext().getResources().getString(R.string.mostrarUsuarios),
                                Toast.LENGTH_SHORT).show();
                    }
                }
                recyclerViewBuscar.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerViewBuscar.setAdapter(new AdapterBuscarRecyclerView(usuariosFiltrados));
            }
        });

        recyclerViewBuscar.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewBuscar.setAdapter(new AdapterBuscarRecyclerView(getUsuariosSinUsuarioActivo()));

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (dbHelper != null) {
            dbHelper.close(); // Cierre de la instancia de BaseDatosHelper si no es nula
        }
    }
}
package com.example.instadam.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.instadam.R;
import com.example.instadam.adapter.AdapterPublicarRecyclerView;
import com.example.instadam.base_datos.BaseDatosHelper;
import com.example.instadam.java.Post;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.LinkedList;

/**
 * Fragmento Publicar, subir publicaciones anónimas en una base de datos y mostrarlas
 */
public class PublicarFragment extends Fragment {

    private TextView publicacionesTextView;
    private RecyclerView recyclerViewPublicar;
    private FloatingActionButton publicarButton;
    private BaseDatosHelper dbHelper;

    private LinkedList<Post> posts;

    public PublicarFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_publicar, container, false);


        recyclerViewPublicar = view.findViewById(R.id.recyclerViewPublicar);
        publicarButton = view.findViewById(R.id.publicarButton);
        publicacionesTextView = view.findViewById(R.id.publicacionesTextView);
        posts = new LinkedList<>();
        // Para subrrayar texto, en este caso "InstaDam"
        publicacionesTextView.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        dbHelper = new BaseDatosHelper(view.getContext());
        recyclerViewPublicar.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewPublicar.setAdapter(new AdapterPublicarRecyclerView(dbHelper.getAllPublicaciones()));

        publicarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Abrimos la camara con este método
                camaraLauncher.launch(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    // Abrimos la cámara, subimos la foto en la base de datos y la mostramos en el recycler
    private final ActivityResultLauncher<Intent> camaraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Bundle extras = result.getData().getExtras();
                        Bitmap imgBitmap = (Bitmap) extras.get("data");
                        dbHelper = new BaseDatosHelper(getContext());
                        dbHelper.insertPublicacion(imgBitmap);
                        recyclerViewPublicar.setLayoutManager(new LinearLayoutManager(getContext()));
                        recyclerViewPublicar.setAdapter(new AdapterPublicarRecyclerView(dbHelper.getAllPublicaciones()));
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
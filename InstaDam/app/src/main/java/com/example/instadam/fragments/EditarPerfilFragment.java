package com.example.instadam.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.instadam.R;
import com.example.instadam.base_datos.BaseDatosHelper;

/**
 * Fragmento EditarPerfil, editas todos los datos en texto del Usuario
 */
public class EditarPerfilFragment extends Fragment {

    private Button datosGenerales;
    private Button datosImportantes;

    private TextView editarNombreUsuario;

    private TextView editarDescripcion;

    private Spinner generoSpinner;

    private String generoSeleccionado = "";

    private TextView editarEmail;
    private TextView editarPassword;

    private BaseDatosHelper dbHelper;

    public EditarPerfilFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editar_perfil, container, false);

        datosGenerales = view.findViewById(R.id.datosGenerales);
        datosImportantes = view.findViewById(R.id.datosImportantes);
        editarNombreUsuario = view.findViewById(R.id.editarNombreUsuario);
        editarDescripcion = view.findViewById(R.id.editarDescripcion);
        generoSpinner = view.findViewById(R.id.generoSpinner);
        editarEmail = view.findViewById(R.id.editarEmail);
        editarPassword = view.findViewById(R.id.editarPassword);

        // Configuración del Spinner
        String[] opciones = getResources().getStringArray(R.array.genero);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, opciones);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Adaptador en el Spinner
        generoSpinner.setAdapter(adapter);

        // Opciones del Spinner
        generoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                generoSeleccionado = adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // Configura el nombre de usuario, la descripción y el genero del usuario
        datosGenerales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editarNombreUsuario.getText().toString().equals("") && !editarDescripcion.getText().toString().equals("") &&
                        !generoSeleccionado.equals("")) {
                    dbHelper = new BaseDatosHelper(getContext());
                    if (dbHelper.upgradeDatosGenerales(editarNombreUsuario.getText().toString(), editarDescripcion.getText().toString(), generoSeleccionado)) {
                        Toast.makeText(getContext(), getResources().getString(R.string.cambiosAplicados), Toast.LENGTH_SHORT).show();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new PerfilFragment()).commit();
                    } else {
                        Toast.makeText(getContext(), getResources().getString(R.string.errorGeneral), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), getResources().getString(R.string.errorCamposVacios), Toast.LENGTH_SHORT).show();
                }

            }
        });

        // Configura el email y la contraseña del usuario
        datosImportantes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editarEmail.getText().toString().equals("") && !editarPassword.getText().toString().equals("")) {
                    dbHelper = new BaseDatosHelper(getContext());
                    if (dbHelper.upgradeDatosImportantes(editarEmail.getText().toString(), editarPassword.getText().toString())) {
                        Toast.makeText(getContext(), getResources().getString(R.string.cambiosAplicados), Toast.LENGTH_SHORT).show();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new PerfilFragment()).commit();
                    } else {
                        Toast.makeText(getContext(), getResources().getString(R.string.errorGeneral), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), getResources().getString(R.string.errorCamposVacios), Toast.LENGTH_SHORT).show();
                }
            }
        });

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
package com.example.instadam.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instadam.R;
import com.example.instadam.base_datos.BaseDatosHelper;
import com.example.instadam.java.Usuario;

import java.util.LinkedList;
import java.util.Objects;

/**
 * Clase Adaptador para el Recycler del fragmento Buscar
 */
public class AdapterBuscarRecyclerView extends RecyclerView.Adapter<AdapterBuscarRecyclerView.PerfilesViewHolder> {

    private LinkedList<Usuario> usuarios;

    /**
     * Lista de usuarios
     * @param usuarios
     */
    public AdapterBuscarRecyclerView(LinkedList<Usuario> usuarios){
        this.usuarios = usuarios;
    };

    /**
     * Inflar el recycler con el layout correspondiente
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return
     */
    @NonNull
    @Override
    public PerfilesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_buscar_usuarios, parent, false);
        return new PerfilesViewHolder(view);
    }

    /**
     *  Asocia los datos de un elemento de la lista con la vista correspondiente
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull PerfilesViewHolder holder, int position) {
        holder.bind(usuarios.get(position));
    }

    /**
     * Devuelve el número total de elementos de la lista
     * @return
     */
    @Override
    public int getItemCount() {
        return usuarios.size();
    }

    /**
     * Clase para referirse a la celda personalizada del recycler
     */
    class PerfilesViewHolder extends RecyclerView.ViewHolder{

        private TextView userNameUsuario;
        private TextView descripcionUsuario;
        private ImageView fotoUsuario;
        private Button seguirButton;

        private BaseDatosHelper dbHelper;

        /**
         * Declaramos lo que es cada item, para interactuar con él
         * @param itemView
         */
        public PerfilesViewHolder(@NonNull View itemView) {
            super(itemView);
            this.userNameUsuario = itemView.findViewById(R.id.userNameBuscar);
            this.descripcionUsuario = itemView.findViewById(R.id.generoBuscar);
            this.fotoUsuario = itemView.findViewById(R.id.fotoBuscar);
            this.seguirButton = itemView.findViewById(R.id.seguirButton);
        }

        /**
         * Describimos su funcionalidad
         * @param usuario
         */
        public void bind(Usuario usuario){
            userNameUsuario.setText(usuario.getUsername());
            if (!Objects.isNull(usuario.getGenero())){
                descripcionUsuario.setText(usuario.getGenero());
            }
            if (!Objects.isNull(usuario.getImagenPerfil())){
                fotoUsuario.setImageBitmap(usuario.getImagenPerfil());
            }

            seguirButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dbHelper = new BaseDatosHelper(itemView.getContext());
                    dbHelper.upgradeSeguidores(usuario.getEmail());

                    Toast.makeText(itemView.getContext(), itemView.getContext().getResources().getString(R.string.seguisteA) + " " +
                            usuario.getEmail(), Toast.LENGTH_SHORT).show();
                    dbHelper.close();
                }
            });
        }
    }
}

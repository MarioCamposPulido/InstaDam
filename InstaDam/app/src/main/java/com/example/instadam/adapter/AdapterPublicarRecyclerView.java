package com.example.instadam.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instadam.R;
import com.example.instadam.java.Post;

import java.util.LinkedList;

/**
 * Clase Adaptador para el Recycler del fragmento Publicar
 */
public class AdapterPublicarRecyclerView extends RecyclerView.Adapter<AdapterPublicarRecyclerView.PostsViewHolder> {

    private LinkedList<Post> posts;

    /**
     * Lista de posts
     * @param posts
     */
    public AdapterPublicarRecyclerView(LinkedList<Post> posts){
        this.posts = posts;
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
    public PostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_publicar, parent, false);
        return new PostsViewHolder(view);
    }

    /**
     * Asocia los datos de un elemento de la lista con la vista correspondiente
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull PostsViewHolder holder, int position) {
        holder.bind(posts.get(position));
    }

    /**
     * Devuelve el número total de elementos de la lista
     * @return
     */
    @Override
    public int getItemCount() {
        return posts.size();
    }

    /**
     * Clase para referirse a la celda personalizada del recycler
     */
    class PostsViewHolder extends RecyclerView.ViewHolder{

        private ImageView fotoPost;

        /**
         * Declaramos lo que es cada item, para interactuar con él
         * @param itemView
         */
        public PostsViewHolder(@NonNull View itemView) {
            super(itemView);
            this.fotoPost = itemView.findViewById(R.id.ImagePublicacion1);
        }

        /**
         * Describimos su funcionalidad
         * @param post
         */
        public void bind(Post post){
            fotoPost.setImageBitmap(post.getImagenPost());
        }
    }
}
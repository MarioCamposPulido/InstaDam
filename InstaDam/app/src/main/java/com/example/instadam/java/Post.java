package com.example.instadam.java;

import android.graphics.Bitmap;

/**
 * Clase Post para el recycler de la clase Publicar
 */
public class Post {

    private Bitmap imagenPost;

    public Post(Bitmap imagenPost) {
        this.imagenPost = imagenPost;
    }

    public Bitmap getImagenPost() {
        return imagenPost;
    }

    public void setImagenPost(Bitmap imagenPost) {
        this.imagenPost = imagenPost;
    }

    @Override
    public String toString() {
        return "Post{" +
                "imagenPost=" + imagenPost +
                '}';
    }
}

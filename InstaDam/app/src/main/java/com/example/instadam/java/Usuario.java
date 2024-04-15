package com.example.instadam.java;

import android.graphics.Bitmap;

/**
 * Clase Usuario, para referirse a todos los usuarios de la base de datos
 */
public class Usuario {

    private String email;
    private String password;
    private String username;
    private int numPublicaciones;
    private int numSeguidores;
    private int numSeguidos;
    private String descripcion;
    private String genero;
    private Bitmap imagenPerfil;

    public Usuario(String email, String password, String username, int numPublicaciones, int numSeguidores, int numSeguidos, String descripcion, String genero, Bitmap imagenPerfil) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.numPublicaciones = numPublicaciones;
        this.numSeguidores = numSeguidores;
        this.numSeguidos = numSeguidos;
        this.descripcion = descripcion;
        this.genero = genero;
        this.imagenPerfil = imagenPerfil;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getNumPublicaciones() {
        return numPublicaciones;
    }

    public void setNumPublicaciones(int numPublicaciones) {
        this.numPublicaciones = numPublicaciones;
    }

    public int getNumSeguidores() {
        return numSeguidores;
    }

    public void setNumSeguidores(int numSeguidores) {
        this.numSeguidores = numSeguidores;
    }

    public int getNumSeguidos() {
        return numSeguidos;
    }

    public void setNumSeguidos(int numSeguidos) {
        this.numSeguidos = numSeguidos;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Bitmap getImagenPerfil() {
        return imagenPerfil;
    }

    public void setImagenPerfil(Bitmap imagenPerfil) {
        this.imagenPerfil = imagenPerfil;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", numPublicaciones=" + numPublicaciones +
                ", numSeguidores=" + numSeguidores +
                ", numSeguidos=" + numSeguidos +
                ", descripcion='" + descripcion + '\'' +
                ", genero='" + genero + '\'' +
                ", imagenPerfil=" + imagenPerfil +
                '}';
    }
}

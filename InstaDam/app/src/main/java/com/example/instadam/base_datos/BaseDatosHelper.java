package com.example.instadam.base_datos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.instadam.activities.MainActivity;
import com.example.instadam.java.Post;
import com.example.instadam.java.Usuario;

import java.io.ByteArrayOutputStream;
import java.util.LinkedList;

/**
 * Clase BaseDatosHelper, toda la funcionalidad de la base de datos
 */
public class BaseDatosHelper extends SQLiteOpenHelper {

    public BaseDatosHelper(Context context) {
        super(context, EstructuraBBDD.DATABASE_NAME, null, EstructuraBBDD.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(EstructuraBBDD.SQL_CREATE_TABLE_USERS);
        db.execSQL(EstructuraBBDD.SQL_CREATE_TABLE_POSTS);
    }

    /**
     * Cuando se realiza alguna actualizacion de la BBDD
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Código para actualizar la estructura de la base de datos
        db.execSQL("DROP TABLE IF EXISTS " + EstructuraBBDD.TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + EstructuraBBDD.TABLE_POSTS);
        onCreate(db);
    }

    /**
     * Transforma un Array de bytes a bitmap
     * @param bytes Array de bytes
     * @return Bitmap
     */
    public static Bitmap getBitmapFromBytes(byte[] bytes) {
        if (bytes != null) {
            return BitmapFactory.decodeByteArray(bytes, 0 ,bytes.length);
        }
        return null;
    }

    /**
     * Inserta un nuevo Usuario en la base de datos
     * @param email
     * @param pasw
     * @param userName
     */
    public void insertNewUserRegistro(String email, String pasw, String userName) {

        // Creamos mapa de valores con los nombres de las tablas
        ContentValues values = new ContentValues();
        values.put(EstructuraBBDD.COLUMN_EMAIL_USER, email);
        values.put(EstructuraBBDD.COLUMN_PASSWORD_USER, pasw);
        values.put(EstructuraBBDD.COLUMN_USERNAME_USER, userName);
        values.put(EstructuraBBDD.COLUMN_NUM_PUBLICATIONS_USER, 0);
        values.put(EstructuraBBDD.COLUMN_NUM_FOLLOWERS_USER, 0);
        values.put(EstructuraBBDD.COLUMN_NUM_FOLLOWING_USER, 0);

        // Insertar nueva fila indicando nombre de la tabla
        long newRowId = this.getWritableDatabase().insert(
                EstructuraBBDD.TABLE_USERS, null, values);
        //this.close();

    }

    /**
     * Inserta una nueva publicación en la base de datos
     * @param fotoPublicacionParam
     */
    public void insertPublicacion(Bitmap fotoPublicacionParam) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream(20480);
        fotoPublicacionParam.compress(Bitmap.CompressFormat.PNG, 0 , baos);
        byte[] blob = baos.toByteArray();

        // Creamos mapa de valores con los nombres de las tablas
        ContentValues values = new ContentValues();
        values.put(EstructuraBBDD.COLUMN_IMAGE_POST, blob);

        // Insertar nueva fila indicando nombre de la tabla
        long newRowId = this.getWritableDatabase().insert(
                EstructuraBBDD.TABLE_POSTS, null, values);
        //this.close();

    }

    /**
     * Comprueba si existe el Usuario en BBDD por su email
     * @param email
     * @return
     */
    public boolean checkEmail(String email) {

        Cursor cursor = this.getReadableDatabase().rawQuery(
                "SELECT * FROM " + EstructuraBBDD.TABLE_USERS +
                        " WHERE " + EstructuraBBDD.COLUMN_EMAIL_USER + "=? ",
                new String[]{email});

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        //this.close();

        return exists;
    }

    /**
     * Comprueba si el Usuario existe por su email y contraseña
     * @param emailParam
     * @param passwordParam
     * @return
     */
    public boolean checkUserLogin(String emailParam, String passwordParam) {

        Cursor cursor = this.getReadableDatabase().rawQuery(
                "SELECT * FROM " + EstructuraBBDD.TABLE_USERS +
                        " WHERE " + EstructuraBBDD.COLUMN_EMAIL_USER + "=? AND " +
                        EstructuraBBDD.COLUMN_PASSWORD_USER + "=? ",
                new String[]{emailParam, passwordParam});

        boolean exists = cursor.getCount() == 1;
        if (cursor.moveToFirst()) {
            // Acceso a los datos de la fila actual
            String email = cursor.getString(cursor.getColumnIndexOrThrow(EstructuraBBDD.COLUMN_EMAIL_USER));
            String password = cursor.getString(cursor.getColumnIndexOrThrow(EstructuraBBDD.COLUMN_PASSWORD_USER));
            String userName = cursor.getString(cursor.getColumnIndexOrThrow(EstructuraBBDD.COLUMN_USERNAME_USER));
            String numPublicaciones = cursor.getString(cursor.getColumnIndexOrThrow(EstructuraBBDD.COLUMN_NUM_PUBLICATIONS_USER));
            String numSeguidores = cursor.getString(cursor.getColumnIndexOrThrow(EstructuraBBDD.COLUMN_NUM_FOLLOWERS_USER));
            String numSeguidos = cursor.getString(cursor.getColumnIndexOrThrow(EstructuraBBDD.COLUMN_NUM_FOLLOWING_USER));
            String descripcion = cursor.getString(cursor.getColumnIndexOrThrow(EstructuraBBDD.COLUMN_DESCRIPTION_USER));
            String genero = cursor.getString(cursor.getColumnIndexOrThrow(EstructuraBBDD.COLUMN_GENDER_USER));
            byte[] fotoPerfil = cursor.getBlob(cursor.getColumnIndexOrThrow(EstructuraBBDD.COLUMN_IMG_USER));
            Bitmap fotoAlmacenada = getBitmapFromBytes(fotoPerfil);

            MainActivity.usuario = new Usuario(email, password,userName,
                    Integer.parseInt(numPublicaciones), Integer.parseInt(numSeguidores), Integer.parseInt(numSeguidos),
                    descripcion, genero, fotoAlmacenada);

        }
        cursor.close();
        //this.close();


        return exists;
    }

    /**
     * Devuelve Todos los usuarios de la BBDD
     * @return Lista de usuarios
     */
    public LinkedList<Usuario> getAllUsers() {

        LinkedList<Usuario> usuarios = new LinkedList<>();

        Cursor cursor = this.getReadableDatabase().rawQuery(
                "SELECT * FROM " + EstructuraBBDD.TABLE_USERS,
                new String[]{});

        boolean exists = cursor.getCount() == 1;
        if (cursor.moveToFirst()) {
            do {
                // Acceso a los datos de la fila actual
                String email = cursor.getString(cursor.getColumnIndexOrThrow(EstructuraBBDD.COLUMN_EMAIL_USER));
                String password = cursor.getString(cursor.getColumnIndexOrThrow(EstructuraBBDD.COLUMN_PASSWORD_USER));
                String userName = cursor.getString(cursor.getColumnIndexOrThrow(EstructuraBBDD.COLUMN_USERNAME_USER));
                String numPublicaciones = cursor.getString(cursor.getColumnIndexOrThrow(EstructuraBBDD.COLUMN_NUM_PUBLICATIONS_USER));
                String numSeguidores = cursor.getString(cursor.getColumnIndexOrThrow(EstructuraBBDD.COLUMN_NUM_FOLLOWERS_USER));
                String numSeguidos = cursor.getString(cursor.getColumnIndexOrThrow(EstructuraBBDD.COLUMN_NUM_FOLLOWING_USER));
                String descripcion = cursor.getString(cursor.getColumnIndexOrThrow(EstructuraBBDD.COLUMN_DESCRIPTION_USER));
                String genero = cursor.getString(cursor.getColumnIndexOrThrow(EstructuraBBDD.COLUMN_GENDER_USER));
                byte[] fotoPerfil = cursor.getBlob(cursor.getColumnIndexOrThrow(EstructuraBBDD.COLUMN_IMG_USER));
                Bitmap fotoAlmacenada = getBitmapFromBytes(fotoPerfil);

                usuarios.add(new Usuario(email, password,userName,
                        Integer.parseInt(numPublicaciones), Integer.parseInt(numSeguidores), Integer.parseInt(numSeguidos),
                        descripcion, genero, fotoAlmacenada));
            }while(cursor.moveToNext());

        }
        cursor.close();
        //this.close();


        return usuarios;
    }

    /**
     * Devuelve todas las publicaciones de la BBDD
     * @return Lista de posts
     */
    public LinkedList<Post> getAllPublicaciones() {

        LinkedList<Post> posts = new LinkedList<>();

        Cursor cursor = this.getReadableDatabase().rawQuery(
                "SELECT * FROM " + EstructuraBBDD.TABLE_POSTS,
                new String[]{});

        boolean exists = cursor.getCount() == 1;
        if (cursor.moveToFirst()) {
            do {
                // Acceso a los datos de la fila actual
                byte[] fotoPublicacion = cursor.getBlob(cursor.getColumnIndexOrThrow(EstructuraBBDD.COLUMN_IMAGE_POST));
                Bitmap fotoAlmacenada = getBitmapFromBytes(fotoPublicacion);

                posts.add(new Post(fotoAlmacenada));
            }while(cursor.moveToNext());

        }
        cursor.close();
        //this.close();


        return posts;
    }

    /**
     * Actualiza el Nombre de usuario, la descripción y el género del Usuario
     * @param userNameParam
     * @param descripcionParm
     * @param generoParm
     * @return
     */
    public boolean upgradeDatosGenerales(String userNameParam, String descripcionParm, String generoParm) {

        // Nuevo valor para la columna
        ContentValues values = new ContentValues();
        values.put(EstructuraBBDD.COLUMN_USERNAME_USER, userNameParam);
        values.put(EstructuraBBDD.COLUMN_DESCRIPTION_USER, descripcionParm);
        values.put(EstructuraBBDD.COLUMN_GENDER_USER, generoParm);

        // NOmbre columna a actualizar
        String selection = EstructuraBBDD.COLUMN_EMAIL_USER + " LIKE ?";

        int count = this.getWritableDatabase().update(
                EstructuraBBDD.TABLE_USERS,
                values,
                selection,
                new String[]{MainActivity.usuario.getEmail()});

        MainActivity.usuario.setUsername(userNameParam);
        MainActivity.usuario.setDescripcion(descripcionParm);
        MainActivity.usuario.setGenero(generoParm);

        return count > 0;

    }

    /**
     * Actualiza el email y la contraseña del Usuario
     * @param emailParam
     * @param passwordParam
     * @return
     */
    public boolean upgradeDatosImportantes(String emailParam, String passwordParam) {

        // Nuevo valor para la columna
        ContentValues values = new ContentValues();
        values.put(EstructuraBBDD.COLUMN_EMAIL_USER, emailParam);
        values.put(EstructuraBBDD.COLUMN_PASSWORD_USER, passwordParam);

        // NOmbre columna a actualizar
        String selection = EstructuraBBDD.COLUMN_EMAIL_USER + " LIKE ?";

        int count = this.getWritableDatabase().update(
                EstructuraBBDD.TABLE_USERS,
                values,
                selection,
                new String[]{MainActivity.usuario.getEmail()});

        MainActivity.usuario.setEmail(emailParam);
        MainActivity.usuario.setPassword(passwordParam);

        return count > 0;

    }

    /**
     * Actualiza la foto de Perfil del Usuario
     * @param fotoPerfil
     * @return
     */
    public boolean upgradeFotoPerfil(Bitmap fotoPerfil) {

        // tamaño del baos depende del tamaño de tus imagenes en promedio
        ByteArrayOutputStream baos = new ByteArrayOutputStream(20480);
        fotoPerfil.compress(Bitmap.CompressFormat.PNG, 0 , baos);
        byte[] blob = baos.toByteArray();

        // Nuevo valor para la columna
        ContentValues values = new ContentValues();
        values.put(EstructuraBBDD.COLUMN_IMG_USER, blob);

        // NOmbre columna a actualizar
        String selection = EstructuraBBDD.COLUMN_EMAIL_USER + " LIKE ?";

        int count = this.getWritableDatabase().update(
                EstructuraBBDD.TABLE_USERS,
                values,
                selection,
                new String[]{MainActivity.usuario.getEmail()});

        return count > 0;

    }

    /**
     * Actualiza los seguidores del Usuario
     * @param emailFollow Usuario a seguir
     * @return
     */
    public boolean upgradeSeguidores(String emailFollow) {

        int valueFollowers = obtenerValorActualFollowers(emailFollow) + 1;
        int valueFollowing = obtenerValorActualFollowing() + 1;

        // Nuevo valor para la columna
        ContentValues values = new ContentValues();
        values.put(EstructuraBBDD.COLUMN_NUM_FOLLOWERS_USER, valueFollowers);

        // NOmbre columna a actualizar
        String selection = EstructuraBBDD.COLUMN_EMAIL_USER + " LIKE ?";

        int count = this.getWritableDatabase().update(
                EstructuraBBDD.TABLE_USERS,
                values,
                selection,
                new String[]{emailFollow});

        // Nuevo valor para la columna
        values = new ContentValues();
        values.put(EstructuraBBDD.COLUMN_NUM_FOLLOWING_USER, valueFollowing);

        int count2 = this.getWritableDatabase().update(
                EstructuraBBDD.TABLE_USERS,
                values,
                selection,
                new String[]{MainActivity.usuario.getEmail()});

        MainActivity.usuario.setNumSeguidos(valueFollowing);

        return count > 0 && count2 > 0;

    }

    /**
     * Método para obtener el valor actual de los seguidores
     * @param userEmailParam
     * @return
     */
    private int obtenerValorActualFollowers(String userEmailParam) {
        int valorActual = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String comillasUserEmailParam = "'" + userEmailParam + "'";
        Cursor cursor = db.rawQuery("SELECT " + EstructuraBBDD.COLUMN_NUM_FOLLOWERS_USER + " FROM " + EstructuraBBDD.TABLE_USERS + " WHERE " +
                EstructuraBBDD.COLUMN_EMAIL_USER + " LIKE " + comillasUserEmailParam, null);

        if (cursor.moveToFirst()) {
            valorActual = cursor.getInt(cursor.getColumnIndexOrThrow(EstructuraBBDD.COLUMN_NUM_FOLLOWERS_USER));
        }

        cursor.close();
        db.close();

        return valorActual;
    }

    /**
     * Método para obtener el valor actual de los seguidos
     * @return
     */
    private int obtenerValorActualFollowing() {
        int valorActual = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String comillasUserEmailParam = "'" + MainActivity.usuario.getEmail() + "'";
        Cursor cursor = db.rawQuery("SELECT " + EstructuraBBDD.COLUMN_NUM_FOLLOWING_USER + " FROM " + EstructuraBBDD.TABLE_USERS + " WHERE " +
                EstructuraBBDD.COLUMN_EMAIL_USER + " LIKE " + comillasUserEmailParam, null);

        if (cursor.moveToFirst()) {
            valorActual = cursor.getInt(cursor.getColumnIndexOrThrow(EstructuraBBDD.COLUMN_NUM_FOLLOWING_USER));
        }

        cursor.close();
        db.close();

        return valorActual;
    }


}






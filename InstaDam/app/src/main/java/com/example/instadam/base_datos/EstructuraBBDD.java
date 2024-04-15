package com.example.instadam.base_datos;


/**
 * Clase para crear la estructura de la BBDD a usar
 */
public class EstructuraBBDD {

    /* Definimos datos de la tabla*/
    public static final String DATABASE_NAME = "InstaDam";
    public static final int DATABASE_VERSION = 2;
    // Tabla Users
    public static final String TABLE_USERS = "Users";
    public static final String TABLE_POSTS = "Posts";
    public static final String COLUMN_ID_USER = "id_user";
    public static final String COLUMN_EMAIL_USER = "email";
    public static final String COLUMN_PASSWORD_USER = "password";
    public static final String COLUMN_USERNAME_USER = "user_name";
    public static final String COLUMN_NUM_PUBLICATIONS_USER = "num_publications";
    public static final String COLUMN_NUM_FOLLOWERS_USER = "num_followers";
    public static final String COLUMN_NUM_FOLLOWING_USER = "num_following";
    public static final String COLUMN_DESCRIPTION_USER = "description";
    public static final String COLUMN_GENDER_USER = "gender";
    public static final String COLUMN_IMG_USER = "img";
    // Tabla Posts
    public static final String COLUMN_ID_POST = "id_post";
    public static final String COLUMN_IMAGE_POST = "image";

    // Creamos las tablas
    public static final String SQL_CREATE_TABLE_USERS =
            "CREATE TABLE " + EstructuraBBDD.TABLE_USERS + " (" +
                    EstructuraBBDD.COLUMN_ID_USER + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    EstructuraBBDD.COLUMN_EMAIL_USER + " TEXT UNIQUE," +
                    EstructuraBBDD.COLUMN_PASSWORD_USER + " TEXT," +
                    EstructuraBBDD.COLUMN_USERNAME_USER + " TEXT," +
                    EstructuraBBDD.COLUMN_NUM_PUBLICATIONS_USER + " INTEGER," +
                    EstructuraBBDD.COLUMN_NUM_FOLLOWERS_USER + " INTEGER," +
                    EstructuraBBDD.COLUMN_NUM_FOLLOWING_USER + " INTEGER," +
                    EstructuraBBDD.COLUMN_DESCRIPTION_USER + " TEXT," +
                    EstructuraBBDD.COLUMN_GENDER_USER + " TEXT," +
                    EstructuraBBDD.COLUMN_IMG_USER + " BLOB);" ;

    public static final String SQL_CREATE_TABLE_POSTS =
            "CREATE TABLE " + EstructuraBBDD.TABLE_POSTS + " (" +
                    EstructuraBBDD.COLUMN_ID_POST + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    EstructuraBBDD.COLUMN_IMAGE_POST + " BLOB);";
}






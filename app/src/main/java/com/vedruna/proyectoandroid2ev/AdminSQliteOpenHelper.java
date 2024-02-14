package com.vedruna.proyectoandroid2ev;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

/**
 * La clase AdminSQliteOpenHelper es un ayudante para la gestión de la base de datos SQLite.
 */

public class AdminSQliteOpenHelper extends SQLiteOpenHelper {
    // Define el número de versión deseado para tu base de datos
    private static final int DATABASE_VERSION = 7;

    /**
     * Constructor de la clase AdminSQliteOpenHelper
     * @param context El contexto de la aplicación
     */

    public AdminSQliteOpenHelper(@Nullable Context context) {
        super(context, "libros.db", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create usuarios table
        db.execSQL("CREATE TABLE usuarios (usuario TEXT PRIMARY KEY, contraseña TEXT)");
        // Create characters table
        try {
            // Try to create books table
            db.execSQL("CREATE TABLE libros (libro TEXT PRIMARY KEY, descripcion TEXT)");
        } catch (SQLiteException e) {
            // Ignore the exception if the table already exists
        }

        ContentValues values = new ContentValues();
        values.put("usuario", "Francisco");
        values.put("contraseña", "Abcd1234");
        db.insert("usuarios", null, values);




    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Aquí puedes incluir la lógica para migrar los datos si es necesario
        // Por ejemplo, puedes eliminar la tabla existente y crear una nueva
       if (oldVersion < newVersion) {
            db.execSQL("DROP TABLE IF EXISTS usuarios");
            db.execSQL("DROP TABLE IF EXISTS libros");
           onCreate(db);
        }
    }
    /**
     * Método para obtener la lista de libros desde la base de datos
     * @return Lista de libros
     */

    public ArrayList<String> obtenerLibros() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> libros = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT libro, descripcion FROM libros", null);
        if (cursor.moveToFirst()) {
            do {
                String nombreLibro = cursor.getString(cursor.getColumnIndexOrThrow("libro"));
                String descripcionLibro = cursor.getString(cursor.getColumnIndexOrThrow("descripcion"));
                String libro = nombreLibro + "\n" + descripcionLibro;
                libros.add(libro);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return libros;
    }

    /**
     * Método para insertar un nuevo libro en la base de datos
     * @param libro Nombre del libro
     * @param descripcion Descripción del libro
     */

    public void insertarLibro(String libro, String descripcion) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            // Verificar si el libro ya existe
            Cursor cursor = db.rawQuery("SELECT * FROM libros WHERE libro = ?", new String[]{libro});
            if (cursor.moveToFirst()) {
                // El libro ya existe, no se puede crear
                cursor.close();
                return;
            }
            cursor.close();
            // Si el libro no existe, se inserta en la base de datos
            ContentValues values = new ContentValues();
            values.put("libro", libro);
            values.put("descripcion", descripcion);
            db.insert("libros", null, values);
        } catch (Exception e) {
            // Manejar cualquier excepción que pueda ocurrir
            e.printStackTrace();
        } finally {
            // Asegurarse de cerrar la base de datos
            if (db != null) {
                db.close();
            }
        }
    }

}
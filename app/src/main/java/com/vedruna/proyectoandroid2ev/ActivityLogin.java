package com.vedruna.proyectoandroid2ev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * La clase ActivityLogin representa la funcionalidad de inicio de sesión de la aplicación.
 */

public class ActivityLogin extends AppCompatActivity {

    private EditText nombre;
    private EditText contrasena;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        nombre = findViewById(R.id.Nombre);
        contrasena = findViewById(R.id.Contrasena);

    }


    /**LOGIN
     * Método para manejar la acción de inicio de sesión cuando se hace clic en el botón de inicio de sesión.
     * @param view Vista del botón de inicio de sesión
     */

    public void loguear(View view) {
        String user = nombre.getText().toString();
        String password = contrasena.getText().toString();

        if(user.isEmpty() || password.isEmpty()) {
            Toast.makeText(ActivityLogin.this, "You cannot leave fields empty", Toast.LENGTH_SHORT).show();
            return;
        }

        // Access the database to verify user credentials
        AdminSQliteOpenHelper dbHelper = new AdminSQliteOpenHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {"usuario", "contraseña"};
        String selection = "usuario = ? AND contraseña = ?";
        String[] selectionArgs = {user, password};

        Cursor cursor = db.query("usuarios", projection, selection, selectionArgs, null, null, null);

        // Check if a user with the provided credentials was found
        if (cursor.moveToFirst()) {
            // If credentials are valid, start the main activity
            Toast.makeText(ActivityLogin.this, "Login successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ActivityLogin.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            // If credentials are incorrect, display an error message
            Toast.makeText(ActivityLogin.this, "Incorrect username or password", Toast.LENGTH_SHORT).show();
        }

        // Close the cursor and database to release resources
        cursor.close();
        db.close();
    }
}
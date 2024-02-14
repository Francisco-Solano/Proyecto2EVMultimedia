package com.vedruna.proyectoandroid2ev;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * Una simple subclase de {@link AppCompatActivity} para la actividad principal.
 */
public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    public  AdminSQliteOpenHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Establecer la ActionBar
        setSupportActionBar(findViewById(R.id.toolbar));  // Reemplaza "R.id.toolbar" con el ID correcto de tu Toolbar

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavController navController = Navigation.findNavController(this, R.id.navHostFragment);




        // Configura la AppBarConfiguration con los fragmentos de nivel superior
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.home,
                R.id.crear,
                R.id.editar,
                R.id.eliminar,
                R.id.salir
        ).build();


        // Configura la barra de acción con el controlador de navegación
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        // Configura la navegación con el controlador y la barra de acción
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        // Manejar la selección del elemento del menú en el BottomNavigationView
        dbHelper = new AdminSQliteOpenHelper(this);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home) {
                navController.navigate(R.id.homeFragment);
                return true;
            } else if (item.getItemId() == R.id.crear) {
                navController.navigate(R.id.createFragment);
                return true;
            } else if (item.getItemId() == R.id.editar) {
                navController.navigate(R.id.editFragment);
                return true;
            } else if (item.getItemId() == R.id.eliminar) {
                navController.navigate(R.id.eliminarFragment);
                return true;
            } else if (item.getItemId() == R.id.salir) {
                showLogoutDialog();
                return true;
            } else {
                return false;
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.home);

        // getAll();

    }

    private void showLogoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.logout_dialog);
        final AlertDialog dialog = builder.create();
        dialog.show();

        Button buttonYes = dialog.findViewById(R.id.buttonYes);
        Button buttonNo = dialog.findViewById(R.id.buttonNo);

        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí puedes realizar las acciones para cerrar la sesión
                // Por ejemplo, iniciar otra actividad de inicio de sesión o cerrar la sesión actual
                dialog.dismiss();
                cerrarSesion();
            }
        });

        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void cerrarSesion() {
        // Puedes mostrar un Toast antes de cambiar de actividad
        Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show();

        // Abrir la nueva actividad (ActivityLogin)
        Intent intent = new Intent(this, ActivityLogin.class);
        startActivity(intent);
        // Asegúrate de finalizar la actividad actual si no deseas volver a ella
        finish();
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.navHostFragment);
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }
    private void getAll(){
        dbHelper.insertarLibro("Cien años de soledad", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
        dbHelper.insertarLibro("El principito", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
        dbHelper.insertarLibro("Don Quijote de la Mancha", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
        dbHelper.insertarLibro("Crónica de una muerte anunciada", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
        dbHelper.insertarLibro("Rayuela", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
        dbHelper.insertarLibro("El amor en los tiempos del cólera", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
        dbHelper.insertarLibro("Cien años de soledad", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
        dbHelper.insertarLibro("Ficciones", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
        dbHelper.insertarLibro("El túnel", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
        dbHelper.insertarLibro("La ciudad y los perros", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
        dbHelper.insertarLibro("El otoño del patriarca", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
        dbHelper.insertarLibro("Pedro Páramo", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
    }


}
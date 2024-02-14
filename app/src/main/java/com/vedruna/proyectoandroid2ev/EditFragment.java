package com.vedruna.proyectoandroid2ev;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Una simple subclase de {@link Fragment} para editar libro.
 * Use el método de fábrica {@link EditFragment#newInstance} para
 * crear una instancia de este fragmento.
 */

public class EditFragment extends Fragment {
    private EditText actualNameText;
    private EditText libronuevo;
    private EditText descripcionnueva;
    private Button editButton;
    private AdminSQliteOpenHelper dbHelper;
    private ArrayList<String> libros = new ArrayList<>();
    private CreateFragment create;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    /**
     * Constructor público vacío requerido.
     */

    public EditFragment() {
        // Required empty public constructor
    }

    /**
     * Use este método de fábrica para crear una nueva instancia de este fragmento utilizando los parámetros proporcionados.
     *
     * @param param1 Parámetro 1.
     * @param param2 Parámetro 2.
     * @return Una nueva instancia de EditFragment.
     */

    public static EditFragment newInstance(String param1, String param2) {
        EditFragment fragment = new EditFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new AdminSQliteOpenHelper(getContext());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit, container, false);
        actualNameText = view.findViewById(R.id.actualNameText);
        libronuevo = view.findViewById(R.id.nameText);
        descripcionnueva = view.findViewById(R.id.desText);
        editButton = view.findViewById(R.id.editButton); // Inicializar el botón editButton


        editButton.setOnClickListener(v -> editarLibro());
        create = new CreateFragment();

        return view;
    }

    /**
     * Método para editar un libro en la base de datos.
     */

    private void editarLibro() {
        String actualBook = actualNameText.getText().toString().trim();
        String book = libronuevo.getText().toString().trim();
        String description = descripcionnueva.getText().toString().trim();
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Realizar la actualización del libro
        ContentValues values = new ContentValues();
        values.put("libro", book);
        values.put("descripcion", description);
        int rowsAffected = db.update("libros", values, "libro = ?", new String[]{actualBook});
        db.close();

        if (rowsAffected > 0) {
            Toast.makeText(requireContext(), "Libro actualizado exitosamente.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(requireContext(), "No se pudo actualizar el libro. Por favor, verifique el nombre.", Toast.LENGTH_SHORT).show();
        }
    }

}
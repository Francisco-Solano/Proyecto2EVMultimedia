package com.vedruna.proyectoandroid2ev;

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
 * Un simple subclase de {@link Fragment} para eliminar un libro.
 * Utilice el método de fábrica {@link DeleteFragment#newInstance} para
 * crear una instancia de este fragmento.
 */

public class DeleteFragment extends Fragment {
    private EditText libro;
    private Button deleteButton;
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

    public DeleteFragment() {
        // Required empty public constructor
    }

    /**
     * Use este método de fábrica para crear una nueva instancia de este fragmento utilizando los parámetros proporcionados.
     *
     * @param param1 Parámetro 1.
     * @param param2 Parámetro 2.
     * @return Una nueva instancia de DeleteFragment.
     */

    public static DeleteFragment newInstance(String param1, String param2) {
        DeleteFragment fragment = new DeleteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        dbHelper = new AdminSQliteOpenHelper(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delete, container, false);
        libro = view.findViewById(R.id.nameText);
        deleteButton = view.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(v -> eliminarLibro(libro.getText().toString()));

        create = new CreateFragment();
        return view;
    }


    /**
     * Método para eliminar un libro de la base de datos.
     *
     * @param libro Nombre del libro a eliminar.
     */

    private void eliminarLibro(String libro) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int deletedRows = db.delete("libros", "libro = ?", new String[]{libro});
        if (deletedRows > 0) {
            Toast.makeText(requireContext(), "Libro eliminado exitosamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(requireContext(), "No se puede eliminar el libro. Verifique el nombre", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }


}
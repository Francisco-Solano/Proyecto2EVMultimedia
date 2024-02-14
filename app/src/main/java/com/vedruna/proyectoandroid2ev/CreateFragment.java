package com.vedruna.proyectoandroid2ev;

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
 * Una simple subclase de {@link Fragment} para crear un nuevo libro.
 * Use el método de fábrica {@link CreateFragment#newInstance} para
 * crear una instancia de este fragmento.
 */


public class CreateFragment extends Fragment {
    private EditText libro;
    private EditText descripcion;
    private Button createButton;
    private AdminSQliteOpenHelper dbHelper;
    private ArrayList<String> libros = new ArrayList<>();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    /**
     * Constructor predeterminado para CreateFragment.
     */

    public CreateFragment() {
        // Required empty public constructor
    }

    /**
     * Utilice este método de fábrica para crear una nueva instancia de este fragmento utilizando los parámetros proporcionados.
     *
     * @param param1 Parámetro 1.
     * @param param2 Parámetro 2.
     * @return Una nueva instancia de CreateFragment.
     */

    public static CreateFragment newInstance(String param1, String param2) {
        CreateFragment fragment = new CreateFragment();
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create, container, false);
        libro = view.findViewById(R.id.nameText);
        descripcion = view.findViewById(R.id.desText);
        createButton = view.findViewById(R.id.createButton);
        dbHelper = new AdminSQliteOpenHelper(getContext());

        createButton.setOnClickListener(v -> createLibros());
        // Inflate the layout for this fragment
        return view;
    }

    /**
     * Método para crear nuevos libros basados en la entrada del usuario.
     */

    private void createLibros() {
        String book = libro.getText().toString().trim();
        String description = descripcion.getText().toString().trim();

        if (!book.isEmpty() && !description.isEmpty()) {
            dbHelper.insertarLibro(book, description);
            actualizarListaLibros();
            Toast.makeText(getContext(), "Libro creado correctamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Por favor, introduce un libro y una descripción", Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * Método para actualizar la lista de libros después de la creación de un nuevo libro.
     */

    public void actualizarListaLibros() {
        libros.clear();
        libros.addAll(dbHelper.obtenerLibros());
        // Obtener el fragmento HomeFragment y actualizar la lista de libros
        Fragment homeFragment = getParentFragmentManager().findFragmentByTag("homeFragment");
        if (homeFragment instanceof HomeFragment) {
            ((HomeFragment) homeFragment).actualizarListaLibros(libros);
        }
    }
}

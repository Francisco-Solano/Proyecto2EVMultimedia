package com.vedruna.proyectoandroid2ev;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Una simple subclase de {@link Fragment} para el fragmento de inicio.
 * Use el método de fábrica {@link HomeFragment#newInstance} para * crear una instancia de este fragmento.
 */

public class HomeFragment extends Fragment {
    private ListView listaLibros;
    private AdminSQliteOpenHelper dbHelper;
    private ArrayAdapter<String> adapter;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    /**
     * Constructor público vacío requerido.
     */

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Utilice este método de fábrica para crear una nueva instancia de este fragmento utilizando los parámetros proporcionados.
     *
     * @param param1 Parámetro 1.
     @param param2 Parámetro 2.
      * @return Una nueva instancia de HomeFragment.
     */

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        listaLibros = view.findViewById(R.id.listaLibros);
        mostrarLibros();
        return view;
    }

    public void actualizarListaLibros(ArrayList<String> libros) {
        if (adapter == null) {
            adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, libros);
            listaLibros.setAdapter(adapter);
        } else {
            adapter.clear();
            adapter.addAll(libros);
            adapter.notifyDataSetChanged();
        }
    }

    private void mostrarLibros() {
        ArrayList<String> libros = dbHelper.obtenerLibros();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, libros);
        listaLibros.setAdapter(adapter);
        actualizarListaLibros(libros);
    }
}
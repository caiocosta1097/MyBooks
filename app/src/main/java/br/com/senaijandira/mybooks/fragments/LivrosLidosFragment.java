package br.com.senaijandira.mybooks.fragments;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import br.com.senaijandira.mybooks.adapter.LivrosLidosAdapter;
import br.com.senaijandira.mybooks.R;
import br.com.senaijandira.mybooks.model.Utils;
import br.com.senaijandira.mybooks.db.MyBooksDatabase;
import br.com.senaijandira.mybooks.model.Livro;

public class LivrosLidosFragment extends Fragment {

    // Inicializando o ListView
    ListView listViewLivros;

    // Inicializando o LivrosAdapter
    LivrosLidosAdapter adapter;

    // Inicializando o banco de dados
    MyBooksDatabase appDB;

    // Método para criar uma view
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // View que recebe o xml da fragment
        View view = inflater.inflate(R.layout.fragment_livros_lidos, container, false);

        // Conectabdo com o banco de dados
        appDB = Room.databaseBuilder(getContext(), MyBooksDatabase.class, Utils.DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        // Pegando o id no xml
        listViewLivros = view.findViewById(R.id.listViewLivros);

        // Instânciando o adapter do ListView
        adapter = new LivrosLidosAdapter(getContext());

        // ListView recebendo o adapter
        listViewLivros.setAdapter(adapter);

        // Retornando a view
        return view;

    }

    // Chamando o método atualizar no onResume
    @Override
    public void onResume() {
        super.onResume();

        atualizar();

    }

    // Método para atualizar a fragment toda vez que ficar visível
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

        super.setUserVisibleHint(isVisibleToUser);

        try{

            atualizar();

        }catch (Exception e){

            e.printStackTrace();

        }

    }

    // Método para atualizar a fragment
    public void atualizar(){

        // Limpa o adapter
        adapter.clear();

        // Seleciona os livros novamente
        Livro[] livros = appDB.livroDao().selecionarLivrosLidos();

        // Exibe de novo
        adapter.addAll(livros);

    }

}

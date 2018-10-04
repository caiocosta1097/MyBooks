package br.com.senaijandira.mybooks.fragments;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import br.com.senaijandira.mybooks.adapter.LivrosAdapter;
import br.com.senaijandira.mybooks.R;
import br.com.senaijandira.mybooks.db.Utils;
import br.com.senaijandira.mybooks.db.MyBooksDatabase;
import br.com.senaijandira.mybooks.model.Livro;

public class LivrosFragment extends Fragment {

    ListView listViewLivros;

    LivrosAdapter adapter;

    MyBooksDatabase appDB;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

        try{

            atualizar();

        }catch (Exception e){

            e.printStackTrace();

        }

        super.setUserVisibleHint(isVisibleToUser);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_livros, container, false);

        appDB = Room.databaseBuilder(getContext(), MyBooksDatabase.class, Utils.DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        listViewLivros = view.findViewById(R.id.listViewLivros);

        adapter = new LivrosAdapter(getContext());

        listViewLivros.setAdapter(adapter);

        return view;

    }

    @Override
    public void onResume() {
        super.onResume();

        atualizar();

    }

    public void atualizar(){

        adapter.clear();

        Livro[] livros = appDB.livroDao().selecionarTodos();

        adapter.addAll(livros);

    }

}

package br.com.senaijandira.mybooks.tabs;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import br.com.senaijandira.mybooks.LivrosAdapter;
import br.com.senaijandira.mybooks.R;
import br.com.senaijandira.mybooks.Utils;
import br.com.senaijandira.mybooks.db.MyBooksDatabase;
import br.com.senaijandira.mybooks.model.Livro;

public class LivrosLidosFragment extends Fragment {

    ListView listViewLivros;

    LivrosAdapter adapter;

    MyBooksDatabase appDB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_livros_lidos, container, false);

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

        adapter.clear();

        Livro[] livros = appDB.livroDao().selecionarLivrosLidos();

        adapter.addAll(livros);

    }

}

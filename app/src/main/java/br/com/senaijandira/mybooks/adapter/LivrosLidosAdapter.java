package br.com.senaijandira.mybooks.adapter;


import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.senaijandira.mybooks.R;
import br.com.senaijandira.mybooks.db.Utils;
import br.com.senaijandira.mybooks.db.MyBooksDatabase;
import br.com.senaijandira.mybooks.model.Livro;

public class LivrosLidosAdapter extends ArrayAdapter<Livro> {

    MyBooksDatabase appDB;

    ImageView imgLivroLer;

    public LivrosLidosAdapter(Context context){

        super(context, 0, new ArrayList<Livro>());

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;

        appDB = Room.databaseBuilder(getContext(), MyBooksDatabase.class, Utils.DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        if (view == null){

            view = LayoutInflater.from(getContext()).inflate(R.layout.livro_lidos_layout, parent, false);

        }

        final Livro livro = getItem(position);

        ImageView imgLivroCapa = view.findViewById(R.id.imgLivroCapa);
        TextView txtLivroTitulo = view.findViewById(R.id.txtLivroTitulo);
        TextView txtLivroDescricao = view.findViewById(R.id.txtLivroDescricao);

        ImageView imgDeletarLivro = view.findViewById(R.id.imgDeleteLivro);

        imgDeletarLivro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Livro livroLista = new Livro(livro.getId(), livro.getCapa(), livro.getTitulo(), livro.getDescricao(), 0);

                appDB.livroDao().atualizar(livroLista);

                clear();

                Livro[] livros = appDB.livroDao().selecionarLivrosLidos();

                addAll(livros);

            }
        });


        imgLivroLer = view.findViewById(R.id.imgLivrosLer);

        imgLivroLer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Livro livroLer = new Livro(livro.getId(), livro.getCapa(), livro.getTitulo(), livro.getDescricao(), 1);

                appDB.livroDao().atualizar(livroLer);

                clear();

                Livro[] livros = appDB.livroDao().selecionarLivrosLidos();

                addAll(livros);

            }
        });


        imgLivroCapa.setImageBitmap(Utils.toBitmap(livro.getCapa()));

        txtLivroTitulo.setText(livro.getTitulo());

        txtLivroDescricao.setText(livro.getDescricao());

        return view;

    }
}

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
import android.widget.Toast;

import java.util.ArrayList;

import br.com.senaijandira.mybooks.R;
import br.com.senaijandira.mybooks.db.Utils;
import br.com.senaijandira.mybooks.db.MyBooksDatabase;
import br.com.senaijandira.mybooks.model.Livro;

public class LivrosLerAdapter extends ArrayAdapter<Livro> {

    MyBooksDatabase appDB;

    ImageView imgLivroLido;

    public LivrosLerAdapter(Context context){

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

            view = LayoutInflater.from(getContext()).inflate(R.layout.livro_ler_layout, parent, false);

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

                Livro[] livros = appDB.livroDao().selecionarLivrosLer();

                addAll(livros);

                Toast.makeText(getContext(), "Livro removido da lista", Toast.LENGTH_SHORT).show();

            }
        });


        imgLivroLido = view.findViewById(R.id.imgLivrosLidos);

        imgLivroLido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Livro livroLido = new Livro(livro.getId(), livro.getCapa(), livro.getTitulo(), livro.getDescricao(), 2);

                appDB.livroDao().atualizar(livroLido);

                clear();

                Livro[] livros = appDB.livroDao().selecionarLivrosLer();

                addAll(livros);

                Toast.makeText(getContext(), "Livro adicionado na lista de livros lidos", Toast.LENGTH_SHORT).show();

            }
        });


        imgLivroCapa.setImageBitmap(Utils.toBitmap(livro.getCapa()));

        txtLivroTitulo.setText(livro.getTitulo());

        txtLivroDescricao.setText(livro.getDescricao());

        return view;

    }
}

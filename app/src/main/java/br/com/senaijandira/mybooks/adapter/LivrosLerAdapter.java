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
import br.com.senaijandira.mybooks.model.Utils;
import br.com.senaijandira.mybooks.db.MyBooksDatabase;
import br.com.senaijandira.mybooks.model.Livro;

public class LivrosLerAdapter extends ArrayAdapter<Livro> {

    // Inicializando o banco de dados
    MyBooksDatabase appDB;

    // Construtor da classe
    public LivrosLerAdapter(Context context){

        super(context, 0, new ArrayList<Livro>());

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // Variável que recebe um view
        View view = convertView;

        //  Conectando o banco de dados
        appDB = Room.databaseBuilder(getContext(), MyBooksDatabase.class, Utils.DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        if (view == null){

            view = LayoutInflater.from(getContext()).inflate(R.layout.livro_ler_layout, parent, false);

        }

        // Criando o livro a partir do item que foi selecionado na lista
        final Livro livro = getItem(position);

        // Pegando os id do xml
        ImageView imgLivroCapa = view.findViewById(R.id.imgLivroCapa);
        TextView txtLivroTitulo = view.findViewById(R.id.txtLivroTitulo);
        TextView txtLivroDescricao = view.findViewById(R.id.txtLivroDescricao);
        ImageView imgRemoverLivro = view.findViewById(R.id.imgDeleteLivro);
        ImageView imgLivroLido = view.findViewById(R.id.imgLivrosLidos);

        // OnClick da imagem de remover
        imgRemoverLivro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Criando um novo livro e só mudando o status para 0
                Livro livroLista = new Livro(livro.getId(), livro.getCapa(), livro.getTitulo(), livro.getDescricao(), 0);

                //  Atualizando o livro no banco de dados
                appDB.livroDao().atualizar(livroLista);

                // Limpando o listView
                clear();

                // Pegando todos os livros do banco de dados
                Livro[] livros = appDB.livroDao().selecionarLivrosLer();

                // Adicionando os livros no listView novamente
                addAll(livros);

                // Mostrar mensagem
                Toast.makeText(getContext(), "Livro removido da lista", Toast.LENGTH_SHORT).show();

            }
        });

        // OnClick da imagem de livros lidos
        imgLivroLido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Criando um novo livro e só mudando o status para 2
                Livro livroLido = new Livro(livro.getId(), livro.getCapa(), livro.getTitulo(), livro.getDescricao(), 2);

                //  Atualizando o livro no banco de dados
                appDB.livroDao().atualizar(livroLido);

                // Limpando o listView
                clear();

                // Pegando todos os livros do banco de dados
                Livro[] livros = appDB.livroDao().selecionarLivrosLer();

                // Adicionando os livros no listView novamente
                addAll(livros);

                // Mostrar mensagem
                Toast.makeText(getContext(), "Livro adicionado na lista de livros lidos", Toast.LENGTH_SHORT).show();

            }
        });

        // Colocando a capa do livro no ImageView
        imgLivroCapa.setImageBitmap(Utils.toBitmap(livro.getCapa()));

        // Colocando o título do livro no TextView
        txtLivroTitulo.setText(livro.getTitulo());

        // Colocando o descrição do livro no TextView
        txtLivroDescricao.setText(livro.getDescricao());

        // Retornando a view na lista
        return view;

    }
}

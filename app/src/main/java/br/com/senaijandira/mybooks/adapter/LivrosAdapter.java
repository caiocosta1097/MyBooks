package br.com.senaijandira.mybooks.adapter;


import android.app.AlertDialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.graphics.drawable.Drawable;
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

import br.com.senaijandira.mybooks.crud.EditarLivro;
import br.com.senaijandira.mybooks.R;
import br.com.senaijandira.mybooks.model.Utils;
import br.com.senaijandira.mybooks.db.MyBooksDatabase;
import br.com.senaijandira.mybooks.model.Livro;

public class LivrosAdapter extends ArrayAdapter<Livro> {

    // Inicializando o banco de dados
    MyBooksDatabase appDB;

    // Inicializando os Drawable
    Drawable drawableLer, drawableLido;

    // Inicializando um alerta
    AlertDialog alerta;

    // Construtor da classe
    public LivrosAdapter(Context context){

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

            view = LayoutInflater.from(getContext()).inflate(R.layout.livro_layout, parent, false);

        }

        // Criando o livro a partir do item que foi selecionado na lista
        final Livro livro = getItem(position);

        // Pegando os id do xml
        ImageView imgLivroCapa = view.findViewById(R.id.imgLivroCapa);
        TextView txtLivroTitulo = view.findViewById(R.id.txtLivroTitulo);
        TextView txtLivroDescricao = view.findViewById(R.id.txtLivroDescricao);
        ImageView imgDeletarLivro = view.findViewById(R.id.imgDeleteLivro);
        ImageView imgEditarLivro = view.findViewById(R.id.imgEditarLivro);
        ImageView imgLivroLer = view.findViewById(R.id.imgLivrosLer);
        ImageView imgLivroLido = view.findViewById(R.id.imgLivrosLidos);

        // OnClick da imagem de deletar
        imgDeletarLivro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Verificando se o livro está na lista para ler ou na lista de lidos
                if (livro.getStatus() == 1 || livro.getStatus() == 2){

                    // Se estiver, exibir um alert de erro
                    alert("Erro", "Não é possível excluir o livro enquanto estiver em outra lista");

                } else {

                    // senão, deletar o livro do banco
                    appDB.livroDao().deletar(livro);

                    // remover o livro da lista
                    remove(livro);

                    // exibe uma mensagem que o livro foi excluído
                    Toast.makeText(getContext(), "Livro excluído", Toast.LENGTH_SHORT).show();

                }

            }
        });

        // OnClick da imagem de editar
        imgEditarLivro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Criando um intent que vai receber a activity EditarLivro
                Intent intent = new Intent(getContext(), EditarLivro.class);

                // Enviando o valor do id do livro selecionado pelo intent
                intent.putExtra("idLivro", livro.getId());

                // Abrindo a activity de editar
                getContext().startActivity(intent);

            }
        });

        // Verificando se o status é igual a 1 para mudar o ícone após o click
        if (livro.getStatus() == 1){

            drawableLer = getContext().getResources().getDrawable(R.drawable.livros_ler);

            imgLivroLer.setImageDrawable(drawableLer);

        } else {

            drawableLer = getContext().getResources().getDrawable(R.drawable.livros_ler_click);

            imgLivroLer.setImageDrawable(drawableLer);

        }

        // OnClick da imagem de livros para ler
        imgLivroLer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // Criando um novo livro e só mudando o status para 1
               Livro livroLer = new Livro(livro.getId(), livro.getCapa(), livro.getTitulo(), livro.getDescricao(), 1);

               //  Atualizando o livro no banco de dados
               appDB.livroDao().atualizar(livroLer);

               // Limpando o listView
               clear();

               // Pegando todos os livros do banco de dados
               Livro[] livros = appDB.livroDao().selecionarTodos();


               // Adicionando os livros no listView novamente
               addAll(livros);

               // Mostrar mensagem apenas se o status do livro for 1
               if (livro.getStatus() != 1)
                   Toast.makeText(getContext(), "Livro adicionado na lista de livros para ler", Toast.LENGTH_SHORT).show();

            }
        });

        // Verificando se o status é igual a 2 para mudar o ícone após o click
        if (livro.getStatus() == 2){

            drawableLido = getContext().getResources().getDrawable(R.drawable.livros_lidos);

            imgLivroLido.setImageDrawable(drawableLido);

        } else {

            drawableLido = getContext().getResources().getDrawable(R.drawable.livros_lidos_click);

            imgLivroLido.setImageDrawable(drawableLido);

        }

        // OnClick da imagem de livros lidos
        imgLivroLido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Criando um novo livro e só mudando o status para 2
                Livro livroLido = new Livro(livro.getId(), livro.getCapa(), livro.getTitulo(), livro.getDescricao(), 2);

                //  Atualizando o livro no banco de dados
                appDB.livroDao().atualizar(livroLido);

                // Pegando todos os livros do banco de dados
                clear();

                // Pegando todos os livros do banco de dados
                Livro[] livros = appDB.livroDao().selecionarTodos();

                // Adicionando os livros no listView novamente
                addAll(livros);

                // Mostrar mensagem apenas se o status do livro for 2
                if (livro.getStatus() != 2)
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

    // Método para criar um alert
    public void alert(String titulo, String mensagem) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle(titulo);
        builder.setMessage(mensagem);
        builder.setCancelable(false);


        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    alerta.cancel();
                }
            });

        alerta = builder.create();
        alerta.show();

    }

}

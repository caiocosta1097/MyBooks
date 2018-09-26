package br.com.senaijandira.mybooks;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.senaijandira.mybooks.db.MyBooksDatabase;
import br.com.senaijandira.mybooks.model.Livro;

public class MainActivity extends AppCompatActivity {

    ListView listaViewLivros;

    LivrosAdapter adapter;

    public static Livro[] livros;

    // Variável de acesso ao banco
    private MyBooksDatabase myBooksDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Criando a instância do banco de dados
        myBooksDatabase = Room.databaseBuilder(getApplicationContext(), MyBooksDatabase.class, Utils.DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        listaViewLivros = findViewById(R.id.listViewLivros);

        adapter = new LivrosAdapter(this);

        listaViewLivros.setAdapter(adapter);

    }


    @Override
    protected void onResume() {
        super.onResume();

        adapter.clear();

        // Aqui faz um select no banco
        livros = myBooksDatabase.livroDao().selecionarTodos();

        adapter.addAll(livros);

    }

    public void deletarLivro(Livro livro){

        // Remover do banco de dados
        myBooksDatabase.livroDao().deletar(livro);

        // Remover item da tela
        adapter.remove(livro);

    }

    public void criarLivro(final Livro livro, ViewGroup root){

        final View v = LayoutInflater.from(this)
                .inflate(R.layout.livro_layout,
                        root, false);

        ImageView imgLivroCapa = v.findViewById(R.id.imgLivroCapa);
        TextView txtLivroTitulo = v.findViewById(R.id.txtLivroTitulo);
        TextView txtLivroDescricao = v.findViewById(R.id.txtLivroDescricao);

        ImageView imgDeleteLivro = v.findViewById(R.id.imgDeleteLivro);

        imgDeleteLivro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletarLivro(livro);
            }
        });

        //Setando a imagem
        imgLivroCapa.setImageBitmap( Utils.toBitmap(livro.getCapa()) );

        //Setando o titulo do livro
        txtLivroTitulo.setText(livro.getTitulo());

        //Setando a descrição do livro
        txtLivroDescricao.setText(livro.getDescricao());

        //Exibindo na tela
        root.addView(v);

    }

    public void abrirCadastro(View v){
        startActivity(new Intent(this, CadastroActivity.class));
    }


    private class LivrosAdapter extends ArrayAdapter<Livro>{

        public LivrosAdapter(Context context){

            super(context, 0, new ArrayList<Livro>());

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            View view = convertView;

            if (view == null){

                view = LayoutInflater.from(getContext()).inflate(R.layout.livro_layout, parent, false);

            }

            final Livro livro = getItem(position);

            ImageView imgLivroCapa = view.findViewById(R.id.imgLivroCapa);
            TextView txtLivroTitulo = view.findViewById(R.id.txtLivroTitulo);
            TextView txtLivroDescricao = view.findViewById(R.id.txtLivroDescricao);

            ImageView imgDeleteLivro = view.findViewById(R.id.imgDeleteLivro);

            imgDeleteLivro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deletarLivro(livro);
                }
            });

            //Setando a imagem
            imgLivroCapa.setImageBitmap( Utils.toBitmap(livro.getCapa()) );

            //Setando o titulo do livro
            txtLivroTitulo.setText(livro.getTitulo());

            //Setando a descrição do livro
            txtLivroDescricao.setText(livro.getDescricao());

            return view;

        }
    }

}

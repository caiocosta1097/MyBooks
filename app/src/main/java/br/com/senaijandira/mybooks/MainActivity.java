package br.com.senaijandira.mybooks;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import br.com.senaijandira.mybooks.db.MyBooksDatabase;
import br.com.senaijandira.mybooks.model.Livro;

public class MainActivity extends AppCompatActivity {

    LinearLayout listaLivros;

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

        listaLivros = findViewById(R.id.listaLivros);

        //Criando cadastros Fake
        livros = new Livro[]{
//                new Livro(1,
//                        Utils.toByteArray(getResources(), R.drawable.pequeno_principe ),
//                        "O pequeno principe", getString(R.string.pequeno_principe)
//                        ),
//                new Livro(2,
//                        Utils.toByteArray(getResources(), R.drawable.cinquenta_tons_cinza ),
//                        "50 Tons de cinza", getString(R.string.cinquenta_tons)
//                ),
//                new Livro(3,
//                        Utils.toByteArray(getResources(), R.drawable.kotlin_android ),
//                        "Kotlin com Android", getString(R.string.kotlin_android)
//                )
        };

    }


    @Override
    protected void onResume() {
        super.onResume();

        // Aqui faz um select no banco
        livros = myBooksDatabase.livroDao().selecionarTodos();

        listaLivros.removeAllViews();

        for(Livro l : livros){
            criarLivro(l, listaLivros);
        }

    }

    public void deletarLivro(Livro livro, View v){

        // Remover do banco de dados
        myBooksDatabase.livroDao().deletar(livro);

        // Remover item da tela
        listaLivros.removeView(v);

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
                deletarLivro(livro, v);
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


}

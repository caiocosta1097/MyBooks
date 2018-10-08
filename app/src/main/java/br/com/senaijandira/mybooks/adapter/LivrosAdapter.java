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
import br.com.senaijandira.mybooks.db.Utils;
import br.com.senaijandira.mybooks.db.MyBooksDatabase;
import br.com.senaijandira.mybooks.model.Livro;

public class LivrosAdapter extends ArrayAdapter<Livro> {

    MyBooksDatabase appDB;

    ImageView imgLivroLer;
    ImageView imgLivroLido;

    Drawable drawableLer;
    Drawable drawableLido;

    AlertDialog alerta;

    public LivrosAdapter(Context context){

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

            view = LayoutInflater.from(getContext()).inflate(R.layout.livro_layout, parent, false);

        }

        final Livro livro = getItem(position);

        ImageView imgLivroCapa = view.findViewById(R.id.imgLivroCapa);
        TextView txtLivroTitulo = view.findViewById(R.id.txtLivroTitulo);
        TextView txtLivroDescricao = view.findViewById(R.id.txtLivroDescricao);

        ImageView imgDeletarLivro = view.findViewById(R.id.imgDeleteLivro);

        imgDeletarLivro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (livro.getStatus() == 1 || livro.getStatus() == 2){

                    alert("Erro", "Não é possível excluir o livro enquanto estiver em outra lista");

                } else {

                    appDB.livroDao().deletar(livro);

                    remove(livro);

                    alert("Sucesso", "Livro excluído com sucesso!");

                }

            }
        });

        ImageView imgEditarLivro = view.findViewById(R.id.imgEditarLivro);

        imgEditarLivro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), EditarLivro.class);
                intent.putExtra("livro", livro.getId());

                getContext().startActivity(intent);

            }
        });

        imgLivroLer = view.findViewById(R.id.imgLivrosLer);

        if (livro.getStatus() == 1){

            drawableLer = getContext().getResources().getDrawable(R.drawable.livros_ler);

            imgLivroLer.setImageDrawable(drawableLer);

        } else {

            drawableLer = getContext().getResources().getDrawable(R.drawable.livros_ler_click);

            imgLivroLer.setImageDrawable(drawableLer);

        }

        imgLivroLer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Livro livroLer = new Livro(livro.getId(), livro.getCapa(), livro.getTitulo(), livro.getDescricao(), 1);

               appDB.livroDao().atualizar(livroLer);

               clear();

               Livro[] livros = appDB.livroDao().selecionarTodos();

               addAll(livros);

               if (livro.getStatus() != 1)
                   Toast.makeText(getContext(), "Livro adicionado na lista de livros para ler", Toast.LENGTH_SHORT).show();

            }
        });


        imgLivroLido = view.findViewById(R.id.imgLivrosLidos);

        if (livro.getStatus() == 2){

            drawableLido = getContext().getResources().getDrawable(R.drawable.livros_lidos);

            imgLivroLido.setImageDrawable(drawableLido);

        } else {

            drawableLido = getContext().getResources().getDrawable(R.drawable.livros_lidos_click);

            imgLivroLido.setImageDrawable(drawableLido);

        }

        imgLivroLido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Livro livroLido = new Livro(livro.getId(), livro.getCapa(), livro.getTitulo(), livro.getDescricao(), 2);

                appDB.livroDao().atualizar(livroLido);

                clear();

                Livro[] livros = appDB.livroDao().selecionarTodos();

                addAll(livros);

                if (livro.getStatus() != 2)
                    Toast.makeText(getContext(), "Livro adicionado na lista de livros lidos", Toast.LENGTH_SHORT).show();

            }
        });


        imgLivroCapa.setImageBitmap(Utils.toBitmap(livro.getCapa()));

        txtLivroTitulo.setText(livro.getTitulo());

        txtLivroDescricao.setText(livro.getDescricao());

        return view;

    }

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
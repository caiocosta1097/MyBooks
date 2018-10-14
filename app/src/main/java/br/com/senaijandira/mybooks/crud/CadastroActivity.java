package br.com.senaijandira.mybooks.crud;

import android.app.Activity;
import android.app.AlertDialog;
import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.InputStream;

import br.com.senaijandira.mybooks.R;
import br.com.senaijandira.mybooks.model.Utils;
import br.com.senaijandira.mybooks.db.MyBooksDatabase;
import br.com.senaijandira.mybooks.model.Livro;

public class CadastroActivity extends AppCompatActivity {

    // Inicializando o Bitmap
    Bitmap livroCapa;

    // Inicializando o ImageView
    ImageView imgLivroCapa;

    // Inicializando os EditText
    EditText txtTitulo, txtDescricao;

    // Inicializando o AlertDialog
    AlertDialog alerta;

    // Inicializando o InputStream
    InputStream input;

    // Variável que recebe o código da galeria
    private final int COD_REQ_GALERIA = 101;

    // Inicializando o banco de dados
    private MyBooksDatabase myBooksDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        //  Conectando o banco de dados
        myBooksDatabase = Room.databaseBuilder(getApplicationContext(), MyBooksDatabase.class, Utils.DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        // Pegando os id do xml
        imgLivroCapa = findViewById(R.id.imgLivroCapa);
        txtTitulo = findViewById(R.id.txtTitulo);
        txtDescricao = findViewById(R.id.txtDescricao);
    }

    // Método para abrir a galeria de imagens
    public void AbrirGaleria(View view) {

        // Intent que faz uma ação que permite selecionar dados e retorna-los
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

        // Definindo o tipo de dados que serão retornados
        intent.setType("image/*");

        // Definindo a ação com o intent, um titulo e com o código da galeria
        startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), COD_REQ_GALERIA);

    }

    // Método para mostrar o resultado da activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Verificando se o código requisitado é o mesmo da galeria e se o código do resultado está OK
        if (requestCode == COD_REQ_GALERIA && resultCode == Activity.RESULT_OK){

            try {

                // Pegando o tipo de dado selecionado
                input = getContentResolver().openInputStream(data.getData());

                // Converte para bitmap
                livroCapa = BitmapFactory.decodeStream(input);

                // Coloca o bitmap da imagem no ImagemView e exibe
                imgLivroCapa.setImageBitmap(livroCapa);

            }catch (Exception e){

                e.printStackTrace();

            }

        }

    }

    // Método para salvar o livro
    public void salvarLivro(View view) {

        // Verifica se nenhuma imagem foi selecionada
        if (livroCapa == null){

            // Se não for, exibe um alert com o erro
            alert("Erro", "Selecione uma imagem", 1);

        } else {

            // Tranforma a imagem em um array de bytes e coloca em uma varíavel
            byte[] capa = Utils.toByteArray(livroCapa);

            // Pega o título e coloca em uma variável
            String titulo = txtTitulo.getText().toString();

            // Pega a descrição e colcoa em uma variável
            String descricao = txtDescricao.getText().toString();

            // o status do livro começa em 0
            int status = 0;

            // Verifica se alguma caixa ficou em branco
            if (titulo.equals("") || descricao.equals("")) {

                // Se sim, exibe um alert com erro
                alert("Erro", "Preencha todos os campos", 1);

            } else {

                // Senão, exibe um alert de sucesso
                alert("Sucesso", "Livro cadastrado com sucesso!", 0);

                // Cria um objeto livro e coloca os dados do livro junto do id 0 por ser autoGenerate
                Livro livro = new Livro(0, capa, titulo, descricao, status);

                // Grava o livro no banco de dados
                myBooksDatabase.livroDao().inserir(livro);

            }

        }

    }

    // Método que cria um alert
    public void alert(String titulo, String mensagem, int tipoAlert) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(titulo);
        builder.setMessage(mensagem);
        builder.setCancelable(false);

        if (tipoAlert == 1){

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    alerta.cancel();
                }
            });

        } else {

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });

        }

        alerta = builder.create();
        alerta.show();

    }

}

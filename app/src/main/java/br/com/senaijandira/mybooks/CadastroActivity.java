package br.com.senaijandira.mybooks;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.Arrays;

import br.com.senaijandira.mybooks.model.Livro;

public class CadastroActivity extends AppCompatActivity {

    Bitmap livroCapa;
    ImageView imgLivroCapa;
    EditText txtTitulo, txtDescricao;
    AlertDialog alerta;

    InputStream input;

    private final int COD_REQ_GALERIA = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        imgLivroCapa = findViewById(R.id.imgLivroCapa);
        txtTitulo = findViewById(R.id.txtTitulo);
        txtDescricao = findViewById(R.id.txtDescricao);
    }

    public void AbrirGaleria(View view) {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

        intent.setType("image/*");

        startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), COD_REQ_GALERIA);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == COD_REQ_GALERIA && resultCode == Activity.RESULT_OK){

            try {

                input = getContentResolver().openInputStream(data.getData());

                // Converteu para bitmap
                livroCapa = BitmapFactory.decodeStream(input);

                imgLivroCapa.setImageBitmap(livroCapa);

            }catch (Exception e){

                e.printStackTrace();

            }

        }

    }

    public void salvarLivro(View view) {

        if (livroCapa == null){

            alert("Erro", "Selecione uma imagem", 1);

        } else {

            byte[] capa = Utils.toByteArray(livroCapa);

            String titulo = txtTitulo.getText().toString();

            String descricao = txtDescricao.getText().toString();

            if (titulo.equals("") || descricao.equals("")) {

                alert("Erro", "Preencha todos os campos", 1);

            } else {

                alert("Livro cadastrado", "Livro cadastrado com sucesso!", 0);

                Livro livro = new Livro(0, capa, titulo, descricao);

                // Inserir na variável estática da MainActivity
                int tamanhoArray = MainActivity.livros.length;

                MainActivity.livros = Arrays.copyOf(MainActivity.livros, tamanhoArray + 1);

                MainActivity.livros[tamanhoArray] = livro;

            }

        }

    }

    public void alert(String titulo, String mensagem, int tipoAlert) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(titulo);
        builder.setMessage(mensagem);
        builder.setCancelable(false);

        if (tipoAlert == 1){

            builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    alerta.cancel();
                }
            });

        } else {

            builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
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

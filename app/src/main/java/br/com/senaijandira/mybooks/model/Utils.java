package br.com.senaijandira.mybooks.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class Utils {

    // Variável estática que guarda o nome do banco
    public static final String DATABASE_NAME = "mybooks.db";

    // Método que recebe uma imagem e transforma em um array de bytes para enviar para o banco
    public static byte[] toByteArray( Bitmap bitmap){

        ByteArrayOutputStream blob = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0 , blob);

        return blob.toByteArray();
    }

    // Método que recebe um array de bytes e transforma em uma imagem
    public static Bitmap toBitmap(byte[] imagem){
        return BitmapFactory.decodeByteArray(imagem, 0, imagem.length);
    }

}

package br.com.senaijandira.mybooks.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

// Declara a classe como uma entidade no banco de dados
@Entity
public class Livro {

    // Define a chave primária
    @PrimaryKey(autoGenerate = true)
    private int id;

    // A imagem de capa será um array de bytes
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] capa;

    // Define os atributos
    private String titulo;
    private String descricao;
    private int status;

    // Construtor da classe
    public Livro(){}

    // Método para criar um livro
    public Livro(int id, byte[] capa, String titulo, String descricao, int status){
        this.id= id;
        this.capa = capa;
        this.titulo = titulo;
        this.descricao = descricao;
        this.status = status;
    }

    // Métodos GET e SET da classe
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getCapa() {
        return capa;
    }

    public void setCapa(byte[] capa) {
        this.capa = capa;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

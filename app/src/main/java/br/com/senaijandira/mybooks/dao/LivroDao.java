package br.com.senaijandira.mybooks.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import br.com.senaijandira.mybooks.model.Livro;

@Dao
public interface LivroDao {

    // Método para inserir livro no banco
    @Insert
    void inserir(Livro livro);

    // Método para atualizar livro no banco
    @Update
    void atualizar(Livro livro);

    // Método para deletar livro do banco
    @Delete
    void deletar(Livro livro);

    // Método para pegar todos os livros do banco
    @Query("SELECT * FROM livro")
    Livro[] selecionarTodos();

    // Método para pegar todos os livros para ler
    @Query("SELECT * FROM livro WHERE status = 1")
    Livro[] selecionarLivrosLer();

    // Método para pegar todos os livros lidos
    @Query("SELECT * FROM livro WHERE status = 2")
    Livro[] selecionarLivrosLidos();

    // Método para um livro do banco
    @Query("SELECT * FROM livro WHERE id = :idLivro")
    Livro pegarLivro(int idLivro);

}

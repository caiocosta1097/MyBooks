package br.com.senaijandira.mybooks;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import br.com.senaijandira.mybooks.adapterFragments.AbasAdapter;
import br.com.senaijandira.mybooks.crud.CadastroActivity;


public class MainActivity extends AppCompatActivity {

    // Inicializando o adapter das fragments
    AbasAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Criando o adapter
        adapter = new AbasAdapter(getSupportFragmentManager());

        // Pegando o id do ViewPager no xml
        ViewPager viewPager = findViewById(R.id.abasViewPager);

        //  Colocando o adapter dentro do viewPager
        viewPager.setAdapter(adapter);

        // Pegando o id do TabLayout no xml
        TabLayout tabLayout = findViewById(R.id.abas);

        // Colocando o viewPager dentro do tabLayout
        tabLayout.setupWithViewPager(viewPager);

    }

    // Método para abrir a tela de  cadastro
    public void abrirCadastro(View view){

        // Abrindo a activity de cadastro
        startActivity(new Intent(this, CadastroActivity.class));

    }

}

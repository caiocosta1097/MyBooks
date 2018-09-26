package br.com.senaijandira.mybooks;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import br.com.senaijandira.mybooks.tabs.LivrosFragment;
import br.com.senaijandira.mybooks.tabs.LivrosLerFragment;
import br.com.senaijandira.mybooks.tabs.LivrosLidosFragment;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AbasAdapter adapter = new AbasAdapter(getSupportFragmentManager());
        adapter.adicionar(new LivrosFragment(), "Livros");
        adapter.adicionar(new LivrosLerFragment(), "Ler");
        adapter.adicionar(new LivrosLidosFragment(), "Lidos");

        ViewPager viewPager = (ViewPager) findViewById(R.id.abasViewPager);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.abas);
        tabLayout.setupWithViewPager(viewPager);

    }

    public void abrirCadastro(View view){

        startActivity(new Intent(this, CadastroActivity.class));

    }

}

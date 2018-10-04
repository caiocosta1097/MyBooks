package br.com.senaijandira.mybooks;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import br.com.senaijandira.mybooks.adapterFragments.AbasAdapter;
import br.com.senaijandira.mybooks.crud.CadastroActivity;


public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    AbasAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new AbasAdapter(getSupportFragmentManager());

        ViewPager viewPager = findViewById(R.id.abasViewPager);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);

        TabLayout tabLayout = findViewById(R.id.abas);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        Fragment fragment = adapter.getFragment(position);
        if (fragment != null) {
            fragment.onResume();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void abrirCadastro(View view){

        startActivity(new Intent(this, CadastroActivity.class));

    }

}

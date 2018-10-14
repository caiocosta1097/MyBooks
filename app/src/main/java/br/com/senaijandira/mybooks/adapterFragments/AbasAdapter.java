package br.com.senaijandira.mybooks.adapterFragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import br.com.senaijandira.mybooks.fragments.LivrosFragment;
import br.com.senaijandira.mybooks.fragments.LivrosLerFragment;
import br.com.senaijandira.mybooks.fragments.LivrosLidosFragment;

public class AbasAdapter  extends FragmentPagerAdapter{

    // Varíável estática que recebe o número de fragments
    private static int NUM_ITEMS = 3;

    // Construtor da classe
    public AbasAdapter(FragmentManager manager){

        super(manager);

    }

    // Método que retorna a quantidade de fragments
    @Override
    public int getCount() {

        return NUM_ITEMS;

    }

    // Método que retorna as fragments a partir da posição
    @Override
    public Fragment getItem(int position) {

        switch (position){

            case 0:
                return new LivrosFragment();
            case 1:
                return new LivrosLerFragment();
            case 2:
                return new LivrosLidosFragment();
            default:
                return null;
        }
    }

    // Método que retorna os títulos das fragments a partir da posição
    @Override
    public CharSequence getPageTitle(int position) {

        switch (position){

            case 0:
                return "Livros";
            case 1:
                return "Ler";
            case 2:
                return "Lidos";
            default:
                return null;
        }
    }
}

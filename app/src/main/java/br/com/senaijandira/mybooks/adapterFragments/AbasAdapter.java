package br.com.senaijandira.mybooks.adapterFragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

import br.com.senaijandira.mybooks.fragments.LivrosFragment;
import br.com.senaijandira.mybooks.fragments.LivrosLerFragment;
import br.com.senaijandira.mybooks.fragments.LivrosLidosFragment;

public class AbasAdapter  extends FragmentStatePagerAdapter{

    private static int NUM_ITEMS = 3;

    public AbasAdapter(FragmentManager manager){

        super(manager);

    }

    @Override
    public int getCount() {

        return NUM_ITEMS;

    }

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

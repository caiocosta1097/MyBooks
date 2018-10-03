package br.com.senaijandira.mybooks;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import br.com.senaijandira.mybooks.tabs.LivrosFragment;
import br.com.senaijandira.mybooks.tabs.LivrosLerFragment;
import br.com.senaijandira.mybooks.tabs.LivrosLidosFragment;

public class AbasAdapter  extends FragmentPagerAdapter{


    public AbasAdapter(FragmentManager manager){

        super(manager);

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
    public int getItemPosition(@NonNull Object object) {

        return POSITION_NONE;

    }

    @Override
    public int getCount() {

        return 3;

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

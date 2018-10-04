package br.com.senaijandira.mybooks.adapterFragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

import br.com.senaijandira.mybooks.fragments.LivrosFragment;
import br.com.senaijandira.mybooks.fragments.LivrosLerFragment;
import br.com.senaijandira.mybooks.fragments.LivrosLidosFragment;

public class AbasAdapter  extends FragmentPagerAdapter{

    private static int NUM_ITEMS = 3;
    private Map<Integer, String> mFragmentTags;
    private FragmentManager mFragmentManager;

    public AbasAdapter(FragmentManager manager){

        super(manager);
        mFragmentManager = manager;
        mFragmentTags = new HashMap<Integer, String>();

    }

    @Override
    public int getCount() {

        return NUM_ITEMS;

    }

    @Override
    public Fragment getItem(int position) {

        switch (position){

            case 0:
                return LivrosFragment.newIntance();
            case 1:
                return LivrosLerFragment.newIntance();
            case 2:
                return LivrosLidosFragment.newIntance();
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

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        Object object = super.instantiateItem(container, position);

        if (object instanceof Fragment){

            Fragment fragment = (Fragment) object;

            String tag = fragment.getTag();

            mFragmentTags.put(position, tag);

        }

        return object;

    }

    public Fragment getFragment(int position) {
        Fragment fragment = null;
        String tag = mFragmentTags.get(position);
        if (tag != null) {
            fragment = mFragmentManager.findFragmentByTag(tag);
        }
        return fragment;
    }

}

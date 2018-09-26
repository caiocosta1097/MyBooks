package br.com.senaijandira.mybooks.tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.senaijandira.mybooks.R;

public class LivrosLidosFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_livros_lidos, container, false);

        TextView textView = view.findViewById(R.id.text3);
        textView.setText("Livros lidos");

        return view;

    }

}

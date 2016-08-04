package vhbandroidprogrammierung.de.spruecheapp.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import vhbandroidprogrammierung.de.spruecheapp.R;
import vhbandroidprogrammierung.de.spruecheapp.RecyclerViewStuff.RecyclerAdapter;
import vhbandroidprogrammierung.de.spruecheapp.Saying;


public class AllSayingsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<Saying> sayingArrayList;
    private RecyclerView.LayoutManager layoutManager;
    RecyclerAdapter recyclerAdapter;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_sayings, null);

        context = getContext();

        recyclerView = (RecyclerView) view.findViewById(R.id.rv_all_sayings);
        sayingArrayList = new ArrayList<>();
        sayingArrayList.add(new Saying("Glaube an Wunder, Liebe und Glück! Schau nach vorn und nicht zurück!\n" +
                "Tu was du willst, und steh dazu; denn dein Leben lebst nur du!", "unbekannt", "Lebenssprüche ", false, false));
        sayingArrayList.add(new Saying("\"Lohnt es sich denn?\" fragt der Kopf.\n" +
                "\"Nein, aber es tut so gut!\" antwortet das Herz.", "unbekannt", "Lebenssprüche", false, false));
        sayingArrayList.add(new Saying("Ein langer Streit beweist, dass beide Seiten Unrecht haben.", "Voltaire", "Charakter", false, false));
        sayingArrayList.add(new Saying("Mütter lieben ihre Kinder mehr, als Väter es tun, weil sie sicher sein können, dass es ihre sind.", "Aristoteles", "Taufe ", false, false));
        sayingArrayList.add(new Saying("Ich wünschte ich könnte, aber ich will nicht!", "unbekannt", "Filmzitat ", false, false));

        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        recyclerAdapter = new RecyclerAdapter(context, sayingArrayList);

        recyclerView.setAdapter(recyclerAdapter);


        return view;
    }
}
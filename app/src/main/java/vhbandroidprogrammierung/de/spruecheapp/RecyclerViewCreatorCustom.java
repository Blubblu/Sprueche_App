package vhbandroidprogrammierung.de.spruecheapp;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import vhbandroidprogrammierung.de.spruecheapp.RecyclerViewStuff.RecyclerAdapter;

/**
 * Zentrales Code Snippet zum erstllen von RecyclerViews, da wir diesen Part in mehreren Fragmenten brauchen.
 */

public class RecyclerViewCreatorCustom {

    public RecyclerViewCreatorCustom() {

    }

    public  RecyclerAdapter buildRecyclerViewWithAdapter(RecyclerView rv, List<Saying> sal, Context c) {

        Context context = c;
        List<Saying> sayingArrayList = sal;
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, context.getResources().getInteger(R.integer.recycler_columns));

        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(c, sal);


        rv.setLayoutManager(layoutManager);
        recyclerAdapter = new RecyclerAdapter(context, sayingArrayList);
        rv.setAdapter(recyclerAdapter);

        return recyclerAdapter;

    }


}

package vhbandroidprogrammierung.de.spruecheapp;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import vhbandroidprogrammierung.de.spruecheapp.RecyclerViewStuff.RecyclerAdapter;


public class RecyclerViewCreator {

    public RecyclerViewCreator() {

    }

    public static RecyclerAdapter buildRecyclerViewWithAdapter(RecyclerView rv, ArrayList<Saying> sal, Context c) {

        Context context = c;
        ArrayList<Saying> sayingArrayList = sal;
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, context.getResources().getInteger(R.integer.recycler_columns));

        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(c, sal);


        rv.setLayoutManager(layoutManager);
        recyclerAdapter = new RecyclerAdapter(context, sayingArrayList);
        rv.setAdapter(recyclerAdapter);

        return recyclerAdapter;

    }


}

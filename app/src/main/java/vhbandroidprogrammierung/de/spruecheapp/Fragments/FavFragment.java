package vhbandroidprogrammierung.de.spruecheapp.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vhbandroidprogrammierung.de.spruecheapp.R;

public class FavFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /**
         *Inflate tab_layout and setup Views.
         */
        View x =  inflater.inflate(R.layout.fragment_fav_sayings,null);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.fav_sayings);
        return x;
    }
}
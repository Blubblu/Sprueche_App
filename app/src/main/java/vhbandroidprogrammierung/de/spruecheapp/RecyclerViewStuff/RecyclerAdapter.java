package vhbandroidprogrammierung.de.spruecheapp.RecyclerViewStuff;

/**
 * Adapter that is assigned to the RecyclerView
 * Creates new ViewHolders
 * Connects the data with the view
 *
 * @author Patrick Engelhardt
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import vhbandroidprogrammierung.de.spruecheapp.R;
import vhbandroidprogrammierung.de.spruecheapp.Saying;


public class RecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {

    private static final String TAG = "RecyclerAdapter";
    private List<Saying> mItems;
    private Context context;


    public RecyclerAdapter(Context context, List<Saying> items) {

        this.context = context;
        this.mItems = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int type) {
        View view = null;

        view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.item_recyclerview_saying, viewGroup, false);
        return new ViewHolderSaying(context, view);

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int pos) {
        int type = getItemViewType(pos);

        ListItemSaying item = mItems.get(pos);
        viewHolder.bindType(item);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}

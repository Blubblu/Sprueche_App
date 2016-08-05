package vhbandroidprogrammierung.de.spruecheapp.Fragments;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import vhbandroidprogrammierung.de.spruecheapp.Config;
import vhbandroidprogrammierung.de.spruecheapp.R;
import vhbandroidprogrammierung.de.spruecheapp.Saying;


public class RandomSayingFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "RandomSayingFragment";
    private View view;
    private FloatingActionButton fab;
    private Saying currentSaying;
    private ImageView iv_fav, iv_share;
    private TextView tv_saying;
    private boolean isFragmentVisible;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        this.view = inflater.inflate(R.layout.fragment_random_saying, null);

        currentSaying = new Saying();
        currentSaying.setFavorite(false);

        initUI();

        return view;
    }


    /**
     * Setzt einen boolean wenn das Fragment sichtbar oder unsichtbar wird.
     * FAB wird versteckt wenn das Fragment unsichtbar wird
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isFragmentVisible = isVisibleToUser;

        if (isFragmentVisible) {
            if(fab!= null) {
                animateFab();
            }
            Log.d(TAG, "this fragment is now visible");
        } else {
            if(fab != null) {
                fab.hide();
            }
            Log.d(TAG, "this fragment is now invisible");
        }
    }

    /**
     * FAB nach einem kurzen Delay einblenden
     */
    private void animateFab() {

        fab.setVisibility(View.INVISIBLE);
        fab.setScaleX(0.0F);
        fab.setScaleY(0.0F);
        fab.setAlpha(0.0F);
        fab.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                fab.getViewTreeObserver().removeOnPreDrawListener(this);
                fab.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fab.show();
                    }
                }, Config.fabAnimationTimeMS);
                return true;
            }
        });

}

    private void initUI() {

        /*
        Custom Font
        liegt in src/main/assets/fonts
         */
        tv_saying = (TextView) view.findViewById(R.id.tv_saying_random);
        tv_saying.setTypeface(Typeface.createFromAsset(getContext().getAssets(), getContext().getString(R.string.font_path)));

        this.iv_fav = (ImageView) view.findViewById(R.id.iv_favorite);
        this.iv_share = (ImageView) view.findViewById(R.id.iv_share);
        this.fab = (FloatingActionButton) view.findViewById(R.id.fab_random_sayings);

        this.iv_fav.setOnClickListener(this);
        this.iv_share.setOnClickListener(this);
        this.fab.setOnClickListener(this);

        animateFab();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.iv_favorite:

                /*
                Icon bei user Click ändern
                 */
                if (currentSaying.isFavorite()) {
                    Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_star_outline_white_48dp, null);
                    iv_fav.setImageDrawable(drawable);
                } else {
                    Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_star_white_48dp, null);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        int color = ContextCompat.getColor(getContext(), R.color.orange);
                        drawable.setTint(color);
                    }
                    iv_fav.setImageDrawable(drawable);
                }

                // Boolean isFavorite ändern
                currentSaying.setFavorite(!currentSaying.isFavorite());
                break;

            case R.id.iv_share:
                //TODO: Share
                break;

            case R.id.fab_random_sayings:
                //TODO Neuen Random Spruch laden
                break;

            default:
                break;

        }

    }
}
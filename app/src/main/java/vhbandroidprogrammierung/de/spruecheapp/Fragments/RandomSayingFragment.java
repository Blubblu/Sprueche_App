package vhbandroidprogrammierung.de.spruecheapp.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.Random;

import vhbandroidprogrammierung.de.spruecheapp.Activities.MainActivity;
import vhbandroidprogrammierung.de.spruecheapp.R;
import vhbandroidprogrammierung.de.spruecheapp.SayingDataObjects.Saying;
import vhbandroidprogrammierung.de.spruecheapp.ShakeDetector;


public class RandomSayingFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "RandomSayingFragment";
    private View view;
    private FloatingActionButton fab;
    private Saying currentSaying;
    private ImageView iv_fav, iv_share;
    private TextView tv_saying, tv_author, tv_category;
    private boolean isFragmentVisible;
    private boolean didUserSeeShakeHint;
    private AlertDialog alertDialog;
    private SharedPreferences sharedPreferences;
    private MainActivity activity;
    private Saying randomSaying;
    private TableLayout bottomLayout;

    //Sensor Shake Erkennung
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private ShakeDetector shakeDetector;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        this.view = inflater.inflate(R.layout.fragment_random_saying, null);
        this.activity = (MainActivity) getActivity();

        // Nur zu Testzwecken, um das Setzen den Fav-Booleans zu testen.
        currentSaying = new Saying();
        currentSaying.setFavorite(false);

        initSharedPreferences();
        initUI();
        initShakeDetector();
        loadRandomSaying();

        return view;
    }

    private void loadRandomSaying() {

        // Zufälliges Element aus der Hauptliste der Main Activity holen
        Random rand = new Random();

        try {
            randomSaying = activity
                    .getSayingList()
                    .get(rand.nextInt(activity.getSayingList().size()) + 1);
        } catch (Exception e) {
            Log.e(TAG, "initRandomSaying: Error at loading a random saying");
        }

        if (randomSaying != null) {
            tv_saying.setText(randomSaying.getSaying());
            tv_author.setText(randomSaying.getSayingAuthor().getAuthorName());
            tv_category.setText(randomSaying.getSayingCategory().getCategoryName());
        } else {
            Log.e(TAG, "loadRandomSaying: randomSaying was null");
        }
    }


    private void initSharedPreferences() {

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        // Wenn der user den hint-dialog per Schütteln geschlossen hat, wird der geladene Wert true sein und der dialog wird nie mehr angezeigt.
        didUserSeeShakeHint = sharedPreferences.getBoolean("pref_shake_hint", false);
        Log.i(TAG, "initSharedPreferences: " + didUserSeeShakeHint + " loaded from prefs");
    }

    private void saveSharedPrefs() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("pref_shake_hint", didUserSeeShakeHint);
        editor.commit();
        Log.i(TAG, "saveSharedPrefs: " + didUserSeeShakeHint + " saved to prefs");
    }

    private void initShakeDetector() {

        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        shakeDetector = new ShakeDetector();
        shakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake() {

                if (isFragmentVisible) {

                    // 500 ms vibrieren
                    Vibrator v = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                    v.vibrate(500);

                    loadRandomSaying();

                    /*
                    Wenn der User-Hint-Dialog sichtbar ist, kann man ihn über das Schütteln schließen
                     */
                    if (alertDialog != null && !didUserSeeShakeHint) {
                        alertDialog.dismiss();
                        didUserSeeShakeHint = true;
                    }
                }
            }
        });
    }

    /**
     * Weist den User darauf hin, wie man einen neuen zufälligen Spruch laden kann
     */
    private void showHintDialog() {
        AlertDialog.Builder shakeHintDialog = new AlertDialog.Builder(getContext());
        shakeHintDialog.setTitle("Neuen zufälligen Spruch laden");
        shakeHintDialog.setMessage("Lade einen neuen Spruch entweder über diesen Button, oder indem du das Gerät schüttelst. Teste das Schütteln um den Dialog zu schließen").setCancelable(true);
        alertDialog = shakeHintDialog.create();
        alertDialog.show();
    }


    /**
     * Setzt einen boolean wenn das Fragment sichtbar oder unsichtbar wird.
     * FAB wird versteckt wenn das Fragment unsichtbar wird
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isFragmentVisible = isVisibleToUser;

        if (isFragmentVisible) {
            if (fab != null) {
                //  animateFab();
            }
            Log.d(TAG, "this fragment is now visible");
        } else {
            if (fab != null) {
                //   fab.hide();
            }
            Log.d(TAG, "this fragment is now invisible");
        }
    }

    /**
     * FAB nach einem kurzen Delay einblenden
     */
    private void animateFab() {

        Log.i(TAG, "animateFab: ");
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
                }, 0);
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

        tv_author = (TextView) view.findViewById(R.id.tv_author_random);
        tv_category = (TextView) view.findViewById(R.id.tv_category_random);

        this.bottomLayout = (TableLayout) view.findViewById(R.id.ll_bottom_items);
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

                if (!didUserSeeShakeHint) {
                    showHintDialog();
                }

                loadRandomSaying();

                break;

            default:
                break;
        }
    }

    @Override
    public void onResume() {
        sensorManager.registerListener(shakeDetector, accelerometer, SensorManager.SENSOR_DELAY_UI);
        Log.i(TAG, "onResume: sensorManager registered");
        super.onResume();
    }

    @Override
    public void onPause() {
        sensorManager.unregisterListener(shakeDetector);
        Log.i(TAG, "onPause: sensorManager unregistered");

        saveSharedPrefs();
        super.onPause();
    }

    public void changeBottomBarVisibility(float f) {

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;

        int navigationBarHeight = 0;
        int resourceId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            navigationBarHeight = getResources().getDimensionPixelSize(resourceId);
        }

        // Log.i(TAG, "changeBottomBarVisibility: Display height: " + height + "\n BottomBar Height: " + bottomLayout.getHeight() + " " + navigationBarHeight);
        Log.i(TAG, "changeBottomBarVisibility: " + f);

        bottomLayout.setY(1150 + 3 * f * 100);

        if (f > 0.1) {
            fab.hide();
        }

        if (f < 0.5 && fab.getVisibility() != View.VISIBLE) {
            animateFab();
        }
    }
}
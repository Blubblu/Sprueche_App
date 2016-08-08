package vhbandroidprogrammierung.de.spruecheapp.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vhbandroidprogrammierung.de.spruecheapp.Config;
import vhbandroidprogrammierung.de.spruecheapp.R;

public class HomeFragment extends Fragment {

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);

        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));

        /**
         * Das Runnable ist notwendig. Evtl. Bug in der SupportLibary
         */
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);

                // wenn nur ein Icon zu sehen sein soll
                if (!Config.showTabText) {
                    tabLayout.getTabAt(0).setIcon(R.drawable.ic_shuffle_variant_white_48dp);
                    tabLayout.getTabAt(1).setIcon(R.drawable.ic_view_list_white_48dp);
                }
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;

    }

    class MyAdapter extends FragmentPagerAdapter {

        private static final String TAG = "MyAdapter";
        public String[] tabTitles = getResources().getStringArray(R.array.tab_titles);

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Gibt, abhängig von der Position, das anzuzeigende Fragment zurück
         */

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new RandomSayingFragment();
                case 1:
                    return new AllSayingsFragment();
            }
            return null;
        }

        @Override
        public int getCount() {

            return int_items;

        }

        /**
         * Gibt den jeweiligen Tab Titel zurück.
         * Nichts zurück geben, wenn Icons angezeigt werden sollen
         */

        @Override
        public CharSequence getPageTitle(int position) {

            Log.i(TAG, "getPageTitle: " + tabTitles[0] + " " + tabTitles[1]);


            if (!Config.showTabText) {
                return "";
            } else {
                return tabTitles[position];
            }
        }
    }

}
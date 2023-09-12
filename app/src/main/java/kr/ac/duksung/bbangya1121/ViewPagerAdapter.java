package kr.ac.duksung.bbangya1121;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import kr.ac.duksung.bbangya1121.ui.Map.MapFragment;
import kr.ac.duksung.bbangya1121.ui.Rank.RankFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm)
    {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position)
    {
        Fragment fragment = null;
        if (position == 0)
            fragment = new MainFragment();
        else if (position == 1)
            fragment = new RankFragment();
        else if (position == 2)
            fragment = new
                    MapFragment();

        return fragment;
    }

    @Override
    public int getCount()
    {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        String title = null;
        if (position == 0)
            title = "Algorithm";
        else if (position == 1)
            title = "Courses";
        else if (position == 2)
            title = "Login";
        return title;
    }
}
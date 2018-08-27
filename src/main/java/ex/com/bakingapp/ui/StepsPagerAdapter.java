package ex.com.bakingapp.ui;

import java.util.ArrayList;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ex.com.bakingapp.data.db.Step;

public class StepsPagerAdapter extends FragmentPagerAdapter {
    public ArrayList<Step> steps = new ArrayList<>();

    public StepsPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        return null;
    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return String.valueOf(steps.get(position).getStepId());
    }
    @Override
    public int getCount() {
        return steps.size();
    }
}

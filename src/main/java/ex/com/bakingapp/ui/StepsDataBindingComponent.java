package ex.com.bakingapp.ui;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingComponent;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

public class StepsDataBindingComponent implements DataBindingComponent {
    @BindingAdapter(value = {"android:pagerAdapter"}, requireAll = false)
    public static void setViewPager(ViewPager viewPager, FragmentPagerAdapter adapter) {
        viewPager.setAdapter(adapter);
    }
}

package ex.com.bakingapp.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ex.com.bakingapp.R;

// StepsPagerFragment hold pager with steps set
/* "View Pager with databinding “custom setter”
 * https://codedesignpattern.wordpress.com/2016/09/16/view-pager-with-databinding-custom-setter/
 */
public class StepsPagerFragment extends Fragment {
    private FragmentPagerBinding fragmentPagerBinding;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        fragmentPagerBinding = DataBindingUtil.bind(inflater.inflate(R.layout.steps_pager,
                container, false), new StepsDataBindingComponent());
        fragmentPagerBinding.setPagerAdapter(new StepsPagerAdapter(getChildFragmentManager()));
        return fragmentPagerBinding.getRoot();
    }



}

package ex.com.bakingapp.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ex.com.bakingapp.R;
import ex.com.bakingapp.data.db.AppDB;
import ex.com.bakingapp.data.db.Step;
import ex.com.bakingapp.data.db.StepEntity;
import ex.com.bakingapp.databinding.MakeFragmentBinding;

public class MakeFragment extends Fragment {
    // Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String KEY_ID = "primary-id";
    private static final String KEY_RECIPE_ID = "recipe-id";
    private static final String KEY_STEP_ID = "step-id";
    private static final String KEY_SHORT = "shot";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_VIDEO_URL = "video-url";
    private static final String KEY_THUMBNAIL_URL = "thumbnail-url";

    private MakeFragmentBinding binding;
    private AppDB appDB;

    public MakeFragment() {
        // Required empty public constructor
    }
    /* Creates make fragment for specific step */
    public static MakeFragment forStep(Step step) {
        MakeFragment fragment = new MakeFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_ID, step.getId());
        args.putInt(KEY_RECIPE_ID, step.getRecipeId());
        args.putInt(KEY_STEP_ID, step.getStepId());
        args.putString(KEY_SHORT, step.getShortDescription());
        args.putString(KEY_DESCRIPTION, step.getDescription());
        args.putString(KEY_VIDEO_URL, step.getVideoURL());
        args.putString(KEY_THUMBNAIL_URL, step.getThumbnailURL());
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate this data binding layout
        final MakeFragmentBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.make_fragment, container, false);
        StepEntity entity = new StepEntity();
        entity.setId(getArguments().getInt(KEY_ID));
        entity.setRecipeId(getArguments().getInt(KEY_RECIPE_ID));
        entity.setStepId(getArguments().getInt(KEY_STEP_ID));
        entity.setShortDescription(getArguments().getString(KEY_SHORT));
        entity.setDescription(getArguments().getString(KEY_DESCRIPTION));
        entity.setVideoURL(getArguments().getString(KEY_VIDEO_URL));
        entity.setThumbnailURL(getArguments().getString(KEY_THUMBNAIL_URL));
        binding.setStep(entity);
        binding.hasPendingBindings();
        return binding.getRoot();
    }
}

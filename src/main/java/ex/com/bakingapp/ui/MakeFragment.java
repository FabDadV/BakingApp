package ex.com.bakingapp.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ex.com.bakingapp.DataRepository;
import ex.com.bakingapp.R;
import ex.com.bakingapp.data.db.AppDB;
import ex.com.bakingapp.data.db.StepEntity;
import ex.com.bakingapp.databinding.MakeFragmentBinding;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MakeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MakeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MakeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String KEY_RECIPE_ID = "recipe-id";
    private static final String KEY_STEP_ID = "step-id";
    private AppDB appDB;
    // TODO: Define Listener
//    private OnFragmentInteractionListener mListener;

    public MakeFragment() {
        // Required empty public constructor
    }
    /*
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MakeFragment.
     */
    // (M.1) Rename and change types and number of parameters
/*    public static MakeFragment newInstance(int id, String param2) {
        MakeFragment fragment = new MakeFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_RECIPE_ID, recipeId);
        args.putInt(KEY_STEP_ID, stepId);
        fragment.setArguments(args);
        return fragment;
    }
  */
    /** Creates make fragment for specific step ID */
    public static MakeFragment forStep(int recipeId, int stepId) {
        MakeFragment fragment = new MakeFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_RECIPE_ID, recipeId);
        args.putInt(KEY_STEP_ID, stepId);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate this data binding layout
        final MakeFragmentBinding binding;
        binding = DataBindingUtil.inflate(inflater, R.layout.make_fragment, container, false);
        // inflater.inflate(R.layout.make_fragment, container, false);
//        if (getArguments() != null) {
            int recipeId = getArguments().getInt(KEY_RECIPE_ID);
            int stepId = getArguments().getInt(KEY_STEP_ID);
//        }
//        LiveData<StepEntity> step =     appDB.stepsDao().getByRecipeId(recipeId, stepId);
        DataRepository repository = DataRepository.getInstance(appDB);
        StepEntity entity = repository.loadEntity(recipeId, stepId);
        entity.setShortDescription("Test shD");
        entity.setDescription("Test Des");
        entity.setVideoURL("Test VideoURL");
        entity.setThumbnailURL("Test thURLD");
        binding.setEntity(entity);
        binding.hasPendingBindings();
        return binding.getRoot();
    }
/*
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
*/
/*
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mId = getArguments().getInt(KEY_STEP_ID);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
*/
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
/*
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
*/
}

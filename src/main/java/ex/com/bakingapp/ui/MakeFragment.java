package ex.com.bakingapp.ui;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.squareup.picasso.Picasso;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import ex.com.bakingapp.R;
import ex.com.bakingapp.data.db.AppDB;
import ex.com.bakingapp.data.db.Step;

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
    public static final String SAVED_PLAYER_STATE = "playerState";
    public static final String SAVED_PLAYER_POSITION = "playerPosition";

    private AppDB appDB;

    private MediaSessionCompat mediaSession;
    private PlaybackStateCompat.Builder stateBuilder;
    SimpleExoPlayer exoPlayer;
    PlayerView playerView;
    String videoUrl;
    long playerPosition;
    boolean playState;

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
        Log.d("TAG", "Make onCreateView ");
        View view = inflater.inflate(R.layout.make_fragment, container, false);

        String thumbUrl = getArguments().getString(KEY_THUMBNAIL_URL);
        videoUrl = getArguments().getString(KEY_VIDEO_URL);

        playerView = view.findViewById(R.id.step_player);
        ImageView ivThumb = view.findViewById(R.id.step_thumb);
        TextView tvShort = view.findViewById(R.id.step_short);
        TextView tvDes = view.findViewById(R.id.step_des);
        TextView tvVideoUrl = view.findViewById(R.id.step_url);
        TextView tvThumbUrl = view.findViewById(R.id.step_path);
        //        Intent intent = getActivity().getIntent();
        tvShort.setText(getArguments().getString(KEY_SHORT));
        tvDes.setText(getArguments().getString(KEY_DESCRIPTION));
        tvVideoUrl.setText(videoUrl);
        tvThumbUrl.setText(thumbUrl);

        int orientation = getResources().getConfiguration().orientation;

        if (!TextUtils.isEmpty(videoUrl)) {
            playerView.setVisibility(View.VISIBLE);
            ivThumb.setVisibility(View.GONE);
            initializePlayer(Uri.parse(videoUrl));
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
                playerView.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
                playerView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
                hideSystemUI();
                tvDes.setVisibility(View.GONE);
            };
        } else {
            playerView.setVisibility(View.GONE);
            ivThumb.setVisibility(View.VISIBLE);
            if(!TextUtils.isEmpty(thumbUrl)) {
                Picasso.get()
                        .load(thumbUrl)
                        .placeholder(R.drawable.bon_appetit)
                        .error(R.drawable.bon_appetit)
                        .into(ivThumb);
            } else {

            }
        }
        return view;
    }

    private void initializePlayer(Uri mediaUri){
        if(exoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            exoPlayer =  ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            playerView.setPlayer(exoPlayer);
            //Data Source
            String userAgent = Util.getUserAgent(getActivity(), "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(getActivity(),
                    userAgent), new DefaultExtractorsFactory(),null, null);
            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(true);
        }
    }

    private void hideSystemUI() {
        View decorView = getActivity().getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    private void releasePlayer(){
        if(exoPlayer!=null){
            exoPlayer.stop();
            exoPlayer.release();
        }
        exoPlayer = null;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }
    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }
    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }
}
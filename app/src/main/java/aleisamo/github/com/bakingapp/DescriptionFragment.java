package aleisamo.github.com.bakingapp;

import android.app.NotificationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DescriptionFragment extends Fragment {
    private SimpleExoPlayer mExoPlayerVideo;
    private static MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;
    private NotificationManager mNotificationManager;
    private boolean videoRelease;
    @BindView(R.id.playerView)
    SimpleExoPlayerView mExoPlayerView;
    @BindView(R.id.text_description)
    TextView mDescription;
    @BindView(R.id.imageCupcake)
    ImageView mImage;
    @BindView(R.id.viewlineTop)
    View mLineTop;
    @BindView(R.id.viewlineBottom)
    View mLineBottom;


    public DescriptionFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_description, container, false);
        ButterKnife.bind(this, rootView);
        if (getArguments() != null) {
            String description = getArguments().getString("ARGUMENTS_DESCRIPTION");
            String videoUri = getArguments().getString("ARGUMENTS_VIDEO");

            // check landscape mode
            boolean isLand = getResources().getBoolean(R.bool.isLandScape);
            if(isLand){
                setVideo(videoUri);
                mDescription.setVisibility(View.GONE);
                mLineBottom.setVisibility(View.GONE);
                mLineTop.setVisibility(View.GONE);
                mExoPlayerView.setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            }
            else{
                mDescription.setText(description);
                setVideo(videoUri);
            }
        }
        return rootView;
    }

    private void setVideo(String videoUri){
        if (videoUri != null) {
            videoRelease = true;
            playVideo(Uri.parse(videoUri));
        }
        else {
            mExoPlayerView.setVisibility(View.GONE);
            mImage.setImageResource(R.drawable.confectionery);
            mImage.setVisibility(View.VISIBLE);
        }

    }

    private void playVideo(Uri videoUri) {
        if (mExoPlayerVideo == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayerVideo = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mExoPlayerView.setPlayer(mExoPlayerVideo);

            // prepare the VideoResource
            String mediaPlayer = Util.getUserAgent(getContext(), "StepbyStep");
            MediaSource mediaSource = new ExtractorMediaSource(videoUri,
                    new DefaultDataSourceFactory(getContext(), mediaPlayer),
                    new DefaultExtractorsFactory(), null, null);
            mExoPlayerVideo.prepare(mediaSource);
            mExoPlayerVideo.setPlayWhenReady(true);
        }
    }

    private void releasePlayer() {
        if (videoRelease) {
            mExoPlayerVideo.release();
            mExoPlayerVideo = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

}

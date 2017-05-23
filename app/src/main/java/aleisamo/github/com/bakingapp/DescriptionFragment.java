package aleisamo.github.com.bakingapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.NotificationCompat;
import android.support.v4.media.session.MediaButtonReceiver;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.NOTIFICATION_SERVICE;

public class DescriptionFragment extends Fragment implements ExoPlayer.EventListener{
    private SimpleExoPlayer mExoPlayerVideo;
    private static MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;
    private NotificationManager mNotificationManager;
    @BindView(R.id.playerView)
    SimpleExoPlayerView mExoPlayerView;
    @BindView(R.id.text_description)
    TextView mDescription;
    @BindView(R.id.back)
    Button mBack;
    @BindView(R.id.next)
    Button mNext;

    public DescriptionFragment(){}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_description,container,false);
        ButterKnife.bind(this,rootView);
        Intent description = getActivity().getIntent();
        mDescription.setText(description.getStringExtra("description"));

        initializeMediaSession();

        playVideo(Uri.parse(description.getStringExtra("VideoURL")));

        return rootView;






    }
    private void initializeMediaSession (){
        mMediaSession = new MediaSessionCompat(getContext(),DescriptionFragment.class.getSimpleName());

        mMediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS|
        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        // don't start the player when the app is not visible
        mMediaSession.setMediaButtonReceiver(null);
        // set an initial playbackstate ACTION PLAY
        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY|
                        PlaybackStateCompat.ACTION_PAUSE|
                        PlaybackStateCompat.ACTION_PLAY_PAUSE
                );
        mMediaSession.setPlaybackState(mStateBuilder.build());

        //mMediaSession.setCallback(new MysessionCallback());
    }

    private void playVideo (Uri videoUri) {
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

    private void showNotification (PlaybackStateCompat stateCompat){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext());
        int icon;
        String state;
        if(stateCompat.getState() == PlaybackStateCompat.STATE_PLAYING){
            icon = R.drawable.exo_controls_pause;
            state ="Pause";
        }else {
            icon = R.drawable.exo_controls_play;
            state ="Play";
        }

        NotificationCompat.Action playPauseAction = new NotificationCompat.Action(
                icon, state,
                MediaButtonReceiver.buildMediaButtonPendingIntent(getContext(),
                        PlaybackStateCompat.ACTION_PLAY_PAUSE));

        NotificationCompat.Action restartAction = new android.support.v4.app.NotificationCompat
                .Action(R.drawable.exo_controls_previous, getString(R.string.restart),
                MediaButtonReceiver.buildMediaButtonPendingIntent
                        (getContext(), PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS));
        PendingIntent contentPendingIntent = PendingIntent.getActivity
                (getContext(), 0, new Intent(getContext(), DescriptionFragment.class), 0);
        // TODO change string by the current step
        builder.setContentTitle("Recipe")
                .setContentText("Press play to continue with the recipes")
                .setContentIntent(contentPendingIntent)
                .setSmallIcon(R.drawable.ic_baking)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .addAction(restartAction)
                .addAction(playPauseAction)
                .setStyle(new NotificationCompat.MediaStyle()
                        .setMediaSession(mMediaSession.getSessionToken())
                        .setShowActionsInCompactView(0,1));
        mNotificationManager =(NotificationManager) getContext().getSystemService(NOTIFICATION_SERVICE);
        mNotificationManager.notify(0,builder.build());
}

    private void releasePlayer(){
        mNotificationManager.cancelAll();
        mExoPlayerVideo.stop();
        mExoPlayerVideo.release();
        mExoPlayerVideo=null;
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if((playbackState == ExoPlayer.STATE_READY) && playWhenReady){
            mStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                    mExoPlayerVideo.getCurrentPosition(), 1f);
        } else if((playbackState == ExoPlayer.STATE_READY)){
            mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                    mExoPlayerVideo.getCurrentPosition(), 1f);
        }
        mMediaSession.setPlaybackState(mStateBuilder.build());
        showNotification(mStateBuilder.build());
    }



    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }
}

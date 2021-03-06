package mx.saudade.discovermusicapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mx.saudade.discovermusicapp.R;
import mx.saudade.discovermusicapp.responses.Root;
import mx.saudade.discovermusicapp.responses.Track;
import mx.saudade.discovermusicapp.utils.PlaylistUtils;

/**
 * Created by angie on 6/28/16.
 */
public class PlaylistFragment extends Fragment {

    private static final String TAG = PlaylistFragment.class.getSimpleName();

    public static final String TRACKS_KEY = TAG + "_tracks_key";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.playlist_fragment, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        updateContent(getTracks());
    }

    private void updateContent(List<Track> tracks) {
        if (tracks == null || tracks.size() == 0) {
            getTextView().setText(R.string.no_results);
            getTextView().setVisibility(TextView.VISIBLE);
        } else {
            PlaylistUtils.createRecyclerView(getActivity(), getView().findViewById(
                    R.id.playlist_container), tracks);
            getTextView().setVisibility(TextView.GONE);
        }
    }

    private TextView getTextView() {
        return (TextView) getView().findViewById(R.id.playlist_no_results);
    }

    private List<Track> getTracks() {
        if (this.getArguments() == null) {
            return new ArrayList<Track>();
        }
        Root root = this.getArguments().getParcelable(TRACKS_KEY);
        return root.getTracks();
    }

}

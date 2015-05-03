package fr.vcaen.lyontour.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import fr.vcaen.lyontour.R;

import fr.vcaen.lyontour.Activity.dummy.DummyContent;
import fr.vcaen.lyontour.models.PointInteret;
import fr.vcaen.lyontour.models.containers.VisiteContainer;
import fr.vcaen.lyontour.network.RestHelper;

/**
 * A fragment representing a single Point d'interet detail screen.
 * This fragment is either contained in a {@link fr.vcaen.lyontour.Activity.PointdinteretListActivity}
 * in two-pane mode (on tablets) or a {@link fr.vcaen.lyontour.Activity.PointdinteretDetailActivity}
 * on handsets.
 */
public class PointdinteretDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private PointInteret mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PointdinteretDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = VisiteContainer.PI_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pointdinteret_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.attraction_detail_title)).setText(mItem.getTitle());
            ((TextView) rootView.findViewById(R.id.attraction_detail_description)).setText(mItem.getDescription());
            NetworkImageView image = (NetworkImageView) rootView.findViewById(R.id.attraction_detail_image);
            image.setImageUrl(
                mItem.getImageURL(),
                RestHelper.getInstance(getActivity()).getImageLoader());
        }

        return rootView;
    }
}

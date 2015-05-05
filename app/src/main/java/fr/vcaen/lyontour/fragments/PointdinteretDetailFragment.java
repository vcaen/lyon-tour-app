package fr.vcaen.lyontour.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.rey.material.widget.EditText;

import fr.vcaen.lyontour.R;

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
        final TextView adresse = (TextView) rootView.findViewById(R.id.adresseTexte);
        final ImageView house = (ImageView) rootView.findViewById(R.id.adresseMarker);
        final TextView phoneNumber = (TextView) rootView.findViewById(R.id.phoneTexte);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Uri uri = Uri.parse("https://www.google.fr/maps/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        };

        adresse.setOnClickListener(clickListener);
        house.setOnClickListener(clickListener);

        phoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numero = phoneNumber.getText().toString();
                Intent keypad = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+numero));
                startActivity(keypad);
            }
        });

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

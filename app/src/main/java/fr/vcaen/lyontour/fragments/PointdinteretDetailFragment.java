package fr.vcaen.lyontour.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.overlay.GpsLocationProvider;
import com.mapbox.mapboxsdk.overlay.Icon;
import com.mapbox.mapboxsdk.overlay.Marker;
import com.mapbox.mapboxsdk.overlay.UserLocationOverlay;
import com.mapbox.mapboxsdk.tileprovider.tilesource.MapboxTileLayer;
import com.mapbox.mapboxsdk.views.MapView;

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
        final ImageView phoneIcon = (ImageView) rootView.findViewById(R.id.phoneIcon);

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

        View.OnClickListener call = new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                String numero = phoneNumber.getText().toString();
                Intent keypad = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+numero));
                startActivity(keypad);
            }
        };

        phoneNumber.setOnClickListener(call);
        phoneIcon.setOnClickListener(call);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.attraction_detail_title)).setText(mItem.getTitle());
            ((TextView) rootView.findViewById(R.id.attraction_detail_description)).setText(mItem.getDescription());
            NetworkImageView image = (NetworkImageView) rootView.findViewById(R.id.attraction_detail_image);
            image.setImageUrl(
                mItem.getImageURL(),
                RestHelper.getInstance(getActivity()).getImageLoader());
        }

        LatLng latLng = new LatLng(mItem.getLatitude(), mItem.getLongitude());
        MapView mv = (MapView) rootView.findViewById(R.id.mapview);
        mv.setAccessToken("sk.eyJ1Ijoic29uaWFoNDMxMiIsImEiOiJDbGVzMmZJIn0.2Nj61Rz4inJyKYD6MXUsnQ");
        mv.setTileSource(new MapboxTileLayer("soniah4312.7c398ef3"));
        mv.setMinZoomLevel(mv.getTileProvider().getMinimumZoomLevel());
        mv.setMaxZoomLevel(mv.getTileProvider().getMaximumZoomLevel());
        mv.setCenter(latLng);
        mv.setZoom(16);

        Marker marker = new Marker("titre", "decription", latLng);
        mv.addMarker(marker);
        return rootView;
    }
}

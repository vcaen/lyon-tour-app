package fr.vcaen.lyontour.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import fr.vcaen.lyontour.R;
import fr.vcaen.lyontour.adapter.VisitListAdapter;
import fr.vcaen.lyontour.models.containers.VisiteContainer;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * A list fragment representing a list of Points d'interets. This fragment
 * also supports tablet devices by allowing list items to be given an
 * 'activated' state upon selection. This helps indicate which item is
 * currently being viewed in a {@link PointdinteretDetailFragment}.
 * <p/>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class PointdinteretListFragment extends Fragment {

    private static final String TAG = PointdinteretListFragment.class.getName();

    public static final String ARG_DATE_DEBUT = "datedebut";
    public static final String ARG_DATE_FIN = "datefin";
    public static final String ARG_FILTER = "filter";

    StickyListHeadersListView mListView;
    StickyListHeadersAdapter adapter;
    ViewGroup wrapper;

    /**
     * The serialization (saved instance state) Bundle key representing the
     * activated item position. Only used on tablets.
     */
    private static final String STATE_ACTIVATED_POSITION = "activated_position";

    /**
     * The fragment's current callback object, which is notified of list item
     * clicks.
     */
    private Callbacks mCallbacks = sDummyCallbacks;

    /**
     * The current activated item position. Only used on tablets.
     */
    private int mActivatedPosition = ListView.INVALID_POSITION;
    private ViewGroup header;

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callbacks {
        /**
         * Callback for when an item has been selected.
         */
        public void onItemSelected(String id);
    }

    /**
     * A dummy implementation of the {@link Callbacks} interface that does
     * nothing. Used only when this fragment is not attached to an activity.
     */
    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(String id) {
        }
    };

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PointdinteretListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_pointinteret_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Restore the previously serialized activated item position.
        if (savedInstanceState != null
                && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            //mListView.setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }
        mListView = (StickyListHeadersListView) getView().findViewById(R.id.attraction_list_view);
        //mListView.setParallaxView(getActivity().getLayoutInflater().inflate(R.layout.attraction_list_header, mListView, false));
        adapter = new VisitListAdapter(getActivity(), 0, VisiteContainer.PI_LIST);
        mListView.setAdapter(adapter);
        getView().getRootView().setBackgroundResource(R.color.activitybackground);
        mListView.setOnItemClickListener(new onListItemClick());
        mListView.getItemAtPosition(1);

//        wrapper = (ViewGroup) getView().findViewById(R.id.attraction_list_wrapper);
//        header = (ViewGroup) getView().findViewById(R.id.attration_list_header);


//        wrapper.setOnTouchListener(new View.OnTouchListener() {
//            float orig_y;
//            float new_y;
//            float header_y =  header.getY();
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        orig_y = event.getY();
//                        return true;
//                    case MotionEvent.ACTION_MOVE:
//                        new_y = event.getY();
//                        if(new_y < orig_y) {
//                            header.setY(header_y + new_y - orig_y);
//                        }
//                }
//                return false;
//            }
//        });


        TextView debutSejour = (TextView) getView().findViewById(R.id.debut_sejour);
        TextView finSejour = (TextView) getView().findViewById(R.id.fin_sejour);
        SimpleDateFormat in = new SimpleDateFormat("ddMMyyyy");
        SimpleDateFormat out = new SimpleDateFormat("cccc dd MMMM");
        String debut = getActivity().getSharedPreferences("lyon_tour", Context.MODE_PRIVATE).getString("date_debut", "01011970");
        String fin = getActivity().getSharedPreferences("lyon_tour", Context.MODE_PRIVATE).getString("date_fin", "01011970");
        try {
            debutSejour.setText(out.format(in.parse(debut)));
            finSejour.setText(out.format(in.parse(fin)));
        }catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Activities containing this fragment must implement its callbacks.
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        // Reset the active callbacks interface to the dummy implementation.
        mCallbacks = sDummyCallbacks;
    }


    public class onListItemClick implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            mCallbacks.onItemSelected(String.valueOf(((VisitListAdapter)adapter).getItemStringId(position)));
        }
    }

    
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            // Serialize and persist the activated item position.
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    /**
     * Turns on activate-on-click mode. When this mode is on, list items will be
     * given the 'activated' state when touched.
     */
    /*
    public void setActivateOnItemClick(boolean activateOnItemClick) {
        // When setting CHOICE_MODE_SINGLE, ListView will automatically
        // give items the 'activated' state when touched.
        getListView().setChoiceMode(activateOnItemClick
                ? ListView.CHOICE_MODE_SINGLE
                : ListView.CHOICE_MODE_NONE);
    }

    private void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }

        mActivatedPosition = position;
    } */
}

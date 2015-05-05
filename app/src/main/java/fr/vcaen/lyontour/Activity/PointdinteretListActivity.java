package fr.vcaen.lyontour.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;

import fr.vcaen.lyontour.R;
import fr.vcaen.lyontour.fragments.PointdinteretDetailFragment;
import fr.vcaen.lyontour.fragments.PointdinteretListFragment;

/**
 * An activity representing a list of Points d'interets. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link PointdinteretDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p/>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link fr.vcaen.lyontour.fragments.PointdinteretListFragment} and the item details
 * (if present) is a {@link fr.vcaen.lyontour.fragments.PointdinteretDetailFragment}.
 * <p/>
 * This activity also implements the required
 * {@link fr.vcaen.lyontour.fragments.PointdinteretListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class PointdinteretListActivity extends ActionBarActivity
        implements PointdinteretListFragment.Callbacks {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    String dateDebut;
    String dateFin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pointdinteret_list);
        dateDebut = getIntent().getExtras().getString(PointdinteretListFragment.ARG_DATE_DEBUT);
        dateFin = getIntent().getExtras().getString(PointdinteretListFragment.ARG_DATE_FIN);
    }

    /**
     * Callback method from {@link PointdinteretListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(String id) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(PointdinteretDetailFragment.ARG_ITEM_ID, id);
            PointdinteretDetailFragment fragment = new PointdinteretDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.pointdinteret_detail_container, fragment)
                    .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, PointdinteretDetailActivity.class);
            detailIntent.putExtra(PointdinteretDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }
}

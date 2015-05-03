package fr.vcaen.lyontour.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;

import fr.vcaen.lyontour.R;
import fr.vcaen.lyontour.models.PointInteret;
import fr.vcaen.lyontour.widgets.PointInteretView;

/**
 * Created by vcaen on 28/04/2015.
 */
public class VisitListAdapter extends ArrayAdapter<PointInteret> {

    ImageView connector;



    public VisitListAdapter(Context context, int resource) {
        super(context, resource);
    }


    public VisitListAdapter(Context context, int resource, PointInteret[] objects) {
        super(context, resource, objects);
    }


    public VisitListAdapter(Context context, int resource, List<PointInteret> objects) {
        super(context, resource, objects);
    }

    public String getItemStringId(int position) {
        return getItem(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


            connector = new ImageView(getContext());
            connector.setImageDrawable(getContext().getResources().getDrawable(R.drawable.list_connector));


            PointInteretView piv =  new PointInteretView(getContext());
            piv.setPointIntert(getItem(position));
            LinearLayout wrapper = new LinearLayout(getContext());
            wrapper.setLayoutParams(new AbsListView.LayoutParams(parent.getWidth(), AbsListView.LayoutParams.WRAP_CONTENT));
            wrapper.setOrientation(LinearLayout.VERTICAL);
            wrapper.setGravity(Gravity.CENTER);
            wrapper.setPadding(10,0,10,0);
            wrapper.addView(piv);
            if( position < getCount() - 1) {
                wrapper.addView(connector);
            }

        return wrapper;
    }
}

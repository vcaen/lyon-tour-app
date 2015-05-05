package fr.vcaen.lyontour.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import fr.vcaen.lyontour.R;
import fr.vcaen.lyontour.models.PointInteret;
import fr.vcaen.lyontour.widgets.PointInteretView;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by vcaen on 28/04/2015.
 */
public class VisitListAdapter extends ArrayAdapter<PointInteret> implements StickyListHeadersAdapter{

    ImageView connector;
    private LayoutInflater inflater;
    private SimpleDateFormat humanReadable = new SimpleDateFormat("cccc MMMM yyyy");

    private static HashMap<String, Bitmap> meteoMap = new HashMap<>();


    public VisitListAdapter(Context context, int resource) {
        super(context, resource);
        init();
    }


    public VisitListAdapter(Context context, int resource, PointInteret[] objects) {
        super(context, resource, objects);
        init();
    }


    public VisitListAdapter(Context context, int resource, List<PointInteret> objects) {
        super(context, resource, objects);
        init();
    }

    private void init(){
        inflater = LayoutInflater.from(getContext());
        if(meteoMap.size() == 0) {
            meteoMap.put(
                    PointInteret.Meteo.SUNNY.text,
                    BitmapFactory.decodeResource(getContext().getResources(),R.drawable.clear));
            meteoMap.put(
                    PointInteret.Meteo.CLOUDLY.text,
                    BitmapFactory.decodeResource(getContext().getResources(),R.drawable.cloudy));
            meteoMap.put(
                    PointInteret.Meteo.MOSTY_CLOUDLY.text,
                    BitmapFactory.decodeResource(getContext().getResources(),R.drawable.mostly_cloudy));
            meteoMap.put(
                    PointInteret.Meteo.RAINY.text,
                    BitmapFactory.decodeResource(getContext().getResources(),R.drawable.showers));
        }
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

    @Override
    public View getHeaderView(int i, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = inflater.inflate(R.layout.widget_day_header, parent, false);
            holder.date = (TextView) convertView.findViewById(R.id.header_date);
            holder.imageview = (ImageView) convertView.findViewById(R.id.meteo_icon_container);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }
        holder.date.setText(humanReadable.format(getItem(i).getDate()));
        holder.imageview.setImageBitmap(meteoMap.get(getItem(i).getMeteo()));
        return convertView;
    }

    @Override
    public long getHeaderId(int i) {
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
        return Long.parseLong(sdf.format(getItem(i).getDate()));
    }

    private class HeaderViewHolder {
        TextView date;
        ImageView imageview;
    }
}

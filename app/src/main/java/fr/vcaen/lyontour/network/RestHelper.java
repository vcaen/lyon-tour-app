package fr.vcaen.lyontour.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import fr.vcaen.lyontour.R;
import fr.vcaen.lyontour.models.PointInteret;

/**
 * Created by vcaen on 27/04/15.
 */
public class RestHelper {

    
    public static final String TAG = RestHelper.class.getName();
    private static RestHelper instance;
    private Context mContext;
    int socketTimeout = 30000;//30 seconds - change to what you want
    RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

    public static final String ENDPOINT_ATTRACTION = "attraction";
    public static final String ENDPOINT_PHOTO = "photo/";

    private RequestQueue queue;
    private ImageLoader imageLoader;

    public static RestHelper getInstance(Context context) {
        if(instance == null) {
            instance = new RestHelper(context);
        }
        return instance;
    }

    private RestHelper(Context context) {
        mContext = context;
        queue = Volley.newRequestQueue(context);
        imageLoader = new ImageLoader(this.queue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(10);
            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }
            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }
        });
    }



    public void getAttractions(String dateDebut, String dateFin, List<String> filtre, final APICallBack<List<PointInteret>> callback) {
        String url =  ENDPOINT_ATTRACTION + "?datedebut="+dateDebut+"&datefin="+dateFin;
        if(filtre.size() == 0) return;
        url += "&filtre=";
        for(String f : filtre) {
            url += f + ",";
        }
        url = url.substring(0, url.length() - 1);

        getJsonObject(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
                        ArrayList<PointInteret> pis = new ArrayList<PointInteret>();

                        try {
                            JSONArray jours = jsonObject.getJSONArray("Jours");
                            for (int j = 0; j < jours.length(); j++) {
                                JSONObject jour = jours.getJSONObject(j);
                                Date date;
                                String weather;
                                try {
                                    date = sdf.parse(jour.getString("date"));
                                } catch (ParseException e) {
                                    date = Calendar.getInstance().getTime();
                                }
                                weather = jour.optString("weather_status", "sunny");

                                JSONArray etapes = jour.getJSONArray("etapes");
                                for (int k = 0; k < etapes.length(); k++) {
                                    JSONObject etape = etapes.getJSONObject(k);
                                    JSONObject pi = etape.getJSONObject("attraction");

                                    // Date
                                    Calendar cal = Calendar.getInstance();
                                    cal.setTime(date);
                                    cal.set(Calendar.HOUR_OF_DAY, etape.optInt("heure", 12));

                                    pis.add(new PointInteret(
                                            pi.getString("foursquare_id"),
                                            pi.getString("name"),
                                            NetworkConfiguration.SERVER_FULL_ADDRESS + ENDPOINT_PHOTO + pi.getString("photo"),
                                            pi.getString("description"  ),
                                            cal.getTime(),
                                            weather,
                                            pi.getDouble("latitude"),
                                            pi.getDouble("longitude")
                                    ));
                                }
                            }
                            callback.response(pis);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.error(error);
                    }
                },
                ENDPOINT_ATTRACTION
        );
    }


    /**
     *
     * @param endpoint Endpoint of the API (i.e : the part after the server address)
     */
    public void getJsonObject(String endpoint, Response.Listener<JSONObject> responseList, Response.ErrorListener errorList, Object tag) {
        if(errorList == null) {
            errorList = getDefaultErrorListener();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, NetworkConfiguration.SERVER_FULL_ADDRESS + endpoint,
                null,
                responseList,
                errorList);
        request.setTag((tag != null) ? tag : TAG);
        request.setRetryPolicy(policy);
        queue.add(request);
    }
    public void getJsonObject(String endpoint, Response.Listener<JSONObject> responseList) {
        getJsonObject(endpoint, responseList, null, null);
    }

    private Response.ErrorListener getDefaultErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.w(TAG, volleyError.getMessage());
                Toast.makeText(mContext, mContext.getString(R.string.network_error), Toast.LENGTH_SHORT).show();
            }
        };
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    public interface APICallBack<T> {
        public void response(T object);
        public void error(VolleyError error);
    }
}

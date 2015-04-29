package fr.vcaen.lyontour.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import fr.vcaen.lyontour.R;

/**
 * Created by vcaen on 27/04/15.
 */
public class RestHelper {
    
    public static final String TAG = RestHelper.class.getName();
    private static RestHelper instance;
    private Context mContext;

    

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
}
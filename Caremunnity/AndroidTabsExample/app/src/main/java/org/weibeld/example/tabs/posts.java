package org.weibeld.example.tabs;

/**
 * Created by sananasir on 2017-11-20.
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

public class posts {
    private int id;
    private String name;
    private String content;
    private Bitmap photo;

    public posts(int id, String first, String last, int post_id, String content, String title, String photo, int likes, Context context) {
        setName(first, last);
        setContent(content);
        //setPhoto(photo, context);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setId(int x) {
        id = x;
    }

    public void setName(String x, String y) {
        name = x + " " + y;
    }

    public void setContent(String x) {
        content = x;
    }

    public void setPhoto(String x, Context context) {
        ImageRequest imageRequest = new ImageRequest(x,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        photo = response;
                    }
                }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        Mysingleton.getInstance(context).addToRequestque(imageRequest);
    }
}

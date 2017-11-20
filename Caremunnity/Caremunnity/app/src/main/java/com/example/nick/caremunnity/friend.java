package com.example.nick.caremunnity;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

import org.json.JSONArray;

public class friend {
    private int id;
    private String name;
    private String email;
    private Bitmap photo;

    public friend() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
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

    public void setEmail(String x) {
        email = x;
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
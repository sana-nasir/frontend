package com.example.nick.caremunnity;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


public class Mysingleton {
    private static Mysingleton mInstance;
    private RequestQueue requestQueue;
    private static Context mCtx;

    private Mysingleton(Context context) {
        mCtx = context;
        requestQueue = getRequestQue();
    }

    public RequestQueue getRequestQue() {
        if(requestQueue == null) {
            requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return requestQueue;
    }

    public static synchronized Mysingleton getInstance(Context context) {
        if(mInstance == null) {
            mInstance = new Mysingleton(context);
        }
        return mInstance;
    }

    public<T> void addToRequestque(Request<T> request) {
        requestQueue.add(request);
    }
}


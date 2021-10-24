package com.example.methodafrontend.Repository;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleySingleton {
    private final RequestQueue mRequestQueue;
    private static VolleySingleton mInstance;

    private VolleySingleton(Context context) {
        mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static synchronized VolleySingleton getInstance(Context context){
        if(mInstance == null) // if instance is not created before
        {
            mInstance = new VolleySingleton(context);
        }

        return mInstance;
    }
    public RequestQueue getRequestQueue(){
        return mRequestQueue;
    }
}
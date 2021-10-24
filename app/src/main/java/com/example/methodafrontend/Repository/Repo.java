package com.example.methodafrontend.Repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.methodafrontend.Model.Status;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeSet;

public class Repo {
    static Repo instance;
    private ArrayList<Status> mStatusList;
    private final String connectionIP = "192.168.1.25";
    private TreeSet<Status> treeSet;
    static Context mContext;
    static LoadDataListener loadDataListener;

    public static Repo getInstance(Context context) {
        mContext = context;
        if (instance == null) {
            instance = new Repo();
        }
        loadDataListener = (LoadDataListener) mContext;
        return instance;
    }

    public MutableLiveData<ArrayList<Status>> getStatuses() {
        loadStatusesList();
        MutableLiveData<ArrayList<Status>> dataFromServer = new MutableLiveData<>();
        dataFromServer.setValue(mStatusList);
        return dataFromServer;
    }

    private void loadStatusesList() {
        RequestQueue requestQueue = VolleySingleton.getInstance(mContext).getRequestQueue();
        mStatusList = new ArrayList<>();
        String url = "https://192.168.1.25:45458/api/Status/GetAllStatuses";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, (Response.Listener<JSONArray>) response -> {
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            Status[] statues = gson.fromJson(response.toString(), Status[].class);
            Collections.addAll(mStatusList, statues);
            treeSet.addAll(mStatusList);
            loadDataListener.onStatusLoaded();
        }, error -> {
            Log.d("TAG", "loadStatusesList: ");
        });
        requestQueue.add(jsonArrayRequest);
    }
}

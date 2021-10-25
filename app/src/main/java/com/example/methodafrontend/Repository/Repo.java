package com.example.methodafrontend.Repository;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.methodafrontend.Model.Status;
import com.example.methodafrontend.Model.Transition;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Repo {
    private static final String TAG = "Repo";
    private static final String CONNECTION_STRING = "192.168.1.34:45455";
    static Repo instance;
    static Context mContext;
    static LoadDataListener loadDataListener;
    private ArrayList<Status> mStatusList;
    private final RequestQueue requestQueue = VolleySingleton.getInstance(mContext).getRequestQueue();
    private ArrayList<Transition> mTransitionsList;

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

    public MutableLiveData<ArrayList<Transition>> getTransitions() {
        loadTransitionList();
        MutableLiveData<ArrayList<Transition>> dataFromServer = new MutableLiveData<>();
        dataFromServer.setValue(mTransitionsList);
        return dataFromServer;
    }

    private void loadStatusesList() {
        mStatusList = new ArrayList<>();
        String url = String.format("http://%s/api/Status/GetAllStatuses", CONNECTION_STRING);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, null, response -> {
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            Status[] statuses = gson.fromJson(response.toString(), Status[].class);
            for (Status status : statuses) {
                mStatusList.add(new Status(status.getStatusName()));
            }
            loadDataListener.onStatusChanged();
        }, error -> Log.d("TAG", "loadStatusesList: "));
        requestQueue.add(jsonArrayRequest);
    }

    private void loadTransitionList() {
        mTransitionsList = new ArrayList<>();
        String url = String.format("http://%s/api/Transition/GetAllTransitions", CONNECTION_STRING);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, null, response -> {
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            Transition[] transitions = gson.fromJson(response.toString(), Transition[].class);
            Collections.addAll(mTransitionsList, transitions);
            for (Transition transition : transitions) {
                convertTransitionIntoStatuses(transition);
            }
            loadDataListener.onTransitionChanged();
        }, error -> Log.d("TAG", "loadTransitionList: "));
        requestQueue.add(jsonArrayRequest);
    }


    private void clearRelationShips(Transition transition){
        for (Status status: mStatusList
             ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                if(status.getStatusName().equals(transition.getFrom())) {
                    Status status2 = status.getNeighbours().stream().filter(status1 -> status1.getStatusName().equals(transition.getTo())).findAny().orElse(null);
                    status.removeNeighbours(status2);
                }
            }
        }
    }
    private void convertTransitionIntoStatuses(Transition transition) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mStatusList.stream().filter(status ->
                    status.getStatusName().equals(transition.getFrom())
            ).findFirst().ifPresent(status -> status.AddNeighbours(
                    mStatusList.stream().filter(status1 -> status1.getStatusName().equals(transition.getTo()))
                            .findAny().orElse(null)));
        }
    }

    private void DeleteOrWriteNewTransitionToDB(Transition transition, String task) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", transition.getId());
            jsonObject.put("from", transition.getFrom());
            jsonObject.put("to", transition.getTo());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (task.equals("RemoveTransition")) {
            mTransitionsList.remove(transition);
            clearRelationShips(transition);
        } else {
            mTransitionsList.add(transition);
            convertTransitionIntoStatuses(transition);
        }
        String url = String.format("http://%s/api/Transition/%s", CONNECTION_STRING, task);
        CustomJsonObjectRequest jsonObjectRequest = new CustomJsonObjectRequest(Request.Method.POST, url, jsonObject, response ->
        {
            loadDataListener.onTransitionChanged();
        },
                error -> Log.d(TAG, error.toString()));
        requestQueue.add(jsonObjectRequest);
    }

    private void DeleteOrWriteNewStatusToDB(Status status, String task) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("statusName", status.getStatusName());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (task.equals("RemoveStatus")) {
            mStatusList.remove(status);
            for (Status status1 : mStatusList) {
                status1.removeNeighbours(status);
            }
            List<Transition> found = new ArrayList<>();
            for (Transition transition : mTransitionsList) {
                if (transition.getFrom().equals(status.getStatusName()) ||
                        transition.getTo().equals(status.getStatusName())) {
                    found.add(transition);
                }
            }
            mTransitionsList.removeAll(found);
            loadDataListener.onTransitionChanged();
        } else {
            mStatusList.add(status);
        }

        String url = String.format("http://%s/api/Status/%s", CONNECTION_STRING, task);
        CustomJsonObjectRequest jsonObjectRequest = new CustomJsonObjectRequest(Request.Method.POST, url, jsonObject, response ->
                loadDataListener.onStatusChanged(),
                error -> Log.d(TAG, error.toString()));
        requestQueue.add(jsonObjectRequest);
    }

    public void AddOrDeleteStatus(Status status, String task) {
        DeleteOrWriteNewStatusToDB(status, task);
    }

    public void AddOrDeleteTransition(Transition transition, String task) {
        DeleteOrWriteNewTransitionToDB(transition, task);
    }
}

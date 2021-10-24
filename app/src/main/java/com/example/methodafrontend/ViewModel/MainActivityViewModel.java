package com.example.methodafrontend.ViewModel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.methodafrontend.Model.Status;
import com.example.methodafrontend.Repository.Repo;

import java.util.ArrayList;

public class MainActivityViewModel extends ViewModel {
    MutableLiveData<ArrayList<Status>> statusesList;

    public void init(Context context) {
        if (statusesList != null) {
            return;
        }
        statusesList = Repo.getInstance(context).getStatuses();
    }

    public MutableLiveData<ArrayList<Status>> getStatusesList() {
        return statusesList;
    }
}

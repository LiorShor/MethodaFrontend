package com.example.methodafrontend.ViewModel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.methodafrontend.Model.Status;
import com.example.methodafrontend.Model.Transition;
import com.example.methodafrontend.Repository.Repo;

import java.util.ArrayList;

public class MainActivityViewModel extends ViewModel {
    MutableLiveData<ArrayList<Status>> statusesList;
    MutableLiveData<ArrayList<Transition>> transitionsList;
    Repo repo;

    public void init(Context context) {
        if (statusesList != null || transitionsList != null) {
            return;
        }
        repo = Repo.getInstance(context);
        statusesList = Repo.getInstance(context).getStatuses();
        transitionsList = Repo.getInstance(context).getTransitions();
    }

    public MutableLiveData<ArrayList<Status>> getStatusesList() {
        return statusesList;
    }

    public MutableLiveData<ArrayList<Transition>> getTransitionsList() {
        return transitionsList;
    }

    public void DeleteOrWriteNewTransitionToDB(Transition transition, String task) {
        repo.AddOrDeleteTransition(transition,task);
    }

    public void DeleteOrWriteNewStatusToDB(Status status, String task) {
        repo.AddOrDeleteStatus(status,task);
    }
}

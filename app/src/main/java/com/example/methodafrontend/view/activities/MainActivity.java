package com.example.methodafrontend.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;

import com.example.methodafrontend.Model.Status;
import com.example.methodafrontend.Repository.LoadDataListener;
import com.example.methodafrontend.ViewModel.MainActivityViewModel;
import com.example.methodafrontend.databinding.ActivityMainBinding;
import com.example.methodafrontend.view.adapters.StatusAdapter;
import com.example.methodafrontend.view.adapters.TransitionAdapter;
import com.example.methodafrontend.view.dialogs.TransitionDialog;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoadDataListener {
    private StatusAdapter mStatusAdapter;
    private TransitionAdapter mTransitionAdapter;
    private MainActivityViewModel mMainActivityViewModel;
    private ArrayList<Status> statusesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.example.methodafrontend.databinding.ActivityMainBinding mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mBinding.recyclerViewStatuses.setLayoutManager(new LinearLayoutManager(this));
        mBinding.recyclerViewTransitions.setLayoutManager(new LinearLayoutManager(this));
        mMainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        mMainActivityViewModel.init(MainActivity.this);
        mStatusAdapter = new StatusAdapter(mMainActivityViewModel.getStatusesList().getValue(), this);
        mTransitionAdapter = new TransitionAdapter(mMainActivityViewModel.getTransitionsList().getValue(), this);
        mBinding.recyclerViewStatuses.setAdapter(mStatusAdapter);
        mBinding.recyclerViewTransitions.setAdapter(mTransitionAdapter);
        mBinding.AddStatusfloatingActionButton.setOnClickListener(view ->
        {
            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Add Status")
                    .setView(input)
                    .setMessage("Enter a status name")
                    .setPositiveButton("Finish", (dialogInterface, i) -> mMainActivityViewModel.DeleteOrWriteNewStatusToDB(new Status(input.getText().toString()), "AddNewStatus"))
                    .setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.cancel())
                    .show();
        });

        mBinding.AddTransitionfloatingActionButton.setOnClickListener(view ->
        {
            showEditDialog();
        });
    }

    @Override
    public void onStatusChanged() {
        mMainActivityViewModel.getStatusesList().observe(this, statuses -> {
            if (statuses != null) {
                this.statusesList = statuses;
                mStatusAdapter.setStatusesList(statuses);
            }
        });
    }

    @Override
    public void onTransitionChanged()
    {
        mMainActivityViewModel.getTransitionsList().observe(this, transitions -> {

            if (transitions != null) {
                mTransitionAdapter.setTransitionArrayList(transitions);
            }
        });
    }

    private void showEditDialog() {
        FragmentManager fm = getSupportFragmentManager();
        TransitionDialog editNameDialogFragment = TransitionDialog.newInstance(statusesList, mMainActivityViewModel);
        editNameDialogFragment.show(fm,null);
    }
}
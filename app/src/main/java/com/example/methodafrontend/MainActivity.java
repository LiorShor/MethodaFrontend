package com.example.methodafrontend;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.example.methodafrontend.Repository.LoadDataListener;
import com.example.methodafrontend.ViewModel.MainActivityViewModel;
import com.example.methodafrontend.databinding.ActivityMainBinding;
import com.example.methodafrontend.view.adapters.StatusAdapter;

public class MainActivity extends AppCompatActivity implements LoadDataListener {
    private ActivityMainBinding mBinding;
    private StatusAdapter mStatusAdapter;
    private MainActivityViewModel mMainActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mBinding.recyclerViewStatuses.setLayoutManager(new LinearLayoutManager(this));
        mBinding.recyclerViewTransitions.setLayoutManager(new LinearLayoutManager(this));
        mMainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        mMainActivityViewModel.init(MainActivity.this);
        mStatusAdapter = new StatusAdapter(mMainActivityViewModel.getStatusesList().getValue());
    }

    @Override
    public void onStatusLoaded() {
        mMainActivityViewModel.getStatusesList().observe(this, statuses -> mStatusAdapter.notifyDataSetChanged());
    }
}
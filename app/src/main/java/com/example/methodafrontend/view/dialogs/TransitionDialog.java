package com.example.methodafrontend.view.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.methodafrontend.Model.Status;
import com.example.methodafrontend.Model.Transition;
import com.example.methodafrontend.Repository.LoadDataListener;
import com.example.methodafrontend.ViewModel.MainActivityViewModel;
import com.example.methodafrontend.databinding.TransitionDialogBinding;
import com.example.methodafrontend.view.activities.MainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TransitionDialog extends DialogFragment {
    private TransitionDialogBinding mBinding;
    private List<Status> statusesList;
    private final MainActivityViewModel mMainActivityViewModel;

    public TransitionDialog(List<Status> statuses, MainActivityViewModel mainActivityViewModel) {
        this.statusesList = statuses;
        mMainActivityViewModel = mainActivityViewModel;
    }

    public static TransitionDialog newInstance(List<Status> statuses, MainActivityViewModel mainActivityViewModel) {
        return new TransitionDialog(statuses, mainActivityViewModel);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mBinding = TransitionDialogBinding.inflate(inflater, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayAdapter<Status> spinnerArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, statusesList);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mBinding.spinnerFrom.setAdapter(spinnerArrayAdapter);
        mBinding.spinnerTo.setAdapter(spinnerArrayAdapter);
        mBinding.buttonSave.setOnClickListener(view1 ->
        {
            String statusFrom = mBinding.spinnerFrom.getSelectedItem().toString();
            String statusTo = mBinding.spinnerTo.getSelectedItem().toString();
            Transition transition = new Transition(UUID.randomUUID().toString(), statusFrom, statusTo);
            mMainActivityViewModel.DeleteOrWriteNewTransitionToDB(transition, "AddNewTransition");
            this.dismiss();
        });
        mBinding.buttonCancel.setOnClickListener(view1 -> this.dismiss());
    }
}

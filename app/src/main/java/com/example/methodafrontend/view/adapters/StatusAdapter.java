package com.example.methodafrontend.view.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.methodafrontend.Model.Status;
import com.example.methodafrontend.databinding.RowBinding;

import java.util.ArrayList;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.ViewHolder> {
    private final ArrayList<Status> statusesList;

    public StatusAdapter(ArrayList<Status> statusesList) {
        this.statusesList = statusesList;
    }

    @NonNull
    @Override
    public StatusAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(RowBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StatusAdapter.ViewHolder holder, int position) {
        holder.rowBinding.statusName.setText(statusesList.get(position).getStatueName());
    }

    @Override
    public int getItemCount() {
        return statusesList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final RowBinding rowBinding;
        public ViewHolder(@NonNull RowBinding rowBinding) {
            super(rowBinding.getRoot());
            this.rowBinding = rowBinding;
        }
    }
}

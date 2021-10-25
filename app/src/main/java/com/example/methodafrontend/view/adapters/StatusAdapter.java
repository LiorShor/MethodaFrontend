package com.example.methodafrontend.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.methodafrontend.Model.BFSForTrees;
import com.example.methodafrontend.Model.Status;
import com.example.methodafrontend.R;
import com.example.methodafrontend.ViewModel.MainActivityViewModel;
import com.example.methodafrontend.databinding.RowBinding;

import java.util.ArrayList;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.ViewHolder> {
    private ArrayList<Status> statusesList;
    private int lastSelectedPosition = -1;
    private final Context mContext;

    public StatusAdapter(ArrayList<Status> statusesList, Context context) {
        this.mContext = context;
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
        holder.rowBinding.statusName.setText(statusesList.get(position).getStatusName());
        holder.rowBinding.radioButton.setChecked(lastSelectedPosition == position);
        holder.rowBinding.kind.setText(statusesList.get(position).getRelation());
        MainActivityViewModel mainActivityViewModel = new ViewModelProvider((FragmentActivity) mContext).get(MainActivityViewModel.class);
        holder.rowBinding.removeButton.setOnClickListener(view ->
                mainActivityViewModel.DeleteOrWriteNewStatusToDB(statusesList.get(holder.getAdapterPosition()),mContext.getString(R.string.deleteStatus)));
    }

    @Override
    public int getItemCount() {
        return statusesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final RowBinding rowBinding;

        public ViewHolder(@NonNull RowBinding rowBinding) {
            super(rowBinding.getRoot());
            this.rowBinding = rowBinding;
            this.rowBinding.radioButton.setOnClickListener(view -> {
                lastSelectedPosition = getAdapterPosition();
                BFSForTrees bfs = new BFSForTrees();
                bfs.PerformBFS(statusesList.get(getAdapterPosition()),statusesList);
                notifyDataSetChanged();
            });
        }
    }

    public void setStatusesList(ArrayList<Status> statusesList) {
        this.statusesList = statusesList;
        notifyDataSetChanged();
    }
}

package com.example.methodafrontend.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.methodafrontend.Model.Transition;
import com.example.methodafrontend.R;
import com.example.methodafrontend.ViewModel.MainActivityViewModel;
import com.example.methodafrontend.databinding.TransitionrowBinding;

import java.util.ArrayList;

public class TransitionAdapter  extends RecyclerView.Adapter<TransitionAdapter.ViewHolder> {
    private ArrayList<Transition> transitionArrayList;
    private final Context mContext;
    public TransitionAdapter(ArrayList<Transition> transitionList, Context context) {
        this.transitionArrayList = transitionList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public TransitionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(TransitionrowBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TransitionAdapter.ViewHolder holder, int position) {
        holder.rowBinding.statusFrom.setText(transitionArrayList.get(position).getFrom());
        holder.rowBinding.statusTo.setText(transitionArrayList.get(position).getTo());
        MainActivityViewModel mainActivityViewModel = new ViewModelProvider((FragmentActivity) mContext).get(MainActivityViewModel.class);
        holder.rowBinding.removeButton.setOnClickListener(view ->
                mainActivityViewModel.DeleteOrWriteNewTransitionToDB(transitionArrayList.get(holder.getAdapterPosition()),mContext.getString(R.string.deleteTransition)));
    }

    @Override
    public int getItemCount() {
        return transitionArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TransitionrowBinding rowBinding;
        public ViewHolder(@NonNull TransitionrowBinding rowBinding) {
            super(rowBinding.getRoot());
            this.rowBinding = rowBinding;
        }
    }

    public void setTransitionArrayList(ArrayList<Transition> transitionArrayList){
        this.transitionArrayList = transitionArrayList;
        notifyDataSetChanged();
    }
}
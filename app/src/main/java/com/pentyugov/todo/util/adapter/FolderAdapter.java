package com.pentyugov.todo.util.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pentyugov.todo.R;

import java.util.List;

public class FolderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<String> folderNames;

    public FolderAdapter(List<String> folderNames) {
        this.folderNames = folderNames;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerView.ViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.folders_view, parent, false)){

        };
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TextView name = holder.itemView.findViewById(R.id.view_tv_folderName);
        name.setOnClickListener(v -> {
            folderNames.remove(position);
            this.notifyDataSetChanged();
        });
        name.setText(String.format("%s. %s", position + 1, this.folderNames.get(position)));
    }

    @Override
    public int getItemCount() {
        return this.folderNames.size();
    }
}

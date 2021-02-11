package com.pentyugov.todo.util.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pentyugov.todo.R;
import com.pentyugov.todo.model.folder.Folder;

import java.util.List;

public class FolderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<Folder> folders;

    public FolderAdapter(List<Folder> folders) {
        this.folders = folders;
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
        TextView pos = holder.itemView.findViewById(R.id.view_tv_folderPosition);
        pos.setText(String.format("%s.", position + 1));
        name.setText(String.format("%s", this.folders.get(position).getName()));
    }

    @Override
    public int getItemCount() {
        return this.folders.size();
    }




}

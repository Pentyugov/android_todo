package com.pentyugov.todo.util.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.pentyugov.todo.R;
import com.pentyugov.todo.model.note.Note;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<Note> notes;
    private Context context;

    public NoteAdapter(List<Note> notes, Context context) {
        this.notes = notes;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerView.ViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.note_view, parent, false)){

        };
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TextView title = holder.itemView.findViewById(R.id.view_tv_noteTitle);
        TextView pos = holder.itemView.findViewById(R.id.view_tv_notePosition);
        CheckBox isDone = holder.itemView.findViewById(R.id.view_cb_isDone);

        title.setOnLongClickListener(v -> {
            showPopupMenu(v, position);
            return true;
        });

        isDone.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked) {
                title.setPaintFlags(title.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                notes.get(position).setDone(true);
            } else {
                title.setPaintFlags(0);
                notes.get(position).setDone(false);
            }
        });
        pos.setText(String.format("%s.", position + 1));
        title.setText(String.format("%s", this.notes.get(position).getTitle()));
    }

    @Override
    public int getItemCount() {
        return this.notes.size();
    }


    private void showPopupMenu(View v, int position) {
        PopupMenu popupMenu = new PopupMenu(context, v);
        popupMenu.inflate(R.menu.note_popupmenu);

        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_note_rename: {

                } return true;

                case R.id.menu_note_delete: {

                } return true;
                default:
                    return false;
            }
        });

        popupMenu.setOnDismissListener(menu -> {

        });
        popupMenu.show();
    }
}

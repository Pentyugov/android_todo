package com.pentyugov.todo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pentyugov.todo.model.folder.Folder;
import com.pentyugov.todo.model.folder.FolderManager;
import com.pentyugov.todo.util.adapter.FolderAdapter;
import com.pentyugov.todo.util.listener.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView title;
    private ImageView newFolder;
    private RecyclerView folders;
    private RecyclerView.Adapter adapter;
    private AlertDialog dialogBuilder;
    private List<Folder> systemFolders;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.0F);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public void init() {
        systemFolders = FolderManager.getFoldersFromFile(this);
        if(systemFolders == null || systemFolders.isEmpty()) {
            systemFolders = FolderManager.getSystemFolders(getResources());
            FolderManager.saveFoldersToFile(systemFolders, this);
        }

        title = findViewById(R.id.main_tv_title);
        newFolder = findViewById(R.id.main_iv_newFolder);


        folders = findViewById(R.id.main_rv_foldersList);
        adapter = new FolderAdapter(systemFolders);
        folders.setHasFixedSize(true);
        folders.setLayoutManager(new LinearLayoutManager(this));
        folders.setAdapter(adapter);
        folders.addOnItemTouchListener(
                new RecyclerItemClickListener(this, folders ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(MainActivity.this, NoteActivity.class);
                        intent.putExtra("folderPosition", position);
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        showPopupMenu(view, position);
                    }
                })
        );
        folders.addItemDecoration(new DividerItemDecoration(folders.getContext(), DividerItemDecoration.VERTICAL));

        newFolder.setOnClickListener(v -> {
            v.startAnimation(buttonClick);
            createNewFolderAlertDialog();
            dialogBuilder.show();
        });

    }

    public void createNewFolderAlertDialog() {
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.textedit_alert, null);
        EditText newFolderName = dialogView.findViewById(R.id.alert_newFolderName);

        dialogBuilder = new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.title_newFolder))
                .setMessage(getResources().getString(R.string.message_newFolderName))
                .setPositiveButton(getResources().getString(R.string.submit), (dialog, which) -> {
                    boolean isFolderExist = FolderManager.findFolderByName(systemFolders, newFolderName.getText().toString()) != null;
                    if(isFolderExist) {
                        showErrorAlert("Folder with name " + newFolderName.getText().toString() + "already exists");
                    } else {
                        systemFolders.add(
                                FolderManager.createNewFolder(newFolderName.getText().toString(), systemFolders.size() + 1));
                                FolderManager.saveFoldersToFile(systemFolders, this);
                        adapter.notifyDataSetChanged();
                    }

                })
                .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.cancel())
                .setView(dialogView)
                .create();
    }

    public void createRenameFolderAlertDialog(int position) {
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.textedit_alert, null);
        EditText newFolderName = dialogView.findViewById(R.id.alert_newFolderName);

        dialogBuilder = new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.title_renameFolder))
                .setMessage(getResources().getString(R.string.message_renameFolder))
                .setPositiveButton(getResources().getString(R.string.submit), (dialog, which) -> {
                    boolean isFolderExist = FolderManager.findFolderByName(systemFolders, newFolderName.getText().toString()) != null;
                    if(isFolderExist) {
                        showErrorAlert("Folder with name " + newFolderName.getText().toString() + "already exists");
                    } else {
                        systemFolders.get(position).setName(newFolderName.getText().toString());
                        FolderManager.saveFoldersToFile(systemFolders, this);
                        adapter.notifyDataSetChanged();
                    }

                })
                .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.cancel())
                .setView(dialogView)
                .create();
    }

    private void showErrorAlert(String message) {
        AlertDialog alert = new AlertDialog.Builder(this)
                .setTitle(R.string.error)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(R.string.ok, (dialog, which) -> dialog.cancel())
                .create();
        alert.show();
    }


    private void showPopupMenu(View v, int position) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.inflate(R.menu.folder_popupmenu);

        if (systemFolders.get(position).isSystem()) {
            popupMenu.getMenu().getItem(0).setEnabled(false);
            popupMenu.getMenu().getItem(1).setEnabled(false);
        }

        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_folder_rename: {
                    createRenameFolderAlertDialog(position);
                    dialogBuilder.show();
                } return true;

                case R.id.menu_folder_delete: {
                    FolderManager.deleteFolder(systemFolders, position);
                    FolderManager.saveFoldersToFile(systemFolders, this);
                    adapter.notifyDataSetChanged();
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
package com.pentyugov.todo.model.folder;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.content.res.TypedArray;

import androidx.annotation.IdRes;

import com.pentyugov.todo.R;

import java.util.ArrayList;
import java.util.List;

public class FolderManager {

    @SuppressLint("ResourceType")
    public static Folder createFolderFromArray(@IdRes int arrayId, Resources resources) {
        Folder folder = new Folder();
        TypedArray array = resources.obtainTypedArray(arrayId);
        folder.setName(array.getString(0));
        folder.setPosition(Integer.parseInt(array.getString(1)));
        folder.setIsSystem(array.getBoolean(2, true));
        return folder;
    }

    @SuppressLint("ResourceType")
    public static List<Folder> getSystemFolders(Resources resources) {
        List<Folder> sysFolders = new ArrayList<>();
        TypedArray array = resources.obtainTypedArray(R.array.sys_folder_main);
        sysFolders.add(new Folder(array.getString(0), array.getInt(1, 1), array.getBoolean(2, true)));
        array = resources.obtainTypedArray(R.array.sys_folder_delete);
        sysFolders.add(new Folder(array.getString(0), array.getInt(1, 1), array.getBoolean(2, true)));
        return sysFolders;
    }

    public static Folder createNewFolder(String folderName, int position) {
        return new Folder(folderName, position, false);
    }

    public static Folder findFolderByName(List<Folder> folders, String folderName) {
        for (Folder f : folders) {
            String s = folderName.replace(" ", "").toLowerCase();
            if (f.getName().toLowerCase().equals(s)) {
                return f;
            }
        }
        return null;
    }

    public static boolean deleteFolder(List<Folder> folders, int position) {
        if (!folders.get(position).isSystem()) {
            folders.remove(position);
            return true;
        } else return false;

    }
}

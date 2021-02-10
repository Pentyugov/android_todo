package com.pentyugov.todo.model.folder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;

import androidx.annotation.IdRes;

import com.pentyugov.todo.R;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

    public static void saveFoldersToFile(List<Folder> folders, Context context) {
        try {
            FileOutputStream fileOutputStream = context.openFileOutput("folders.csv", Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fileOutputStream);
            out.writeObject(folders);
            out.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Folder> getFoldersFromFile(List<Folder> folders, Context context) {
        List<Folder> foldersFromFile;
        try {
            FileInputStream inputStream = context.openFileInput("folders.csv");
            ObjectInputStream in = new ObjectInputStream(inputStream);
            foldersFromFile = (ArrayList<Folder>) in.readObject();
            folders.clear();
            folders.addAll(foldersFromFile);
            in.close();
            inputStream.close();
            return folders;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Folder> getFoldersFromFile(Context context) {
        List<Folder> foldersFromFile;
        try {
            FileInputStream inputStream = context.openFileInput(context
                                                                    .getResources()
                                                                    .getString(R.string.foldersFileName));
            ObjectInputStream in = new ObjectInputStream(inputStream);
            foldersFromFile = (ArrayList<Folder>) in.readObject();
            in.close();
            inputStream.close();
            return foldersFromFile;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}

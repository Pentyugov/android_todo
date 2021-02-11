package com.pentyugov.todo.model.folder;

import com.pentyugov.todo.model.note.Note;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class Folder implements Serializable {
    private UUID id;
    private String name;
    private int position;
    private boolean isSystem;
    private List<Note> notes = new ArrayList<>();

    public Folder() {

    }

    public Folder(String name, int position, boolean isSystem) {
        this.name = name;
        this.position = position;
        this.isSystem = isSystem;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isSystem() {
        return isSystem;
    }

    public void setIsSystem(boolean system) {
        isSystem = system;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public void addNote(Note note) {
        this.notes.add(note);
    }

    public void removeNote(Note note) {
        this.notes.remove(note);
    }
}

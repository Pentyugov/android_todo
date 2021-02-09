package com.pentyugov.todo.model.folder;

import java.util.Objects;
import java.util.UUID;

public class Folder {
    private UUID id;
    private String name;
    private int position;
    private boolean isSystem;

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
}

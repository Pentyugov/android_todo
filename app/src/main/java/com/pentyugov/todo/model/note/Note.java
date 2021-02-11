package com.pentyugov.todo.model.note;

import java.io.Serializable;
import java.util.Date;

public class Note implements Serializable {
    private String description;
    private String title;
    private Date createTs;
    private Boolean isDone;

    public Note() {

    }

    public Note(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreateTs() {
        return createTs;
    }

    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
    }

    public Boolean getDone() {
        return isDone;
    }

    public void setDone(Boolean done) {
        isDone = done;
    }
}

package com.example.project_1;

//定义模型类，三个字段用来保存id，内容，时间。
public class Note {
    private String id;
    private String content;
    private String note_time;
    private String title;

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNote_time() {
        return note_time;
    }
    public void setNote_time(String note_time) {
        this.note_time = note_time;
    }
}


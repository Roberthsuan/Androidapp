package com.example.pretest0813;

public class Todo {
    private String title;
    private int number;

    private String imgName;
    private String content;



    public Todo(String title, int number, String imgName, String content) {
        this.title = title;
        this.number = number;
        this.imgName = imgName;
        this.content = content;
    }
//    public Todo(String title, String content){
//        this.title = title;
//        this.content = content;
//
//    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }
}


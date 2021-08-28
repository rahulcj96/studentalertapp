package com.studentalert.cses6;


class maincontent {
    //Data Variables
    private String title;
    private String content;
    private String date;

    public maincontent(String title) {
        this.title = title;
    }

    public maincontent() {

    }

    //Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

package com.studentalert.cses6;


class subjectContent {

    //Data Variables
    private String name;
    private String description;
    private String author;
    private String type;
    private String download_link;
    private String subject;
    private String Date;

    //Getters
    public String getName() {
        return name;
    }
    public String getDescription() {    return description;   }
    public String getAuthor() {
        return author;
    }
    public String getType() {
        return type;
    }
    public String getSubject() {
        return subject;
    }
    public String getDate() {
        return Date;
    }
    public String getDownload_link() {
        return download_link;
    }

    //Setters
    public void setName(String name) {
        this.name = name;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public void setDate(String Date) {
        this.Date = Date;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setDownload_link(String download_link) {
        this.download_link = download_link;
    }
}
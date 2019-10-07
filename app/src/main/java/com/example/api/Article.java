package com.example.api;

public class Article
{
    private String title,desc,publishAt,urltoImage;

    public Article() {
    }

    public Article(String title, String desc, String publishAt, String urltoImage) {
        this.title = title;
        this.desc = desc;
        this.publishAt = publishAt;
        this.urltoImage = urltoImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPublishAt() {
        return publishAt;
    }

    public void setPublishAt(String publishAt) {
        this.publishAt = publishAt;
    }

    public String getUrltoImage() {
        return urltoImage;
    }

    public void setUrltoImage(String urltoImage) {
        this.urltoImage = urltoImage;
    }
}

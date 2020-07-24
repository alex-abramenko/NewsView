package ru.alxabr.newsview.Model.Wrapper;

import java.util.Date;

public class News {
    private String title;
    private String description;
    private Date pubDate;
    private String image_url;
    private String source;

    public News(String title, String description, Date pubDate, String image_url, String source) {
        this.title = title;
        this.description = description;
        this.pubDate = pubDate;
        this.image_url = image_url;
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}

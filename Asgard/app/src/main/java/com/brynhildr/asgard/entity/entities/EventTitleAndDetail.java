package com.brynhildr.asgard.entity.entities;

/**
 * Created by willQian on 2015/11/22.
 */
public class EventTitleAndDetail {
    private String title;
    private String detail;

    public EventTitleAndDetail(String title, String detail) {
        this.title = title;
        this.detail = detail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}

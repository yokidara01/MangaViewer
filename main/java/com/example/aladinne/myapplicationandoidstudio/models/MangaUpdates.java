package com.example.aladinne.myapplicationandoidstudio.models;

import com.orm.SugarRecord;

/**
 * Created by Aladinne on 20/02/2016.
 */

public class MangaUpdates extends SugarRecord {

    private String  title,team,chapter,link,date,discription;

    public MangaUpdates(String title, String team, String chapter, String link, String date, String discription) {
        this.title = title;
        this.team = team;
        this.chapter = chapter;
        this.link = link;
        this.date = date;
        this.discription = discription;
    }

    public MangaUpdates() {
        super();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }
}

package com.nguyen.week5challenge.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
public class Meme {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String pictureUrl;

    @NotNull
    @Size(min=1, max=50)
    private String topCaption;

    @NotNull
    @Size(min=1, max=50)
    private String bottomCaption;

    @NotNull
    @Size(min=1, max=200)
    private String description;

    @NotNull
    @Size(min=1, max=100)
    private String shortDescription;

    private int likeCount;
    private String postDate;

    public Meme() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getTopCaption() {
        return topCaption;
    }

    public void setTopCaption(String topCaption) {
        this.topCaption = topCaption;
    }

    public String getBottomCaption() {
        return bottomCaption;
    }

    public void setBottomCaption(String bottomCaption) {
        this.bottomCaption = bottomCaption;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(LocalDate postDate) {
        DateTimeFormatter firstFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        this.postDate = postDate.format(firstFormat);
    }

    public String moreThan20(String text) {
        String result="";
        text=description;
        int stopAt20 = 0;
        if (text.length()>20) {
            for (char letter : description.toCharArray()) {
                result+=letter;
                stopAt20++;
                if (stopAt20>20) {
                    result+="...";
                    break;
                }
            }
            return result;
        }
        else {
            return description;
        }
    }
}

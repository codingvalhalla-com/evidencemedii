package com.codingvalhalla.meredith.evidence.model;

import java.util.Objects;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Meredith
 */
public class Season implements java.io.Serializable {

    private static final long serialVersionUID = 9113784715691496880L;

    private String name;
    private int stars;
    private ObservableList<Episode> episodes;
    private boolean watching;
    private String comments;

    public Season(String name, int episodesNumber, int stars, boolean watching, String comments) {
        this.name = name;
        this.episodes =  FXCollections.observableArrayList();
        this.stars = stars;
        this.watching = watching;
        this.comments = comments;
    }

      public int getEpisodesNumber() {
        return episodes.size();
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public boolean isWatching() {
        return watching;
    }

    public void setWatching(boolean watching) {
        this.watching = watching;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, episodes, stars, comments, watching);

    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Season other = (Season) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.stars, other.stars)) {
            return false;
        }
        if (!Objects.equals(this.comments, other.comments)) {
            return false;
        }
        if (!Objects.equals(this.episodes, other.episodes)) {
            return false;
        }
        if (!Objects.equals(this.watching, other.watching)) {
            return false;
        }
        return true;
    }
}

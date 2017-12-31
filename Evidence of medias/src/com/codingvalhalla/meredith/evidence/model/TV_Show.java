package com.codingvalhalla.meredith.evidence.model;

import java.util.Objects;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Meredith
 */
public class TV_Show  implements java.io.Serializable {

    private static final long serialVersionUID = 6680195713251243848L;

    private String name;
    private RatingMPAA ratingMPAA;
    private String comments;
    private int stars;
    private ObservableList<Season> seasons;
    private boolean watching;

    public TV_Show(String name, int stars,RatingMPAA ratingMPAA, boolean watching, String comments) {
        this.name = name;
        this.ratingMPAA = ratingMPAA;
        this.comments = comments;
        this.stars = stars;
        this.seasons = FXCollections.observableArrayList();
        this.watching = watching;
    }

    public int getSeasonsNumber() {
        return seasons.size();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RatingMPAA getRatingMPAA() {
        return ratingMPAA;
    }

    public void setRatingMPAA(RatingMPAA ratingMPAA) {
        this.ratingMPAA = ratingMPAA;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, ratingMPAA, stars, comments, seasons, watching);
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
        TV_Show other = (TV_Show) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.ratingMPAA, other.ratingMPAA)) {
            return false;
        }
        if (!Objects.equals(this.stars, other.stars)) {
            return false;
        }
        if (!Objects.equals(this.comments, other.comments)) {
            return false;
        }
        if (!Objects.equals(this.seasons, other.seasons)) {
            return false;
        }
        if (!Objects.equals(this.watching, other.watching)) {
            return false;
        }
        return true;
    }

}

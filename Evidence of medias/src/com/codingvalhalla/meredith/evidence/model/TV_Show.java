package com.codingvalhalla.meredith.evidence.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Meredith
 */
public class TV_Show implements java.io.Serializable {

    private static final long serialVersionUID = 5207101937016083734L;

    private String name;
    private RatingMPAA ratingMPAA;
    private String comments;
    private int stars;
    private List<Season> seasons;

    private boolean watching;

    public TV_Show(String name, int stars, RatingMPAA ratingMPAA, boolean watching, String comments) {
        this.name = name;
        this.ratingMPAA = ratingMPAA;
        this.comments = comments;
        this.stars = stars;
        this.watching = watching;
        this.seasons = new ArrayList<>();
    }

    public List<Season> getSeasons() {
        return seasons;
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
        return Objects.hash(name, ratingMPAA, stars, seasons, comments, watching);
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

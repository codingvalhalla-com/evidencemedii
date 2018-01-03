package com.codingvalhalla.meredith.evidence.model;

import java.util.Objects;

/**
 *
 * @author Meredith
 */
public class Episode implements java.io.Serializable {

    private static final long serialVersionUID = 4131847251103174308L;

    private String name;
    private int stars;
    private RatingMPAA ratingMPAA;
    private boolean watching;
    private String comment;

    public Episode(String name, int stars, boolean watching, String comments) {
        this.name = name;
        this.stars = stars;
        this.watching = watching;
        this.comment = comments;
    }

    public void setRatingMPAA(RatingMPAA ratingMPAA) {
        this.ratingMPAA = ratingMPAA;
    }

    public RatingMPAA getRatingMPAA() {
        return ratingMPAA;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, stars, ratingMPAA, watching);
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
        Episode other = (Episode) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.ratingMPAA, other.ratingMPAA)) {
            return false;
        }
        if (!Objects.equals(this.stars, other.stars)) {
            return false;
        }
        if (!Objects.equals(this.watching, other.watching)) {
            return false;
        }
        return true;
    }
}

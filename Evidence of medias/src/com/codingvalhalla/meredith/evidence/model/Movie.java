package com.codingvalhalla.meredith.evidence.model;

import java.util.Objects;

/**
 *
 * @author Meredith
 */
public class Movie implements java.io.Serializable {

    private static final long serialVersionUID = 1278730912718014871L;

    private String name;
    private RatingMPAA ratingMPAA;
    private String comments;
    private int stars;

    public Movie(String name, RatingMPAA ratingMPAA, String comment, int stars) {
        this.name = name;
        this.ratingMPAA = ratingMPAA;
        this.comments = comment;
        this.stars = stars;
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

    @Override
    public int hashCode() {
        return Objects.hash(name, ratingMPAA, stars, comments);
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
        Movie other = (Movie) obj;
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
        return true;
    }
}

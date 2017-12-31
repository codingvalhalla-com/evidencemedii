package com.codingvalhalla.meredith.evidence.model;

import java.util.Objects;

/**
 *
 * @author Meredith
 */
public class Episode implements java.io.Serializable {

    private static final long serialVersionUID = 6107485503623428828L;

    private String name;
    private int stars;
    private String comments;
    private boolean watching;

    public Episode(String name, int stars, boolean watching, String comments) {
        this.name = name;
        this.stars = stars;
        this.watching = watching;
        this.comments = comments;
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

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public boolean isWatching() {
        return watching;
    }

    public void setWatching(boolean watching) {
        this.watching = watching;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, stars, comments, watching);
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
        if (!Objects.equals(this.stars, other.stars)) {
            return false;
        }
        if (!Objects.equals(this.comments, other.comments)) {
            return false;
        }
        if (!Objects.equals(this.watching, other.watching)) {
            return false;
        }
        return true;
    }
}

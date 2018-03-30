package com.codingvalhalla.meredith.evidence.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Meredith
 */
public class Season implements java.io.Serializable {

    private static final long serialVersionUID = 4856384869611178224L;

    private String name;
    private int stars;
    private RatingMPAA ratingMPAA;
    private List<Episode> episodes;
    private boolean watching;

    public Season(String name, int stars, boolean watching) {
        this.name = name;
        this.stars = stars;
        this.watching = watching;
        this.episodes = new ArrayList<>();

    }

    public void setRatingMPAA(RatingMPAA ratingMPAA) {
        this.ratingMPAA = ratingMPAA;
    }

    public RatingMPAA getRatingMPAA() {
        return ratingMPAA;
    }

    public List<Episode> getEpisodes() {
        return episodes;
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

    @Override
    public int hashCode() {
        return Objects.hash(name, stars, ratingMPAA, episodes, watching);
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
        if (!Objects.equals(this.ratingMPAA, other.ratingMPAA)) {
            return false;
        }
        if (!Objects.equals(this.stars, other.stars)) {
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

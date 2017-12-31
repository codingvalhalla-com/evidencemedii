package com.codingvalhalla.meredith.evidence.model;

/**
 *
 * @author Meredith
 */
public enum RatingMPAA {
    G("<G> - General Audience"),
    PG("<PG> -Parental Guidance Suggested"),
    PG_13("<PG-13> Parents Strongly Cautioned"),
    R("<R> Restricted"),
    NC_17("<NC-17> Adults Only");

    private final String name;

    private RatingMPAA(String name) {
        this.name = name;
    }

}

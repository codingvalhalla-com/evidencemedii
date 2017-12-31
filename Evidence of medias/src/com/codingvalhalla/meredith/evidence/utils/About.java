package com.codingvalhalla.meredith.evidence.utils;

/**
 *
 * @author Meredith
 */
public class About {
    
    private static final String verze = "Alpha 1.3.2";
    private static final String about = "Evidence medii\nVerze: "+verze+"\nAutor: Richard \"Meredith\" Grossmann\nCopyright \u00a9 2017-2018 by CodingValhalla.com";

    private About() {
    }

    public static String getAbout() {
        return about;
    }

}

package com.codingvalhalla.meredith.evidence.utils;

import com.codingvalhalla.meredith.evidence.gui.GraphicUserInterface;

/**
 *
 * @author Meredith
 */
public class About {

    private static final String verze = "Alpha 1.5.1";
    private static final String about = GraphicUserInterface.getTitle() + "\nVerze: " + verze + "\nAutor: Richard \"Meredith\" Grossmann\nCopyright \u00a9 2017-2018 by CodingValhalla";

    private About() {
    }

    public static String getAbout() {
        return about;
    }

}

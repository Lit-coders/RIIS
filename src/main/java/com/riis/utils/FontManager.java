package com.riis.utils;

import javafx.scene.text.Font;

public class FontManager {
    public Font getFont(String fontName, int fontSize) {
        Font regularFont = Font.loadFont(getClass().getResourceAsStream("/com/riis/fonts/" + fontName + ".ttf"), fontSize);
        return regularFont;
    }
}
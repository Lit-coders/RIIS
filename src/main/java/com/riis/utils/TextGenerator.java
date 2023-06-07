package com.riis.utils;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class TextGenerator {
    public static Text generateText(String text, int fontSize, String fontColor, String fontFamily, String fontWeight) {
        Text _text = new Text(text);
        _text.setStyle("-fx-font-size: " + fontSize + "px; -fx-color: " + fontColor + "; -fx-font-family: " + fontFamily + ";" + " -fx-font-weight: " + fontWeight + ";");
        return _text;
    }
    
    public static Text generateText(String text) {
        Text _text = new Text(text);
        _text.setStyle("-fx-font-size: " + 20 + "px; -fx-color: " + "#000000" + "; -fx-font-family: " + "Times New Roman" + ";");
        return _text;
    }

    public static Text generateText(String text, String fontName, int fontSize, String fontColor) {
        Text _text = new Text(text);
        _text.setFont(new FontManager().getFont(fontName,fontSize));
        _text.setFill(Color.valueOf(fontColor));
        return _text;
    }
}
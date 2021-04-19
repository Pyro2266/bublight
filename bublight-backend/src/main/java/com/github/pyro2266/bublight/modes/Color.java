package com.github.pyro2266.bublight.modes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class Color {

    @Getter
    private final int red;
    @Getter
    private final int green;
    @Getter
    private final int blue;

    public Color() {
        this(10, 10, 10);
    }

    public Color(int rgb) {
        java.awt.Color color = new java.awt.Color(rgb);
        this.red = color.getRed();
        this.green = color.getGreen();
        this.blue = color.getBlue();
    }

    public Color(float hue, float saturation, float brightness) {
        this(java.awt.Color.HSBtoRGB(hue, saturation, brightness));
    }

    public static com.github.mbelling.ws281x.Color toWs281xColor(Color color) {
        return new com.github.mbelling.ws281x.Color(color.getRed(), color.getGreen(), color.getBlue());
    }

    public static float[] rgbToHsb(int r, int g, int b, float[] floats) {
        return java.awt.Color.RGBtoHSB(r, g, b, floats);
    }
}

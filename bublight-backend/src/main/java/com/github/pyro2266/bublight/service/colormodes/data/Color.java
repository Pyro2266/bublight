package com.github.pyro2266.bublight.service.colormodes.data;

public class Color {

    private final int red;
    private final int green;
    private final int blue;

    public Color(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

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

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    public static com.github.mbelling.ws281x.Color toWs281xColor(Color color) {
        return new com.github.mbelling.ws281x.Color(color.getRed(), color.getGreen(), color.getBlue());
    }

    public static float[] rgbToHsb(int r, int g, int b, float[] floats) {
        return java.awt.Color.RGBtoHSB(r, g, b, floats);
    }

    @Override
    public String toString() {
        return "Color{" + "red=" + red + ", green=" + green + ", blue=" + blue + '}';
    }
}

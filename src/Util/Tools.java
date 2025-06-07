package Util;

// <editor-fold defaultstate="collapsed" desc="*** import ***">
import Util.Constantes.ColorComponente;
import java.util.HashMap;
import java.util.Map;
import processing.core.PApplet;
// </editor-fold>

/**
 * @author CPZ
 */
public class Tools {

    public static String getFecha() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%02d", PApplet.day())).append("/");
        sb.append(String.format("%02d", PApplet.month())).append("/");
        sb.append(String.format("%02d", PApplet.year()));
        return sb.toString();
    }

    public static String getHora() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%02d", PApplet.hour())).append(":");
        sb.append(String.format("%02d", PApplet.minute())).append(":");
        sb.append(String.format("%02d", PApplet.second()));
        return sb.toString();
    }

    public static boolean isHovering(float mouseX, float mouseY, float x, float y, float w, float h) {
        boolean bx = mouseX > x && mouseX < x + w;
        boolean by = mouseY > y && mouseY < y + h;
        return bx && by;
    }

    public static int construirColor(float red, float green, float blue, float alpha) {
        return ((int) alpha << 24) | ((int) red << 16) | ((int) green << 8) | (int) blue;
    }

    public static int construirColor(float gray) {
        return construirColor(gray, gray, gray, 255);
    }

    public static int construirColor(float gray, float alpha) {
        return construirColor(gray, gray, gray, alpha);
    }

    public static Map<ColorComponente, Integer> deconstruirColor(int c) {
        Map<ColorComponente, Integer> componentes = new HashMap<>();
        componentes.put(ColorComponente.RED, (c >> 16) & 0xFF);
        componentes.put(ColorComponente.GREEN, (c >> 8) & 0xFF);
        componentes.put(ColorComponente.BLUE, c & 0xFF);
        componentes.put(ColorComponente.ALPHA, (c >> 24) & 0xFF);
        return componentes;
    }
}

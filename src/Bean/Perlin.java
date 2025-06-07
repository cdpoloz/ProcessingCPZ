package Bean;

import processing.core.PApplet;

import static java.lang.Math.random;

/**
 * @author CPZ
 */
public class Perlin {

    private PApplet sketch;
    private float valor;
    private float velocidad;

    public Perlin() {
        valor = (float) random();
    }

    public void update() {
        valor += velocidad;
    }

    public float get() {
        return sketch.noise(valor);
    }

    public void reset() {
        valor = sketch.random(5000);
    }

    // <editor-fold defaultstate="collapsed" desc="*** setter & getter ***">
    public void setSketch(PApplet sketch) {
        this.sketch = sketch;
    }

    public float getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(float velocidad) {
        this.velocidad = velocidad;
    }
// </editor-fold>
}

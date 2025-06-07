package Bean;

import processing.core.PApplet;
import processing.core.PVector;

/**
 * @author CPZ
 */
public class PerlinVector {

    private Perlin x, y, z;

    public PerlinVector() {
        x = new Perlin();
        y = new Perlin();
        z = new Perlin();
    }

    public void update() {
        x.update();
        y.update();
        z.update();
    }

    public PVector get() {
        return new PVector(x.get(), y.get(), z.get());
    }

    // <editor-fold defaultstate="collapsed" desc="*** setter & getter ***">
    public void setVelocidad(float velX, float velY) {
        x.setVelocidad(velX);
        y.setVelocidad(velY);
    }

    public void setVelocidad(float velX, float velY, float velZ) {
        x.setVelocidad(velX);
        y.setVelocidad(velY);
        z.setVelocidad(velZ);
    }

    public PVector getVelocidad() {
        return new PVector(x.getVelocidad(), y.getVelocidad(), z.getVelocidad());
    }

    public void setVelocidad(float vel) {
        x.setVelocidad(vel);
        y.setVelocidad(vel);
        z.setVelocidad(vel);
    }

    public void setSketch(PApplet sketch) {
        x.setSketch(sketch);
        y.setSketch(sketch);
        z.setSketch(sketch);
    }
    // </editor-fold>
}

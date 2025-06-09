/*
 * Copyright 2025 Carlos Polo Zamora - CPZ - CePeZeta
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package Bean;

import Main.Sketch;
import processing.core.PApplet;
import processing.core.PVector;

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

    public void setSketch(Sketch sketch) {
        x.setSketch(sketch);
        y.setSketch(sketch);
        z.setSketch(sketch);
    }
    // </editor-fold>
}

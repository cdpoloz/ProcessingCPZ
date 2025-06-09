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

import static java.lang.Math.random;

public class Perlin {

    private Sketch sketch;
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
    public void setSketch(Sketch sketch) {
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

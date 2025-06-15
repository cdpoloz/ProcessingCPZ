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

package com.cpz.processing.Bean;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

import static processing.core.PApplet.map;
import static processing.core.PConstants.CENTER;

/**
 * @author CPZ
 */
public class Movil {

    private final PApplet sketch;
    private final PVector pos;
    private PVector normal;
    private PImage img;
    private int colorRelleno, indPos, periodo;
    private float alfa, alfaMin, alfaMax, off, dOff, desviacionMax, diametro;
    private final Timer timer;
    private List<PVector> lstPos, lstNormal;
    private boolean finRecorrido;

    public Movil(PApplet sketch) {
        this.sketch = sketch;
        timer = new Timer();
        timer.setSketch(sketch);
        pos = new PVector();
    }

    public void setup() {
        timer.iniciar(periodo);
        off = sketch.random(5000);
        reiniciarCondiciones();
    }

    public void update() {
        if (!timer.periodoPulso()) {
            return;
        }
        indPos += 1;
        finRecorrido = indPos >= lstPos.size();
        if (finRecorrido) {
            reiniciarCondiciones();
            return;
        }
        PVector v = lstPos.get(indPos);
        float d = sketch.noise(off);
        d = map(d, 0, 1, -desviacionMax, desviacionMax);
        PVector vDesv = new PVector(normal.x * d, normal.y * d);
        pos.set((v.x + vDesv.x) * sketch.width, (v.y + vDesv.y) * sketch.height);
        off += dOff;
    }

    public void draw() {
        sketch.imageMode(CENTER);
        sketch.tint(colorRelleno);
        sketch.image(img, pos.x, pos.y, diametro, diametro);
        sketch.tint(255, 255);
    }

    private void reiniciarCondiciones() {
        indPos = 0;
        alfa = sketch.random(alfaMin, alfaMax) * 255;
        colorRelleno = sketch.color(sketch.red(colorRelleno), sketch.green(colorRelleno), sketch.blue(colorRelleno), alfa);
    }

    // <editor-fold defaultstate="collapsed" desc="*** setter & getter ***">
    public void setColorRelleno(int colorRelleno) {
        this.colorRelleno = colorRelleno;
    }

    public void setImg(PImage img) {
        this.img = img;
    }

    public void setLstPos(List<PVector> lstPos) {
        this.lstPos = lstPos;
        pos.set(lstPos.getFirst().x, lstPos.getFirst().y);
    }

    public void addPos(PVector pos, PVector normal) {
        if (lstPos == null) {
            lstPos = new ArrayList<>();
        }
        lstPos.add(pos);
        if (lstNormal == null) {
            lstNormal = new ArrayList<>();
        }
        lstNormal.add(normal);
    }

    public void setVelocidadNoise(float dOff) {
        this.dOff = dOff;
    }

    public void setDesviacionMax(float desviacionMax) {
        this.desviacionMax = desviacionMax;
    }

    public void setNormal(PVector normal) {
        this.normal = normal;
    }

    public void setRangoAlfa(float alfaMin, float alfaMax) {
        this.alfaMin = alfaMin;
        this.alfaMax = alfaMax;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }

    public boolean isFinRecorrido() {
        return finRecorrido;
    }

    public void setDiametro(float diametro) {
        this.diametro = diametro * sketch.height;
    }
    // </editor-fold>
}

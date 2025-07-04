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

package com.cpz.processing.UI;

import com.cpz.processing.Interfaces.Editable;
import com.cpz.processing.Interfaces.UI.ElementoUI;
import com.cpz.processing.Interfaces.Identificable;
import com.cpz.processing.Interfaces.UI.Hoverable;
import com.cpz.processing.Util.Constantes.Esquina;
import com.cpz.processing.Util.Tools;
import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Switch implements ElementoUI, Identificable, Editable, Hoverable {

    private final PVector pos;
    private PApplet sketch;
    private int id;
    private String codigo, rutaShape;
    private float ancho, alto, x, y;
    private int c, cOn, cHover, cOff;
    private PShape shape;
    private boolean on, onPrev, showHover, editable, display;
    private Map<Esquina, PVector> esquinas;

    public Switch() {
        pos = new PVector();
    }

    public Switch(PApplet sk) {
        this.sketch = sk;
        pos = new PVector();
    }

    public Switch(Switch sw) {
        sketch = sw.sketch;
        pos = new PVector(sw.pos.x, sw.pos.y);
        ancho = sw.ancho;
        alto = sw.alto;
        c = sw.c;
        cOn = sw.cOn;
        cHover = sw.cHover;
        cOff = sw.cOff;
        shape = sw.shape;
        on = sw.on;
        showHover = sw.showHover;
        editable = sw.editable;
        display = sw.display;
    }

    @Override
    public void update() {
        if (esquinas == null) {
            esquinas = new HashMap<>();
            esquinas.put(Esquina.SUP_IZQ, new PVector(pos.x, pos.y));
            esquinas.put(Esquina.SUP_DER, new PVector(pos.x + ancho, pos.y));
            esquinas.put(Esquina.INF_IZQ, new PVector(pos.x, pos.y + alto));
            esquinas.put(Esquina.INF_DER, new PVector(pos.x + ancho, pos.y + alto));
        }
        if (!display || !editable) {
            return;
        }
        updateHover();
    }

    @Override
    public void draw() {
        if (!display) {
            return;
        }
        sketch.noStroke();
        sketch.fill(c);
        sketch.shape(shape, pos.x, pos.y, ancho, alto);
        if (showHover && editable) {
            // se grafica el efecto hover
            sketch.fill(cHover);
            sketch.shape(shape, pos.x, pos.y, ancho, alto);
        }
    }

    public PVector getCentro() {
        return new PVector(pos.x + ancho * 0.5f, pos.y + alto * 0.5f);
    }

    public PVector getEsquina(Esquina esquina) {
        return esquinas.get(esquina);
    }

    public List<PVector> getLstEsquinas() {
        List<PVector> lstEsquinas = new ArrayList<>();
        lstEsquinas.add(esquinas.get(Esquina.SUP_IZQ));
        lstEsquinas.add(esquinas.get(Esquina.SUP_DER));
        lstEsquinas.add(esquinas.get(Esquina.INF_DER));
        lstEsquinas.add(esquinas.get(Esquina.INF_IZQ));
        return lstEsquinas;
    }

    @Override
    public void updateHover() {
        if (!display || !editable) {
            return;
        }
        cHover = sketch.color(sketch.red(cHover), sketch.green(cHover), sketch.blue(cHover), isHovering() ? 64 : 0);
    }

    @Override
    public boolean isHovering(Object... o) {
        if (!display || !editable) {
            return false;
        }
        return Tools.isHovering(sketch.mouseX, sketch.mouseY, pos.x, pos.y, ancho, alto);
    }

    @Override
    public boolean isFlancoSubida() {
        return !onPrev && on;
    }

    @Override
    public boolean isFlancoBajada() {
        return onPrev && !on;
    }

    @Override
    public boolean isCambioValor() {
        return onPrev != on;
    }

    // <editor-fold defaultstate="collapsed" desc="*** setter & getter ***">
    public void setPos(float x, float y) {
        this.pos.set(x, y);
    }

    public boolean isShowHover() {
        return showHover;
    }

    @Override
    public void setShowHover(boolean showHover) {
        this.showHover = showHover;
    }

    public String getRutaShape() {
        return rutaShape;
    }

    public void setRutaShape(String rutaShape) {
        this.rutaShape = rutaShape;
    }

    public float getAncho() {
        return ancho;
    }

    public void setAncho(float ancho) {
        this.ancho = ancho;
    }

    public float getAlto() {
        return alto;
    }

    public void setAlto(float alto) {
        this.alto = alto;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setSketch(PApplet sketch) {
        this.sketch = sketch;
    }

    public boolean isDisplay() {
        return display;
    }

    @Override
    public void setDisplay(boolean display) {
        this.display = display;
    }

    public PVector getPos() {
        return pos;
    }

    public void setPos(PVector pos) {
        this.pos.set(pos.x, pos.y);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void conmutarEstado() {
        onPrev = on;
        on = !on;
        c = (on ? cOn : cOff);
    }

    public boolean isOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.onPrev = this.on;
        this.on = on;
        c = (on ? cOn : cOff);
    }

    public int getColorOff() {
        return cOff;
    }

    public void setColorOff(int cOff) {
        this.cOff = cOff;
    }

    public void setColorOn(int cOn) {
        this.cOn = cOn;
    }

    public int getColorOn() {
        return cOn;
    }

    public void setColorHover(int cHover) {
        this.cHover = cHover;
    }

    public void setShape(PShape shape) {
        this.shape = shape;
        this.shape.disableStyle();
    }

    @Override
    public String getCodigo() {
        return codigo;
    }

    @Override
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public boolean isEditable() {
        return editable;
    }

    @Override
    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public PApplet getSketch() {
        return sketch;
    }
// </editor-fold>

}

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

package UI;

import Interfaces.ElementoUI;
import Interfaces.Identificable;
import Util.Constantes.Esquina;
import Util.Tools;
import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Switch implements ElementoUI, Identificable {

    private final PVector pos;
    private PApplet sk;
    private int id;
    private String codigo, estadoApp, rutaShape;
    private float ancho, alto, x, y;
    private int c, cOn, cHover, cOff;
    private PShape shape;
    private boolean on, onPrev, showHover, editable, display;
    private Map<Esquina, PVector> esquinas;

    public Switch() {
        pos = new PVector();
    }

    public Switch(PApplet sk) {
        this.sk = sk;
        pos = new PVector();
    }

    public Switch(Switch sw) {
        sk = sw.sk;
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
        sk.noStroke();
        sk.fill(c);
        sk.shape(shape, pos.x, pos.y, ancho, alto);
        if (showHover && editable) {
            // se grafica el efecto hover
            sk.fill(cHover);
            sk.shape(shape, pos.x, pos.y, ancho, alto);
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
        cHover = sk.color(sk.red(cHover), sk.green(cHover), sk.blue(cHover), isHovering() ? 64 : 0);
    }

    @Override
    public boolean isHovering() {
        if (!display || !editable) {
            return false;
        }
        return Tools.isHovering(sk.mouseX, sk.mouseY, pos.x, pos.y, ancho, alto);
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

    public void setSk(PApplet sk) {
        this.sk = sk;
    }

    public String getEstadoApp() {
        return estadoApp;
    }

    public void setEstadoApp(String estadoApp) {
        this.estadoApp = estadoApp;
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

    // </editor-fold>

}

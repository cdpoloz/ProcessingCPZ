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

package com.cpz.processingUI.UI;

import com.cpz.processingUI.Interfaces.Editable;
import com.cpz.processingUI.Interfaces.UI.ElementoUI;
import com.cpz.processingUI.Interfaces.Identificable;
import com.cpz.processingUI.Util.Constantes.AlineaX;
import com.cpz.processingUI.Util.Constantes.AlineaY;
import com.cpz.processingUI.Util.Constantes.Esquina;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Label implements ElementoUI, Identificable, Editable {

    private final PVector pos;
    private PApplet sketch;
    private int id;
    private float tamTexto;
    private String texto, codigo, estado, estadoApp;
    private int c, alineaX, alineaY;
    private boolean display;
    private int anchoVentana, altoVentana;

    public Label() {
        pos = new PVector();
    }

    public Label(PApplet sk) {
        this.sketch = sk;
        pos = new PVector();
    }

    public Label(Label lbl) {
        sketch = lbl.sketch;
        pos = new PVector(lbl.pos.x, lbl.pos.y);
        tamTexto = lbl.tamTexto;
        texto = lbl.texto;
        c = lbl.c;
        alineaX = lbl.alineaX;
        alineaY = lbl.alineaY;
        display = lbl.display;
    }

    @Override
    public void draw() {
        /*
        método que dibuja el label en pantalla
         */
        if (!display || texto == null) {
            return;
        }
        // se grafica el texto
        sketch.fill(c);
        sketch.noStroke();
        sketch.textAlign(alineaX, alineaY);
        sketch.textSize(tamTexto);
        sketch.text(texto, pos.x, pos.y);
    }

    public PVector getCentro() {
        if (texto == null || texto.isEmpty()) {
            return new PVector(pos.x, pos.y);
        }
        float ancho = sketch.textWidth(texto);
        float alto = sketch.textAscent() + sketch.textDescent();
        float x = pos.x;
        float y = pos.y;
        if (alineaX == PApplet.LEFT) {
            x += ancho * 0.5f;
        } else if (alineaX == PApplet.RIGHT) {
            x -= ancho * 0.5f;
        }
        if (alineaY == PApplet.TOP) {
            y += alto * 0.5f;
        } else if (alineaY == PApplet.BOTTOM) {
            y -= alto * 0.5f;
        }
        return new PVector(x, y);
    }

    public PVector getEsquina(Esquina esquina) {
        return getEsquinas().get(esquina);
    }

    public Map<Esquina, PVector> getEsquinas() {
        Map<Esquina, PVector> esquinas = new HashMap<>();
        if (texto == null || texto.isEmpty()) {
            PVector p = new PVector(pos.x, pos.y);
            esquinas.put(Esquina.SUP_IZQ, p);
            esquinas.put(Esquina.SUP_DER, p);
            esquinas.put(Esquina.INF_DER, p);
            esquinas.put(Esquina.INF_IZQ, p);
            return esquinas;
        }
        float ancho = sketch.textWidth(texto);
        float alto = sketch.textAscent() + sketch.textDescent();
        PVector centro = getCentro();
        esquinas.put(Esquina.SUP_IZQ, new PVector(centro.x - ancho * 0.5f, centro.y - alto * 0.5f));
        esquinas.put(Esquina.SUP_DER, new PVector(centro.x + ancho * 0.5f, centro.y - alto * 0.5f));
        esquinas.put(Esquina.INF_DER, new PVector(centro.x + ancho * 0.5f, centro.y + alto * 0.5f));
        esquinas.put(Esquina.INF_IZQ, new PVector(centro.x - ancho * 0.5f, centro.y + alto * 0.5f));
        return esquinas;
    }

    public List<PVector> getLstEsquinas() {
        List<PVector> lstEsquinas = new ArrayList<>();
        Map<Esquina, PVector> esquinas = getEsquinas();
        lstEsquinas.add(esquinas.get(Esquina.SUP_IZQ));
        lstEsquinas.add(esquinas.get(Esquina.SUP_DER));
        lstEsquinas.add(esquinas.get(Esquina.INF_DER));
        lstEsquinas.add(esquinas.get(Esquina.INF_IZQ));
        return lstEsquinas;
    }

    @Override
    public void update() {
    }

    @Override
    public void updateHover() {
    }

    @Override
    public boolean isHovering() {
        return false;
    }

    @Override
    public boolean isFlancoSubida() {
        return false;
    }

    @Override
    public boolean isFlancoBajada() {
        return false;
    }

    @Override
    public boolean isCambioValor() {
        return false;
    }

    @Override
    public boolean isEditable() {
        return false;
    }

    @Override
    public void setEditable(boolean b) {
    }

    // <editor-fold defaultstate="collapsed" desc="*** setter & getter ***">
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEstadoApp() {
        return estadoApp;
    }

    public void setEstadoApp(String estadoApp) {
        this.estadoApp = estadoApp;
    }

    public void setSketch(PApplet sketch) {
        this.sketch = sketch;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setAlineaX(AlineaX x) {
        switch (x) {
            case AlineaX.IZQ -> this.alineaX = PApplet.LEFT;
            case AlineaX.CENTRO -> this.alineaX = PApplet.CENTER;
            case AlineaX.DER -> this.alineaX = PApplet.RIGHT;
        }
    }

    public void setAlineaY(AlineaY y) {
        switch (y) {
            case AlineaY.SUP -> this.alineaY = PApplet.TOP;
            case AlineaY.CENTRO -> this.alineaY = PApplet.CENTER;
            case AlineaY.INF -> this.alineaY = PApplet.BOTTOM;
        }
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public void setTexto(StringBuilder texto) {
        this.texto = texto.toString();
    }

    @Override
    public String getCodigo() {
        return codigo;
    }

    @Override
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setPos(float x, float y) {
        this.pos.set(x, y);
    }

    public PVector getPos() {
        return pos;
    }

    public void setPos(PVector pos) {
        this.pos.set(pos.x, pos.y);
    }

    public float getTamTexto() {
        return tamTexto;
    }

    public void setTamTexto(float tamTexto) {
        this.tamTexto = tamTexto;
    }

    public int getColorTexto() {
        return c;
    }

    public void setColorTexto(int c) {
        this.c = c;
    }

    public boolean isDisplay() {
        return display;
    }

    @Override
    public void setDisplay(boolean display) {
        this.display = display;
    }

    @Override
    public void setShowHover(boolean b) {
    }

    public void setAnchoVentana(int anchoVentana) {
        this.anchoVentana = anchoVentana;
    }

    public void setAltoVentana(int altoVentana) {
        this.altoVentana = altoVentana;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="*** métodos de soporte ***">
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Tipo    : ").append(this.getClass().toString().split("\\.")[1]).append("\n");
        sb.append("Texto   : ").append(texto).append("\n");
        sb.append("Codigo  : ").append(codigo).append("\n");
        sb.append("Posicion: (").append(pos.x).append(", ").append(pos.y).append(")\n");
        return sb.toString();
    }
    // </editor-fold>
}

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
import com.cpz.processing.Interfaces.UI.Hoverable;
import com.cpz.processing.Interfaces.Identificable;
import com.cpz.processing.Util.Constantes.BarraModo;
import com.cpz.processing.Util.Constantes.Orientacion;
import com.cpz.processing.Util.Constantes.Esquina;
import com.cpz.processing.Util.Tools;
import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.cpz.processing.Util.Constantes.MATH_CONTEXT;

public class Barra implements ElementoUI, Identificable, Editable, Hoverable {

    private PApplet sketch;
    private int id;
    private final PVector pos;
    private int c, cOff, cHover, cIni, cFin;
    private float wMax, hMax, wBar, hBar, ancho, alto;
    private BigDecimal valorMin, valorMax, valor, valorPrevio, dValor;
    private String codigo;
    private BarraModo modo;
    private Orientacion orientacion;
    private PShape shape;
    private boolean editable, display, showHover;
    private String rutaShape;
    private Map<Esquina, PVector> esquinas;

    public Barra() {
        pos = new PVector();
        valor = BigDecimal.ZERO;
        valorMin = BigDecimal.ZERO;
        valorMax = BigDecimal.ONE;
    }

    public Barra(PApplet sketch) {
        this.sketch = sketch;
        pos = new PVector();
        valor = BigDecimal.ZERO;
        valorMin = BigDecimal.ZERO;
        valorMax = BigDecimal.ONE;
    }

    @Override
    public void update() {
        if (esquinas == null) {
            esquinas = new HashMap<>();
            esquinas.put(Esquina.SUP_IZQ, new PVector(pos.x, pos.y));
            esquinas.put(Esquina.SUP_DER, new PVector(pos.x + wMax, pos.y));
            esquinas.put(Esquina.INF_IZQ, new PVector(pos.x, pos.y + hMax));
            esquinas.put(Esquina.INF_DER, new PVector(pos.x + wMax, pos.y + hMax));
        }
        if (!display || !editable) {
            return;
        }
        updateHover();
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
        return Tools.isHovering(sketch.mouseX, sketch.mouseY, pos.x, pos.y, wMax, hMax);
    }

    public void setValor(BigDecimal v) {
        valorPrevio = new BigDecimal(valor.toString());
        valor = new BigDecimal(v.toString());
        if (modo == BarraModo.INTERACTIVO) {
            if (valor.compareTo(valorMin) < 0) {
                valor = new BigDecimal(valorMin.toString());
            } else if (valor.compareTo(valorMax) > 0) {
                valor = new BigDecimal(valorMax.toString());
            }
        }
        if (orientacion == Orientacion.VERTICAL) {
            hBar = PApplet.map(valor.floatValue(), valorMin.floatValue(), valorMax.floatValue(), 0, hMax);
            if (modo == BarraModo.INDICADOR) {
                hBar = PApplet.constrain(hBar, 0, hMax);
            }
            wBar = wMax;
        } else if (orientacion == Orientacion.HORIZONTAL) {
            hBar = hMax;
            wBar = PApplet.map(valor.floatValue(), valorMin.floatValue(), valorMax.floatValue(), 0, wMax);
            if (modo == BarraModo.INDICADOR) {
                wBar = PApplet.constrain(wBar, 0, wMax);
            }
        }
        c = Tools.lerpColor(cIni, cFin, PApplet.map(valor.floatValue(), valorMin.floatValue(), valorMax.floatValue(), 0, 1));
    }

    public void setValorPorDiferencial(float d) {
        setValorPorDiferencial(new BigDecimal(d));
    }

    public void setValorPorDiferencial(BigDecimal d) {
        if (!display || !editable) {
            return;
        }
        valorPrevio = new BigDecimal(valor.toString());
        valor = valor.add(d, MATH_CONTEXT);
        if (modo == BarraModo.INTERACTIVO) {
            if (valor.compareTo(valorMin) < 0) {
                valor = new BigDecimal(valorMin.toString());
            } else if (valor.compareTo(valorMax) > 0) {
                valor = new BigDecimal(valorMax.toString());
            }
        }
        if (orientacion == Orientacion.VERTICAL) {
            hBar = PApplet.map(valor.floatValue(), valorMin.floatValue(), valorMax.floatValue(), 0, hMax);
            if (modo == BarraModo.INDICADOR) {
                hBar = PApplet.constrain(hBar, 0, hMax);
            }
            wBar = wMax;
        } else if (orientacion == Orientacion.HORIZONTAL) {
            hBar = hMax;
            wBar = PApplet.map(valor.floatValue(), valorMin.floatValue(), valorMax.floatValue(), 0, wMax);
            if (modo == BarraModo.INDICADOR) {
                wBar = PApplet.constrain(wBar, 0, wMax);
            }
        }
        c = Tools.lerpColor(cIni, cFin, PApplet.map(valor.floatValue(), valorMin.floatValue(), valorMax.floatValue(), 0, 1));
    }

    public void setValorPorMouse(int d) {
        if (!display || !editable || !isHovering()) {
            return;
        }
        valorPrevio = new BigDecimal(valor.toString());
        BigDecimal factor = new BigDecimal(d).multiply(new BigDecimal(-1));
        valor = valor.add(dValor.multiply(factor, MATH_CONTEXT), MATH_CONTEXT);
        if (modo == BarraModo.INTERACTIVO) {
            if (valor.compareTo(valorMin) < 0) {
                valor = new BigDecimal(valorMin.toString());
            } else if (valor.compareTo(valorMax) > 0) {
                valor = new BigDecimal(valorMax.toString());
            }
        }
        if (orientacion == Orientacion.VERTICAL) {
            hBar = PApplet.map(valor.floatValue(), valorMin.floatValue(), valorMax.floatValue(), 0, hMax);
            if (modo == BarraModo.INDICADOR) {
                hBar = PApplet.constrain(hBar, 0, hMax);
            }
            wBar = wMax;
        } else if (orientacion == Orientacion.HORIZONTAL) {
            hBar = hMax;
            wBar = PApplet.map(valor.floatValue(), valorMin.floatValue(), valorMax.floatValue(), 0, wMax);
            if (modo == BarraModo.INDICADOR) {
                wBar = PApplet.constrain(wBar, 0, wMax);
            }
        }
        c = Tools.lerpColor(cIni, cFin, PApplet.map(valor.floatValue(), valorMin.floatValue(), valorMax.floatValue(), 0, 1));
    }

    @Override
    public boolean isFlancoSubida() {
        return valor.compareTo(valorPrevio) > 0;
    }

    @Override
    public boolean isFlancoBajada() {
        return valor.compareTo(valorPrevio) < 0;
    }

    @Override
    public boolean isCambioValor() {
        return valorPrevio.compareTo(valor) != 0;
    }

    @Override
    public void draw() {
        if (!display) {
            return;
        }
        sketch.noStroke();
        if (orientacion == Orientacion.VERTICAL) {
            // se dibuja el fondo
            sketch.fill(cOff);
            sketch.shape(shape, pos.x, pos.y + hMax, wMax, -hMax);
            // se dibuja la barra con el valor actual
            sketch.fill(c);
            sketch.shape(shape, pos.x, pos.y + hMax, wBar, -hBar);
        } else if (orientacion == Orientacion.HORIZONTAL) {
            // se dibuja el fondo
            sketch.fill(cOff);
            sketch.shape(shape, pos.x, pos.y, wMax, hMax);
            // se dibuja la barra con el color actual
            sketch.fill(c);
            sketch.shape(shape, pos.x, pos.y, wBar, hBar);
        }
        // se grafica el efecto hover
        if (showHover) {
            sketch.fill(cHover);
            sketch.shape(shape, pos.x, pos.y, wMax, hMax);
        }
    }

    public PVector getCentro() {
        return new PVector(pos.x + wMax * 0.5f, pos.y + hMax * 0.5f);
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

    // <editor-fold defaultstate="collapsed" desc="*** setter & getter ***">
    public PApplet getSketch() {
        return sketch;
    }

    public void setSketch(PApplet sketch) {
        this.sketch = sketch;
    }

    @Override
    public String getCodigo() {
        return codigo;
    }

    @Override
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getRutaShape() {
        return rutaShape;
    }

    public void setRutaShape(String rutaShape) {
        this.rutaShape = rutaShape;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(float v) {
        setValor(new BigDecimal(v));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }

    public BigDecimal getValorMax() {
        return valorMax;
    }

    public BigDecimal getValorMin() {
        return valorMin;
    }

    public void setPos(PVector pos) {
        this.pos.set(pos.x, pos.y);
    }

    public void setPos(float x, float y) {
        pos.set(x, y);
    }

    public void setMedidas(float wMax, float hMax) {
        this.wMax = wMax;
        this.hMax = hMax;
    }

    public void setShape(PShape shape) {
        this.shape = shape;
        this.shape.disableStyle();
    }

    public void setColorHover(int cHover) {
        this.cHover = cHover;
    }

    public void setD(BigDecimal dValor) {
        this.dValor = new BigDecimal(dValor.toString());
    }

    public void setModo(BarraModo modo) {
        this.modo = modo;
    }

    @Override
    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    @Override
    public boolean isEditable() {
        return editable;
    }

    public boolean isDisplay() {
        return display;
    }

    @Override
    public void setDisplay(boolean display) {
        this.display = display;
    }

    public int getColorActual() {
        return c;
    }

    public int getColorOff() {
        return cOff;
    }

    public void setColorOff(int cOff) {
        this.cOff = cOff;
    }

    public int getColorFinal() {
        return cFin;
    }

    public void setColorFinal(int cFin) {
        this.cFin = cFin;
    }

    public int getColorInicial() {
        return cIni;
    }

    public void setColorInicial(int cIni) {
        this.cIni = cIni;
        c = cIni;
    }

    public float getAltoMax() {
        return hMax;
    }

    public float getAnchoMax() {
        return wMax;
    }

    public BigDecimal getDeltaValor() {
        return dValor;
    }

    public BigDecimal getValorPrevio() {
        return valorPrevio;
    }

    public Orientacion getOrientacion() {
        return orientacion;
    }

    public void setOrientacion(Orientacion orientacion) {
        this.orientacion = orientacion;
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

    @Override
    public void setShowHover(boolean showHover) {
        this.showHover = showHover;
    }

    public Map<Esquina, PVector> getEsquinas() {
        return esquinas;
    }

    // </editor-fold>

}

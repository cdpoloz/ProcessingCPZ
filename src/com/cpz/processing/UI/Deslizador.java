package com.cpz.processing.UI;

import com.cpz.processing.Interfaces.Identificable;
import com.cpz.processing.Util.Calc;
import com.cpz.processing.Util.Constantes;
import processing.core.PApplet;

import java.math.BigDecimal;

public class Deslizador implements Identificable {

    private PApplet sketch;
    private String codigo;
    private Barra bar;
    private Switch sw;

    public void update() {
        bar.update();
        float x, y;
        if (bar.getOrientacion() == Constantes.Orientacion.VERTICAL) {
            x = bar.getEsquina(Constantes.Esquina.INF_IZQ).x + bar.getAnchoMax() * 0.5f - sw.getAncho() * 0.5f;
            y = bar.getEsquina(Constantes.Esquina.INF_IZQ).y - PApplet.map(bar.getValor().floatValue(), 0, 1, 0, bar.getAltoMax()) - sw.getAlto() * 0.5f;
        } else {
            // *********************************************************************************************************
            // CONTINUAR AQUÍ ******************************************************************************************
            // *********************************************************************************************************
            x = bar.getEsquina(Constantes.Esquina.INF_IZQ).x + bar.getAnchoMax() * 0.5f - sw.getAncho() * 0.5f;
            y = bar.getEsquina(Constantes.Esquina.INF_IZQ).y - PApplet.map(bar.getValor().floatValue(), 0, 1, 0, bar.getAltoMax());
        }
        sw.setPos(x, y);
        sw.update();
        sw.setOn(bar.isHovering() && sketch.mousePressed);
    }

    public void draw() {
        bar.draw();
        sw.draw();
    }

    public void mouseWheel(int d) {
        bar.setValorPorMouse(d);
    }

    public boolean isHovering() {
        return bar.isHovering();
    }

    public void mouseDragged() {
        BigDecimal posMin, posMax;
        BigDecimal valorMin = bar.getValorMin();
        BigDecimal valorMax = bar.getValorMax();
        BigDecimal valor;
        if (bar.getOrientacion() == Constantes.Orientacion.HORIZONTAL) {
            posMin = new BigDecimal(bar.getEsquina(Constantes.Esquina.SUP_IZQ).x);
            posMax = new BigDecimal(bar.getEsquina(Constantes.Esquina.SUP_DER).x);
            valor = Calc.map(new BigDecimal(sketch.mouseX), posMin, posMax, valorMin, valorMax);
        } else {
            posMax = new BigDecimal(bar.getEsquina(Constantes.Esquina.SUP_IZQ).y);
            posMin = new BigDecimal(bar.getEsquina(Constantes.Esquina.INF_IZQ).y);
            valor = Calc.map(new BigDecimal(sketch.mouseY), posMin, posMax, valorMin, valorMax);
        }
        bar.setValor(valor);
    }

    // <editor-fold defaultstate="collapsed" desc="*** setter & getter ***">
    @Override
    public String getCodigo() {
        return codigo;
    }

    @Override
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setBar(Barra bar) {
        this.bar = bar;
    }

    public void setSw(Switch sw) {
        this.sw = sw;
    }

    public void setSketch(PApplet sketch) {
        this.sketch = sketch;
    }
// </editor-fold>
}

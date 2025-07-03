package com.cpz.processing.Bean;

import org.jetbrains.annotations.NotNull;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

import static com.cpz.processing.Util.Constantes.*;

public class Bezier {

    private final PVector pIni, pFin, pCtrl1, pCtrl2;
    private final List<Bezier> curvasAdicionales;
    private final List<Float> longitudCurvas, factoresLongitudCurvas;
    private PApplet sketch;
    private float anchoStroke;
    private float diametroGuia;
    private float longitud, longitudTotal;
    private int colorStroke;
    private boolean dibujarGuia, proxIni, proxFin, proxCtrl1, proxCtrl2;

    public Bezier() {
        pIni = new PVector();
        pFin = new PVector();
        pCtrl1 = new PVector();
        pCtrl2 = new PVector();
        curvasAdicionales = new ArrayList<>();
        longitudCurvas = new ArrayList<>();
        factoresLongitudCurvas = new ArrayList<>();
    }

    public Bezier(@NotNull Bezier bezier) {
        curvasAdicionales = new ArrayList<>();
        sketch = bezier.sketch;
        pIni = new PVector();
        pFin = new PVector();
        pCtrl1 = new PVector();
        pCtrl2 = new PVector();
        pIni.set(bezier.pFin.x, bezier.pFin.y);
        pCtrl1.set(0.367f * sketch.width, 0.367f * sketch.height);
        pCtrl2.set(0.633f * sketch.width, 0.633f * sketch.height);
        pFin.set(0.9f * sketch.width, 0.9f * sketch.height);
        anchoStroke = bezier.anchoStroke;
        diametroGuia = bezier.diametroGuia;
        colorStroke = bezier.colorStroke;
        dibujarGuia = bezier.dibujarGuia;
        longitudCurvas = new ArrayList<>();
        factoresLongitudCurvas = new ArrayList<>();
    }

    public void update() {
        medirLongitud();
        calcularFactoresDeLongitud();
        if (!dibujarGuia) return;
        PVector mouse = new PVector(sketch.mouseX, sketch.mouseY);
        proxIni = PVector.dist(mouse, pIni) < diametroGuia * 0.5f;
        proxCtrl1 = PVector.dist(mouse, pCtrl1) < diametroGuia * 0.5f;
        proxCtrl2 = PVector.dist(mouse, pCtrl2) < diametroGuia * 0.5f;
        proxFin = PVector.dist(mouse, pFin) < diametroGuia * 0.5f;
        curvasAdicionales.forEach(Bezier::update);
    }

    public void draw() {
        dibujarCurvaPrincipal();
        curvasAdicionales.forEach(Bezier::draw);
        if (!dibujarGuia) return;
        dibujarTangentes();
        dibujarPuntosGuia();
    }

    private void dibujarCurvaPrincipal() {
        sketch.strokeWeight(anchoStroke);
        sketch.stroke(colorStroke);
        sketch.noFill();
        sketch.bezier(pIni.x, pIni.y, pCtrl1.x, pCtrl1.y, pCtrl2.x, pCtrl2.y, pFin.x, pFin.y);
    }

    private void dibujarPuntosGuia() {
        dibujarPuntoGuia(proxIni, pIni, false);
        dibujarPuntoGuia(proxCtrl1, pCtrl1, true);
        dibujarPuntoGuia(proxCtrl2, pCtrl2, true);
        dibujarPuntoGuia(proxFin, pFin, false);
    }

    private void dibujarPuntoGuia(boolean proximidad, @NotNull PVector p, boolean ctrl) {
        sketch.stroke(proximidad ? ROJO : ctrl ? AMARILLO : VERDE);
        sketch.strokeWeight(1);
        sketch.ellipse(p.x, p.y, diametroGuia, diametroGuia);
        sketch.strokeWeight(5);
        sketch.point(p.x, p.y);
    }

    private void dibujarTangentes() {
        sketch.stroke(ROJO);
        sketch.strokeWeight(1);
        sketch.line(pIni.x, pIni.y, pCtrl1.x, pCtrl1.y);
        sketch.line(pFin.x, pFin.y, pCtrl2.x, pCtrl2.y);
    }


    public void mouseDragged() {
        // cambio de posición de curva(s)
        modificarPosicionPunto(proxIni, pIni);
        modificarPosicionPunto(proxCtrl1, pCtrl1);
        modificarPosicionPunto(proxCtrl2, pCtrl2);
        modificarPosicionPunto(proxFin, pFin);
        curvasAdicionales.forEach(Bezier::mouseDragged);
    }

    private void modificarPosicionPunto(boolean b, PVector p) {
        if (!b) return;
        p.set(sketch.mouseX, sketch.mouseY);
    }

    public void agregarCurvaAdicional() {
        if (curvasAdicionales.isEmpty()) curvasAdicionales.add(new Bezier(this));
        else curvasAdicionales.add(new Bezier(curvasAdicionales.getLast()));
    }

    public void eliminarCurvaAdicional() {
        if (curvasAdicionales.isEmpty()) return;
        curvasAdicionales.removeLast();
    }

    private void medirLongitud() {
        int muestras = 100;
        longitud = 0;
        longitudTotal = 0;
        float prevX = sketch.bezierPoint(pIni.x, pCtrl1.x, pCtrl2.x, pFin.x, 0);
        float prevY = sketch.bezierPoint(pIni.y, pCtrl1.y, pCtrl2.y, pFin.y, 0);
        for (int i = 1; i <= muestras; i++) {
            float t = i / (float) muestras;
            float x = sketch.bezierPoint(pIni.x, pCtrl1.x, pCtrl2.x, pFin.x, t);
            float y = sketch.bezierPoint(pIni.y, pCtrl1.y, pCtrl2.y, pFin.y, t);
            longitud += PVector.dist(new PVector(prevX, prevY), new PVector(x, y));
            prevX = x;
            prevY = y;
        }
        longitudTotal += longitud;
        for (Bezier bezier : curvasAdicionales) longitudTotal += bezier.getLongitudTotal();
        longitudCurvas.clear();
        longitudCurvas.add(longitud);
        curvasAdicionales.forEach(c -> longitudCurvas.add(c.getLongitudTotal()));
    }

    private void calcularFactoresDeLongitud() {
        factoresLongitudCurvas.clear();
        longitudCurvas.forEach(longitudSegmento -> factoresLongitudCurvas.add(longitudSegmento / longitudTotal));
    }

    public List<Float> getFactoresLongitudCurvas() {
        return factoresLongitudCurvas;
    }

    public List<PVector> calcularPosicionesEnCurva(int pasos) {
        List<PVector> posiciones = new ArrayList<>();
        // posiciones para la primera curva

        // *************************************************************************************************************
        // CONTINUAR AQUÍ **********************************************************************************************
        // *************************************************************************************************************


        return posiciones;
    }

    public void reset() {
        setSketch(sketch);
        curvasAdicionales.clear();
        longitudCurvas.clear();
        factoresLongitudCurvas.clear();
    }

    // <editor-fold defaultstate="collapsed" desc="*** setter & getter ***">
    public void setSketch(@NotNull PApplet sketch) {
        this.sketch = sketch;
        diametroGuia = (float) sketch.height * 40f / 1080f;
        pIni.set(0.1f * sketch.width, 0.1f * sketch.height);
        pCtrl1.set(0.367f * sketch.width, 0.367f * sketch.height);
        pCtrl2.set(0.633f * sketch.width, 0.633f * sketch.height);
        pFin.set(0.9f * sketch.width, 0.9f * sketch.height);
    }

    public void setAnchoStroke(float anchoStroke) {
        this.anchoStroke = anchoStroke;
    }

    public void setColorStroke(int colorStroke) {
        this.colorStroke = colorStroke;
    }

    public boolean isDibujarGuia() {
        return dibujarGuia;
    }

    public void setDibujarGuia(boolean dibujarGuia) {
        this.dibujarGuia = dibujarGuia;
        curvasAdicionales.forEach(cp -> cp.setDibujarGuia(dibujarGuia));
    }

    public int getCantidadCurvas() {
        return curvasAdicionales.size() + 1;
    }

    public float getLongitudTotal() {
        return longitudTotal;
    }
// </editor-fold>
}

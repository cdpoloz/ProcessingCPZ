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

package com.cpz.processing.Util;

import com.cpz.processing.Util.Constantes.ColorComponente;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import processing.core.PApplet;
import processing.core.PVector;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tools {

    @NotNull
    public static String getFecha() {
        return String.format("%02d", PApplet.day())
                + "/"
                + String.format("%02d", PApplet.month())
                + "/"
                + String.format("%02d", PApplet.year());
    }

    @NotNull
    public static String getHora() {
        return String.format("%02d", PApplet.hour())
                + ":"
                + String.format("%02d", PApplet.minute())
                + ":"
                + String.format("%02d", PApplet.second());
    }

    public static boolean isHovering(float mouseX, float mouseY, float x, float y, float w, float h) {
        boolean bx = mouseX > x && mouseX < x + w;
        boolean by = mouseY > y && mouseY < y + h;
        return bx && by;
    }

    public static int construirColor(String s) {
        String[] componentes = s.split(":");
        int r = Integer.parseInt(componentes[0]);
        int g = Integer.parseInt(componentes[1]);
        int b = Integer.parseInt(componentes[2]);
        int a = Integer.parseInt(componentes[3]);
        return construirColor(r, g, b, a);
    }

    public static int construirColor(float red, float green, float blue, float alpha) {
        return ((int) alpha << 24) | ((int) red << 16) | ((int) green << 8) | (int) blue;
    }

    public static int construirColor(float gray) {
        return construirColor(gray, gray, gray, 255);
    }

    public static int construirColor(float gray, float alpha) {
        return construirColor(gray, gray, gray, alpha);
    }

    @NotNull
    public static Map<ColorComponente, Integer> deconstruirColor(int c) {
        Map<ColorComponente, Integer> componentes = new HashMap<>();
        componentes.put(ColorComponente.RED, (c >> 16) & 0xFF);
        componentes.put(ColorComponente.GREEN, (c >> 8) & 0xFF);
        componentes.put(ColorComponente.BLUE, c & 0xFF);
        componentes.put(ColorComponente.ALPHA, (c >> 24) & 0xFF);
        return componentes;
    }

    public static int lerpColor(int c1, int c2, float t) {
        t = Math.max(0, Math.min(1, t));
        int a1 = (c1 >> 24) & 0xFF;
        int r1 = (c1 >> 16) & 0xFF;
        int g1 = (c1 >> 8) & 0xFF;
        int b1 = (c1) & 0xFF;
        int a2 = (c2 >> 24) & 0xFF;
        int r2 = (c2 >> 16) & 0xFF;
        int g2 = (c2 >> 8) & 0xFF;
        int b2 = (c2) & 0xFF;
        int a = (int) (a1 + (a2 - a1) * t);
        int r = (int) (r1 + (r2 - r1) * t);
        int g = (int) (g1 + (g2 - g1) * t);
        int b = (int) (b1 + (b2 - b1) * t);
        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    public static PVector calcularNormal(PVector posIni, PVector posFin) {
        if (posIni == null || posFin == null) {
            return null;
        }
        PVector dir = PVector.sub(posFin, posIni);
        PVector normal = new PVector(-dir.y, dir.x);
        normal.normalize();
        return normal;
    }

    public static List<String> leerArchivo(String ruta) {
        try {
            Path path = Paths.get(ruta);
            return Files.readAllLines(path);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return List.of();
        }
    }

    public static List<PVector> calcularPosicionesEnRecta(PVector pIni, PVector pFin, int pasos) {
        if (pIni == null || pFin == null) return null;
        List<PVector> lstPos = new ArrayList<>();
        for (int i = 0; i < pasos; i++) {
            PVector pos = PVector.lerp(pIni, pFin, (float) i / pasos);
            lstPos.add(new PVector(pos.x, pos.y));
        }
        return lstPos;
    }
}

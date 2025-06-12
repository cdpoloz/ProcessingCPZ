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

package com.cpz.processingUI.Util;

import com.cpz.processingUI.Util.Constantes.ColorComponente;
import java.util.HashMap;
import java.util.Map;
import processing.core.PApplet;

public class Tools {

    public static String getFecha() {
        String s
                = String.format("%02d", PApplet.day())
                + "/"
                + String.format("%02d", PApplet.month())
                + "/"
                + String.format("%02d", PApplet.year());
        return s;
    }

    public static String getHora() {
        String s
                = String.format("%02d", PApplet.hour())
                + ":"
                + String.format("%02d", PApplet.minute())
                + ":"
                + String.format("%02d", PApplet.second());
        return s;
    }

    public static boolean isHovering(float mouseX, float mouseY, float x, float y, float w, float h) {
        boolean bx = mouseX > x && mouseX < x + w;
        boolean by = mouseY > y && mouseY < y + h;
        return bx && by;
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

    public static Map<ColorComponente, Integer> deconstruirColor(int c) {
        Map<ColorComponente, Integer> componentes = new HashMap<>();
        componentes.put(ColorComponente.RED, (c >> 16) & 0xFF);
        componentes.put(ColorComponente.GREEN, (c >> 8) & 0xFF);
        componentes.put(ColorComponente.BLUE, c & 0xFF);
        componentes.put(ColorComponente.ALPHA, (c >> 24) & 0xFF);
        return componentes;
    }
}

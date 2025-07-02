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

package com.cpz.processing.Main;

import java.util.Locale;

import processing.core.PApplet;

public class Launcher extends PApplet {

    public static void main(String[] args) {
        Locale.setDefault(Locale.forLanguageTag("en-US"));
        //String sketchParaEjecutar = "com.cpz.processing.Ejemplos.SimpleUI.SimpleUI";
        String sketchParaEjecutar = "com.cpz.processing.Ejemplos.Fluidos.FluidoSimple";
        PApplet.main(new String[]{sketchParaEjecutar});
        //PApplet.main(new String[]{"--present", sketchParaEjecutar}); // ejecución en pantalla completa
    }

}

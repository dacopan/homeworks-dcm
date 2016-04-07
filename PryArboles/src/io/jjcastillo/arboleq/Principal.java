/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.jjcastillo.arboleq;

import io.jjcastillo.arboleq.helper.ReaderHelper;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author cisco
 */
public class Principal {

    Lista equiposLista;
    Arbol campeon;

    public static void main(String[] args) {
        new Principal().run();
    }

    public Equipo ganador(Equipo eq1, Equipo eq2) {

        while (eq1.getGoles() == eq2.getGoles()) {
            eq1.generarGolesAleatorios();
            eq2.generarGolesAleatorios();
        }
        if (eq1.getGoles() > eq2.getGoles()) {
            return eq1;
        } else {
            return eq2;
        }

    }

    public void run() {
        System.out.println("╔═════════════════════════════╗");
        System.out.println("║   Arbole Campeonato         ║");
        System.out.println("╚═════════════════════════════╝");

        equiposLista = new Lista();

        readEquiposAction();

        generarEncuentrosAleatorios();

        String r = "si";
        while (r.equalsIgnoreCase("si") || r.equalsIgnoreCase("Sí")) {
            int opt = printMenu();
            switch (opt) {
                case 1:
                    printEquiposAction();
                    break;
                case 2:
                    verGolesAction();
                    break;
                case 3:
                    salir();
                    break;
                default:
                    System.out.println("opcion inválida");
                    break;
            }
        }
    }

    private int printMenu() {
        // imprimo menu
        System.out.println("");
        System.out.println("╔═════════════════════════════╗");
        System.out.println("║  MENU VIDEOJUEGOS           ║");
        System.out.println("╠═════════════════════════════╣");
        System.out.println("║ Opción:                     ║");
        System.out.println("║        1. Ver equipos       ║");
        System.out.println("║        2. Buscar goles      ║");
        System.out.println("║        3. Salir             ║");
        System.out.println("╚═════════════════════════════╝");
        System.out.println("");
        //leo opcion del usuario
        return new ReaderHelper().readOpcion("Seleccione opción", 1, 3);

    }

    private void salir() {
        String r = new ReaderHelper().readString("Seguro desea salir?");
        if (r.equalsIgnoreCase("si") || r.equalsIgnoreCase("Sí")) {
            System.out.println("Gracias por utilizar nuestros servicios...");
            System.exit(0);
        }
    }

    private void readEquiposAction() {
        System.out.println("╔═════════════════════════════╗");
        System.out.println("║   Ingreso de Equipos        ║");
        System.out.println("╚═════════════════════════════╝");
        for (int i = 0; i < 8; i++) {
            String eqNombre = new ReaderHelper().readString("Nombre equipo " + (i + 1));
            Equipo e = new Equipo(eqNombre, 0);
            if (equiposLista.contains(e)) {
                System.out.println("ya existe ese equipo.");
                i--;
            } else {
                equiposLista.addLast(e);
            }
        }
    }

    private void generarEncuentrosAleatorios() {
        Random rand = new Random();
        int max = 7, min = 0;
        ArrayList<Integer> tmpIndex = new ArrayList(); //almacena temporalmente los index de los qupios para asegurarse de la aleatoriedad y no se repitan.
        ArrayList<Arbol> encuentros = new ArrayList();

        for (int i = 0; i < 4; i++) {//generar los 4 encuetros aleatoriamente entre los equipos

            int eqIx = rand.nextInt((max - min) + 1) + min;
            while (tmpIndex.contains(eqIx)) {
                eqIx = rand.nextInt((max - min) + 1) + min;
            }
            tmpIndex.add(eqIx);

            Arbol eq1 = new Arbol(equiposLista.get(eqIx));
            //System.out.println("xxxx:" + equiposLista.get(eqIx));

            eqIx = rand.nextInt((max - min) + 1) + min;
            while (tmpIndex.contains(eqIx)) {
                eqIx = rand.nextInt((max - min) + 1) + min;
            }
            tmpIndex.add(eqIx);

            Arbol eq2 = new Arbol(equiposLista.get(eqIx));

            Arbol encuentro = new Arbol();
            encuentro.unirArboles(ganador(eq1.raiz.getDatos(), eq2.raiz.getDatos()), eq1, eq2);
            encuentros.add(encuentro);
            System.out.printf("Encuentro %d: %s(%d)-(%d)%s%n", (i + 1),
                    eq1.raiz.getDatos().getNombre(),
                    eq1.raiz.getDatos().getGoles(),
                    eq2.raiz.getDatos().getGoles(),
                    eq2.raiz.getDatos().getNombre());

        }
        Arbol semifinal1 = new Arbol();
        semifinal1.unirArboles(ganador(encuentros.get(0).raiz.getDatos(), encuentros.get(1).raiz.getDatos()),
                encuentros.get(0), encuentros.get(1));

        System.out.printf("Semifinal %d: %s(%d)-(%d)%s%n", 1, semifinal1.raiz.getIzquierdo().getDatos().getNombre(), semifinal1.raiz.getIzquierdo().getDatos().getGoles(), semifinal1.raiz.getDerecho().getDatos().getGoles(), semifinal1.raiz.getDerecho().getDatos().getNombre());

        Arbol semifinal2 = new Arbol();
        semifinal2.unirArboles(ganador(encuentros.get(2).raiz.getDatos(), encuentros.get(3).raiz.getDatos()),
                encuentros.get(2), encuentros.get(3));

        System.out.printf("Semifinal %d: %s(%d)-(%d)%s%n", 2, semifinal2.raiz.getIzquierdo().getDatos().getNombre(), semifinal2.raiz.getIzquierdo().getDatos().getGoles(), semifinal2.raiz.getDerecho().getDatos().getGoles(), semifinal2.raiz.getDerecho().getDatos().getNombre());

        //
        campeon = new Arbol();
        campeon.unirArboles(ganador(semifinal1.raiz.getDatos(), semifinal2.raiz.getDatos()),
                semifinal1, semifinal2);
        campeon.recorridoNivel();

        System.out.printf("Final: %s(%d)-(%d)%s%n", campeon.raiz.getIzquierdo().getDatos().getNombre(), campeon.raiz.getIzquierdo().getDatos().getGoles(), campeon.raiz.getDerecho().getDatos().getGoles(), campeon.raiz.getDerecho().getDatos().getNombre());
        System.out.printf("Campeón: %s %n", campeon.raiz.getDatos().getNombre());

    }

    private void generarEncuentrosAleatoriosXXXX() {
        // TODO code application logic here

        Arbol eq1 = new Arbol(new Equipo("Liga de quito"));
        Arbol eq2 = new Arbol(new Equipo("Emelec"));

        Arbol encuentro1 = new Arbol();
        encuentro1.unirArboles(ganador(eq1.raiz.getDatos(), eq2.raiz.getDatos()), eq1, eq2);

        Arbol eq3 = new Arbol(new Equipo("Barcelona"));
        Arbol eq4 = new Arbol(new Equipo("Independiente Jose Teran"));

        Arbol encuentro2 = new Arbol();
        encuentro2.unirArboles(ganador(eq3.raiz.getDatos(), eq4.raiz.getDatos()), eq3, eq4);

        Arbol eq5 = new Arbol(new Equipo("Universidad Catolica"));
        Arbol eq6 = new Arbol(new Equipo("Nacional"));

        Arbol encuentro3 = new Arbol();
        encuentro3.unirArboles(ganador(eq5.raiz.getDatos(), eq6.raiz.getDatos()), eq5, eq6);

        Arbol eq7 = new Arbol(new Equipo("Aucas"));
        Arbol eq8 = new Arbol(new Equipo("Deportivo Quito"));

        Arbol encuentro4 = new Arbol();
        encuentro4.unirArboles(ganador(eq7.raiz.getDatos(), eq8.raiz.getDatos()), eq7, eq8);

        encuentro1.raiz.getDatos().generarGolesAleatorios();
        encuentro2.raiz.getDatos().generarGolesAleatorios();
        Arbol semifinal1 = new Arbol();
        semifinal1.unirArboles(ganador(encuentro1.raiz.getDatos(), encuentro2.raiz.getDatos()),
                encuentro1, encuentro2);

        encuentro3.raiz.getDatos().generarGolesAleatorios();
        encuentro4.raiz.getDatos().generarGolesAleatorios();

        Arbol semifinal2 = new Arbol();
        semifinal2.unirArboles(ganador(encuentro3.raiz.getDatos(), encuentro4.raiz.getDatos()),
                encuentro3, encuentro4);

        semifinal1.raiz.getDatos().generarGolesAleatorios();
        semifinal2.raiz.getDatos().generarGolesAleatorios();
        campeon = new Arbol();
        campeon.unirArboles(ganador(semifinal1.raiz.getDatos(), semifinal2.raiz.getDatos()),
                semifinal1, semifinal2);
        campeon.recorridoNivel();

    }

    private void printEquiposAction() {
        equiposLista.print();
    }

    private void verGolesAction() {
        String eqNombre = new ReaderHelper().readString("Nombre equipo");
        AtomicInteger goles = new AtomicInteger(0);
        campeon.goles(eqNombre, Arbol.recorrido.PREORDEN, goles);
        System.out.printf("Total Goles de %s : %d%n", eqNombre, goles.get());

    }

}

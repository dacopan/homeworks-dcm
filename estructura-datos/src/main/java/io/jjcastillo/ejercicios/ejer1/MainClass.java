/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.jjcastillo.ejercicios.ejer1;

import io.jjcastillo.ejercicios.helper.ReaderHelper;

/**
 *
 * @author dacopan
 */
public class MainClass {

    private Lista xboxLista;
    private Lista ps4Lista;

    public static void main(String[] args) {
        new MainClass().run();
    }

    public void run() {
        xboxLista = new Lista();
        ps4Lista = new Lista();

        String r = "si";
        while (r.equalsIgnoreCase("si") || r.equalsIgnoreCase("Sí")) {
            int opt = printMenu();
            switch (opt) {
                case 1:
                    agregarAction();
                    break;
                case 2:
                    printMenuImprimir();
                    break;
                case 3:
                    buscarAction();
                    break;
                case 4:
                    eliminarAction();
                    break;
                case 5:
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
        System.out.println("║        1. Agregar           ║");
        System.out.println("║        2. Imprimir          ║");
        System.out.println("║        3. Buscar            ║");
        System.out.println("║        4. Eliminar          ║");
        System.out.println("║        5. Salir             ║");
        System.out.println("╚═════════════════════════════╝");
        System.out.println("");
        //leo opcion del usuario
        return new ReaderHelper().readOpcion("Seleccione opción", 1, 5);

    }

    private void salir() {
        String r = new ReaderHelper().readString("Seguro desea salir?");
        if (r.equalsIgnoreCase("si") || r.equalsIgnoreCase("Sí")) {
            System.out.println("Gracias por utilizar nuestros servicios...");
            System.exit(0);
        }
    }

    private void agregarAction() {
        System.out.println("Plataforma donde agregar");
        System.out.println(" Opción:                     ");
        System.out.println("        1. " + Plataforma.XBOX);
        System.out.println("        2. " + Plataforma.PS4);

        int plataforma = new ReaderHelper().readOpcion("Seleccione opción", 1, 2);

        String titulo = new ReaderHelper().readString("Titulo videojuego");
        Integer cantidad = new ReaderHelper().readIntPositivo("Cantidad");
        Double valor = new ReaderHelper().readDoublePositivo("Valor");

        VideoJuego v = new VideoJuego(titulo, plataforma == 1 ? Plataforma.XBOX : Plataforma.PS4, cantidad, valor);

        if (plataforma == 1) {
            xboxLista.addLast(v);
        } else {
            ps4Lista.addLast(v);
        }

    }

    private void printMenuImprimir() {
        // imprimo menu
        System.out.println("");
        System.out.println("╔═════════════════════════════╗");
        System.out.println("║   MENU Imprimir             ║");
        System.out.println("╠═════════════════════════════╣");
        System.out.println("║ Opción:                     ║");
        System.out.println("║        1. Lista Completa    ║");
        System.out.println("║        2. Xbox              ║");
        System.out.println("║        3. Ps4               ║");
        System.out.println("║        4. Regresar          ║");
        System.out.println("╚═════════════════════════════╝");
        System.out.println("");
        //leo opcion del usuario
        int opt = new ReaderHelper().readOpcion("Seleccione opción", 1, 4);

        if (opt == 1) {
            printAll();
        } else if (opt == 2) {
            printCustom(Plataforma.XBOX);
        } else if (opt == 3) {
            printCustom(Plataforma.PS4);
        }

    }

    private void printAll() {
        System.out.println("╔═════════════════════════════╗");
        System.out.println("║   Lista completa            ║");
        System.out.println("╚═════════════════════════════╝");
        printCustom(Plataforma.XBOX);
        printCustom(Plataforma.PS4);
    }

    private void printCustom(Plataforma plataforma) {

        if (plataforma.text.equalsIgnoreCase(Plataforma.XBOX.toString())) {
            System.out.println("╔═════════════════════════════╗");
            System.out.println("║   XBOX                      ║");
            System.out.println("╚═════════════════════════════╝");
            System.out.println("");
            xboxLista.printVideojuegos();
        } else {
            System.out.println("╔═════════════════════════════╗");
            System.out.println("║   PS4                       ║");
            System.out.println("╚═════════════════════════════╝");
            System.out.println("");
            ps4Lista.printVideojuegos();

        }
    }

    private void buscarAction() {
        System.out.println("Buscar videojuego");
        String titulo = new ReaderHelper().readString("Titulo videojuego a buscar");
        if (xboxLista.contains(titulo)) {
            System.out.println("Videojuego parte del catalogo de " + Plataforma.XBOX);
        }
        if (ps4Lista.contains(titulo)) {
            System.out.println("Videojuego parte del catalogo de " + Plataforma.PS4);
        }
    }

    private void eliminarAction() {
        System.out.printf("%nVideojuegos eliminados de %10s : %5d%n", Plataforma.XBOX, xboxLista.eliminarDuplicados());
        System.out.printf("Videojuegos eliminados de %10s : %5d%n%n", Plataforma.PS4, ps4Lista.eliminarDuplicados());
    }

}

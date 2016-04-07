/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.jjcastillo.ejercicios.ejer2;

import io.jjcastillo.ejercicios.helper.ReaderHelper;

/**
 *
 * @author dacopan
 */
public class MainClassB {

    private Cola ventanillaA;
    private Cola ventanillaB;
    private boolean isSegundaVentanillaActiva;

    public MainClassB() {
    }

    public static void main(String[] args) {
        new MainClassB().run();
    }

    private void run() {
        ventanillaA = new Cola();
        ventanillaB = new Cola();

        String r = "si";
        while (r.equalsIgnoreCase("si") || r.equalsIgnoreCase("Sí")) {
            int opt = printMenu();
            switch (opt) {
                case 1:
                    agregarAction();
                    break;
                case 2:
                    imprimirAction();
                    break;
                case 3:
                    newVentanillaAction();
                    break;
                case 4:
                    atenderAction();
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
        System.out.println("╔═════════════════════════════════╗");
        System.out.println("║  MENU SRI                       ║");
        System.out.println("╠═════════════════════════════════╣");
        System.out.println("║ Opción:                         ║");
        System.out.println("║        1. Agregar               ║");
        System.out.println("║        2. Imprimir              ║");
        System.out.println("║        3. Habilitar Ventanilla  ║");
        System.out.println("║        4. Atender               ║");
        System.out.println("║        5. Salir                 ║");
        System.out.println("╚═════════════════════════════════╝");
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
        System.out.println("Ingresar Cliente");

        String nombre = new ReaderHelper().readString("Nombre");
        Integer edad = new ReaderHelper().readIntPositivo("Edad");

        System.out.println(" Género:                     ");
        System.out.println("        1. " + Genero.MASCULINO);
        System.out.println("        2. " + Genero.FEMENINO);

        int genero = new ReaderHelper().readOpcion("Seleccione opción", 1, 2);
        Genero gen = genero == 1 ? Genero.MASCULINO : Genero.FEMENINO;

        Double ingresos = new ReaderHelper().readDoublePositivo("Ingresos anuales");
        Double egresos = new ReaderHelper().readDoublePositivo("Egresos anuales");

        Cliente cliente = new Cliente(nombre, edad, gen, ingresos, egresos);
        //TODO calcular impuestos

        double deducible = ingresos - egresos;
        if (deducible < (ingresos / 2)) {
            deducible = (ingresos / 2);
        }
        double exceso = ingresos - deducible;

        double fraccionBasica = 0.0;
        double porcentajeFraccionExcedente = 0.0;
        double impuestoFraccionBasica = 0.0;
        if (exceso < 10800) {
            fraccionBasica = 0;
            impuestoFraccionBasica = 0.0;
            porcentajeFraccionExcedente = 0.0;

        } else if (exceso < 13770) {
            fraccionBasica = 10800;
            impuestoFraccionBasica = 149.0;
            porcentajeFraccionExcedente = 0.05;
        } else {
            fraccionBasica = 13770;
            impuestoFraccionBasica = 493.0;
            porcentajeFraccionExcedente = 0.10;
        }

        double impuestoFraccionExcedente = (exceso - fraccionBasica) * porcentajeFraccionExcedente;
        double impuesto = impuestoFraccionBasica + impuestoFraccionExcedente;
        cliente.setImpuestos(impuesto);
        //insert
        if (ventanillaA.contiene(cliente) || ventanillaB.contiene(cliente)) {

            System.out.println("Cliente ya existe");
        } else {

            if (isSegundaVentanillaActiva && cliente.getGenero().toString().equalsIgnoreCase(Genero.FEMENINO.toString())) {
                ventanillaB.insertar(cliente);
            } else {
                ventanillaA.insertar(cliente);
            }
            System.out.println("Cliente ingresado");
        }

    }

    private void imprimirAction() {

        System.out.println("╔═════════════════════════════╗");
        System.out.println("║   Ventanilla A              ║");
        System.out.println("╚═════════════════════════════╝");
        System.out.println("");
        ventanillaA.printClientes();
        if (isSegundaVentanillaActiva) {
            System.out.println("╔═════════════════════════════╗");
            System.out.println("║   Ventanilla Mujeres        ║");
            System.out.println("╚═════════════════════════════╝");
            System.out.println("");
            ventanillaB.printClientes();
        } else {
            System.out.println("╔═════════════════════════════╗");
            System.out.println("║   Ventanilla Mujeres        ║");
            System.out.println("╚═════════════════════════════╝");
            System.out.println("");
            System.out.println("Deshabilitada\n");
        }

    }

    private void newVentanillaAction() {
        if (isSegundaVentanillaActiva) {
            System.out.println("Ventanilla ya activa");
        } else {
            Cola colaB = ventanillaA.getClientesMujeres();

            System.out.printf("%nClientes movidos de %15s a %s : %5d%n", "Ventanilla A", "Ventanilla Mujeres", colaB.getTamano());
            isSegundaVentanillaActiva = true;

            for (int i = 0; i < colaB.getTamano(); i++) {
                ventanillaB.insertar(colaB.extraer());
            }

        }
    }

    private void atenderAction() {
        System.out.println("Atender a cliente de ventanilla:");
        System.out.println(" Ventanilla:                     ");
        System.out.println("        1. " + "Ventanilla A");
        if (isSegundaVentanillaActiva) {
            System.out.println("        2. " + "Ventanilla Mujeres");
        }

        int ventanilla = new ReaderHelper().readOpcion("Seleccione opción", 1, isSegundaVentanillaActiva ? 2 : 1);

        Cola c = ventanilla == 1 ? ventanillaA : ventanillaB;
        Cliente data = c.extraer();
        System.out.println("Cliente atendido:");
        System.out.printf("%20s %10s %10s %10s %10s %10s%n", "Nombre", "Edad", "Género", "Ingresos", "Egresos", "Impuestos");
        System.out.printf("%20s %10d años %10s %10.2f %10.2f %10.2f%n", data.getNombre(), data.getEdad(), data.getGenero(), data.getTotal_ingresos_anuales(), data.getTotal_egresos_anuales(), data.getImpuestos());

    }
}

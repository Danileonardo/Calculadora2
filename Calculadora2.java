package genc181802.desafio2;

import java.util.Scanner;

public class Calculadora2 {

    public static int suma(int a, int b) {
        return a + b;
    }

    public static int resta(int a, int b) {
        return a - b;
    }

    private static double multiplicacion(double a, double b) {
        return a * b;
    }
    private static double multiplicacion(double a, double b, double c) {
        return a * b * c;
    }

    protected static float division(float a, float b) {
        if (b == 0) {
            System.out.println("No se puede dividir por cero");
            return 0;
        }
        return a / b;
    }

    // funcion para detectar la cantidad de operadores que quiera el usuario
    public static void entradaMatematicaUsuario(){
        try (Scanner entrada = new Scanner(System.in)) {
            System.out.println("Introduce una operacion (ej: 2 + 3 + 4): ");
            String linea = entrada.nextLine();
            String[] partes = linea.split(" "); // separa la operacion por espacios
            if (partes.length < 3) {
                System.out.println("Operacion invalida");
                return;
            }
            char operador = partes[1].charAt(0);
            try {
                switch (operador) {
                    case '+' -> {
                    int resultado = Integer.parseInt(partes[0]);
                        
                    for (int i = 2; i < partes.length; i += 2) {
                        resultado += Integer.parseInt(partes[i]);
                        }
                        
                    System.out.println(resultado);
                    }
                    case '-' -> {
                        int resultado = Integer.parseInt(partes[0]);

                    for (int i = 2; i < partes.length; i += 2) {
                        resultado -= Integer.parseInt(partes[i]);
                        }

                    System.out.println(resultado);
                     }
                    case '*' -> {
                        double resultado = Double.parseDouble(partes[0]);

                    for (int i = 2; i < partes.length; i += 2) {
                        resultado *= Double.parseDouble(partes[i]);
                        }

                      System.out.println(resultado);
                    }
                    case '/' -> {
                        float resultado = Float.parseFloat(partes[0]);

                        for (int i = 2; i < partes.length; i += 2) {
                            resultado = division(resultado, Float.parseFloat(partes[i]));
                        }

                        System.out.println(resultado);
                    }
                        
                    default -> System.out.println("Operador no valido");
                }
            } catch (NumberFormatException e) {
                    System.out.println("Debes ingresar numeros validos");
            }
        }
    }

    public static void main(String[] args) {
        entradaMatematicaUsuario();
    }
}


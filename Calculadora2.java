import java.util.Scanner;

public class Calculadora2 {

    protected static float division(float a, float b) {
        if (b == 0) {
            throw new ArithmeticException("No se puede dividir por cero");
        }
        return a / b;
    }
    
    // nuevo metodo para validar operador
    private static boolean operadorValido(String[] partes, int i, char operador) {
        return partes[i - 1].charAt(0) == operador;
    }

    private static void errorOperadores() {
        System.out.println("Todos los operadores deben ser iguales");
    }

    // funcion para detectar la cantidad de operadores que quiera el usuario
    public static void entradaMatematicaUsuario(){
        try (Scanner entrada = new Scanner(System.in)) {
            System.out.println("Introduce una operacion (ej: 2 + 3 + 4): ");
            String linea = entrada.nextLine();
            String[] partes = linea.split(" "); // separa la operacion por espacios
            // validar que la operacion tenga formato correcto: numero operador numero operador numero
            if (partes.length % 2 == 0) {
                System.out.println("Formato invalido. Usa: numero operador numero (ej: 2 + 3 + 4)");
                return;
            }
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
                             if (!operadorValido(partes, i, operador)) {
                                errorOperadores();
                                return;
                            }
                            
                            resultado += Integer.parseInt(partes[i]);
                        }
                        System.out.println(resultado);
                    }
                    case '-' -> {
                        int resultado = Integer.parseInt(partes[0]);

                        for (int i = 2; i < partes.length; i += 2) {
                            if (!operadorValido(partes, i, operador)) {
                                errorOperadores();
                                return;
                            }
                            
                            resultado -= Integer.parseInt(partes[i]);
                        }
                        System.out.println(resultado);
                    }
                    case '*' -> {
                        double resultado = Double.parseDouble(partes[0]);

                        for (int i = 2; i < partes.length; i += 2) {
                             if (!operadorValido(partes, i, operador)) {
                                errorOperadores();
                                return;
                            }
                            
                            resultado *= Double.parseDouble(partes[i]);
                        }
                        System.out.println(resultado);
                    }
                    case '/' -> {
                        float resultado = Float.parseFloat(partes[0]);

                        for (int i = 2; i < partes.length; i += 2) {
                             if (!operadorValido(partes, i, operador)) {
                                errorOperadores();
                                return;
                            }
                            
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





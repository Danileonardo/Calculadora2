import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Calculadora2 {

    public static void main(String[] args) {
        try (Scanner entrada = new Scanner(System.in)) {
            System.out.println("Introduce una operación (ej: 2+3*4/2 o 2 + 3 * 4 / 2):");
            String linea = entrada.nextLine();

            // Eliminar espacios
            linea = linea.replaceAll("\\s+", "");

            // Separar números y operadores
            List<String> tokens = new ArrayList<>();
            StringBuilder num = new StringBuilder();
            for (char c : linea.toCharArray()) {
                if ("+-*/".indexOf(c) >= 0) {
                    tokens.add(num.toString());
                    tokens.add(String.valueOf(c));
                    num = new StringBuilder();
                } else {
                    num.append(c);
                }
            }
            tokens.add(num.toString());

            // Procesar multiplicación y división primero
            for (int i = 0; i < tokens.size(); i++) {
                String token = tokens.get(i);
                if (token.equals("*") || token.equals("/")) {
                    double a = Double.parseDouble(tokens.get(i - 1));
                    double b = Double.parseDouble(tokens.get(i + 1));
                    double res = token.equals("*") ? a * b : a / b;
                    tokens.set(i - 1, String.valueOf(res));
                    tokens.remove(i); // eliminar operador
                    tokens.remove(i); // eliminar segundo número
                    i--; // retroceder índice
                }
            }

            // Procesar suma y resta
            double resultado = Double.parseDouble(tokens.get(0));
            for (int i = 1; i < tokens.size(); i += 2) {
                String op = tokens.get(i);
                double num2 = Double.parseDouble(tokens.get(i + 1));
                if (op.equals("+")) resultado += num2;
                else if (op.equals("-")) resultado -= num2;
            }

            System.out.printf("Resultado: %.2f\n", resultado);
        }
    }
}
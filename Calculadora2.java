import java.util.Scanner;

public class Calculadora2 {

    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        System.out.println("Introduce una operación (ej: (1+3*3+1-4)/7 o 2+3*4/2):");
        String linea = entrada.nextLine();
        linea = linea.replaceAll("\\s+", ""); // quitar espacios

        try {
            double resultado = evaluar(linea);
            System.out.printf("Resultado: %.2f\n", resultado);
        } catch (Exception e) {
            System.out.println("Error en la operación: " + e.getMessage());
        }
    }

    // Evaluar toda la expresión respetando jerarquía y paréntesis
    private static double evaluar(String expr) {
        return parseSumSub(expr, 0).value;
    }

    // Clase auxiliar para manejar el valor calculado y el índice hasta donde se evaluó
    private static class Result {
        double value;
        int index;
        Result(double value, int index) {
            this.value = value;
            this.index = index;
        }
    }

    // Suma y resta
    private static Result parseSumSub(String expr, int start) {
        Result res = parseMulDiv(expr, start);
        double valor = res.value;
        int i = res.index;

        while (i < expr.length()) {
            char op = expr.charAt(i);
            if (op != '+' && op != '-') break;

            Result next = parseMulDiv(expr, i + 1);
            if (op == '+') valor += next.value;
            else valor -= next.value;
            i = next.index;
        }
        return new Result(valor, i);
    }

    // Multiplicación y división
    private static Result parseMulDiv(String expr, int start) {
        Result res = parseFactor(expr, start);
        double valor = res.value;
        int i = res.index;

        while (i < expr.length()) {
            char op = expr.charAt(i);
            if (op != '*' && op != '/') break;

            Result next = parseFactor(expr, i + 1);
            if (op == '*') valor *= next.value;
            else valor /= next.value;
            i = next.index;
        }
        return new Result(valor, i);
    }

    // Número, decimal, negativo o paréntesis
    private static Result parseFactor(String expr, int start) {
        int i = start;
        double valor = 0;
        boolean negativo = false;

        if (i < expr.length() && expr.charAt(i) == '-') {
            negativo = true;
            i++;
        }

        if (i < expr.length() && expr.charAt(i) == '(') {
            Result r = parseSumSub(expr, i + 1);
            if (r.index >= expr.length() || expr.charAt(r.index) != ')') {
                throw new RuntimeException("Paréntesis desbalanceados");
            }
            valor = r.value;
            i = r.index + 1;
        } else {
            StringBuilder sb = new StringBuilder();
            while (i < expr.length() && (Character.isDigit(expr.charAt(i)) || expr.charAt(i) == '.')) {
                sb.append(expr.charAt(i++));
            }
            if (sb.length() == 0) {
                throw new RuntimeException("Número esperado en posición " + i);
            }
            valor = Double.parseDouble(sb.toString());
        }

        if (negativo) valor = -valor;
        return new Result(valor, i);
    }
}
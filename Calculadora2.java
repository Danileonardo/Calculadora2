import java.util.Scanner; // Importa la clase Scanner para leer datos desde el teclado

public class Calculadora2 { // Declaración de la clase principal llamada Calculadora2

    public static void main(String[] args) { // Método principal donde comienza la ejecución del programa

        Scanner entrada = new Scanner(System.in); // Crea un objeto Scanner para leer lo que el usuario escribe en la consola

        System.out.println("Introduce una operación (ej: (1+3*3+1-4)/7 o 2+3*4/2):"); // Muestra un mensaje solicitando una operación matemática

        String linea = entrada.nextLine(); // Lee toda la línea que el usuario escribe y la guarda en la variable "linea"

        linea = linea.replaceAll("\\s+", ""); // Elimina todos los espacios en blanco de la expresión ingresada

        try { // Inicia un bloque try para capturar posibles errores durante el cálculo

            double resultado = evaluar(linea); // Llama al método evaluar para calcular la expresión matemática

            System.out.printf("Resultado: %.2f\n", resultado); // Imprime el resultado formateado con 2 decimales

        } catch (Exception e) { // Captura cualquier excepción que ocurra durante el cálculo

            System.out.println("Error en la operación: " + e.getMessage()); // Muestra el mensaje de error si ocurre alguno
        }
    }

    // Evaluar toda la expresión respetando jerarquía y paréntesis
    private static double evaluar(String expr) { // Método que recibe la expresión completa como texto

        return parseSumSub(expr, 0).value; // Llama al parser principal empezando desde el índice 0 de la cadena
    }

    // Clase auxiliar para manejar el valor calculado y el índice hasta donde se evaluó
    private static class Result { // Clase interna que permite devolver dos valores al mismo tiempo

        double value; // Guarda el resultado numérico de la operación
        int index; // Guarda la posición actual dentro de la expresión

        Result(double value, int index) { // Constructor de la clase Result

            this.value = value; // Asigna el valor calculado
            this.index = index; // Asigna el índice actual
        }
    }

    // Suma y resta
    private static Result parseSumSub(String expr, int start) { // Método que procesa operadores + y -

        Result res = parseMulDiv(expr, start); // Primero procesa multiplicaciones y divisiones

        double valor = res.value; // Guarda el valor obtenido de esa operación

        int i = res.index; // Guarda la posición donde terminó el cálculo anterior

        while (i < expr.length()) { // Recorre la expresión mientras no llegue al final

            char op = expr.charAt(i); // Obtiene el operador actual en la posición i

            if (op != '+' && op != '-') break; // Si el operador no es + o -, termina el ciclo

            Result next = parseMulDiv(expr, i + 1); // Calcula la siguiente multiplicación o división

            if (op == '+') valor += next.value; // Si el operador es + suma el siguiente valor
            else valor -= next.value; // Si el operador es - resta el siguiente valor

            i = next.index; // Actualiza la posición actual en la expresión
        }

        return new Result(valor, i); // Devuelve el resultado calculado y la posición actual
    }

    // Multiplicación y división
    private static Result parseMulDiv(String expr, int start) { // Método que procesa operadores * y /

        Result res = parseFactor(expr, start); // Primero obtiene el primer número o paréntesis

        double valor = res.value; // Guarda el valor obtenido

        int i = res.index; // Guarda la posición actual en la expresión

        while (i < expr.length()) { // Recorre la expresión

            char op = expr.charAt(i); // Obtiene el operador actual

            if (op != '*' && op != '/') break; // Si no es * o / termina el ciclo

            Result next = parseFactor(expr, i + 1); // Obtiene el siguiente número o factor

            if (op == '*') valor *= next.value; // Si el operador es * multiplica
            else valor /= next.value; // Si el operador es / divide

            i = next.index; // Actualiza la posición actual en la expresión
        }

        return new Result(valor, i); // Devuelve el resultado y la nueva posición
    }

    // Número, decimal, negativo o paréntesis
    private static Result parseFactor(String expr, int start) { // Método que obtiene números, negativos o expresiones entre paréntesis

        int i = start; // Inicia el índice desde la posición indicada

        double valor = 0; // Variable que almacenará el valor numérico

        boolean negativo = false; // Bandera para detectar si el número es negativo

        if (i < expr.length() && expr.charAt(i) == '-') { // Verifica si el número empieza con signo negativo

            negativo = true; // Marca el número como negativo

            i++; // Avanza al siguiente carácter
        }

        if (i < expr.length() && expr.charAt(i) == '(') { // Verifica si comienza una subexpresión entre paréntesis

            Result r = parseSumSub(expr, i + 1); // Evalúa la expresión dentro del paréntesis

            if (r.index >= expr.length() || expr.charAt(r.index) != ')') { // Verifica si el paréntesis se cerró correctamente

                throw new RuntimeException("Paréntesis desbalanceados"); // Lanza un error si los paréntesis no coinciden
            }

            valor = r.value; // Guarda el valor calculado dentro del paréntesis

            i = r.index + 1; // Avanza la posición después del paréntesis de cierre

        } else {

            StringBuilder sb = new StringBuilder(); // Crea un objeto para construir el número carácter por carácter

            while (i < expr.length() && (Character.isDigit(expr.charAt(i)) || expr.charAt(i) == '.')) { // Mientras sea número o punto decimal

                sb.append(expr.charAt(i++)); // Agrega el carácter al número y avanza al siguiente
            }

            if (sb.length() == 0) { // Si no se encontró ningún número

                throw new RuntimeException("Número esperado en posición " + i); // Lanza un error indicando la posición
            }

            valor = Double.parseDouble(sb.toString()); // Convierte el texto del número a tipo double
        }

        if (negativo) valor = -valor; // Si el número era negativo, cambia el signo

        return new Result(valor, i); // Devuelve el valor calculado y la posición actual
    }
}
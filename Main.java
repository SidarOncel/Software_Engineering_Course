import java.io.*;
import java.util.*;

public class Main {

    static Random rand = new Random();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of questions: ");
        int n = sc.nextInt();

        String studentID = "2456346";

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("result.txt"));

            writer.write(studentID + "\n");

            for (int i = 0; i < n; i++) {

                // Generate operators count (3–5)
                int numOperators = rand.nextInt(3) + 3;
                int numNumbers = numOperators + 1;

                ArrayList<Double> numbers = new ArrayList<>();
                ArrayList<String> operators = new ArrayList<>();

                String[] ops = {"+", "-", "*", "/"};

                // Generate numbers
                for (int j = 0; j < numNumbers; j++) {
                    numbers.add((double) (rand.nextInt(101)));
                }

                // Generate operators
                for (int j = 0; j < numOperators; j++) {
                    operators.add(ops[rand.nextInt(ops.length)]);
                }

                // Build expression string
                String expr = "";
                expr += numbers.get(0);

                for (int j = 0; j < operators.size(); j++) {
                    expr += " " + operators.get(j) + " ";
                    expr += numbers.get(j + 1);
                }

                // Calculate result
                double result = evaluate(numbers, operators);

                // Write to file
                writer.write(expr + " = " + result + "\n");
            }

            writer.close();
            System.out.println("Results written to result.txt");

        } catch (IOException e) {
            System.out.println("Error writing file.");
        }

        sc.close();
    }

    // Evaluation with precedence
    public static double evaluate(ArrayList<Double> numbers, ArrayList<String> operators) {

        // First handle * and /
        for (int i = 0; i < operators.size(); i++) {
            if (operators.get(i).equals("*") || operators.get(i).equals("/")) {

                double a = numbers.get(i);
                double b = numbers.get(i + 1);
                double res = 0;

                if (operators.get(i).equals("*")) {
                    res = a * b;
                } else {
                    if (b == 0) b = 1; // avoid division by zero
                    res = a / b;
                }

                numbers.set(i, res);     // replace first number
                numbers.remove(i + 1);   // remove second number
                operators.remove(i);     // remove operator

                i--; // re-check same index
            }
        }

        // Then handle + and -
        for (int i = 0; i < operators.size(); i++) {

            double a = numbers.get(i);
            double b = numbers.get(i + 1);
            double res = 0;

            if (operators.get(i).equals("+")) {
                res = a + b;
            } else {
                res = a - b;
            }

            numbers.set(i, res);
            numbers.remove(i + 1);
            operators.remove(i);

            i--;
        }

        return numbers.get(0);
    }
}
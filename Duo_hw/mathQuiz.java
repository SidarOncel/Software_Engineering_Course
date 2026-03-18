package Duo_hw;

import java.util.*;

public class mathQuiz {

    static Random rand = new Random();

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        String studentID = "2456346";
        String studentID_2 = "2456347";

        System.out.println(studentID + "\n");
        System.out.println(studentID_2 + "\n");

        int count = 0, right = 0;
        long start = 0, finish = 0, time = 0;
        boolean isRunning = true;

        while (isRunning) {

            // 🔹 Generate operators count (3–5)
            int numOperators = rand.nextInt(3) + 3;
            int numNumbers = numOperators + 1;

            ArrayList<Double> numbers = new ArrayList<>();
            ArrayList<String> operators = new ArrayList<>();

            String[] ops = { "+", "-", "*", "/" };

            // 🔹 Generate numbers
            for (int j = 0; j < numNumbers; j++) {
                numbers.add((double) (rand.nextInt(101)));
            }

            // 🔹 Generate operators
            for (int j = 0; j < numOperators; j++) {
                operators.add(ops[rand.nextInt(ops.length)]);
            }

            // 🔹 Build expression string
            String expr = "" + numbers.get(0);
            for (int j = 0; j < operators.size(); j++) {
                expr += " " + operators.get(j) + " " + numbers.get(j + 1);
            }

            // 🔹 Evaluate (IMPORTANT: use copies)
            double result = evaluate(new ArrayList<>(numbers), new ArrayList<>(operators));

            // 🔹 Ask user
            System.out.println(expr + " = ");
            System.out.print("Enter the answer: ");

            start = System.currentTimeMillis();
            String input = sc.nextLine().trim();
            finish = System.currentTimeMillis();

            count++;

            // 🔹 Convert input safely
            double userAnswer;
            try {
                userAnswer = Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.\n");
                continue;
            }

            // 🔹 Compare answers (with tolerance)
            if (Math.abs(result - userAnswer) < 0.0001) {
                right++;
                System.out.println("Correct!\n");
            } else {
                System.out.println("Wrong input. Correct answer is: " + result + "\n");
            }

            // 🔹 Time tracking
            time += (finish - start);

            // 🔹 Every 10 questions → ask to continue
            if (count % 10 == 0) {
                System.out.print("Do you want to continue? (c = continue, x = exit): ");
                String choice = sc.nextLine().trim().toLowerCase();

                if (choice.equals("x")) {
                    isRunning = false;
                }
            }
        }

        // 🔹 Final results
        System.out.println("\n===== RESULT =====");
        System.out.println("Total questions: " + count);
        System.out.println("Correct answers: " + right);
        System.out.println("Total time: " + time + " ms");

        if (count > 0) {
            System.out.println("Average time per question: " + (time / count) + " ms");
        }

        sc.close();
    }

    // 🔹 Evaluation with precedence
    public static double evaluate(ArrayList<Double> numbers, ArrayList<String> operators) {

        // First: * and /
        for (int i = 0; i < operators.size(); i++) {

            if (operators.get(i).equals("*") || operators.get(i).equals("/")) {

                double a = numbers.get(i);
                double b = numbers.get(i + 1);
                double res;

                if (operators.get(i).equals("*")) {
                    res = a * b;
                } else {
                    if (b == 0) {
                        b = rand.nextInt(100) + 1; // avoid division by zero
                    }
                    res = a / b;
                }

                numbers.set(i, res);
                numbers.remove(i + 1);
                operators.remove(i);
                i--;
            }
        }

        // Then: + and -
        for (int i = 0; i < operators.size(); i++) {

            double a = numbers.get(i);
            double b = numbers.get(i + 1);
            double res;

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
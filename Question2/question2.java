package Question2;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class question2 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine().trim());

        Stack<String> stack = new Stack<String>();
        String[] group1 = scanner.nextLine().trim().split(" ");
        for (int i = 0; i < group1.length; i++) {
            stack.push(group1[i]);
        }

        Queue<String> queue = new LinkedList<String>();
        String[] group2 = scanner.nextLine().trim().split(" ");
        for (int i = 0; i < group2.length; i++) {
            queue.add(group2[i]);
        }

        long result = 0;
        boolean hasResult = false;
        String pendingNum = ""; // akumulasi angka dari iterasi yang semua-angka

        while (!queue.isEmpty() || !stack.isEmpty()) {
            String fromStack = stack.isEmpty() ? null : stack.pop();
            String fromQueue = queue.isEmpty() ? null : queue.poll();

            String iterNum  = null; 
            String iterOp   = null; 
            String iterNum2 = null; 

            for (String t : new String[]{fromStack, fromQueue}) {
                if (t == null) continue;
                if (isOperator(t)) {
                    iterOp = t;
                } else if (iterNum == null) {
                    iterNum = t;
                } else {
                    iterNum2 = t;
                }
            }

            if (iterOp == null) {
                // Iterasi ini hanya berisi angka → gabungkan ke pendingNum
                if (iterNum  != null) pendingNum += iterNum;
                if (iterNum2 != null) pendingNum += iterNum2;

            } else {
                if (!pendingNum.isEmpty()) {
                    long leftNum = Long.parseLong(pendingNum);
                    pendingNum = "";
                    if (!hasResult) {
                        result = leftNum;
                        hasResult = true;
                    }
                }

                if (iterNum != null) {
                    long rightNum = Long.parseLong(iterNum);
                    if (!hasResult) {
                        result = rightNum;
                        hasResult = true;
                    } else {
                        result = applyOperation(result, iterOp, rightNum);
                    }
                }
            }
        }

        if (!pendingNum.isEmpty()) {
            result = Long.parseLong(pendingNum);
        }

        System.out.println(result);
        scanner.close();
    }

    static boolean isOperator(String token) {
        return token.equals("+") || token.equals("-")
             || token.equals("*") || token.equals("/");
    }

    static long applyOperation(long a, String op, long b) {
        if (op.equals("+")) return a + b;
        if (op.equals("-")) return a - b;
        if (op.equals("*")) return a * b;
        if (op.equals("/")) return a / b;
        return a;
    }
}
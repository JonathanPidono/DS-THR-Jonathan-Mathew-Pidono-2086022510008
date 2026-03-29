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
        String pendingOp = null;
        String pendingNum = null;

        while (!queue.isEmpty() || !stack.isEmpty()) {
            String fromQueue = queue.isEmpty() ? null : queue.poll();
            String fromStack = stack.isEmpty() ? null : stack.pop();

            String[] pair = {fromQueue, fromStack};
            for (int i = 0; i < pair.length; i++) {
                String token = pair[i];
                if (token == null) continue;

                if (!isOperator(token)) {
                    if (pendingNum != null) {
                        pendingNum = pendingNum + token;
                    } else {
                        pendingNum = token;
                    }
                } else {
                    if (pendingNum != null) {
                        long num = Long.parseLong(pendingNum);
                        if (pendingOp == null) {
                            result = num;
                        } else {
                            result = applyOperation(result, pendingOp, num);
                        }
                        pendingNum = null;
                    }
                    pendingOp = token;
                }
            }
        }

        if (pendingNum != null) {
            long num = Long.parseLong(pendingNum);
            if (pendingOp == null) {
                result = num;
            } else {
                result = applyOperation(result, pendingOp, num);
            }
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
package Question2;

import java.util.Scanner;

public class question2 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        int n = Integer.parseInt(scanner.nextLine().trim());
        ConfusingCalculator calc = new ConfusingCalculator(n * 10 + 10);

        String[] group1 = scanner.nextLine().trim().split("\\s+");
        for (String token : group1) calc.stackPush(token);

        String[] group2 = scanner.nextLine().trim().split("\\s+");
        for (String token : group2) calc.queueEnqueue(token);

        int result = calc.calculate();
        System.out.println(result);

        scanner.close();
    }

    static public class ConfusingCalculator {

        private String[] stackData;
        private int stackTop;

        private String[] queueData;
        private int queueHead;
        private int queueTail;

        public ConfusingCalculator(int capacity) {
            stackData = new String[capacity];
            stackTop  = -1;

            queueData = new String[capacity];
            queueHead = 0;
            queueTail = 0;
        }

        public void stackPush(String value) {
            stackData[++stackTop] = value;
        }

        public String stackPop() {
            if (stackIsEmpty()) return null;
            return stackData[stackTop--];
        }

        public boolean stackIsEmpty() {
            return stackTop == -1;
        }

        public int stackSize() {
            return stackTop + 1;
        }

        public void queueEnqueue(String value) {
            queueData[queueTail++] = value;
        }

        public String queuePoll() {
            if (queueIsEmpty()) return null;
            return queueData[queueHead++];
        }

        public boolean queueIsEmpty() {
            return queueHead >= queueTail;
        }

        public int queueSize() {
            return queueTail - queueHead;
        }

        public boolean isOperator(String token) {
            return token.equals("+") || token.equals("-")
                || token.equals("*") || token.equals("/");
        }

        public int applyOperator(String op, int a, int b) {
            switch (op) {
                case "+": return a + b;
                case "-": return a - b;
                case "*": return a * b;
                case "/": return b == 0 ? 0 : a / b;
                default:  return 0;
            }
        }

        public int calculate() {
            int sSize = stackSize();
            int qSize = queueSize();

            int[] sNums = new int[sSize];
            String[] sOps = new String[sSize];
            int sNumCount = 0, sOpCount = 0;

            String[] stackTokens = new String[sSize];
            for (int i = sSize - 1; i >= 0; i--) {
                stackTokens[i] = stackPop();
            }
            for (String t : stackTokens) {
                if (t == null) continue;
                if (isOperator(t)) sOps[sOpCount++] = t;
                else sNums[sNumCount++] = Integer.parseInt(t);
            }

            int[] qNums = new int[qSize];
            String[] qOps = new String[qSize];
            int qNumCount = 0, qOpCount = 0;

            String[] queueTokens = new String[qSize];
            for (int i = 0; i < qSize; i++) {
                queueTokens[i] = queuePoll();
            }
            for (String t : queueTokens) {
                if (t == null) continue;
                if (isOperator(t)) qOps[qOpCount++] = t;
                else qNums[qNumCount++] = Integer.parseInt(t);
            }

            int stackResult;
            if (sOpCount > 0) {
                int[] ev = new int[sSize + 1];
                int et = -1;
                for (String t : stackTokens) {
                    if (t == null) continue;
                    if (isOperator(t)) {
                        int b = et >= 0 ? ev[et--] : 0;
                        int a = et >= 0 ? ev[et--] : 0;
                        ev[++et] = applyOperator(t, a, b);
                    } else {
                        ev[++et] = Integer.parseInt(t);
                    }
                }
                stackResult = et >= 0 ? ev[et--] : 0;
                while (et >= 0) stackResult = ev[et--] * stackResult;
            } else {
                stackResult = sNumCount > 0 ? sNums[0] : 0;
                int opIdx = 0;
                for (int i = 1; i < sNumCount; i++) {
                    String op = opIdx < qOpCount ? qOps[opIdx++] : "*";
                    stackResult = applyOperator(op, stackResult, sNums[i]);
                }
                for (int i = 0; i < qNumCount; i++) {
                    String op = opIdx < qOpCount ? qOps[opIdx++] : "*";
                    stackResult = applyOperator(op, stackResult, qNums[i]);
                }
                return stackResult;
            }

            int result = stackResult;
            int opIdx = 0;
            for (int i = 0; i < qNumCount; i++) {
                String op = opIdx < qOpCount ? qOps[opIdx++] : "*";
                result = applyOperator(op, result, qNums[i]);
            }
            return result;
        }
    }
}
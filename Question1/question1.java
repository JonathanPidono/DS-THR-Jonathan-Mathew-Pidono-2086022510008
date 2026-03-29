package Question1;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class question1 {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int aurorCount = scanner.nextInt();
        int timeLimit  = scanner.nextInt();

        int[] crossTime = new int[aurorCount];
        int[] aurorId   = new int[aurorCount];

        for (int i = 0; i < aurorCount; i++) {
            crossTime[i] = scanner.nextInt();
            aurorId[i]   = i + 1;
        }

        Bridge bridge = new Bridge();
        bridge.solve(aurorCount, timeLimit, crossTime, aurorId);
    }

    static public class Bridge {

        public void solve(int aurorCount, int timeLimit, int[] crossTime, int[] aurorId) {

            for (int i = 0; i < aurorCount; i++) {
                for (int j = i + 1; j < aurorCount; j++) {
                    if (crossTime[i] > crossTime[j]) {
                        int tempTime = crossTime[i]; crossTime[i] = crossTime[j]; crossTime[j] = tempTime;
                        int tempId   = aurorId[i];   aurorId[i]   = aurorId[j];   aurorId[j]   = tempId;
                    }
                }
            }

            int totalTime                = 0;
            Queue<String> actionLog      = new ArrayDeque<>();
            boolean[] crossedToRight     = new boolean[aurorCount];
            int leftCount                = aurorCount;
            boolean acromantulaCaught    = false;

            while (leftCount > 3 && !acromantulaCaught) {
                int costStrategyA = crossTime[0] + crossTime[0] + crossTime[leftCount - 2] + crossTime[leftCount - 1];
                int costStrategyB = crossTime[1] + crossTime[0] + crossTime[leftCount - 1] + crossTime[1];

                if (costStrategyB <= costStrategyA) {
                    actionLog.add(aurorId[0] + " " + aurorId[1] + " ->");
                    totalTime += crossTime[1];
                    crossedToRight[0] = true; crossedToRight[1] = true;

                    if (totalTime >= timeLimit) { acromantulaCaught = true; break; }
                    actionLog.add(aurorId[0] + " <-");
                    totalTime += crossTime[0];
                    crossedToRight[0] = false;

                    actionLog.add(aurorId[leftCount - 2] + " " + aurorId[leftCount - 1] + " ->");
                    totalTime += crossTime[leftCount - 1];
                    crossedToRight[leftCount - 2] = true; crossedToRight[leftCount - 1] = true;

                    if (totalTime >= timeLimit) { acromantulaCaught = true; break; }
                    actionLog.add(aurorId[1] + " <-");
                    totalTime += crossTime[1];
                    crossedToRight[1] = false;

                } else {
                    actionLog.add(aurorId[0] + " " + aurorId[leftCount - 1] + " ->");
                    totalTime += crossTime[leftCount - 1];
                    crossedToRight[0] = true; crossedToRight[leftCount - 1] = true;

                    if (totalTime >= timeLimit) { acromantulaCaught = true; break; }
                    actionLog.add(aurorId[0] + " <-");
                    totalTime += crossTime[0];
                    crossedToRight[0] = false;

                    actionLog.add(aurorId[0] + " " + aurorId[leftCount - 2] + " ->");
                    totalTime += crossTime[leftCount - 2];
                    crossedToRight[0] = true; crossedToRight[leftCount - 2] = true;

                    if (totalTime >= timeLimit) { acromantulaCaught = true; break; }
                    actionLog.add(aurorId[0] + " <-");
                    totalTime += crossTime[0];
                    crossedToRight[0] = false;
                }
                leftCount -= 2;
            }

            if (!acromantulaCaught) {
                if (leftCount == 3) {
                    actionLog.add(aurorId[0] + " " + aurorId[1] + " ->");
                    totalTime += crossTime[1];
                    crossedToRight[0] = true; crossedToRight[1] = true;

                    if (totalTime < timeLimit) {
                        actionLog.add(aurorId[0] + " <-");
                        totalTime += crossTime[0];
                        crossedToRight[0] = false;

                        actionLog.add(aurorId[0] + " " + aurorId[2] + " ->");
                        totalTime += crossTime[2];
                        crossedToRight[0] = true; crossedToRight[2] = true;
                    } else {
                        acromantulaCaught = true;
                    }

                } else if (leftCount == 2) {
                    actionLog.add(aurorId[0] + " " + aurorId[1] + " ->");
                    totalTime += crossTime[1];
                    crossedToRight[0] = true; crossedToRight[1] = true;
                    if (totalTime >= timeLimit) acromantulaCaught = true;

                } else if (leftCount == 1) {
                    actionLog.add(aurorId[0] + " ->");
                    totalTime += crossTime[0];
                    crossedToRight[0] = true;
                    if (totalTime >= timeLimit) acromantulaCaught = true;
                }
            }

            while (!actionLog.isEmpty()) {
                System.out.println(actionLog.poll());
            }

            Stack<Integer> nonSurvivors = new Stack<>();
            for (int i = aurorCount - 1; i >= 0; i--) {
                if (!crossedToRight[i]) {
                    nonSurvivors.push(aurorId[i]);
                }
            }

            if (!nonSurvivors.isEmpty()) {
                System.out.print("Non-survivors: [");
                boolean isFirst = true;
                while (!nonSurvivors.isEmpty()) {
                    if (!isFirst) System.out.print(",");
                    System.out.print(nonSurvivors.pop());
                    isFirst = false;
                }
                System.out.println("]");
            }
        }
    }
}
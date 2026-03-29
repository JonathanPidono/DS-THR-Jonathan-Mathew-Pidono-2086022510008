package Question3;

import java.util.Scanner;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.ArrayList;

public class question3 {

    static class Borrower {
        String name;
        int requestedKey;
        int priority;
        int originalIndex;

        Borrower(String name, int requestedKey, int priority, int originalIndex) {
            this.name = name;
            this.requestedKey = requestedKey;
            this.priority = priority;
            this.originalIndex = originalIndex;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();

        Queue<Integer> keyQueue = new LinkedList<>();
        ArrayList<Integer> availableKeys = new ArrayList<Integer>();
        for (int i = 0; i < n; i++) {
            int k = scanner.nextInt();
            keyQueue.add(k);
            availableKeys.add(k);
        }

        String[] names = new String[n];
        int[] requestedKeys = new int[n];
        for (int i = 0; i < n; i++) {
            names[i] = scanner.next();
            requestedKeys[i] = scanner.nextInt();
        }

        int[] priorities = new int[n];
        for (int i = 0; i < n; i++) {
            priorities[i] = scanner.nextInt();
        }

        ArrayList<Borrower> borrowerList = new ArrayList<Borrower>();
        for (int i = 0; i < n; i++) {
            borrowerList.add(new Borrower(names[i], requestedKeys[i], priorities[i], i));
        }

        ArrayList<Borrower> validBorrowers = new ArrayList<Borrower>();
        for (int i = 0; i < borrowerList.size(); i++) {
            if (availableKeys.contains(borrowerList.get(i).requestedKey)) {
                validBorrowers.add(borrowerList.get(i));
            }
        }

        int[] firstIndexOfKey = new int[1001];
        for (int i = 0; i < firstIndexOfKey.length; i++) {
            firstIndexOfKey[i] = Integer.MAX_VALUE;
        }
        for (int i = 0; i < borrowerList.size(); i++) {
            int key = borrowerList.get(i).requestedKey;
            if (i < firstIndexOfKey[key]) {
                firstIndexOfKey[key] = i;
            }
        }

        int vSize = validBorrowers.size();
        for (int i = 0; i < vSize - 1; i++) {
            for (int j = 0; j < vSize - 1 - i; j++) {
                Borrower a = validBorrowers.get(j);
                Borrower b = validBorrowers.get(j + 1);
                int aKeyOrder = firstIndexOfKey[a.requestedKey];
                int bKeyOrder = firstIndexOfKey[b.requestedKey];
                boolean shouldSwap = false;
                if (aKeyOrder > bKeyOrder) {
                    shouldSwap = true;
                } else if (aKeyOrder == bKeyOrder && a.priority > b.priority) {
                    shouldSwap = true;
                }
                if (shouldSwap) {
                    validBorrowers.set(j, b);
                    validBorrowers.set(j + 1, a);
                }
            }
        }

        Queue<Borrower> borrowerQueue = new LinkedList<Borrower>();
        for (int i = 0; i < validBorrowers.size(); i++) {
            borrowerQueue.add(validBorrowers.get(i));
        }

        Stack<Borrower> resultStack = new Stack<Borrower>();
        while (!borrowerQueue.isEmpty()) {
            resultStack.push(borrowerQueue.poll());
        }

        Stack<Borrower> displayStack = new Stack<Borrower>();
        while (!resultStack.isEmpty()) {
            displayStack.push(resultStack.pop());
        }

        while (!displayStack.isEmpty()) {
            Borrower b = displayStack.pop();
            System.out.println(b.name + " | " + b.requestedKey);
        }

        scanner.close();
    }
}
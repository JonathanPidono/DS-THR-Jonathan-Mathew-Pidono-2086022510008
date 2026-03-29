package Question4;

import java.util.Scanner;
import java.util.Stack;

public class question4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String input = scanner.nextLine();

        CardPile pile = new CardPile();
        pile.processCards(input);
        pile.displayStacks();
        
        scanner.close();
    }

    static public class CardPile {
        private Stack<Stack<String>> allCardStacks;

        public CardPile() {
            allCardStacks = new Stack<>();
        }

        private boolean isCardInStack(Stack<String> stack, String card) {
            Stack<String> tempStack = new Stack<>();
            boolean found = false;
            while (!stack.isEmpty()) {
                String top = stack.pop();
                tempStack.push(top);
                if (top.equals(card)) found = true;
            }
            while (!tempStack.isEmpty()) stack.push(tempStack.pop());
            return found;
        }

        private Stack<String> findOldestStackWithout(String card) {
            Stack<Stack<String>> tempStack = new Stack<>();
            Stack<String> result = null;

            while (!allCardStacks.isEmpty()) tempStack.push(allCardStacks.pop());

            while (!tempStack.isEmpty()) {
                Stack<String> s = tempStack.pop();
                allCardStacks.push(s);
                if (!isCardInStack(s, card) && result == null) result = s;
            }

            return result;
        }

        public void processCards(String input) {
            String[] cardTokens = input.trim().split("\\s+");

            for (String card : cardTokens) {
                Stack<String> target = findOldestStackWithout(card);
                if (target == null) {
                    Stack<String> newCardStack = new Stack<>();
                    newCardStack.push(card);
                    allCardStacks.push(newCardStack);
                } else {
                    target.push(card);
                }
            }
        }

        public void displayStacks() {
            Stack<Stack<String>> tempStack = new Stack<>();
            while (!allCardStacks.isEmpty()) tempStack.push(allCardStacks.pop());

            while (!tempStack.isEmpty()) {
                Stack<String> s = tempStack.pop();
                allCardStacks.push(s);

                Stack<String> rev = new Stack<>();
                while (!s.isEmpty()) rev.push(s.pop());

                boolean first = true;
                while (!rev.isEmpty()) {
                    String card = rev.pop();
                    s.push(card);
                    if (!first) System.out.print(" ");
                    System.out.print(card);
                    first = false;
                }
                System.out.println();
            }
        }
    }
}
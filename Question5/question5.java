package Question5;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class question5 {

    static Stack<Card> gameStack;
    static List<Card>[] playerHands;
    static int currentPlayer;
    static int totalPlayers = 4;
    static int lastPlayerWhoMoved;
    static boolean isFirstMove;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        playerHands = new ArrayList[totalPlayers];

        for (int i = 0; i < totalPlayers; i++) {
            playerHands[i] = new ArrayList<Card>();
            String input = scanner.nextLine();
            String[] cards = input.split(" ");

            for (int j = 0; j < cards.length; j++) {
                String[] parts = cards[j].split(",");
                int value = Integer.parseInt(parts[0]);
                int category = Integer.parseInt(parts[1]);
                playerHands[i].add(new Card(value, category));
            }
        }

        currentPlayer = scanner.nextInt() - 1;
        scanner.close();

        gameStack = new Stack<Card>();
        lastPlayerWhoMoved = -1;
        isFirstMove = true;

        int winner = -1;

        while (winner == -1) {
            if (playerHands[currentPlayer].size() == 0) {
                winner = currentPlayer;
                break;
            }

            boolean playerMoved = false;

            if (isFirstMove || lastPlayerWhoMoved == currentPlayer) {
                Card cardToPlay = findSmallestCardLowestCategory(playerHands[currentPlayer]);
                gameStack.push(cardToPlay);
                playerHands[currentPlayer].remove(cardToPlay);
                playerMoved = true;
                lastPlayerWhoMoved = currentPlayer;
                isFirstMove = false;
            } else {
                Card topCard = gameStack.peek();
                Card validCard = findValidCard(playerHands[currentPlayer], topCard.category, topCard.value);

                if (validCard != null) {
                    gameStack.push(validCard);
                    playerHands[currentPlayer].remove(validCard);
                    playerMoved = true;
                    lastPlayerWhoMoved = currentPlayer;
                }
            }

            if (playerMoved) {
                if (playerHands[currentPlayer].size() == 0) {
                    winner = currentPlayer;
                    break;
                }
                if (checkAllOtherPlayersPass(currentPlayer)) {
                    lastPlayerWhoMoved = currentPlayer;
                }
            }

            currentPlayer = (currentPlayer + 1) % totalPlayers;
        }

        System.out.println(winner + 1);

        while (!gameStack.isEmpty()) {
            System.out.println(gameStack.pop());
        }
    }

    static Card findSmallestCardLowestCategory(List<Card> hand) {
        Card result = null;

        for (int i = 1; i <= 4; i++) {
            for (int j = 0; j < hand.size(); j++) {
                if (hand.get(j).category == i) {
                    if (result == null || hand.get(j).value < result.value) {
                        result = hand.get(j);
                    }
                }
            }

            if (result != null) {
                break;
            }
        }

        return result;
    }

    static Card findValidCard(List<Card> hand, int requiredCategory, int requiredValue) {
        Card result = null;

        for (int i = 0; i < hand.size(); i++) {
            Card card = hand.get(i);

            if (card.category == requiredCategory && card.value > requiredValue) {
                if (result == null || card.value < result.value) {
                    result = card;
                }
            }
        }

        return result;
    }

    static boolean checkAllOtherPlayersPass(int currentPlayerIndex) {
        Card topCard = gameStack.peek();

        for (int i = 0; i < totalPlayers; i++) {
            if (i != currentPlayerIndex) {
                if (playerHands[i].size() > 0) {
                    Card validCard = findValidCard(playerHands[i], topCard.category, topCard.value);
                    if (validCard != null) {
                        return false;
                    }
                }
            }
        }

        return true;
    }
}

class Card {
    int value;
    int category;

    public Card(int value, int category) {
        this.value = value;
        this.category = category;
    }

    public String toString() {
        return value + "," + category;
    }
}
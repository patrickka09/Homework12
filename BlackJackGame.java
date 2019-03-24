
package edu.dmacc.codedsm.hw12;



import java.io.FileNotFoundException;

import java.util.*;

import java.io.PrintWriter;



public class BlackJackGame {



    public static int sumOfPlayerCards = 0;

    public static int sumOfDealerCards = 0;

    public static List<Card> chosenFromDeck;

    public static boolean done = false;



    public static void main(String[] args) {



        Map<String, List<Integer>> deck = createDeck();

        List<Card> playerIsHandCards = new ArrayList<>();

        List<Card> dealerIsHandCards = new ArrayList<>();

        initialGame(deck, playerIsHandCards);

        initialGame(deck, dealerIsHandCards);

        playerIsHand(playerIsHandCards);

        dealerIsOneCard(dealerIsHandCards);

        sumOfPlayerCards = totalValuesOfCardsInHand(playerIsHandCards, sumOfPlayerCards);

        sumOfDealerCards = totalValuesOfCardsInHand(dealerIsHandCards, sumOfDealerCards);

        playGame(deck, sumOfPlayerCards, sumOfDealerCards, playerIsHandCards, dealerIsHandCards);

    }



    public static void playGame(Map<String, List<Integer>> deck, int totalPlayerCardsValue, int totalDealerCardsValue, List<Card> playerIsHandCards, List<Card> dealerIsHandCards) {

        while (!done) {

            Scanner in = new Scanner(System.in);

            System.out.print("Enter 1 to hit or 2 to stand : ");

            if (in.hasNextInt()) {

                int input = in.nextInt();

                if (input < 1 || input > 2) {

                    System.out.println("Invalid in put");

                    done = false;

                } else if (input == 1) {

                    if (totalPlayerCardsValue <= 21) {

                        totalPlayerCardsValue = hit(deck, playerIsHandCards, totalPlayerCardsValue);

                        playerIsHand(playerIsHandCards);

                    }

                    if (totalPlayerCardsValue > 21) {

                        playerIsHand(playerIsHandCards);

                        System.out.println(String.format("Player\'s hand was %d", totalPlayerCardsValue));

                        showTheWinner(playerIsHandCards, dealerIsHandCards, totalPlayerCardsValue, totalDealerCardsValue);

                        System.exit(0);

                    }

                    done = false;

                } else if (input == 2) {

                    stand(playerIsHandCards, totalPlayerCardsValue);

                    while (totalDealerCardsValue <= 16) {

                        totalDealerCardsValue = hit(deck, dealerIsHandCards, totalDealerCardsValue);

                        dealerIsHand(dealerIsHandCards);

                        System.out.println(String.format("Dealer\'s hand was %d", totalDealerCardsValue));

                    }

                    if (totalDealerCardsValue >= 17) {

                        showTheWinner(playerIsHandCards, dealerIsHandCards, totalPlayerCardsValue, totalDealerCardsValue);

                    }

                    done = true;

                }

            } else {

                System.out.println("Invalid in put");

            }

        }

    }



    public static int hit(Map<String, List<Integer>> deck, List<Card> myHand, int sumOfCards) {

        chosenFromDeck = DeckRandomizer.chooseRandomCards(deck, 1);

        myHand.add(chosenFromDeck.get(0));

        deck.get(chosenFromDeck.get(0).suit).remove(chosenFromDeck.get(0).value);

        return totalValuesOfCardsInHand(chosenFromDeck, sumOfCards);

    }



    public static void stand(List<Card> cardInHand, int sumOfCards) {

        playerIsHand(cardInHand);

        System.out.println(String.format("Player\'s hand was %d", sumOfCards));

    }



    public static void initialGame(Map<String, List<Integer>> deck, List<Card> myHand) {

        chosenFromDeck = DeckRandomizer.chooseRandomCards(deck, 2);

        for (int i = 0; i < chosenFromDeck.size(); i++) {

            myHand.add(chosenFromDeck.get(i));

            deck.get(chosenFromDeck.get(i).suit).remove(chosenFromDeck.get(i).value);

        }

    }



    public static int totalValuesOfCardsInHand(List<Card> cards, int sumOfCards) {

        int score;

        for (int i = 0; i < cards.size(); i++) {

            if (cards.get(i).value >= 11 && cards.get(i).value <= 13) {

                score = 10;

            } else {

                score = cards.get(i).value;

            }

            sumOfCards += score;

        }

        return sumOfCards;

    }



    public static void playerIsHand(List<Card> card) {

        System.out.print("Player sees the hand :  \n");

        for (int i = 0; i < card.size(); i++) {

            System.out.print(card.get(i).suit + " - " + card.get(i).value);

            if (i + 1 < card.size()) {

                System.out.print(", ");

            }

        }

        System.out.println(" ");

    }



    public static void dealerIsHand(List<Card> card) {

        System.out.print("dealer 's cards in hand are :  \n");

        for (int i = 0; i < card.size(); i++) {

            System.out.print(card.get(i).suit + " - " + card.get(i).value);

            if (i + 1 < card.size()) {

                System.out.print(", ");

            }

        }

        System.out.println(" ");

    }



    public static void dealerIsOneCard(List<Card> card) {

        System.out.print("the player sees the first card of the dealer :  \n");

        System.out.println(card.get(0).suit + " - " + card.get(0).value);



    }



    public static String playerOrDealerLastHand(List<Card> card) {

        String message = "";

        for (int i = 0; i < card.size(); i++) {

            message += card.get(i).suit + " - " + card.get(i).value + ", ";

        }

        message = message.substring(0, message.length() - 2);

        return message;

    }



    public static String determineTheWinner(int scoreForThePlayer, int scoreForTheDealer) {

        System.out.println(String.format("Player\'s hand was %d", scoreForThePlayer));

        System.out.println(String.format("Dealer\'s hand was %d", scoreForTheDealer));

        String winner;

        if (scoreForThePlayer > 21) {

            winner = "Dealer";

        } else if (scoreForTheDealer == 21) {

            winner = "Dealer";

        } else if (scoreForThePlayer <= 21 && scoreForTheDealer > 21) {

            winner = "Player";

        } else if (scoreForThePlayer <= 21 && scoreForThePlayer == scoreForTheDealer) {

            winner = "the game is tie";

        } else {

            if (scoreForThePlayer > scoreForTheDealer) {

                winner = "Player";

            } else {

                winner = "Dealer";

            }

        }

        return winner;

    }



    public static void showTheWinner(List<Card> player, List<Card> dealer, int playerCardValue, int dealerCardValue) {

        dealerIsHand(dealer);

        String winner = determineTheWinner(playerCardValue, dealerCardValue);

        if (winner.equals("Player")) {

            System.out.println(String.format("%s wins!", winner));

        } else if (winner.equals("Dealer")) {

            System.out.println(String.format("%s wins!", winner));

        } else {

            System.out.println("It\\'s a tie!");

        }

        String dealerHand = playerOrDealerLastHand(dealer);

        String playerHand = playerOrDealerLastHand(player);

        winningMessageToAFile(playerHand, Integer.toString(playerCardValue), dealerHand, Integer.toString(dealerCardValue), winner);

    }



    public static void winningMessageToAFile(String outputOne, String outputTwo, String outputThree, String outputFour, String outputFive) {

        try {

            PrintWriter outFile = new PrintWriter("blackjack_log.txt");

            outFile.println("Player's hand is : " + outputOne + " and the sum is : " + outputTwo);

            outFile.println("Dealer's hand is : " + outputThree + " and the sum is : " + outputFour);

            outFile.println("The winner is : " + outputFive);

            outFile.close();



        } catch (FileNotFoundException e) {

            e.printStackTrace();

        }

    }



    private static Map<String, List<Integer>> createDeck() {

        Map<String, List<Integer>> deck = new HashMap<>();

        deck.put("Clubs", createCards());

        deck.put("Diamonds", createCards());

        deck.put("Spades", createCards());

        deck.put("Hearts", createCards());

        return deck;

    }



    private static List<Integer> createCards() {

        List<Integer> cards = new ArrayList<>();

        for (int i = 1; i < 14; i++) {

            cards.add(i);

        }

        return cards;

    }

}
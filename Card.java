
package edu.dmacc.codedsm.hw12;





public class Card {



    public String suit;

    public Integer value;



    public Card(String suit, Integer value) {

        this.suit = suit;

        this.value = value;

    }



    @Override

    public String toString() {

        return "Card{" +

                "suit='" + suit + '\'' +

                ", value=" + value +

                '}';

    }





}
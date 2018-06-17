package kr.ac.cnu.web.games.blackjack;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rokim on 2018. 5. 26..
 */
public class
Hand {
    private Deck deck;
    @Getter
    private List<Card> cardList = new ArrayList<>();

    public Hand(Deck deck) {
        this.deck = deck;
    }

    public Card drawCard() {
        Card card = deck.drawCard();
        cardList.add(card);
        return card;
    }

    public int getCardSum() {
        int valueOfCard=0, sum=cardList.stream().mapToInt(card -> card.getRank()).sum();
        Card[] card_List = cardList.stream().toArray(Card[]::new);//카드 리스트를 배열로 생성

        for(int i=0; i<card_List.length; i++){  // 에이스를 1 또는 11중에 유리한방향으로 선택하는 코드
            valueOfCard = card_List[i].getRank();
            if(valueOfCard == 1){
                if(sum<12) {
                    sum += 10;
                }
            }
        }
        return sum;
    }

    public void reset() {
        cardList.clear();
    }

    public int getCardCount(){
        return cardList.size(); // 현재 카드 갯수를 반환
    }
}

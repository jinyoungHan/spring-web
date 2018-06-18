package kr.ac.cnu.web.games.blackjack;

import kr.ac.cnu.web.exceptions.NotEnoughBalanceException;
import lombok.Getter;
import org.aspectj.weaver.ast.Not;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by rokim on 2018. 5. 26..
 */
public class Player {
    @Getter
    private long balance;
    @Getter
    private long currentBet;
    @Getter
    private boolean isPlaying;
    @Getter
    private Hand hand;

    public Player(long seedMoney, Hand hand) {
        this.balance = seedMoney;
        this.hand = hand;

        isPlaying = false;
    }

    public void reset() {
        hand.reset();
        isPlaying = false;
    }

    public void placeBet(long bet) {
        if(balance < 0) throw new NotEnoughBalanceException();
        if(balance > 1000 && balance < bet) {
            throw new NotEnoughBalanceException();
        }
        if(currentBet != bet) {
            bet -= currentBet;
            balance -= bet;
            bet += currentBet;
        }
        currentBet = bet;

        isPlaying = true;
    }

    public void deal() {
        hand.drawCard();
        hand.drawCard();
    }

    public void win() {
        balance += currentBet * 2;
        balance -= currentBet;
        if(currentBet < 1000){            // 현재 가진 금액이 1000원 이하일경우 all-in
            currentBet = balance;
            balance = 0;
        }
        // currentBet = 0 을 삭제, default
    }

    public void blackjack_win() {       // blackjack일 경우 2.5배.
        balance += currentBet * 2.5;
        balance -= currentBet;
        if(currentBet < 1000){           // 현재 가진 금액이 1000원 이하일경우 all-in
            currentBet = balance;
            balance = 0;
        }
        // currentBet = 0 을 삭제, default
    }

    public void tie() {
        balance += currentBet;
        if(currentBet < 1000){           // 현재 가진 금액이 1000원 이하일경우 all-in
            currentBet = balance;
            balance = 0;
        }
        // currentBet = 0 을 삭제, default
    }

    public void lost() {
        balance -= currentBet;
        if(currentBet < 1000){           // 현재 가진 금액이 1000원 이하일경우 all-in
            currentBet = balance;
            balance = 0;
        }
        // currentBet = 0 을 삭제, default
    }

    public Card hitCard() {
        return hand.drawCard();
    }

    public void stand() {
        this.isPlaying = false;
    }
}

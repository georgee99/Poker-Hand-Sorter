import java.io.BufferedReader;
import java.util.Arrays;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class PokerMain {
	public static int player1WinCount = 0;
	public static int player2WinCount = 0;

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String currentLine;
		File file = new File("poker-hands.txt");
		
		BufferedReader br = new BufferedReader(new FileReader(file));
		
		while((currentLine = br.readLine()) != null) {

			String pokerInput = currentLine;
		    
		    String[] splited = pokerInput.split("\\s+");
		    String[] firstHand = Arrays.copyOfRange(splited, 0, 5);
		    String[] secondHand = Arrays.copyOfRange(splited, 5, 10);
		    
		    Hand player1 = new Hand(firstHand, "player1");
		    Hand player2 = new Hand(secondHand, "player2");
		    new Hand().calculateWinningHand(player1, player2);
		}
		System.out.println("Player 1: " + player1WinCount + " hands");
		System.out.println("Player 2: " + player2WinCount + " hands");
	}
	
//	Hand Class starts here
	
	private static class Hand {
		
		private String[] cards;
		private String player;
		
		public Hand() {
			cards = null;
			player = "";
		}
		
		public Hand(String[] hand, String player) {
			this.player = player;

			Arrays.sort(hand);
			this.cards = hand;
//			System.out.println(Arrays.toString(hand));
		}
		
		public String[] getCards() {
			return this.cards;
		}
		
		private int getRank() {
			String[] tempCard = this.getCards();
			if (isRoyalFlush(tempCard)) {
				return 10;
			} else if (isStraightFlush(tempCard)) {
				return 9;
			} else if (isFourOfAKind(tempCard)) {
				return 8;
			} else if (isFullHouse(tempCard)) {
				return 7;
			} else if (isFlush(tempCard)) {
				return 6;
			} else if (isStraight(tempCard)) {
				return 5;
			} else if (isThreeOfAKind(tempCard)) {
				return 4;
			} else if (isTwoPairs(tempCard)) {
				return 3;
			} else if (isAPair(tempCard)) {
				return 2;
			} else {
				return 1;
			}			
		}
		
		private boolean isRoyalFlush(String[] hand) {
			Character card1 = hand[0].charAt(0);
			Character card5 = hand[4].charAt(0);
			
			if(card1 == 'A' && card5 == 'T') {
				return true;
			}
			return false;
		}
		
		private boolean isStraightFlush(String[] hand) {
			if (isStraight(hand) && isFlush(hand)) {
				return true;
			}
			return false;
		}
		
		private boolean isFourOfAKind(String[] hand) {
			Character card1 = hand[0].charAt(0);
			Character card2 = hand[1].charAt(0);
			Character card4 = hand[3].charAt(0);
			Character card5 = hand[4].charAt(0);
			
			if((card1 == card4) || (card2 == card5)) {
				return true;
			}
			
			return false;
		}
		
		private boolean isFullHouse(String[] hand) {			
			if (isThreeOfAKind(hand) && isAPair(hand)) {
				return true;
			}
			
			return false;
		}
		
		private boolean isFlush(String[] hand) {						
			Character[] suiteArray = new Character[5];
			for(int i = 0; i<5; i++) {
				suiteArray[i] = hand[i].charAt(1);
			}
			
			Arrays.sort(suiteArray);

			if (suiteArray[0] == suiteArray[4]) {
				return true;
			}
			
			return false;
		}
		
		private boolean isStraight(String[] hand) {
			Character card1 = hand[0].charAt(0);
			Character card5 = hand[4].charAt(0);
			if ((card5-card1 == 4) || (card1 == 'T' && card5 == 'A') || (card1 == 'Q' && card5 == '9') ||
					(card1 == 'K' && card5 == '9') || (card1 == 'Q' && card5 == '8') || (card1 == 'J' && card5 == '7') ||
					(card1 == 'T' && card5 == '6')) { 
				return true;
			}
			return false;
		}
		
		private boolean isThreeOfAKind(String[] hand) {
			Character card1 = hand[0].charAt(0);
			Character card2 = hand[1].charAt(0);
			Character card3 = hand[2].charAt(0);
			Character card4 = hand[3].charAt(0);
			Character card5 = hand[4].charAt(0);
			
			if (card1 == card3 || card2 == card4 || card3 == card5) {
				return true;
			}
			
			return false;
		}
		
		private boolean isTwoPairs(String[] hand) {
			Character card1 = hand[0].charAt(0);
			Character card2 = hand[1].charAt(0);
			Character card3 = hand[2].charAt(0);
			Character card4 = hand[3].charAt(0);
			Character card5 = hand[4].charAt(0);
			
			if ((card1 == card2 && card3 == card4 && card2 != card3 && card4 != card5) ||
					(card1 != card2 && card2 == card3 && card3 != card4 && card4 == card5)) { 
				return true; 
			}
			
			return false;
		}
		
		private boolean isAPair(String[] hand) {
			Character card1 = hand[0].charAt(0);
			Character card2 = hand[1].charAt(0);
			Character card3 = hand[2].charAt(0);
			Character card4 = hand[3].charAt(0);
			Character card5 = hand[4].charAt(0);
			
			if ((card1 == card2 && card2 != card3) || (card2 == card3 && card3 != card4 && card1 != card2) ||
					(card3 == card4 && card4 != card5 && card2 != card3) ||
					(card4 == card5 && card4 != card3)) {
				return true;
			}
			return false;
		}
		
		private int highestCard() {
			String[] hand = this.getCards();
			Character high = hand[4].charAt(0);

			char[] cardValues = new char[5];
			for(int i = 0; i<5; i++) {
				cardValues[i] = hand[i].charAt(0);
			}
			
			if(new String(cardValues).indexOf('A') != -1) {
				return 14;
			} else if(new String(cardValues).indexOf('K') != -1) {
				return 13;
			} else if (new String(cardValues).indexOf('Q') != -1) {
				return 12;
			} else if (new String(cardValues).indexOf('J') != -1) {
				return 11;
			} else if (new String(cardValues).indexOf('T') != -1) {
				return 10;
			} 
			return high;
		}
		
		private void calculateWinningHand(Hand player1Hand, Hand player2Hand) {

			int player1Rank = player1Hand.getRank();
			int player2Rank = player2Hand.getRank();
			
			if (player1Rank > player2Rank) {
				player1WinCount++;
			} else if (player2Rank > player1Rank) {
				player2WinCount++;
			} else if (player1Rank == player2Rank) {
				int player1HighCard = player1Hand.highestCard();
				int player2HighCard = player2Hand.highestCard();
				if (player1HighCard == player2HighCard) {
//					Leave this for now
					player1WinCount++;
//					player2WinCount++;
				}
			}
		}
	}
}

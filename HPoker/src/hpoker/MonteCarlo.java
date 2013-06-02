package hpoker;

import UA.*;
import java.util.*;
public class MonteCarlo {

   public static void main(String[] args){
                
		int otherPlayers;
		int trials = 200000;
		Scanner scan = new Scanner (System.in);
		
		System.out.print("Other Players: ");
		otherPlayers=scan.nextInt();
		scan.nextLine();
		
		System.out.print("Hand (2): ");
		String myHandS=scan.nextLine();
		calculate("",myHandS, otherPlayers,trials);
		
		System.out.print("Flop (3): ");
		String boardS=scan.nextLine();
		calculate(boardS,myHandS, otherPlayers,trials);
		
		System.out.print("Turn (1): ");
		boardS+=" " +scan.nextLine();
		calculate(boardS,myHandS, otherPlayers,trials);
		
		System.out.print("River (1): ");
		boardS+=" " +scan.nextLine();
		calculate(boardS,myHandS, otherPlayers,trials);
   } 
   public static float[] calculate(String aboardS, String amyHandS, int otherPlayers, int trials) {
		Hand board = new Hand(aboardS);
		Hand myHand = new Hand(amyHandS+ " " + aboardS);
		Deck theDeck= new Deck();
		HandEvaluator handEval = new HandEvaluator();
		int wins=0;
		int losses=0;
		int ties=0;
		long startTime = System.currentTimeMillis() ;
		for (int i=0; i<trials; i++) {
			board = new Hand(aboardS);
			myHand = new Hand(amyHandS+ " " + aboardS);
			theDeck.shuffle();
			theDeck.extractHand(myHand);
			while (board.size() <5) {
				Card tempCard = new Card();
				tempCard=theDeck.deal();
				board.addCard(tempCard);
				myHand.addCard(tempCard);
			}
			int myHandEval= handEval.rankHand(myHand);
			int stat=1; //1 = win, 2 = lose, 0 = tie
			
			for (int j=0; j<otherPlayers; j++) {
				Hand tempHand = new Hand(board);
				tempHand.addCard(theDeck.deal());
				tempHand.addCard(theDeck.deal());
				int tempEval=handEval.rankHand(tempHand);
				if (tempEval >myHandEval) {
					stat=2;
					break;
				} else if (tempEval==myHandEval && stat==1) {
					stat=0;
				}
			}
			switch (stat) {
				case 1: wins++; break;
				case 2: losses++; break;
				case 0: ties++; break;
			}
			theDeck.reset();
		}
		long computationTime = System.currentTimeMillis() - startTime ;
                float[] returnArray =  { (float)wins/(wins+losses+ties),(float)ties/(wins+losses+ties),(float)losses/(wins+losses+ties) };
                return returnArray;
                
                /*
                for (int i=0; i<25; ++i) System.out.println();
		
                
		System.out.println("___________________________________________");
		System.out.println("Board: "+ aboardS);
		System.out.println("Hand:  "+ amyHandS);
		System.out.println(otherPlayers +1 + " Players | " + trials + " Trials");
		System.out.println();
		System.out.println("WIN%   " + (float) wins/(wins+losses+ties)*100 + " | " + (float)1/(otherPlayers+1)*100);
		System.out.println("TIE%   " + (float) ties/(wins+losses+ties)*100);
		System.out.println("LOSE%  " + (float) losses/(wins+losses+ties)*100);
		System.out.println();
		System.out.println((int)(trials/((float)computationTime/1000)*(otherPlayers+1)/1000) + " Thousand Hands / Second");
		System.out.println("___________________________________________");
                 * 
                 */

   }
}
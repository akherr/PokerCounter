/**
 * @author Austin Herr 
 * 5/14/14
 * 
 * This program is designed to calculate the highest match in a hand of poker.
 * The only specified input from the user is the filepath for the input file,
 * 		which is specified when the program is run.
 * The only output is a System.out of the highest match that is in the hand.
 */
import java.util.*;
import java.io.*;

import javax.swing.JOptionPane;

public class PokerCounter {
	Scanner s;
	String filePath = JOptionPane.showInputDialog("Specify File Path:");
	public void initialize()
	{
		try
        {
            s = new Scanner(new File(filePath));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            System.out.println("File not found");
        }
		System.out.println(calculate(convertCards(getCards(s))));
	}
	
	//Main calculation function. Takes a String array of cards and processes
	//   card by card. Sets booleans for every match there is.
	private String calculate(String[] cards)
	{
		String score = "score string";
		String[] suitArray = new String[5];
		int[] cardArray = new int[5];
		boolean flush = false;
		boolean straight = false;
		boolean straightFlush = false;
		boolean pair = false;
		boolean twoPair = false;
		boolean threeKind = false;
		boolean fourKind = false;
		boolean royalFlush = false;
		boolean fullHouse = false;
		boolean highCard = false;
		
		//Separate the suits and card values into two arrays
		for(int i = 0; i < 5; i++)
		{
			if((cards[i].length()) == 2)
			{
				suitArray[i] = cards[i].substring(1,2);
				cardArray[i] = Integer.parseInt(cards[i].substring(0,1));
			}
			else
			{
				suitArray[i] = cards[i].substring(2,3);
				cardArray[i] = Integer.parseInt(cards[i].substring(0,2));
			}
		}
		
		//sort the card value array from least to greatest
		Arrays.sort(cardArray);

		//Begin by seeing if we have a royal, straight, or regular flush
		//We don't need to use a loop because these depend on all of the cards
		if((suitArray[0].equals(suitArray[1])) && (suitArray[1].equals(suitArray[2])) && 
		   (suitArray[2].equals(suitArray[3])) && (suitArray[3].equals(suitArray[4])))
		{
			if((cardArray[0]+1 == cardArray[1]) && (cardArray[1]+1 == cardArray[2]) && 
			   (cardArray[2]+1 == cardArray[3]) && (cardArray[3]+1 == cardArray[4]))
			{
				if((cardArray[0] == 10) && (cardArray[1] == 11) && (cardArray[2] == 12) &&
				   (cardArray[3] == 13) && (cardArray[4] == 14))
				{
					royalFlush = true;
				}
				else
				{
					straightFlush = true;
				}
			}
			else
			{
				flush = true;
			}
		}
		else if((cardArray[0]+1 == cardArray[1]) && (cardArray[1]+1 == cardArray[2]) && 
				(cardArray[2]+1 == cardArray[3]) && (cardArray[3]+1 == cardArray[4]))
		{
			straight = true;
		}
		
		//Loop for all other match kinds
		//Looks ahead and behind the current position in the array for certain matches
		for(int i = 0; i < 5; i++)
		{
			if(i < 2)
			{
				if((cardArray[i] == cardArray[i+1]) && (cardArray[i+1] == cardArray[i+2]) &&
				   (cardArray[i+2] == cardArray[i+3]))
				{
					fourKind = true;
				}
			}
			if(i < 3)
			{
				if((cardArray[i] == cardArray[i+1]) && (cardArray[i+1] == cardArray[i+2]))
				{
					if((i == 2) && (cardArray[i-1] == cardArray[i-2]))
					{
						fullHouse = true;
					}
					else if((i == 0) && (cardArray[i+3] == cardArray[i+4]))
					{
						fullHouse = true;
					}
					else
					{
						threeKind = true;
					}
				}
			}
			if(i < 4)
			{
				if(cardArray[i] == cardArray[i+1])
				{
					if((i == 0) && ((cardArray[i+2] == cardArray[i+3]) && (cardArray[i+3] == cardArray[i+4])))
					{
						fullHouse = true;
					}
					else if((i == 1) && (cardArray[i+2] == cardArray[i+3]))
					{
						twoPair = true;
					}
					else if((i == 0) && (cardArray[i+2] == cardArray[i+3]))
					{
						twoPair = true;
					}
					else if((i == 0) && (cardArray[i+3] == cardArray[i+4]))
					{
						twoPair = true;
					}
					else
					{
						pair = true;
					}
				}
			}
			else
			{
				highCard = true;
			}
		}
		
		//goes through the least to greatest rank of hands and sets
		//   the return value to be the highest existing hand
		if(highCard == true)
		{
			score = "High Card";
		}
		if(pair == true)
		{
			score = "Pair";
		}
		if(twoPair == true)
		{
			score = "Two Pair";
		}
		if(threeKind == true)
		{
			score = "Three of a Kind";
		}
		if(straight == true)
		{
			score = "Straight";
		}
		if(flush == true)
		{
			score = "Flush";
		}
		if(fullHouse == true)
		{
			score = "Full House";
		}
		if(fourKind == true)
		{
			score = "Four of a Kind";
		}
		if(straightFlush == true)
		{
			score = "Straight Flush";
		}
		if(royalFlush == true)
		{
			score = "Royal Flush";
		}
		return score;
	}
	
	//converts the cards array face cards to numerical values,
	//   and returns a new String array
	private String[] convertCards(String[] cards)
	{
		String[] retCards = cards;
		for(int i = 0; i < 5; i++)
		{
			if(cards[i].substring(0,1).equals("J"))
			{
				retCards[i] = "11" + cards[i].substring(1,2);
			}
			if(cards[i].substring(0,1).equals("Q"))
			{
				retCards[i] = "12" + cards[i].substring(1,2);
			}
			if(cards[i].substring(0,1).equals("K"))
			{
				retCards[i] = "13" + cards[i].substring(1,2);
			}
			if(cards[i].substring(0,1).equals("A"))
			{
				retCards[i] = "14" + cards[i].substring(1,2);
			}
		}
		return retCards;
	}

	//takes the scanner with the input filepath, takes each card,
	//   and puts it into  a String array
	private String[] getCards(Scanner scan)
	{
		String[] cards = new String[] {"0","0","0","0","0"};
		for(int i = 0; i < 5; i++)
		{
			cards[i] = scan.next();
		}
		return cards;
	}
	public static void main (String[] args)
	{
		PokerCounter pc = new PokerCounter();
		pc.initialize();
	}

}

package Assignment_2;

import java.io.PrintStream;
import java.util.List;
// Ayman Tawfeeq
// This program contains the methods that create a giftnode linked list and runs a game known as the white elephant  gift exchange.
// The emthods in this program are used to create the list, print out the names and complete tasks such as rearange the lists
// when someone opens or steals a gift.
public class GiftExchangeManager {

  private GiftNode fp; // Reference to the front of participants list	
  private GiftNode fc; // Reference to the front of chosen list

  public GiftExchangeManager(List < String > nameList) { // takes a list as input and creates a giftNode linked list
	  
    if (nameList.size() == 0|| nameList == null ) { // checks if list is empty
      throw new IllegalArgumentException("List is empty");
    }
    this.fp = new GiftNode(nameList.get(0)); // Creates a new giftNode for the participants and assigns fp field to the front of the participants list
	GiftNode temp = this.fp;
    for (int i = 1; i < nameList.size(); i++) {
		while (temp.next != null) { // checks if next one node is null
			temp = temp.next; // if null then skip to next one
		}
		temp.next = new GiftNode(nameList.get(i)); // adds name to the end of the participants list
    }
  }

  public void printRemainingParticipants(PrintStream stream) { // prints the name and the order of the participants list

    GiftNode current = this.fp; 
    while (current.next != null) { //while the next one isnt null, print name choses before name
		stream.println("  " + current.name + " chooses before " + current.next.name);
        current = current.next;
    }
    stream.println("  " + current.name + " chooses last");
  }

  public void printAlreadyChosen(PrintStream stream) {// prints the names and the gift they got in the order of most recent to latest

    GiftNode current = this.fc;
    if (current != null) { // if list is empty then does nothing
		while (current.next != null) { // while the next node is nt null, print name opend a gift
			stream.println("  " + current.name + " opened a " + current.gift);
			current = current.next;
		}
        stream.println("  " + current.name + " opened a " + current.gift);
    }
  }

  public boolean remainingParticipantsContains(String name) { // checks if a name exists in the participants list

	  return listContains(name,this.fp); // calls helper methods that checks if the name exists in the participants list
  }

  public boolean alreadyChosenContains(String name) { // checks if a name exists in the chosen list

	  return listContains(name,this.fc);// calls helper methods that checks if the name exists in the chosen list
  }
	
  private boolean listContains(String name, GiftNode list){ //helper methods that checks if the name exists in the given list
	  GiftNode current = list;
      while (current != null) { // as long as current does not point to null
		  if (current.name.equalsIgnoreCase(name)) { //check if name exists
			  return true;
		  }
			  current = current.next;
      }
		   return false;
	}

  public boolean isGameOver() { // if the participants list is empty it returns true, represening the game being over
	 if (this.fp == null) {
		return true;
	 } else {
		return false;
	 }
  }

  public void openGift(String gift) { // rearranges the nodes so that the first person in the participants list becomes the first person on the
	  // chosen list. and assigns that persons gift field to the name of the gift given to this method
    if (isGameOver()) { // checks if came is over
		throw new IllegalStateException("There is no one left to open gifts.");
    } else {
      GiftNode frontC = this.fc;  
	  GiftNode frontP = this.fp;
		// moves from beginning of fp list to begining of fc list
	  this.fp.gift = gift;
	  this.fp = this.fp.next;
	  this.fc=frontP;
	  this.fc.next = frontC;  
    }
  }

  public void stealFrom(String name) { // rearranges the nodes so that the the person whos gift is being stolen
	  // gets moved to the front of the participants list and assigns his gift value to null
    if (isGameOver()) {// checks if came is over
      throw new IllegalStateException("The game is done");
    } else if (remainingParticipantsContains(name)) {
      throw new IllegalArgumentException(name + " has not chosen a gift yet");
    } else {
      GiftNode frontC = this.fc;
      GiftNode steal= this.fc;  
	  GiftNode frontP = this.fp;

      while (!steal.name.equalsIgnoreCase(name)) {
        steal = steal.next;
      }
	  String gift = steal.gift ;
      steal.gift = null;
		// moves from beginning of fp list to begining of fc list
		 openGift(gift);

		// rearranges nodes of name who got their gift stolen to beginning of participants list
	  GiftNode befStolen = this.fc;
	  GiftNode aftStolen = steal.next;
	  GiftNode newFrontP = this.fp;
		
	  this.fp = steal;
	  steal.next = newFrontP;
		while (befStolen.next.name != steal.name) {
        befStolen = befStolen.next;
      }
	  befStolen.next = aftStolen;
    }
  }
}
/** A Rock-Paper-Scissors AI algorithm based off of the Iocaine Powder
  * Algorithm. 
  * 
  * If correct conditions are met, it implements modified History Sequence 
  * Matching; else, it implements a constantly changing probability distribution
  * that is updated every 3 turns.
  * 
  * @author Rich Korzelius
  * @author Caroline Thompson
  */
import java.util.*;

public class CrockBot implements RoShamBot {
  
  private ArrayList<Action> opponentMoves = new ArrayList<Action>();
  private int numRocks = 0;
  private int numPaper = 0;
  private int numScissors = 0;
  private int totalMoves = 0;
  private double probRock = 1.0/3.0;
  private double probPaper = 1.0/3.0;
  private double probScissors = 1.0/3.0;
  private final int lookAhead = 9;
  private final int lookBack = 7;
  
  /** Returns an action based on what it predicts the opponent will throw using
    * sequence matching, and if it can't, it uses a probability
    * distribution that is updated every 3 turns.
    * 
    * @param lastOpponentMove the actions that were played by the two agents on the
    *        last round.
    * @return the next action to play.
    */
  public Action getNextMove(Action lastOpponentMove) {
    
    opponentMoves.add(lastOpponentMove);
    totalMoves++;
    
    
    if (lastOpponentMove == Action.ROCK)
      numRocks++;
    if (lastOpponentMove == Action.PAPER)
      numPaper++;
    if (lastOpponentMove == Action.SCISSORS)
      numScissors++;
    
    //get most recent moves

    // if total moves is less than n, do nash equilibrium; where n is the
    // lookAhead value for how often we want to recalculate probabilities
    if (totalMoves < lookAhead) {
      //System.out.println("Using Nash");
      return nashSelection();
    }
    
    double n = Math.random();
    if (totalMoves > lookBack) {
      ArrayList<Action> mostRecentMoves = new ArrayList<Action>();
      for (int i = lookBack; i > 0; i--) {
        mostRecentMoves.add(opponentMoves.get(opponentMoves.size() - i));
      }
      
      if (totalMoves > lookBack && containsSubList(this.opponentMoves, mostRecentMoves)) {
        //System.out.println("Using pattern recognition");
        if (totalMoves % lookAhead == 0)  {
          this.probRock = (numRocks* 1.0)/totalMoves;
          this.probPaper = (numPaper * 1.0)/totalMoves;
          this.probScissors = (numScissors * 1.0)/totalMoves;
        }
        return patternRecognitionSelection(this.opponentMoves, mostRecentMoves);
      }
      
    }
    
    
      //System.out.println("changed prob dist");
    Action nextMove = probabilitySelection(this.probRock, this.probPaper, this.probScissors);
    if (totalMoves % lookAhead == 0)  {
      // if at recalculation point, recalculate prob distribution
      this.probRock = (numRocks* 1.0)/totalMoves;
      this.probPaper = (numPaper * 1.0)/totalMoves;
      this.probScissors = (numScissors * 1.0)/totalMoves;
    }
    return nextMove;
  }
  
  /** Returns an action according to the mixed strategy (1/3, 1/3, 1/3).
      * 
      * @return the next action to play.
      */
  private Action nashSelection(){ 
    double n = Math.random();
    if (n <= 1.0/3.0)
      return Action.ROCK;
    else if (n <= 2.0/3.0)
      return Action.PAPER;
    else
      return Action.SCISSORS;
  }
  
  /** Returns an action according to the mixed strategy (1/3, 1/3, 1/3).
      * 
      * @param probRock the current probability of throwing rock
      * @param probPaper the current probability of throwing paper
      * @param probScissors the curren probability of throwing paper
      * @return the next action to play.
      */
  private Action probabilitySelection(double probRock, double probPaper, double probScissors) {
    // 0 - Rock; 1 - PAPER; 2 - SCISSORS
    double n = Math.random();
    if (n <= probRock)
      return Action.PAPER;
    else if (n <= probRock + probPaper)
      return Action.SCISSORS;
    else
      return Action.ROCK;
  }
  
  /** Returns the move that beats the given move
    * 
    * @param move the move we want to beat
    * @return the move that beats the given move
    */
  private Action getWinningMove(Action move) {
    if (move == Action.ROCK)
      return Action.PAPER;
    else if (move == Action.PAPER)
      return Action.SCISSORS;
    else
      return Action.ROCK;
  }
  
  /**Returns the action to be played based on a strategy of pattern recognition
    * 
    * It chooses the action that beats the opponent that has followed the given 
    * sequence of moves most throughout the match.
    * 
    * @param opponentMoves an array of the opponents move history
    * @param mostRecentmoves the most recent move sequence
    * @return the action to be played
    */
  private Action patternRecognitionSelection(ArrayList<Action> opponentMoves, ArrayList<Action> mostRecentMoves) {
    
    int occurences = 0;
    // 0 - Rock; 1 - PAPER; 2 - SCISSORS
    int[] followUpMoveCounts = {0, 0, 0};
    
    //try:
    for (int i = 0; i < (opponentMoves.size() - mostRecentMoves.size()); i++) {
      if (seqMatch(i, this.opponentMoves, mostRecentMoves)) {
        occurences++;
        if (opponentMoves.get(i + mostRecentMoves.size()) == Action.ROCK)
          followUpMoveCounts[0]++;
        else if (opponentMoves.get(i + mostRecentMoves.size()) == Action.PAPER)
          followUpMoveCounts[1]++;
        else
          followUpMoveCounts[2]++;
      }
    }
    
    int maxIndex = 0;
    for (int i = 0; i < followUpMoveCounts.length; i++) {
      if (followUpMoveCounts[i] > followUpMoveCounts[maxIndex])
        maxIndex = i;
    }
    if (maxIndex == 0)
      return Action.PAPER;
    else if (maxIndex == 1)
      return Action.SCISSORS;
    else
      return Action.ROCK;
    //return probabilitySelection((followUpMoveCounts[0] * 1.0)/occurences, (followUpMoveCounts[1] * 1.0)/occurences, (followUpMoveCounts[2] * 1.0)/occurences);
  }
  
  /**Returns true if a given sublist appears in a given list. 
    * 
    * @param mainList the main list
    * @param subList the subList to be checked
    * @return true if the sublist is actually a sublist of the main list;
    * false otherwise
    */ 
  private boolean containsSubList(ArrayList<Action> mainList, ArrayList<Action> subList) {
    for (int i = 0; i < mainList.size() - subList.size(); i++) {
      if (seqMatch(i, mainList, subList))
        return true;
    }
    return false;
  }
  
  /**Checks if a given seq matches the current cells in the main list
    * 
    * @param index the index in the main list at which we begin 
    * matching the given sequence
    * @param seq the seq to be matched
    * @return true if there is a match; false otherwise
    */
  private boolean seqMatch(int index, ArrayList<Action> mainList, ArrayList<Action> seq) {
    for (int i = 0; i < seq.size(); i++) {
      if (seq.get(i) != mainList.get(index + i)) {
        return false;
      }
    }
    return true;
  }
}


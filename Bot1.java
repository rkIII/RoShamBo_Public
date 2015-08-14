/**RPS Bot 1 
  * - 7 move look back: assume opponent will choose move 
  * they have done most in last 7 moves
  * 
  * @author Rich Korzelius
  * @author Caroline Thompson
  */

import java.util.*;


public class Bot1 implements RoShamBot {
  
  private Queue<Action> prevOpponentMoves = new LinkedList<Action>();
  private Action secondToLastOpponentMove;
  private Action[] allMoves = {Action.ROCK, Action.PAPER, Action.SCISSORS}; 
  private final int lookBack = 7;
  
  
  public Action getNextMove(Action lastOpponentMove) {

    if (prevOpponentMoves.size() < lookBack)
      prevOpponentMoves.add(lastOpponentMove);
    else {
      prevOpponentMoves.remove();
      prevOpponentMoves.add(lastOpponentMove);
    }
    
//    if (lastOpponentMove == secondToLastOpponentMove) {
//      secondToLastOpponentMove = lastOpponentMove;
//      
//      Action[] possibleMoves = getPossibleMoves(lastOpponentMove);
//      
//      double coinFlip = Math.random();
//      
//      if (coinFlip <= 0.5)
//        return possibleMoves[0];
//      else 
//        return possibleMoves[1];
//    }
    
//    else {
    secondToLastOpponentMove = lastOpponentMove;
    Iterator<Action> itr = prevOpponentMoves.iterator();
    //Count Moves - index 0 - ROCK, index 1 - PAPER, index 2 - SCISSOR
    int[] moveCounts = new int[3];
    moveCounts[0] = 0;
    moveCounts[1] = 0;
    moveCounts[2] = 0;
    
    while(itr.hasNext()){
      Action move = itr.next();
      if (move == Action.ROCK)
        moveCounts[0]++;
      else if (move == Action.PAPER)
        moveCounts[1]++;
      else
        moveCounts[2]++;
    }
    int minIndex = 0;
    int maxIndex = 0;
    for (int i = 0; i < moveCounts.length; i++) {
      if (moveCounts[i] > 0 && moveCounts[i] < moveCounts[minIndex] )
        minIndex = i;
      if (moveCounts[i] > moveCounts[maxIndex] )
        maxIndex = i;
    }
    return getWinningMove(this.allMoves[maxIndex]);
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

/** Returns an array list of the two possible moves
  * assuming the opponent won't repeat.
  * 
  * @param lastOpponentMove the opponent's last move
  * @return a list of the two possible moves
  */
private Action[] getPossibleMoves(Action lastOpponentMove) {
  
  Action[] possibleMoves = new Action[3];
  
  if (lastOpponentMove == Action.ROCK) {
    possibleMoves[0] = Action.ROCK;
    possibleMoves[1] = Action.SCISSORS;
    return possibleMoves;
  }
  else if (lastOpponentMove == Action.PAPER) {
    possibleMoves[0] = Action.PAPER;
    possibleMoves[1] = Action.ROCK;
    return possibleMoves;
  }
  else {
    possibleMoves[0] = Action.SCISSORS;
    possibleMoves[1] = Action.PAPER;
    return possibleMoves;
  }
}
}
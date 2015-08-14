

public class BestBot implements RoShamBot {
  
  private ArrayList<Action> opponentsActions = new ArrayList<Action>();
  private Action opponentLastMove;
  private int numRocks = 0;
  private int numPaper = 0;
  private int numScissors = 0;
  private int totalMoves = 0;
  private int tenMoves = 0;
  
  
  public Action getNextMove(Action lastOpponentMove) {
    
    opponentsActions.add(lastOpponentMove);
    totalMoves++;
    tenMoves++;
    
    if (lastOpponentMove == Action.ROCK)
      numRocks++;
    if (lastOpponentMove == Action.PAPER)
      numPaper++;
    if (lastOpponentMove == Action.SCISSORS)
      numScissors++;
    
    
    // if total moves is less than 10, do nash equilibria 
    if (totalMoves < 10) {
      double coinFlip = Math.random();
        
        if (coinFlip <= 1.0/3.0)
            return Action.ROCK;
        else if (coinFlip <= 2.0/3.0)
            return Action.PAPER;
        else
            return Action.SCISSORS;
    }
    
    // else, calculate 
    else {
      int probRock = numRocks/totalMoves;
      int probPaper = numPaper/totalMoves;
      int probScissors = numScissors/totalMoves;
      double coinFlip = Math.random();
        
      /* COMMENTS: Calculating the probabilities of their moves above. Would we want to return the opposite
       * move with those probabilities? And how do we return these moves based on the coin flip?
       */
      
        if (coinFlip <= probRock)
            return Action.ROCK;
        else if (coinFlip <= 2.0/3.0)
            return Action.PAPER;
        else
            return Action.SCISSORS;
    }
  }
  
}
    
    
    
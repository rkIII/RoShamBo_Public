
/** A Rock-Paper-Scissors player that assumes the 
  * opponent will repeat its last action.
  * 
  * @author Rich Korzelius
  * @author Caroline Thompson
  */
public class OpponentRepeatsBot implements RoShamBot {
 
    /** Returns the action that beats what the opponent just threw
      * 
      * @param lastOpponentMove the action that was played by the opponent on
      *        the last round
      * @return the next action to play
      */
    public Action getNextMove(Action lastOpponentMove) {

        if (lastOpponentMove == Action.PAPER)
          return Action.SCISSORS;
        else if (lastOpponentMove == Action.ROCK)
          return Action.PAPER;
        else
            return Action.ROCK;
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/* GUI Wrapper for RPS Bots, where opponent is a human.
 * 
 * @author RR
 */
public class RoShamBoGui implements ActionListener {

    // GUI related fields
    private JFrame window;
    private JButton rock;
    private JButton paper;
    private JButton scissors;
    private JLabel humanAction;
    private JLabel botAction;
    private JLabel results;
    private Map<Action, ImageIcon> humanActionIconMap;
    private Map<Action, ImageIcon> botActionIconMap;

    // Game related fields
    private int[] score; // score[0] = human wins, score[2] = bot wins
    private Action lastHumanAction = Action.ROCK;
    
    /*** Substitute the name of your RoShamBot implementation below! ***/
    /******************************************************************/
    private RoShamBot bot = new CrockBot();
    /******************************************************************/

    private JPanel createButtons() {
 JPanel buttonPanel = new JPanel();
 
        this.rock = new JButton(new ImageIcon("images/button-rock.jpg"));
        this.paper = new JButton(new ImageIcon("images/button-paper.jpg"));
 this.scissors = new JButton(new ImageIcon("images/button-scissors.jpg"));
 buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(this.rock);
        buttonPanel.add(this.paper);
        buttonPanel.add(this.scissors);
                 
        // registers this object as listener for both buttons 
        rock.addActionListener(this); 
        paper.addActionListener(this);
 scissors.addActionListener(this);

 return buttonPanel;
    }

    
    private JPanel createLabels() {
 JPanel actionPanel = new JPanel();
 
 this.humanAction = new JLabel("Your action", humanActionIconMap.get(Action.ROCK), JLabel.CENTER);
 this.botAction = new JLabel("Bot's action", botActionIconMap.get(Action.ROCK), JLabel.CENTER);
 this.humanAction.setVerticalTextPosition(JLabel.TOP);
 this.humanAction.setHorizontalTextPosition(JLabel.CENTER);
 this.botAction.setVerticalTextPosition(JLabel.TOP);
 this.botAction.setHorizontalTextPosition(JLabel.CENTER); 
 actionPanel.setLayout(new FlowLayout());
 actionPanel.add(humanAction);
 actionPanel.add(botAction);

 return actionPanel;
    }


    private String getCurrentScore() {
 return ("Human Wins: " + this.score[0] + "        " +
  "Ties: " + this.score[1] + "        " +
  "Bot Wins: " + this.score[2]);
    }


    /** Load and cache images to avoid file I/O latencies during game play. */
    private void buildActionIconMaps() {
 this.humanActionIconMap = new HashMap<Action, ImageIcon>();
 this.humanActionIconMap.put(Action.ROCK, new ImageIcon("images/rock-left.jpg"));
 this.humanActionIconMap.put(Action.PAPER, new ImageIcon("images/paper-left.jpg"));
 this.humanActionIconMap.put(Action.SCISSORS, new ImageIcon("images/scissors-left.jpg"));
 
 this.botActionIconMap = new HashMap<Action, ImageIcon>();
 this.botActionIconMap.put(Action.ROCK, new ImageIcon("images/rock-right.jpg"));
 this.botActionIconMap.put(Action.PAPER, new ImageIcon("images/paper-right.jpg"));
 this.botActionIconMap.put(Action.SCISSORS, new ImageIcon("images/scissors-right.jpg")); 
    }


    /** Constructor - builds user interface and initializes game state. */
    public RoShamBoGui() {
 this.score = new int[3];
 
 window = new JFrame("Rock Paper Scissors");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

 
 buildActionIconMaps();
 JPanel buttonPanel = createButtons();
 JPanel actionPanel = createLabels();
 this.results = new JLabel(getCurrentScore());

 window.setLayout(new BoxLayout(window.getContentPane(), BoxLayout.PAGE_AXIS));
 buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
 actionPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
 results.setAlignmentX(Component.CENTER_ALIGNMENT);
        window.add(buttonPanel);
 window.add(actionPanel);
 window.add(results);
        window.pack();
        window.setVisible(true);
 window.setResizable(false);
    }

    
    /** Translates human button press into an Action. */
    private Action readHumanMove(JButton button) {
 if (button.equals(this.rock))
     return Action.ROCK;

 if (button.equals(this.paper))
     return Action.PAPER;

 return Action.SCISSORS;
    }


    /** Button listener that updates GUI state. */
    public void actionPerformed(ActionEvent e) {
 // Get human and bot moves (a1 and a2 respectively)
 Action a1 = readHumanMove((JButton)e.getSource());
 Action a2 = bot.getNextMove(this.lastHumanAction);

 // Update images
 humanAction.setIcon(humanActionIconMap.get(a1));
 botAction.setIcon(botActionIconMap.get(a2));

 // Determine winner
 boolean p1Win = 
            ((       (a1 == Action.ROCK) && (a2 == Action.SCISSORS))
      || ((a1 == Action.PAPER) && (a2 == Action.ROCK))
      || ((a1 == Action.SCISSORS) && (a2 == Action.PAPER)));
        
        boolean p2Win = 
            ((       (a2 == Action.ROCK) && (a1 == Action.SCISSORS))
      || ((a2 == Action.PAPER) && (a1 == Action.ROCK))
      || ((a2 == Action.SCISSORS) && (a1 == Action.PAPER)));
        
        if (p1Win)
            this.score[0]++;
        else if (p2Win)
            this.score[2]++;
        else
            this.score[1]++;
        
        this.lastHumanAction = a1;
        this.results.setText(getCurrentScore());
        this.window.pack(); // accommodate growing/shrinking label size
    }


    /** Launcher */
    public static void main(String[] args) {
 new RoShamBoGui();
    }
    
}

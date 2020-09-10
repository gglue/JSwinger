import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Container;
import java.awt.BorderLayout;
import java.awt.GridLayout;

public class JSwinger implements ActionListener {
    int gridSize = 20;
    int numberOfBombs = 30;
    JFrame game = new JFrame("JSwinger");
    JButton smiley = new JButton("Reset Button");
    JButton[][] buttons = new JButton[gridSize][gridSize];
    int[][] buttonValues = new int[gridSize][gridSize];
    Container grid = new Container();
    public JSwinger() {
        // Set up the window
        game.setSize(400, 500);
        game.setLayout(new BorderLayout());


        // Set up the grid buttons
        grid.setLayout(new GridLayout(gridSize, gridSize));
        for (int x = 0; x < gridSize; x++){
            for (int y = 0; y < gridSize; y++){
                buttons[x][y] = new JButton();
                buttons[x][y].addActionListener(this);
                grid.add(buttons[x][y]);
            }
        }
        game.add(grid, BorderLayout.CENTER);
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.setVisible(true);

        // Set up the reset button at the top
        game.add(smiley, BorderLayout.NORTH);
        smiley.addActionListener(this);
    }

    public void placeRandomMines(){

        // Create list of random pairs
        ArrayList<Integer> tracker = new ArrayList<Integer>();
        for (int x = 0; x < gridSize; x++){
            for (int y = 0; y < gridSize; y++){
                tracker.add((x*100) + y);
            }
        }

        // Set every button value to zero again
        buttonValues = new int[gridSize][gridSize];
        

    }

    public static void main(String[] args) {
        new JSwinger();
    }

    @Override
    public void actionPerformed(ActionEvent event){

    }
}

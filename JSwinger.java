import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Container;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;

import java.util.ArrayList;

public class JSwinger implements ActionListener {
    int gridSize = 20;
    int numberOfBombs = 30;
    final int MINE = 10;
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

        placeRandomMines();
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

        // Pick out the number of bombs
        for (int bombNumber = 0; bombNumber < numberOfBombs; bombNumber++){
            int chosenSquare = (int)(Math.random() * tracker.size());
            buttonValues[tracker.get(chosenSquare) / 100][tracker.get(chosenSquare) % 100] = MINE;
            tracker.remove(chosenSquare);
        }

        // Check for each square how much neighbouring mines there are
        for (int x = 0; x < gridSize; x++){
            for (int y = 0; y < gridSize; y++){
                if (buttonValues[x][y] != MINE){
                    int neighbourBombs = 0;



                    // top left
                    if (x > 0 && y > 0 && buttonValues[x-1][y-1] == MINE) {
                        neighbourBombs++;
                    }

                    // up
                    if (y > 0 && buttonValues[x][y-1] == MINE){
                        neighbourBombs++;
                    }

                    // top right
                    if (x < gridSize - 1 && y > 0 && buttonValues[x+1][y-1] == MINE){
                        neighbourBombs++;
                    }

                    // left
                    if (x > 0 && buttonValues[x-1][y] == MINE){
                        neighbourBombs++;
                    }

                    // right
                    if (x < gridSize - 1 && buttonValues[x+1][y] == MINE){
                        neighbourBombs++;
                    }

                    //  bottom left
                    if (x > 0 && y < gridSize - 1 && buttonValues[x-1][y+1] == MINE){
                        neighbourBombs++;
                    }

                    // down
                    if (y < gridSize - 1 && buttonValues[x][y+1] == MINE){
                        neighbourBombs++;
                    }

                    //down right
                    if (x < gridSize - 1 && y < gridSize - 1 && buttonValues[x+1][y+1] == MINE){
                        neighbourBombs++;
                    }

                    buttonValues[x][y] = neighbourBombs;
                }
            }
        }
    }

    public void gameOver(){
        for (int x = 0; x < gridSize; x++){
            for (int y = 0; y < gridSize; y++){
                if (buttons[x][y].isEnabled()){
                    if (buttonValues[x][y] == MINE){
                        buttons[x][y].setBackground(Color.red);
                        buttons[x][y].setText("X");
                    }
                    else{
                        buttons[x][y].setText(buttonValues[x][y] + "");
                    }
                    buttons[x][y].setEnabled(false);
                }
            }
        }
    }

    public void checkWin(){

    }

    public void openEmpty(ArrayList<Integer> toOpen){
        if (toOpen.size() == 0){
            return;
        }
        else {
            // Get x and y coordinate of a square
            int x = toOpen.get(0) / 100;
            int y = toOpen.get(0) % 100;
            toOpen.remove(0);
            // top left
            if (x > 0 && y > 0 && buttons[x-1][y-1].isEnabled()) {
                buttons[x-1][y-1].setText(buttonValues[x-1][y-1] + "");
                buttons[x-1][y-1].setEnabled(false);
                if (buttonValues[x-1][y-1] == 0){
                    toOpen.add((x-1) * 100 + (y-1));
                }
            }
            // top
            if (y > 0 && buttons[x][y-1].isEnabled()){
                buttons[x][y-1].setText(buttonValues[x][y-1] + "");
                buttons[x][y-1].setEnabled(false);
                if (buttonValues[x][y-1] == 0){
                    toOpen.add(x * 100 + (y-1));
                }
            }

            // top right
            if (x < gridSize - 1 && y > 0 && buttons[x+1][y-1].isEnabled()){
                buttons[x+1][y-1].setText(buttonValues[x+1][y-1] + "");
                buttons[x+1][y-1].setEnabled(false);
                if (buttonValues[x+1][y-1] == 0){
                    toOpen.add((x+1) * 100 + (y-1));
                }
            }

            // left
            if (x > 0 && buttons[x-1][y].isEnabled()) {
                buttons[x-1][y].setText(buttonValues[x-1][y] + "");
                buttons[x-1][y].setEnabled(false);
                if (buttonValues[x-1][y] == 0){
                    toOpen.add((x-1) * 100 + y);
                }
            }

            // right
            if (x < gridSize - 1 && buttons[x+1][y].isEnabled()){
                buttons[x+1][y].setText(buttonValues[x+1][y] + "");
                buttons[x+1][y].setEnabled(false);
                if (buttonValues[x+1][y] == 0){
                    toOpen.add((x+1) * 100 + y);
                }
            }

            // down left
            if (x > 0 && y < gridSize - 1 && buttons[x-1][y+1].isEnabled()) {
                buttons[x-1][y+1].setText(buttonValues[x-1][y+1] + "");
                buttons[x-1][y+1].setEnabled(false);
                if (buttonValues[x-1][y+1] == 0){
                    toOpen.add((x-1) * 100 + (y+1));
                }
            }
            // down
            if (y < gridSize - 1 && buttons[x][y+1].isEnabled()){
                buttons[x][y+1].setText(buttonValues[x][y+1] + "");
                buttons[x][y+1].setEnabled(false);
                if (buttonValues[x][y+1] == 0){
                    toOpen.add(x * 100 + (y+1));
                }
            }

            // down right
            if (x < gridSize - 1 && y < gridSize - 1 && buttons[x+1][y+1].isEnabled()){
                buttons[x+1][y+1].setText(buttonValues[x+1][y+1] + "");
                buttons[x+1][y+1].setEnabled(false);
                if (buttonValues[x+1][y+1] == 0){
                    toOpen.add((x+1) * 100 + (y+1));
                }
            }
        openEmpty(toOpen);
        }
    }

    public static void main(String[] args) {
        new JSwinger();
    }

    @Override
    public void actionPerformed(ActionEvent event){
        // Smiley button resets the game
        if (event.getSource().equals(smiley)){
            for (int x = 0; x < gridSize; x++){
                for (int y = 0; y < gridSize; y++){
                    buttons[x][y].setEnabled(true);
                    buttons[x][y].setText("");
                    if (buttonValues[x][y] == MINE){
                        buttons[x][y].setBackground(null);
                    }
                }
            }
            // Reset the grid
            placeRandomMines();
        }
        else {
            for (int x = 0; x < gridSize; x++){
                for (int y = 0; y < gridSize; y++){
                    if (event.getSource().equals(buttons[x][y])){
                        // Game over if it is a mine
                        if (buttonValues[x][y] == MINE){
                            gameOver();
                        }
                        else if (buttonValues[x][y] == 0){
                            buttons[x][y].setText(buttonValues[x][y] + "");
                            buttons[x][y].setEnabled(false);
                            ArrayList<Integer> toOpen = new ArrayList<Integer>();
                            toOpen.add(x*100+y);
                            openEmpty(toOpen);

                        }
                        else {
                            buttons[x][y].setText(buttonValues[x][y] + "");
                            buttons[x][y].setEnabled(false);
                        }
                    }
                }
            }
        }
    }
}

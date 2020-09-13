import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Container;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.ArrayList;
import java.time.Instant;
import java.time.Duration;




public class JSwinger implements MouseListener {
    int gridSize;
    int numberOfBombs;
    int numberOfFlags;
    final int MINE = 10;
    JFrame game = new JFrame("JSwinger");
    JButton smiley = new JButton("Reset Button");
    JLabel flagLabel;
    JPanel topHUD = new JPanel();
    JButton[][] buttons;
    int[][] buttonValues;
    Container grid = new Container();
    Instant startTime = Instant.now();

    public JSwinger(int grid, int bombs){
        gridSize = grid;
        numberOfBombs = bombs;
        numberOfFlags = numberOfBombs;
        buttons = new JButton[gridSize][gridSize];
        buttonValues = new int[gridSize][gridSize];
        flagLabel = new JLabel(String.format("%d flags", numberOfFlags));
        setUp();
    }

    public JSwinger() {
        gridSize = 20;
        numberOfBombs = 30;
        numberOfFlags = numberOfBombs;
        buttons = new JButton[gridSize][gridSize];
        buttonValues = new int[gridSize][gridSize];
        flagLabel = new JLabel(String.format("%d flags", numberOfFlags));
        setUp();
    }

    public void setUp(){
        // Set up the window
        game.setSize(1000, 800);
        game.setLayout(new BorderLayout());

        // Set up the grid buttons
        grid.setLayout(new GridLayout(gridSize, gridSize));
        for (int x = 0; x < gridSize; x++){
            for (int y = 0; y < gridSize; y++){
                buttons[x][y] = new JButton();
                buttons[x][y].addMouseListener(this);
                grid.add(buttons[x][y]);
            }
        }

        placeRandomMines();
        game.add(grid, BorderLayout.CENTER);
        game.setVisible(true);

        game.add(topHUD, BorderLayout.PAGE_START);
        topHUD.add(flagLabel);
        // Set up the reset button at the top
        topHUD.add(smiley);
        smiley.addMouseListener(this);
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
        boolean win = true;
        for (int x = 0; x < gridSize; x++){
            if (win){
                for (int y = 0; y < gridSize; y++){
                    if (buttonValues[x][y] != MINE && buttons[x][y].isEnabled()){
                        win = false;
                        break;
                    }
                }
            }
        }
        if (win){
            Instant endTime = Instant.now();
            Duration elapsedTime = Duration.between(startTime, endTime);
            JOptionPane.showMessageDialog(game, String.format("You win at %d seconds since the program launched!", (int)elapsedTime.getSeconds()));
        }
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

    @Override
    public void mouseClicked(MouseEvent event) {
        // Smiley button resets the game
        if (event.getSource().equals(smiley)){
            // Reset flag number
            numberOfFlags = numberOfBombs;
            // Reset time
            flagLabel.setText(String.format("%d flags",numberOfFlags));

            for (int x = 0; x < gridSize; x++){
                for (int y = 0; y < gridSize; y++){
                    buttons[x][y].setEnabled(true);
                    buttons[x][y].setText("");
                    if (buttonValues[x][y] == MINE || buttons[x][y].getBackground() == Color.yellow){
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
                        // Right click to place a flag
                        if (SwingUtilities.isRightMouseButton(event)) {
                            // Only allow if it isn't already yellow
                            if (buttons[x][y].isEnabled() && numberOfFlags != 0 && buttons[x][y].getBackground() != Color.yellow){
                                buttons[x][y].setBackground(Color.yellow);
                                buttons[x][y].setEnabled(false);
                                numberOfFlags--;
                            }
                            else if (buttons[x][y].getBackground() == Color.yellow){
                                buttons[x][y].setBackground(null);
                                buttons[x][y].setEnabled(true);
                                numberOfFlags++;
                            }
                            flagLabel.setText(String.format("%d flags",numberOfFlags));
                            checkWin();
                        }
                        // Left click to reveal a square if it isn't flagged
                        else if (SwingUtilities.isLeftMouseButton(event) && buttons[x][y].isEnabled()) {
                            // Game over if it is a mine
                            if (buttonValues[x][y] == MINE){
                                gameOver();
                            }
                            // Reveal empty squares nearby if clicked on a 0
                            else if (buttonValues[x][y] == 0){
                                buttons[x][y].setText(buttonValues[x][y] + "");
                                buttons[x][y].setEnabled(false);
                                ArrayList<Integer> toOpen = new ArrayList<Integer>();
                                toOpen.add(x*100+y);
                                openEmpty(toOpen);
                                checkWin();
                            }
                            // Reveal the clicked square
                            else {
                                buttons[x][y].setText(buttonValues[x][y] + "");
                                buttons[x][y].setEnabled(false);
                                checkWin();
                            }
                        }
                    }
                }
            }
        }
    }
    @Override
    public void mouseEntered(MouseEvent event) {
    }
    @Override
    public void mouseExited(MouseEvent event) {
    }
    @Override
    public void mousePressed(MouseEvent event) {
    }
    @Override
    public void mouseReleased(MouseEvent event) {
    }

}

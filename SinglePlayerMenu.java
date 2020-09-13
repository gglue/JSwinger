import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.JOptionPane;
import java.awt.Container;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Component;
import java.awt.event.InputEvent;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.util.ArrayList;
import java.time.Instant;
import java.time.Duration;

import java.lang.NumberFormatException;
import java.lang.IndexOutOfBoundsException;

public class SinglePlayerMenu implements ActionListener {
    JFrame singlePlayerMenu = new JFrame("JSwinger Menu");
    JLabel menuLabel = new JLabel("Choose your difficulty", SwingConstants.CENTER);
    JPanel menuPanel = new JPanel();

    JButton easy  = new JButton("Easy");
    JButton medium = new JButton("Medium");
    JButton hard = new JButton("Hard");
    JButton custom = new JButton("Custom");

    public SinglePlayerMenu(){

        // Set up the window
        singlePlayerMenu.setSize(225, 200);
        singlePlayerMenu.setResizable(false);
        singlePlayerMenu.setLayout(new BorderLayout());

        singlePlayerMenu.add(menuPanel, BorderLayout.CENTER);
        singlePlayerMenu.add(menuLabel, BorderLayout.PAGE_START);

        easy.setAlignmentX(Component.CENTER_ALIGNMENT);
        medium.setAlignmentX(Component.CENTER_ALIGNMENT);
        hard.setAlignmentX(Component.CENTER_ALIGNMENT);
        custom.setAlignmentX(Component.CENTER_ALIGNMENT);

        easy.addActionListener(this);
        medium.addActionListener(this);
        hard.addActionListener(this);
        custom.addActionListener(this);

        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.add(Box.createRigidArea(new Dimension(0,5)));
        menuPanel.add(easy);
        menuPanel.add(Box.createRigidArea(new Dimension(0,5)));
        menuPanel.add(medium);
        menuPanel.add(Box.createRigidArea(new Dimension(0,5)));
        menuPanel.add(hard);
        menuPanel.add(Box.createRigidArea(new Dimension(0,5)));
        menuPanel.add(custom);

        singlePlayerMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        singlePlayerMenu.setVisible(true);
    }

    public static void main(String[] args){
        new SinglePlayerMenu();
    }

    @Override
    public void actionPerformed(ActionEvent event){
        if (event.getSource().equals(easy)){
            new JSwinger(9, 10);
        }
        if (event.getSource().equals(medium)){
            new JSwinger(16, 40);
        }
        if (event.getSource().equals(hard)){
            new JSwinger(22, 99);
        }
        if (event.getSource().equals(custom)){
            String size = JOptionPane.showInputDialog("Please type the size of the x by x grid");
            String bombs = JOptionPane.showInputDialog("Please type the number of bombs you want");
            if (size != null && bombs != null){
                try {
                    int sizeNumber = Integer.parseInt(size);
                    int bombsNumber = Integer.parseInt(bombs);
                    new JSwinger(sizeNumber, bombsNumber);
                }
                catch (NumberFormatException exception){
                    JOptionPane.showMessageDialog(null, "Invalid input! Using default settings instead.");
                    new JSwinger();
                }
                catch (IndexOutOfBoundsException exception){
                    JOptionPane.showMessageDialog(null, "Bombs can't be more than total tiles! Using default settings instead.");
                    new JSwinger();
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "At least one of the fields were empty, please try again.");
            }
        }
    }
}

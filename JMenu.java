import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.Box;

import java.awt.Container;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.InputEvent;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.util.ArrayList;
import java.time.Instant;
import java.time.Duration;


public class JMenu implements ActionListener {
    JFrame menu = new JFrame("JSwinger Menu");
    JLabel menuLabel = new JLabel("Choose your mode", SwingConstants.CENTER);
    JPanel menuPanel = new JPanel();
    JButton singlePlayer  = new JButton("Singleplayer");
    JButton multiPlayer = new JButton("Multiplayer");

    public JMenu(){
        // Set up the window
        menu.setSize(235, 100);
        menu.setResizable(false);
        menu.add(menuPanel, BorderLayout.CENTER);
        menu.add(menuLabel, BorderLayout.PAGE_START);

        singlePlayer.addActionListener(this);

        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.LINE_AXIS));
        menuPanel.add(Box.createRigidArea(new Dimension(5,0)));
        menuPanel.add(singlePlayer);
        menuPanel.add(Box.createRigidArea(new Dimension(5,0)));
        menuPanel.add(multiPlayer);
        menuPanel.add(Box.createRigidArea(new Dimension(5,0)));

        menu.setVisible(true);
    }

    public static void main(String[] args){
        new JMenu();
    }

    @Override
    public void actionPerformed(ActionEvent event){
        if (event.getSource().equals(singlePlayer)){
            new SinglePlayerMenu();
            menu.dispose();
            menu.setVisible(false);
        }
    }
}

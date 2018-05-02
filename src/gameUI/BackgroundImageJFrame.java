package gameUI;

import javax.swing.*;

import java.awt.*;

import java.awt.event.*;

public class BackgroundImageJFrame extends JFrame
{

    JButton b1;
    JLabel l1;

    public BackgroundImageJFrame() {
    	
        setSize(400,400);
        setVisible(true);

        setLayout(new BorderLayout());

        JLabel background=new JLabel(new ImageIcon("C:\\Users\\varit\\Desktop\\softspec\\SnakeAndLedder\\board.jpg"));
        
        int align = FlowLayout.RIGHT; // or LEFT, RIGHT
        JPanel panel = new JPanel(new FlowLayout(align));
        JLabel turn = new JLabel("Who turn?");
        JButton roll = new JButton("Roll dice");
        
        panel.add(turn);
        panel.add(roll);
        
        add(panel);
        add(background);
        pack();
        background.setLayout(new FlowLayout());

    }

    public static void main(String args[]) 
    {
        new BackgroundImageJFrame();
    }
}
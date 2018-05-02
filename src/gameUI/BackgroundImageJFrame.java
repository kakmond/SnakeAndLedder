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

        add(background);

        background.setLayout(new FlowLayout());

    }

    public static void main(String args[]) 
    {
        new BackgroundImageJFrame();
    }
}
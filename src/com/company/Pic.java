package com.company;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

class TP extends JFrame{
    public TP(String bmpFile){
        Image image = null;
        try {
            image = ImageIO.read(new File(bmpFile));
        } catch (IOException ex) {
        }
        JLabel label = new JLabel(new ImageIcon(image));
        add(label);
        setDefaultCloseOperation(JFrame.ICONIFIED);
        pack();
    }
}

public class Pic extends JFrame implements Runnable{	//继承的是框架JFrame
    public void run() {
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
            }
        });
    }
}
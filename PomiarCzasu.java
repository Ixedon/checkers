package com.warcaby;

import javax.swing.*;
import java.awt.*;

public class PomiarCzasu extends JComponent implements Runnable
{
    Thread timer;
    private int width;
    private int milisekundy;
    private int sekundy;
    private  int minuty;



    public PomiarCzasu(int width)
    {
        timer = new Thread(this);
        this.width = width;
        this.milisekundy = 0;
        this.sekundy = 0;
        this.minuty = 0;



    }
    @Override
    public void paintComponent (Graphics g) {
      //  super.paintComponent(g);
    g.drawString(str(minuty)+":"+str(sekundy) + ":"+str( milisekundy/10),width/2,20);
    }
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                System.out.println("Interrupted: " + e.getMessage());
            }
            milisekundy+=50;
            if(milisekundy == 1000){sekundy+=1;milisekundy = 0;}
            if(sekundy == 60){minuty+=1;sekundy = 0;}
            repaint();
        }
    }

    public void begin()
    {
        timer.start();
    }

    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(width,30);
    }
    private String str(int a)
    {
        String s = Integer.toString(a);
        if(s.length() < 2 )s="0"+s;
        return s;
    }


}

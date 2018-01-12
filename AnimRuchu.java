package com.warcaby;

import javax.swing.JComponent;
import java.awt.Graphics;

import static java.lang.Math.abs;

public class AnimRuchu extends JComponent implements Runnable {

    private Thread animator;
    private int x,y,nx,ny;
    Pionek pionek;
    Plansza plansza;

    public AnimRuchu(Pionek pio, int nx, int ny, Plansza plansza)
    {
        this.pionek = pio;
        this.x = pio.getX();
        this.y = pio.getY();
        this.plansza = plansza;
        this.nx = nx;
        this.ny = ny;
        animator = new Thread(this);
    }

    public void begin()
    {
        animator.start();

    }

    @Override
    public void paint(Graphics g) {

    }

    @Override
    public void run() {

        //pionek.setX(0);
        //pionek.setY(0);
        while (abs(pionek.getX() - nx) >0 && abs(pionek.getY()-ny)>0)
        {
            if(abs(pionek.getX() - nx) >0)
                pionek.setX(pionek.getX() - getsign(pionek.getX() - nx));
            if(abs(pionek.getY()-ny)>0)
                pionek.setY(pionek.getY() - getsign(pionek.getY()-ny));

            plansza.repaint();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("Interrupted: " + e.getMessage());
            }
        }

    }
    private  int getsign(int a){
        if(a<0)return -1;
        else return 1;
    }

}

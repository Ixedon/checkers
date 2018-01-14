package com.warcaby;

import java.awt.Graphics;
import java.awt.Color;

import java.util.Scanner;

public class Pionek {
    private static int srednica;
    private static int bok_pola;
    private int kolor;
    private int x,y;
    private int xp, yp;
    private int typ;
    private int zbity;
    private int zbijany;
    private int damka;
    private int ip;
    private boolean inanim;
    private Pole pole;

    public Pionek(int kolor,int x,int y, Pole pole, int bok_pola, int ip)
    {
        this.kolor = kolor;
        this.x = x;
        this.y = y;
        this.zbijany = 0;
        this.zbity = 0;
        this.damka = 0;
        this.inanim = false;
        this.ip = ip-1;
        this.bok_pola = bok_pola;
        this.pole = pole;
        srednica = (int) (bok_pola * 0.8);
    }

    public void draw (Graphics g)
    {
        int roz = (bok_pola - srednica)/2;
        int obw = 4;
        g.setColor(Color.WHITE);
        g.fillOval(x*bok_pola + roz-obw/2, y*bok_pola + roz-obw/2 , srednica+obw, srednica+obw);

        if(kolor==-1) g.setColor(Color.RED);
        else g.setColor(Color.DARK_GRAY);
        g.fillOval(x*bok_pola + roz, y*bok_pola + roz , srednica, srednica);

        if(damka == 1)
        {
            g.setColor(Color.YELLOW);
            g.fillOval(x*bok_pola + roz + srednica/4, y*bok_pola + roz + srednica/4 , srednica-srednica/2, srednica-srednica/2);
        }

    }

    public void drawonpoint (Graphics g)
    {
        int roz = (bok_pola - srednica)/2;
        int obw = 4;
        g.setColor(Color.WHITE);
        g.fillOval(xp + roz-obw/2, yp + roz-obw/2 , srednica+obw, srednica+obw);

        if(kolor==-1) g.setColor(Color.RED);
        else g.setColor(Color.DARK_GRAY);
        g.fillOval(xp + roz, yp + roz , srednica, srednica);

        if(damka == 1)
        {
            g.setColor(Color.YELLOW);
            g.fillOval(xp + roz + srednica/4, yp + roz + srednica/4 , srednica-srednica/2, srednica-srednica/2);
        }

    }

    public void przesun(int x, int y, Pole pole, Plansza plansza, boolean bicie)
    {
        this.pole.usun();
       // this.x = x;
        //this.y = y;
        this.inanim = true;
        AnimRuchu anim = new AnimRuchu(this,x,y,plansza,bok_pola);
        if(bicie) anim.setzbijany(pole.getZbijany());
        else anim.setzbijany(null);
        anim.begin();
        //System.out.println("Start");

        this.pole = pole;
        this.pole.wstaw(this);
        //System.out.println();
       // plansza.repaint();


    }

    public void checkdamka()
    {
        if(kolor == 1 && this.y == this.ip-1 || kolor == -1 && this.y == 1)
        {
            this.damka = 1;
            System.out.println(1);
        }
    }


    public void bij(int x, int y, Pole [][] pola, Plansza plansza)
    {
        //pola[(this.x + x)/2][(this.y + y)/2].getPionek().zbij();
        przesun( x, y, pola[x][y],plansza,true);

        //pola [x][y].getZbijany()
        pola [x][y].getZbijany().zbij(pola,plansza);

    }

    public void zbij(Pole [][] pola, Plansza plansza)
    {
       // this.zbity = 1;
       // przesun(0, 0, pola[x][y],plansza);

        //this.x = 0;
        //this.y = 0;

        this.pole.usun();
    }

    public void setZbity(int zbity) {this.zbity = zbity;}

    public int czyzbity(){return this.zbity;}
    public int czydamka(){return this.damka;}
    public int getKolor(){return this.kolor;}


    public void setXY(int x, int y) {this.x = x; this.y =y;}
    public void setX(int x) {this.xp = x;}
    public void setY(int y) {this.yp = y;}
    public int getY() {return y;}
    public int getX() {return x;}

    public void setInanim(boolean inanim) {this.inanim = inanim;}
    public boolean isInanim() {return inanim;}
}

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
    private Color col;

    public Pionek(int kolor,int x,int y, Pole pole, int bok_pola, int ip, Color col)  //operacje pionka
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
        this.col = col;
        srednica = (int) (bok_pola * 0.8);
    }

    public void draw (Graphics g)  //ryswoanei na polu
    {
        int roz = (bok_pola - srednica)/2;
        int obw = 4;
        g.setColor(Color.WHITE);
        g.fillOval(x*bok_pola + roz-obw/2, y*bok_pola + roz-obw/2 , srednica+obw, srednica+obw);

        g.setColor(col);
        g.fillOval(x*bok_pola + roz, y*bok_pola + roz , srednica, srednica);

        if(damka == 1)
        {
            g.setColor(Color.YELLOW);
            g.fillOval(x*bok_pola + roz + srednica/4, y*bok_pola + roz + srednica/4 , srednica-srednica/2, srednica-srednica/2);
        }

    }

    public void drawonpoint (Graphics g)   //rysownie n abezwzglednych pozycjach
    {
        int roz = (bok_pola - srednica)/2;
        int obw = 4;
        g.setColor(Color.WHITE);
        g.fillOval(xp + roz-obw/2, yp + roz-obw/2 , srednica+obw, srednica+obw);

        g.setColor(col);
        g.fillOval(xp + roz, yp + roz , srednica, srednica);

        if(damka == 1)
        {
            g.setColor(Color.YELLOW);
            g.fillOval(xp + roz + srednica/4, yp + roz + srednica/4 , srednica-srednica/2, srednica-srednica/2);
        }

    }

    public void przesun(int x, int y, Pole pole, Plansza plansza, boolean bicie)    //przesuniecie
    {
        this.pole.usun();
        this.inanim = true;
        Plansza.inanim = true;
        AnimRuchu anim = new AnimRuchu(this,x,y,plansza,bok_pola);               //stworzenie animacji
        if(bicie) anim.setzbijany(pole.getZbijany());
        else anim.setzbijany(null);
        anim.begin();

        this.pole = pole;
        this.pole.wstaw(this);



    }

    public void checkdamka()    //sprawdznie czy jest damka
    {
        if(kolor == 1 && this.y == this.ip-1 || kolor == -1 && this.y == 1)
        {
            this.damka = 1;
            System.out.println(1);
        }
    }


    public void bij(int x, int y, Pole [][] pola, Plansza plansza)   //bicie innego
    {
        przesun( x, y, pola[x][y],plansza,true);
        pola [x][y].getZbijany().zbij(pola,plansza);

    }

    public void zbij(Pole [][] pola, Plansza plansza)  //bycie zbitym
    {
        if(kolor == -1)Plansza.iloscPio1 -=1;
        else Plansza.iloscPio2 -=1;
        this.pole.usun();
    }

    public void setZbity(int zbity) {this.zbity = zbity;}  //parametry

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

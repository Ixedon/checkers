package com.warcaby;

import java.awt.Color;
import java.awt.Graphics;

public class Pole {
    private int x,y;
    private int bok_pola;
    private int selected;

    public Pole(int x,int y, int bok_pola)
    {
        this.x = x;
        this.y = y;
        this.bok_pola = bok_pola;
        this.selected = 0;
    }
    public void draw(Graphics g)
    {
        if(selected == 1) g.setColor(Color.BLUE);
        else if((x+y)%2==1)g.setColor(Color.BLACK);
        else g.setColor(Color.WHITE);
        g.fillRect(x*bok_pola, y*bok_pola, bok_pola, bok_pola);
    }
    public void select() {this.selected=1;}
    public void deselect() {this.selected=0;}

}

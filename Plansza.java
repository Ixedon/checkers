package com.warcaby;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JComponent;

public class Plansza extends JComponent
{

    private static int bok_planszy = 600;
    private static int bok_pola = bok_planszy/8;

    private Dimension prefRozmiar;
    private List<Pionek> pionki;
    private Pionek [][] plan_pion ;
    private Pole [][] pola ;
    private Pole selected;

    public Plansza()
    {
        prefRozmiar = new Dimension(bok_planszy, bok_planszy);
        pionki = new ArrayList<>();
        plan_pion = new Pionek[8][8];
        pola = new Pole[8][8];
        for (int i=0;i<8;i++)
        {
            for(int j=0;j<8;j++)
            {
                if(j<3 && (i+j)%2==1)
                {
                    plan_pion[i][j] = new Pionek(1, i,j,bok_pola);
                    pionki.add(plan_pion[i][j]);
                }

                if(j>4 && (i+j)%2==1)
                {
                    plan_pion[i][j] = new Pionek(0, i, j, bok_pola);
                    pionki.add(plan_pion[i][j]);
                }

                pola[i][j] = new Pole(i,j,bok_pola);
            }
        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed (MouseEvent me)
            {
                // Obtain mouse coordinates at time of press.

                int x = me.getX()/bok_pola;
                int y = me.getY()/bok_pola;
//                System.out.println(x);
//                System.out.println(y);
                if(plan_pion[x][y] == null) return;
                if(selected !=null) selected.deselect();
                selected = pola[x][y];
                pola[x][y].select();
                repaint();
            }

        });

    }




    @Override
    public Dimension getPreferredSize()
    {
        return prefRozmiar;
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        rysujPlansze(g);
        for(Pionek pio : pionki)
        {
            pio.draw(g);
        }
    }

    private void rysujPlansze(Graphics g)
    {
        for (int i =0;i<8;i++)
        {
            for(int j=0;j<8;j++)
            {
                pola[i][j].draw(g);
            }

        }

    }

}

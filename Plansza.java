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

    private static int bok_planszy = 500;
    private int ip = 8 + 2;
    private  int bok_pola = bok_planszy/ip;


    private Dimension prefRozmiar;
    private List<Pionek> pionki;
    private Pole [][] pola ;
    private Pole selected;
    private List<Pole>mozliwosci;
    private List<Pole>mozbicia;
    Plansza plansza;

    public Plansza()
    {
        prefRozmiar = new Dimension(bok_planszy/*+2*bok_pola*/, bok_planszy/*+2*bok_pola*/);
        pionki = new ArrayList<>();
        mozliwosci = new ArrayList<>();
        mozbicia = new ArrayList<>();
        pola = new Pole[ip][ip];
        plansza = this;

        Pionek pionek;

        for (int i=0;i<ip;i++)
        {
            for(int j=0;j<ip;j++)
            {
                pola[i][j] = new Pole(i,j,bok_pola,!naplanszy(i,j));

                if(naplanszy(i,j) && j<ip/2-1 && (i+j)%2==1)
                {
                    pionek = new Pionek(1, i,j, pola[i][j], bok_pola,ip);
                    pionki.add(pionek);
                    pola[i][j].wstaw(pionek);
                }

                if( naplanszy(i,j) && j>ip/2 && (i+j)%2==1)
                {
                    pionek = new Pionek(-1, i,j,pola[i][j], bok_pola,ip);
                    pionki.add(pionek);
                    pola[i][j].wstaw(pionek);
                }


            }
        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed (MouseEvent me)
            {
                // Obtain mouse coordinates at time of press.

                int x = me.getX()/bok_pola;
                int y = me.getY()/bok_pola;
                if(!naplanszy(x,y))return;

                

                if(pola[x][y].czyzajete() == 1)       //na pionek
                {

                    if(selected !=null) {selected.deselect(); selected = null;}
                    decolor();
                    selected = pola[x][y];
                    pola[x][y].select();

                    if(pola[x][y].getPionek().czydamka()== 0)
                    {
                        int kierunek = pola[x][y].getPionek().getKolor();
                        ruch(x,y,1,kierunek);
                        ruch(x,y,-1,kierunek);

                        bicie(x,y,1,kierunek);
                        bicie(x,y,-1,kierunek);
                        bicie(x,y,1,-kierunek);
                        bicie(x,y,-1,-kierunek);
                    }
                    else
                    {
                        ruchdamka(x,y,1,1);
                        ruchdamka(x,y,-1,1);
                        ruchdamka(x,y,1,-1);
                        ruchdamka(x,y,-1,-1);
                    }

                }
                else if(selected != null && pola[x][y].czymozliwe() == 1)    //na puste pole
                {
                    decolor();
                    selected.getPionek().przesun(x,y,pola[x][y],plansza,false);
                }
                else if(selected !=null && pola[x][y].czybicie() == 1)           //bicie
                {
                    decolor();
                    selected.getPionek().bij(x,y,pola, plansza);

                }


                repaint();
            }

        });

    }



    private void decolor()
    {
        for (Pole p : mozliwosci)
        {
            p.niemozliwe();
        }
        mozliwosci.clear();
    }


    private boolean naplanszy(int x, int y)
    {
        return x>=1 && x<ip-1 && y>=1 && y<ip-1;
    }

    private boolean czybicie(int x, int y,int strona, int kierunek)
    {
        return  naplanszy(x,y) &&
                naplanszy(x+strona,y+kierunek) &&
                pola[x][y].czyzajete() == 1 &&
                pola[x+strona][y+kierunek].czyzajete() == 0 &&
                pola[x][y].getPionek().getKolor() != selected.getPionek().getKolor();

    }

    private void bicie(int x,int y, int strona, int kierunek)
    {
        if(czybicie(x+strona,y+kierunek,strona, kierunek))
        {
            pola[x+strona*2][y+kierunek*2].mozbicie(pola[x+strona][y+kierunek].getPionek());
            mozliwosci.add(pola[x+strona*2][y+kierunek*2]);
        }
    }

    private void ruch(int x,int y, int strona, int kierunek)
    {
        if(naplanszy(x+strona,y+kierunek) && pola[x+strona][y+kierunek].czyzajete() == 0)
        {
            pola[x+strona][y+kierunek].mozliwe();
            mozliwosci.add(pola[x+strona][y+kierunek]);
        }
    }

    private void ruchdamka(int x,int y, int strona, int kierunek)
    {
        int a=strona,b=kierunek,c=0;
        Pionek zbijany = null;
        while (naplanszy(x+a, y+b))
        {
            if(pola[x+a][y+b].czyzajete() == 0)
            {
                if(c==0) pola[x + a][y + b].mozliwe();
                else if(c == 1 && zbijany!=null)
                        pola[x + a][y + b].mozbicie(zbijany);
                else break;
                mozliwosci.add(pola[x+a][y+b]);
            }
            else
            {
                c++;
                if(selected.getPionek().getKolor() != pola[x+a][y+b].getPionek().getKolor())
                    zbijany = pola[x+a][y+b].getPionek();
            }
            x+=strona;
            y+=kierunek;
        }
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
            if(pio.czyzbity() == 0)
            {
                if(!pio.isInanim())pio.draw(g);
                //else pio.drawonpoint(g);
            }
        }
        for(Pionek pio : pionki)if(pio.isInanim())pio.drawonpoint(g);
    }

    private void rysujPlansze(Graphics g)
    {
        for (int i =0;i<ip;i++)
        {
            for(int j=0;j<ip;j++)
            {
                pola[i][j].draw(g);
            }

        }

    }

}

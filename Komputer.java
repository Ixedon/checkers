package com.warcaby;

public class Komputer implements Runnable{  //watek odpalenia ruchu komputera
    private Plansza plansza;
    public Komputer(Plansza plansza)
    {
        this.plansza = plansza;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(500);        //opoznienie na ruch gracza
        } catch (InterruptedException e) {
            System.out.println("Interrupted: " + e.getMessage());
        }
        plansza.komuter();
    }
}

package it.uniroma2.dicii.bd.thread;

import it.uniroma2.dicii.bd.controller.ImportController;
import it.uniroma2.dicii.bd.dao.DaoFactory;
import it.uniroma2.dicii.bd.exception.DaoException;
import it.uniroma2.dicii.bd.interfaces.FilamentDao;
import it.uniroma2.dicii.bd.model.Filament;

import java.util.LinkedList;

public class FilamentThread implements Runnable{

    private LinkedList<Filament> filaments;
    private FilamentDao dao;

    public FilamentThread(LinkedList<Filament> filaments) {
        this.filaments = filaments;
        try {
            this.dao = DaoFactory.getSingletonInstance().getFilamentDAO();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        // COMPARE THIS WITH THE CSV SIZE
        if (ImportController.DEBUG){

            System.out.println("DEBUG MODE");
            ImportController.increaseDebugCounter(filaments.size());

        }else{

            System.out.println(Thread.currentThread().getId() + " Start");
            for (;;){

                if (filaments.size() == 0)
                    break;

                try{
                    dao.insertFilament(filaments.poll());
                }catch (DaoException e){
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getId() + " Stop");
        }
    }
}

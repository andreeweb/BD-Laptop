package it.uniroma2.dicii.bd.thread;

import it.uniroma2.dicii.bd.dao.DaoFactory;
import it.uniroma2.dicii.bd.exception.DaoException;
import it.uniroma2.dicii.bd.interfaces.FilamentDao;
import it.uniroma2.dicii.bd.model.Filament;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.concurrent.locks.Lock;

public class ImportThread implements Runnable{

    private LinkedList<Filament> filaments;
    private FilamentDao dao;

    public ImportThread(LinkedList<Filament> filaments) {

        this.filaments = filaments;

        try {
            this.dao = DaoFactory.getSingletonInstance().getFilamentDAO();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        System.out.println(Thread.currentThread().getName() + " Spawn!");

        // init connection

        for (;;){

            if (filaments.size() == 0) {
                break;
            }

            try{

                dao.insert(filaments.poll());

            }catch (DaoException e){

                e.printStackTrace();
            }
        }
    }
}

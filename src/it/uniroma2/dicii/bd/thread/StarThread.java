package it.uniroma2.dicii.bd.thread;

import it.uniroma2.dicii.bd.controller.ImportController;
import it.uniroma2.dicii.bd.dao.DaoFactory;
import it.uniroma2.dicii.bd.exception.DaoException;
import it.uniroma2.dicii.bd.interfaces.StarDao;
import it.uniroma2.dicii.bd.model.Star;

import java.util.LinkedList;

public class StarThread implements Runnable {

    private LinkedList<Star> stars;
    private StarDao dao;

    public StarThread(LinkedList<Star> stars) {
        this.stars = stars;
        try {
            this.dao = DaoFactory.getSingletonInstance().getStarDAO();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        // COMPARE THIS WITH THE CSV SIZE
        if (ImportController.DEBUG) {

            System.out.println("DEBUG MODE");
            ImportController.increaseDebugCounter(stars.size());

        } else {

            System.out.println(Thread.currentThread().getId() + " Start");
            for (; ; ) {

                if (stars.size() == 0)
                    break;

                try {
                    dao.insertStar(stars.poll());
                } catch (DaoException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
            }
            System.out.println(Thread.currentThread().getId() + " Stop");
        }
    }
}

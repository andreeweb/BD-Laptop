package it.uniroma2.dicii.bd.thread;

import it.uniroma2.dicii.bd.controller.ImportController;
import it.uniroma2.dicii.bd.dao.DaoFactory;
import it.uniroma2.dicii.bd.exception.DaoException;
import it.uniroma2.dicii.bd.interfaces.BranchDao;
import it.uniroma2.dicii.bd.interfaces.FilamentDao;
import it.uniroma2.dicii.bd.model.Branch;
import it.uniroma2.dicii.bd.model.Filament;

import java.util.LinkedList;

public class SkeletonThread implements Runnable{

    private LinkedList<Branch> branches;
    private BranchDao dao;

    public SkeletonThread(LinkedList<Branch> branches) {
        this.branches = branches;
        try {
            this.dao = DaoFactory.getSingletonInstance().getBranchDAO();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        // COMPARE THIS WITH THE CSV SIZE
        if (ImportController.DEBUG){

            System.out.println("DEBUG MODE");
            for (Branch branch : branches)
                ImportController.increaseDebugCounter(branch.getgPointBranch().size());

        }else{

            System.out.println(Thread.currentThread().getId() + " Start");
            for (;;){

                if (branches.size() == 0)
                    break;

                Branch branch = branches.poll();

                try{
                    dao.insertBranch(branch);
                }catch (DaoException e){
                    e.printStackTrace();
                }
            }

            System.out.println(Thread.currentThread().getId() + " Stop");
        }
    }
}

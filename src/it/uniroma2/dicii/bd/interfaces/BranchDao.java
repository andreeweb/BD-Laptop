package it.uniroma2.dicii.bd.interfaces;

import it.uniroma2.dicii.bd.exception.DaoException;
import it.uniroma2.dicii.bd.model.Branch;

public interface BranchDao {

    /**
     * Insert branch on database
     *
     * @throws DaoException
     */
    void insertBranch(Branch branch) throws DaoException;
}

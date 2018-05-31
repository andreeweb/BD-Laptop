package it.uniroma2.dicii.bd.interfaces;

import it.uniroma2.dicii.bd.exception.DaoException;
import it.uniroma2.dicii.bd.model.Branch;
import it.uniroma2.dicii.bd.model.GPoint;

public interface BranchDao {

    /**
     * Insert branch on database
     *
     * @throws DaoException
     */
    void insertBranch(Branch branch) throws DaoException;

    /**
     *
     * @param branch
     * @return
     * @throws DaoException
     */
    GPoint getBranchMaxVertex(Branch branch) throws DaoException;

    /**
     *
     * @param branch
     * @return
     * @throws DaoException
     */
    GPoint getBranchMinVertex(Branch branch) throws DaoException;
}

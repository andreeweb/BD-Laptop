package it.uniroma2.dicii.bd.interfaces;

import it.uniroma2.dicii.bd.exception.DaoException;
import it.uniroma2.dicii.bd.model.Satellite;
import it.uniroma2.dicii.bd.model.Tool;

import java.util.List;

public interface ToolDao {

    /**
     * Get all tools.
     *
     * @return list of all tools
     * @throws DaoException
     */
    List<Tool> getAllTools() throws DaoException;

    /**
     * Insert tool on db
     *
     * @param tool
     * @throws DaoException
     */
    void insertTool(Tool tool) throws DaoException;

    /**
     * Delete tool from db
     *
     * @param tool
     * @throws DaoException
     */
    void deleteTool(Tool tool) throws DaoException;

    /**
     *
     * Get all tools per satellite
     *
     * @param satellite
     * @return
     * @throws DaoException
     */
    List<Tool> getTools(Satellite satellite) throws DaoException;
}

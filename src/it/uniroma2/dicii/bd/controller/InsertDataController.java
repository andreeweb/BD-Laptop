package it.uniroma2.dicii.bd.controller;

import it.uniroma2.dicii.bd.bean.SatelliteBean;
import it.uniroma2.dicii.bd.bean.ToolBean;
import it.uniroma2.dicii.bd.dao.DaoFactory;
import it.uniroma2.dicii.bd.exception.DaoException;
import it.uniroma2.dicii.bd.interfaces.SatelliteDao;
import it.uniroma2.dicii.bd.interfaces.ToolDao;
import it.uniroma2.dicii.bd.model.Satellite;
import it.uniroma2.dicii.bd.model.Tool;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for Insert data Use Case
 *
 * @author Andrea Cerra
 */
public class InsertDataController {

    /**
     * Get all satellites from database
     *
     * @return satellite list
     * @throws DaoException error in dao
     */
    public List<SatelliteBean> getSatelliteBeanList() throws DaoException {

        SatelliteDao dao = DaoFactory.getSingletonInstance().getSatelliteDAO();
        List<Satellite> list = dao.getSatellites();
        List<SatelliteBean> beanList = new ArrayList<SatelliteBean>();

        for (Satellite satellite:list) {

            SatelliteBean satelliteBean = new SatelliteBean(satellite.getName());
            satelliteBean.setFirstObservation(satellite.getFirstObservation());
            satelliteBean.setLastObservation(satellite.getLastObservation());
            satelliteBean.setAgency(satellite.getAgency());

            beanList.add(satelliteBean);

        }

        return beanList;

    }

    /**
     * Save satellite on db
     *
     * @param satelliteBean
     * @throws DaoException
     */
    public void saveSatellite(SatelliteBean satelliteBean) throws DaoException {

        Satellite satellite = new Satellite(satelliteBean.getName());
        satellite.setFirstObservation(satelliteBean.getFirstObservation());
        satellite.setLastObservation(satelliteBean.getLastObservation());
        satellite.setAgency(satelliteBean.getAgency());

        SatelliteDao dao = DaoFactory.getSingletonInstance().getSatelliteDAO();
        dao.insertSatellite(satellite);
    }

    /**
     * Delete satellite from DB
     *
     * @param satelliteBean
     * @throws DaoException
     */
    public void deleteSatellite(SatelliteBean satelliteBean) throws DaoException {

        Satellite satellite = new Satellite(satelliteBean.getName());
        satellite.setFirstObservation(satelliteBean.getFirstObservation());
        satellite.setLastObservation(satelliteBean.getLastObservation());
        satellite.setAgency(satelliteBean.getAgency());

        SatelliteDao dao = DaoFactory.getSingletonInstance().getSatelliteDAO();
        dao.deleteSatellite(satellite);
    }

    /**
     * Get all tools from database
     *
     * @return tools list
     * @throws DaoException error in dao
     */
    public List<ToolBean> getToolBeanList() throws DaoException {

        ToolDao dao = DaoFactory.getSingletonInstance().getToolDAO();
        List<Tool> list = dao.getTools();
        List<ToolBean> beanList = new ArrayList<ToolBean>();

        for (Tool tool:list) {

            ToolBean toolBean = new ToolBean(tool.getName());
            toolBean.setBand(tool.getBand());

            SatelliteBean satelliteBean = new SatelliteBean(tool.getSatellite().getName());

            toolBean.setSatellite(satelliteBean);

            beanList.add(toolBean);

        }

        return beanList;

    }

    /**
     * Save tool on db
     *
     * @param toolBean
     * @throws DaoException
     */
    public void saveTool(ToolBean toolBean) throws DaoException {

        Tool tool = new Tool();
        tool.setName(toolBean.getName());
        tool.setBand(toolBean.getBand());

        Satellite satellite = new Satellite(toolBean.getSatellite().getName());

        tool.setSatellite(satellite);

        ToolDao dao = DaoFactory.getSingletonInstance().getToolDAO();
        dao.insertTool(tool);
    }

    /**
     * Delete tool from DB
     *
     * @param toolBean
     * @throws DaoException
     */
    public void deleteTool(ToolBean toolBean) throws DaoException {

        Tool tool = new Tool();
        tool.setName(toolBean.getName());
        tool.setBand(toolBean.getBand());

        Satellite satellite = new Satellite(toolBean.getSatellite().getName());

        tool.setSatellite(satellite);

        ToolDao dao = DaoFactory.getSingletonInstance().getToolDAO();
        dao.deleteTool(tool);
    }
}

package it.uniroma2.dicii.bd.junit;

import it.uniroma2.dicii.bd.bean.SatelliteBean;
import it.uniroma2.dicii.bd.bean.ToolBean;
import it.uniroma2.dicii.bd.controller.InsertDataController;
import it.uniroma2.dicii.bd.exception.DaoException;
import org.junit.Assert;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestREQ3 {

    @Test
    public void test1InsertSatellite() throws DaoException, ParseException {

        SatelliteBean satelliteBean = new SatelliteBean("satelliteTest");

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date parsed = format.parse("1970-01-01");
        java.sql.Date sql = new java.sql.Date(parsed.getTime());

        satelliteBean.setFirstObservation(sql);
        satelliteBean.setLastObservation(sql);
        satelliteBean.setAgency("AgencyTEST");

        InsertDataController controller = new InsertDataController();
        controller.saveSatellite(satelliteBean);

        List<SatelliteBean> listS = controller.getSatelliteBeanList();

        Boolean satelliteFounded = false;
        for (SatelliteBean satelliteB : listS){
            if (satelliteB.getName().equals("satelliteTest"))
                satelliteFounded = true;
        }

        Assert.assertTrue(satelliteFounded);
    }

    @Test
    public void test2InsertTool() throws DaoException {

        ToolBean toolBean = new ToolBean("toolTest");
        toolBean.setBand("250,350,500");

        SatelliteBean satelliteBean = new SatelliteBean("satelliteTest");
        toolBean.setSatellite(satelliteBean);

        InsertDataController controller = new InsertDataController();
        controller.saveTool(toolBean);

        List<ToolBean> listS = controller.getToolBeanList();

        Boolean toolFounded = false;
        for (ToolBean toolB : listS){
            if (toolB.getName().equals("toolTest"))
                toolFounded = true;
        }

        Assert.assertTrue(toolFounded);
    }

    @Test
    public void test3RemoveTool() throws DaoException {

        ToolBean toolBean = new ToolBean("toolTest");

        SatelliteBean satelliteBean = new SatelliteBean("satelliteTest");
        toolBean.setSatellite(satelliteBean);

        InsertDataController controller = new InsertDataController();
        controller.deleteTool(toolBean);

        List<ToolBean> listS = controller.getToolBeanList();

        Boolean toolFounded = false;
        for (ToolBean toolB : listS){
            if (toolB.getName().equals("toolTest"))
                toolFounded = true;
        }

        Assert.assertFalse(toolFounded);
    }

    @Test
    public void test4RemoveSatellite() throws DaoException {

        SatelliteBean satelliteBean = new SatelliteBean("satelliteTest");

        InsertDataController controller = new InsertDataController();
        controller.deleteSatellite(satelliteBean);

        List<SatelliteBean> listS = controller.getSatelliteBeanList();

        Boolean satelliteFounded = false;
        for (SatelliteBean satelliteB : listS){
            if (satelliteB.getName().equals("satelliteTest"))
                satelliteFounded = true;
        }

        Assert.assertFalse(satelliteFounded);
    }
}

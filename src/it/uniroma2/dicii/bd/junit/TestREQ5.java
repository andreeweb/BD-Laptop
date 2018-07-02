package it.uniroma2.dicii.bd.junit;

import it.uniroma2.dicii.bd.bean.GPointBean;
import it.uniroma2.dicii.bd.controller.SearchController;
import it.uniroma2.dicii.bd.exception.DaoException;
import org.junit.Assert;
import org.junit.Test;

public class TestREQ5 {

    @Test
    public void testSearchFilamentByID() throws DaoException {

        SearchController controller = new SearchController();

        Integer filamentID = 52;

        GPointBean centroid = controller.getFilamentCentroidByID(filamentID);
        GPointBean extension = controller.getFilamentExtensionByID(filamentID);
        Integer numberOfSegment = controller.getNumberOfBranchByID(filamentID);

        Assert.assertEquals(Double.valueOf(-1.2120173913043477), centroid.getGlatitude());
        Assert.assertEquals(Double.valueOf(13.810169999999998), centroid.getGlongitude());

        Assert.assertEquals(Double.valueOf(0.09902999999999995), extension.getGlatitude());
        Assert.assertEquals(Double.valueOf(0.05111000000000132), extension.getGlongitude());

        Assert.assertEquals(Long.valueOf(3), Long.valueOf(numberOfSegment));
    }

    @Test
    public void testSearchFilamentByName() throws DaoException {

        SearchController controller = new SearchController();

        String name = "HiGALFil013.8059-1.2194";

        GPointBean centroid = controller.getFilamentCentroidByName(name);
        GPointBean extension = controller.getFilamentExtensionByName(name);
        Integer numberOfSegment = controller.getNumberOfBranchByName(name);

        Assert.assertEquals(Double.valueOf(-1.2120173913043477), centroid.getGlatitude());
        Assert.assertEquals(Double.valueOf(13.810169999999998), centroid.getGlongitude());

        Assert.assertEquals(Double.valueOf(0.09902999999999995), extension.getGlatitude());
        Assert.assertEquals(Double.valueOf(0.05111000000000132), extension.getGlongitude());

        Assert.assertEquals(Long.valueOf(3), Long.valueOf(numberOfSegment));
    }
}

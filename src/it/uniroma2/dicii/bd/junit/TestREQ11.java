package it.uniroma2.dicii.bd.junit;

import it.uniroma2.dicii.bd.bean.GPointBean;
import it.uniroma2.dicii.bd.controller.SearchController;
import it.uniroma2.dicii.bd.exception.DaoException;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class TestREQ11 {

    @Test
    public void testDistanceVertex() throws DaoException {

        SearchController controller = new SearchController();

        Integer branchID = 26;
        Integer filamentID = 45;

        List<GPointBean> vertex = controller.getBranchVertex(branchID, filamentID);

        GPointBean minVertex = vertex.get(1);

        Assert.assertEquals(Double.valueOf(-1.25217), minVertex.getGlatitude());
        Assert.assertEquals(Double.valueOf(13.9134), minVertex.getGlongitude());

        GPointBean maxVertex = vertex.get(0);

        Assert.assertEquals(Double.valueOf(13.9293), maxVertex.getGlongitude());
        Assert.assertEquals(Double.valueOf(-1.21703), maxVertex.getGlatitude());

        Assert.assertEquals(Double.valueOf(0.015710486943440516), controller.getVertexDistanceFromBoundary(minVertex, filamentID));
        Assert.assertEquals(Double.valueOf(0.01289718186271708), controller.getVertexDistanceFromBoundary(maxVertex, filamentID));

    }

    @Test (expected = DaoException.class)
    public void testCaseException() throws DaoException {

        SearchController controller = new SearchController();

        Integer branchID = 45;
        Integer filamentID = 52;

        List<GPointBean> vertex = controller.getBranchVertex(branchID, filamentID);
    }
}

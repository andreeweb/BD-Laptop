package it.uniroma2.dicii.bd.junit;

import it.uniroma2.dicii.bd.bean.GPointBean;
import it.uniroma2.dicii.bd.controller.SearchController;
import it.uniroma2.dicii.bd.exception.DaoException;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class TestREQ10 {

    @Test
    public void testStarFilamentInRegion() throws DaoException {

        SearchController controller = new SearchController();

        Float side1Size = 12.0f;
        Float side2Size = 11.0f;

        Double centerLatitude = 0.0;
        Double centerLongitude = 0.0;

        GPointBean gPointBean = new GPointBean(centerLongitude, centerLatitude);

        Map<String, Float> map = controller.getStarInsideRectRegion(gPointBean, side1Size, side2Size);

        Assert.assertEquals("702", String.format("%.0f", map.get("totalStarInsideFilament")));
        Assert.assertEquals("0", String.format("%.0f", map.get("totalStarOutsideFilament")));

        Assert.assertEquals("17", String.format("%.0f", map.get("percentageOfProtostellarInsideFilament")));
        Assert.assertEquals("70", String.format("%.0f", map.get("percentageOfPrestellarInsideFilament")));
        Assert.assertEquals("13", String.format("%.0f", map.get("percentageOfUnboundInsideFilament")));

        Assert.assertEquals("NaN", String.format("%.0f", map.get("percentageOfProtostellarOutsideFilament")));
        Assert.assertEquals("NaN", String.format("%.0f", map.get("percentageOfPrestellarOutsideFilament")));
        Assert.assertEquals("NaN", String.format("%.0f", map.get("percentageOfUnboundOutsideFilament")));

    }
}

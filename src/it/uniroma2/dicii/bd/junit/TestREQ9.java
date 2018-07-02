package it.uniroma2.dicii.bd.junit;

import it.uniroma2.dicii.bd.controller.SearchController;
import it.uniroma2.dicii.bd.exception.DaoException;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class TestREQ9 {

    @Test
    public void searchStarInsideFilament() throws DaoException {

        SearchController controller = new SearchController();

        Map<String, Float> map = controller.countStarsInsideFilamentByID(45);

        Assert.assertEquals("1230", String.format("%.0f", map.get("totalStar")));
        Assert.assertEquals("12", String.format("%.0f", map.get("percentageOfUnbound")));
        Assert.assertEquals("63", String.format("%.0f", map.get("percentageOfPrestellar")));
        Assert.assertEquals("25", String.format("%.0f", map.get("percentageOfProtostellar")));

    }
}

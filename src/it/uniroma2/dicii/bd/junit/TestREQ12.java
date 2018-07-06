package it.uniroma2.dicii.bd.junit;

import it.uniroma2.dicii.bd.bean.StarBean;
import it.uniroma2.dicii.bd.controller.SearchController;
import it.uniroma2.dicii.bd.exception.DaoException;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestREQ12 {

    @Test
    public void testStarFilamentDistance() throws DaoException {

        SearchController controller = new SearchController();

        List<StarBean> starBeansList = controller.getStarsInsideFilament(45);

        ArrayList<String> list = new ArrayList<String >() {{
            add("HIGALPS012.8034-0.3187");
            add("HIGALPS012.8089+0.0529");
            add("HIGALPS012.8154-0.3052");
            add("HIGALPS012.8297-0.1389");
            add("HIGALPS012.8274-0.1105");
        }};

        for (int i = 0; i < 5; i++){
            StarBean starBean = starBeansList.get(i);
            if (!list.contains(starBean.getName()))
                Assert.fail();
        }
    }
}

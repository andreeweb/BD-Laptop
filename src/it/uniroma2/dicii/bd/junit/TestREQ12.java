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
            add("HIGALPS012.8948+0.1239");
            add("HIGALPS012.8957-0.4315");
            add("HIGALPS012.8974+0.1885");
            add("HIGALPS012.9008-0.2405");
            add("HIGALPS012.9048-0.0306");
        }};

        for (int i = 0; i < 5; i++){
            StarBean starBean = starBeansList.get(i);
            if (!list.contains(starBean.getName()))
                Assert.fail();
        }
    }
}

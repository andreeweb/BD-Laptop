package it.uniroma2.dicii.bd.junit;

import it.uniroma2.dicii.bd.bean.FilamentBean;
import it.uniroma2.dicii.bd.controller.SearchController;
import it.uniroma2.dicii.bd.exception.DaoException;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestREQ7 {

    @Test
    public void testSearchByNumberOfSegment() throws DaoException {

        SearchController controller = new SearchController();

        Integer minNumberSegment = 3;
        Integer maxNumberSegment = 10;

        List<FilamentBean> listS = controller.getFilamentsByNumberOfSegments(minNumberSegment,
                                                                             maxNumberSegment,
                                                                            5,
                                                                            0);

        ArrayList<Integer> list = new ArrayList<Integer>() {{
            add(52);
            add(59);
            add(90);
            add(131);
            add(133);
        }};

        for (FilamentBean filamentBean : listS){
            if (!list.contains(filamentBean.getIdfil()))
                Assert.fail();
        }
    }
}

package it.uniroma2.dicii.bd.junit;

import it.uniroma2.dicii.bd.bean.FilamentBean;
import it.uniroma2.dicii.bd.bean.GPointBean;
import it.uniroma2.dicii.bd.controller.SearchController;
import it.uniroma2.dicii.bd.exception.DaoException;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestREQ8 {

    @Test
    public void searchInsideRegionCircle() throws DaoException {

        SearchController controller = new SearchController();

        Float sideSize = 10.0f;
        Double centerLatitude = 0.0;
        Double centerLongitude = 0.0;

        GPointBean gPointBean = new GPointBean(centerLongitude, centerLatitude);

        List<FilamentBean> listS = controller.getFilamentInsideRegion("Circle",
                                                                      gPointBean,
                                                                      sideSize,
                                                                     5,
                                                                     0);


        ArrayList<Integer> list = new ArrayList<Integer>() {{
            add(1956);
            add(3919);
            add(4178);
            add(5154);
            add(14578);
        }};

        for (FilamentBean filamentBean : listS){
            if (!list.contains(filamentBean.getIdfil()))
                Assert.fail();
        }
    }

    @Test
    public void searchInsideRegionSquare() throws DaoException {

        SearchController controller = new SearchController();

        Float sideSize = 10.0f;
        Double centerLatitude = 0.0;
        Double centerLongitude = 0.0;

        GPointBean gPointBean = new GPointBean(centerLongitude, centerLatitude);

        List<FilamentBean> listS = controller.getFilamentInsideRegion("Square",
                gPointBean,
                sideSize,
                5,
                0);


        ArrayList<Integer> list = new ArrayList<Integer>() {{
            add(14582);
            add(1046464);
            add(1051462);
            add(1052460);
            add(1052825);
        }};

        for (FilamentBean filamentBean : listS){
            if (!list.contains(filamentBean.getIdfil()))
                Assert.fail();
        }
    }
}

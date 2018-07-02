package it.uniroma2.dicii.bd.junit;

import it.uniroma2.dicii.bd.bean.FilamentBean;
import it.uniroma2.dicii.bd.controller.SearchController;
import it.uniroma2.dicii.bd.exception.DaoException;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestREQ6 {

    @Test
    public void testSearchByContrastAndEllipticity() throws DaoException {

        SearchController controller = new SearchController();

        Double luminance = 40.0;
        Float ellipticityMin = 2.0f;
        Float ellipticityMax = 4.0f;

        List<FilamentBean> listS = controller.getFilamentsByLuminanceAndEllipticityWithLimit(luminance,
                                                                                             ellipticityMin,
                                                                                             ellipticityMax,
                                                                                            5,
                                                                                            0);

        ArrayList<Integer> list = new ArrayList<Integer>() {{
            add(255);
            add(380);
            add(383);
            add(388);
            add(389);
        }};

        for (FilamentBean filamentBean : listS){
            if (!list.contains(filamentBean.getIdfil()))
                Assert.fail();
        }

    }
}

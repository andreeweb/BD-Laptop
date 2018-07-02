package it.uniroma2.dicii.bd.junit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        TestREQ1.class,
        TestREQ2.class,
        TestREQ3.class,
        TestREQ4.class,
        TestREQ5.class,
        TestREQ6.class,
        TestREQ7.class,
        TestREQ8.class,
        TestREQ9.class,
        TestREQ10.class,
        TestREQ11.class,
        TestREQ12.class,
})


public class JunitTestSuite {

    //	Tests will be automatically retrieved from
    //  the classes specified in @SuiteClasses
}
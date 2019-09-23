package com.aeyacin.todolist.model;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FilterObjectTest {

    private FilterObject filterObject;

    /**
     * Per Before Test Running
     */
    @BeforeClass
    public static void init() {
        System.out.println("FilterObjectTest Test Start");
    }


    /**
     * firstPerStep
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        System.out.println("setUp");

        filterObject = new FilterObject();
        filterObject.rosterCount = 0;
        filterObject.Name = "query";
        filterObject.Done = false;
        filterObject.Todo = false;
        filterObject.Expired = false;
    }

    /**
     * afterPerTestStep
     *
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        System.out.println("tearDown");
    }

    @Test
    public void isNotFilter() {
        assertFalse("Filter run is right", filterObject.isNotFilter());
        filterObject.Name = null;
        assertTrue("Filter run is right", filterObject.isNotFilter());

    }


    /**
     * After Class Per After Test Running
     */
    @AfterClass
    public static void destroy() {
        System.out.println("FilterObjectTest Test Finish");

    }

}
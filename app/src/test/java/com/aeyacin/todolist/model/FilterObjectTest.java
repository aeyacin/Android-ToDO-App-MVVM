package com.aeyacin.todolist.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FilterObjectTest {

    private FilterObject filterObject;

    @Before
    public void setUp() throws Exception {
        filterObject = new FilterObject();
        filterObject.rosterCount = 0;
        filterObject.Name = "query";
        filterObject.Done = false;
        filterObject.Todo = false;
        filterObject.Expired = false;
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void isNotFilter() {
        assertFalse("Filter run is right", filterObject.isNotFilter());
        filterObject.Name = null;
        assertTrue("Filter run is right", filterObject.isNotFilter());

    }


}
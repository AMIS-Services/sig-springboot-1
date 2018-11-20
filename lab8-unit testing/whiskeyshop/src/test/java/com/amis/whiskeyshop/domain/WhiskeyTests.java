package com.amis.whiskeyshop.domain;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class WhiskeyTests {

    @Test
    public void testConstructorIdName() throws IllegalAccessException {
        //Prepare
        String id = "1";
        String name = "Jack Daniels";

        //Execute
        Whiskey result = new Whiskey(id, name);

        //Verify
        assertEquals(id, FieldUtils.readField(result, "id", true));
        assertEquals(name, FieldUtils.readField(result, "name", true));
    }

}

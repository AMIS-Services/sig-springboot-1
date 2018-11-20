package com.amis.whiskeyshop.web;

import com.amis.whiskeyshop.domain.Whiskey;
import com.amis.whiskeyshop.service.WhiskeyService;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class WhiskeyControllerTests {

    private WhiskeyController testSubject;

    @Mock
    private WhiskeyService whiskeyService;

    @Before
    public void setUp() {
        testSubject = new WhiskeyController(whiskeyService);
    }

    @Test
    public void testConstructor() throws IllegalAccessException {
        assertEquals(whiskeyService, FieldUtils.readField(testSubject, "whiskeyService", true));
    }

    @Test
    public void testGetWhiskey() {
        //Prepare
        String inputId = "10";
        Whiskey expectedWhiskey = new Whiskey();

        when(whiskeyService.getWhiskey(inputId)).thenReturn(expectedWhiskey);

        //Execute
        Whiskey result = testSubject.getWhiskey(inputId);

        //Verify
        assertEquals(expectedWhiskey, result);
        verify(whiskeyService, times(1)).getWhiskey(inputId);
        verifyNoMoreInteractions(whiskeyService);
    }


}

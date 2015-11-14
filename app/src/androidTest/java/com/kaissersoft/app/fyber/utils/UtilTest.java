package com.kaissersoft.app.fyber.utils;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.TreeMap;

import static junit.framework.Assert.*;

/**
 * Created by eefret on 11/11/15.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class UtilTest {

    @Test
    public void testGenerateHashKey() {

        String API_KEY = "e95a21621a1865bcbae3bee89c4d4f84";

        TreeMap<String, String> params = new TreeMap<>();
        params.put(Util.RequestParams.APP_ID, "157");
        params.put(Util.RequestParams.UID, "player1");
        params.put(Util.RequestParams.IP, "212.45.111.17");
        params.put(Util.RequestParams.LOCALE, "de");
        params.put(Util.RequestParams.DEVICE_ID, "2b6f0cc904d137be2e1730235f5664094b831186");
        params.put(Util.RequestParams.PS_TIME, "1312211903");
        params.put(Util.RequestParams.PUB0, "campaign2");
        params.put(Util.RequestParams.PAGE, "2");
        params.put(Util.RequestParams.TIMESTAMP, "1312553361");

        String hashedKey = Util.generateHashKey(params, API_KEY);
        assertEquals("7a2b1604c03d46eec1ecd4a686787b75dd693c4d", hashedKey);
    }

    @Test
    public void testGetIPAddress(){
        assertNotNull(Util.getIPAddress(true));
    }

}

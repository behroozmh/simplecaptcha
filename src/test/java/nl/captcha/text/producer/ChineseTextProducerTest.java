package nl.captcha.text.producer;

import org.junit.Test;

import static nl.captcha.text.producer.ChineseTextProducer.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ChineseTextProducerTest {

    @Test
    public void testDefaultConstructor() {
        ChineseTextProducer cProd = new ChineseTextProducer();
        String ans = cProd.getText();
        assertEquals(ans.length(), DEFAULT_LENGTH);
        for (int i = 0; i < ans.length(); i++) {
            char c = ans.charAt(i);
            assertTrue("Character was'" + c + "' (" + (int) c + "), should be greater than " + CODE_POINT_START + " and less than " + CODE_POINT_END,
                    c >= CODE_POINT_START && c <= CODE_POINT_END);
        }
    }
}
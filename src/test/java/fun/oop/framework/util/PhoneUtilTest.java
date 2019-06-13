package fun.oop.framework.util;

import fun.oop.framework.enums.CarrierOperatorNames;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class PhoneUtilTest {

    @Test
    public void testCarrierOperation() {
        String phone = "15026839299";
        assertEquals("中国移动", CarrierOperatorNames.desc(PhoneUtil.getCarrierOperation(phone)));
    }
}

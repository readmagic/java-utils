package fun.oop.framework.util;

import org.junit.Test;
import fun.oop.framework.enums.CarrierOperatorNames;


public class PhoneUtilTest {

    @Test
    public void testCarrierOperation() {
        String phone = "15026839293";
        System.out.println(CarrierOperatorNames.desc(PhoneUtil.getCarrierOperation(phone)));

    }
}

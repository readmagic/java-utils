package fun.oop.framework.util;

import fun.oop.framework.enums.CarrierOperatorNames;
import fun.oop.framework.validate.validator.IsMobilePhoneValidator;


public class PhoneUtil {

    private PhoneUtil() {

    }

    /**
     * 判断传入的参数号码为哪家运营商
     *
     * @param mobile
     * @return 运营商名称
     */
    public static String getCarrierOperation(String mobile) throws IllegalArgumentException {
        if (mobile == null || mobile.trim().length() != 11) throw new IllegalArgumentException("手机号错误");
        String first3Num = mobile.trim().substring(0, 3);
        if (IsMobilePhoneValidator.CMCC.contains(first3Num)) {
            return CarrierOperatorNames.CMCC.name;
        } else if (IsMobilePhoneValidator.CUCC.contains(first3Num)) {
            return CarrierOperatorNames.CUCC.name;
        } else if (IsMobilePhoneValidator.CTCC.contains(first3Num)) {
            return CarrierOperatorNames.CTCC.name;
        } else if (IsMobilePhoneValidator.VIRTUAL.contains(first3Num)) {
            return CarrierOperatorNames.VIRTUAL.name;
        } else throw new IllegalArgumentException("手机号错误");
    }
}



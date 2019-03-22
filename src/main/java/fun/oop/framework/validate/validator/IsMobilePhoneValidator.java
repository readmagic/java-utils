package fun.oop.framework.validate.validator;

import com.baidu.unbiz.fluentvalidator.Validator;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.baidu.unbiz.fluentvalidator.ValidatorHandler;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class IsMobilePhoneValidator extends ValidatorHandler<String> implements Validator<String> {
    public static List<String> CMCC = Arrays.asList("134", "135", "136", "137", "138", "139", "147", "150", "151", "152", "157", "158", "159", "178", "182", "183", "184", "187", "188", "198");
    public static List<String> CUCC = Arrays.asList("130", "131", "132", "145", "155", "156", "175", "176", "185", "186", "166");
    public static List<String> CTCC = Arrays.asList("133", "153", "177", "180", "181", "189", "173", "199");
    public static List<String> VIRTUAL = Arrays.asList("170", "171");


    @Override
    public boolean validate(ValidatorContext context, String s) {
        if (s == null) {
            context.addErrorMsg("电话号码不能为空");
            return false;
        }

        if (isMobile(s) == false) {
            context.addErrorMsg("电话号码不合法");
            return false;
        }
        return true;
    }

    public static boolean isMobile(String mobile) {
        Pattern p = Pattern.compile("^1\\d{10}$");
        Matcher m = p.matcher(mobile);
        if (mobile.length() < 11) {
            return false;
        }
        String prefix = mobile.substring(0, 3);

        if (m.matches() == true && (CMCC.contains(prefix) || CUCC.contains(prefix) || CTCC.contains(prefix) || VIRTUAL.contains(prefix))) {
            return true;
        } else {
            return false;
        }
    }
}

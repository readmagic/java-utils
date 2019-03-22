package fun.oop.framework.validate;

import com.baidu.unbiz.fluentvalidator.Result;
import com.baidu.unbiz.fluentvalidator.jsr303.HibernateSupportedValidator;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.Locale;

import static com.baidu.unbiz.fluentvalidator.ResultCollectors.toSimple;


public class FluentValidator {


    public static SingleResult singleError(Object object) {
        Locale.setDefault(Locale.CHINESE);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        javax.validation.Validator validator = factory.getValidator();
        Result ret = com.baidu.unbiz.fluentvalidator.FluentValidator.checkAll()
                .on(object, new HibernateSupportedValidator().setHiberanteValidator(validator))
                .on(object)
                .doValidate()
                .result(toSimple());
        if (ret.isSuccess()) {
            return new SingleResult(true, null);
        } else {
            return new SingleResult(false, ret.getErrors().get(0));
        }
    }

    public static Result multiErrors(Object object) {
        Locale.setDefault(Locale.CHINESE);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        javax.validation.Validator validator = factory.getValidator();
        return com.baidu.unbiz.fluentvalidator.FluentValidator.checkAll()
                .failOver()
                .on(object, new HibernateSupportedValidator().setHiberanteValidator(validator))
                .on(object)
                .doValidate()
                .result(toSimple());
    }
}

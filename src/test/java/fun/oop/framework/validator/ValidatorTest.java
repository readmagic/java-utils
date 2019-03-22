package fun.oop.framework.validator;

import com.baidu.unbiz.fluentvalidator.annotation.FluentValidate;
import fun.oop.framework.validate.FluentValidator;
import fun.oop.framework.validate.validator.IsMobilePhoneValidator;
import org.hibernate.validator.constraints.NotBlank;
import org.junit.Test;


public class ValidatorTest {

    @Test
    public void testValidator() {
        UserEntity user = new UserEntity();
        user.setPhone("132222");
        user.setIdCardNo("31022819910614221X");
        System.out.println(FluentValidator.singleError(user).getError());
    }

    class UserEntity {
        @FluentValidate(IsMobilePhoneValidator.class)
        private String phone;
        @NotBlank(message = "身份证不能为空")
        private String idCardNo;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getIdCardNo() {
            return idCardNo;
        }

        public void setIdCardNo(String idCardNo) {
            this.idCardNo = idCardNo;
        }
    }

}


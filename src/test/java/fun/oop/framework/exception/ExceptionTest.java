package fun.oop.framework.exception;

import org.junit.Test;


public class ExceptionTest {

    @Test
    public void testException() {
        try {
            a();
        }catch (BusinessAssertException ex){
            System.out.println(ex.getErrorCode());
            System.out.println(ex.getMessage());
        }

    }

    private void a(){

        throw new BusinessAssertException(2000,"错误测试");
    }
}

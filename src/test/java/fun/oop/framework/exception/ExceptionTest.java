package fun.oop.framework.exception;

import org.junit.Test;

import static org.junit.Assert.assertTrue;


public class ExceptionTest {

    @Test
    public void testException() {
        try {
            a();
        }catch (BusinessAssertException ex){
            assertTrue(2000==ex.getErrorCode());
            assertTrue("错误测试".equals(ex.getMessage()));
        }

    }

    private void a(){

        throw new BusinessAssertException(2000,"错误测试");
    }
}

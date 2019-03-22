package fun.oop.framework.crypto;

import org.junit.Test;


public class AESTest {
    @Test
    public void encrypt() throws Exception {
        System.out.println(AES.encrypt("bdbdnfy56thbkclk", "yfhx"));


    }

    @Test
    public void decrypt() throws Exception {
        System.out.println(AES.decrypt("bdbdnfy56thbkclk", "U2FsdGVkX1/TxfOUSHL37jJBfHrjshGzVXOMmgEktFA="));
    }

}
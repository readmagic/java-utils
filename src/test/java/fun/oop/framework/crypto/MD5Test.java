package fun.oop.framework.crypto;


import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;


public class MD5Test {


    @Test
    public void getMD5CodeTest() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        System.out.println(MD5.getMD5Code("admin"));
    }


}

package com.cqw.cblog.Utils;

import org.springframework.util.DigestUtils;

public class MD5Utils {
    public String EncodeByMd5(String password) {
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
        return md5Password;
    }
    public String EncodeByMd5(String salt, String password) {
        String saltPassword=salt+"/"+password;
        String md5Password = DigestUtils.md5DigestAsHex(saltPassword.getBytes());
        return md5Password;
    }

    public static void main(String[] args) {
        System.out.println(new MD5Utils().EncodeByMd5("1234"));
    }
}

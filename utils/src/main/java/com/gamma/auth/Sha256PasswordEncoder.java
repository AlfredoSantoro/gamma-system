package com.gamma.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.apache.commons.codec.digest.DigestUtils;

public class Sha256PasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        return DigestUtils.sha256Hex(rawPassword.toString());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return this.encode(rawPassword).equals(encodedPassword);
    }
}

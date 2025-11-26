package com.freelancer.management.config;

import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

//@Component
public class BcryptTestRunner implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;

    public BcryptTestRunner(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        String raw = "senha123";
        String hash = passwordEncoder.encode(raw);
        System.out.println("HASH GERADO PARA '" + raw + "': " + hash);
    }
}
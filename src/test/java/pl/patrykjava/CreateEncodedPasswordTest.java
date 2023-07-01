package pl.patrykjava;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CreateEncodedPasswordTest {

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("example"));
    }
}

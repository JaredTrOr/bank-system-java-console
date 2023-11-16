package com.classes;

import com.utils.Utils;

import java.util.Random;
import java.util.UUID;

public class Member {

    private String memberID;
    private String name;
    private String username;
    private String email;
    private String phoneNumber;
    private String birthDate;
    private String gender;
    private String occupation;
    private String PIN;
    private int rol;
    private boolean firstTime;

    public Member() {}

    public Member(
            String memberId, String name, String username, String email, String phoneNumber, String birthDate,
            String gender, String occupation, String pin ,int rol
    ) {
        this.memberID = memberId;
        this.name = name;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.gender = gender;
        this.occupation = occupation;
        this.PIN = pin;
        this.rol = rol;
        this.firstTime = false;
    }

    Member(
            String name, String username, String email, String phoneNumber, String birthDate,
            String gender, String occupation ,int rol
    ) {
        Utils util = new Utils();
        this.memberID = util.generateRandomId();
        this.name = name;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.gender = gender;
        this.occupation = occupation;
        this.PIN = generateRandomPIN();
        this.rol = rol;
        this.firstTime = false;
    }

    private static String generateRandomPIN() {
        String randomPIN = "";

        Random randomGenerator = new Random();

        for (int i = 0; i<4; i++) {
            int randomNumber = randomGenerator.nextInt(10);
            randomPIN += randomNumber;
        }

        return randomPIN;
    }

    public String getMemberID() {
        return memberID;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getGender() {
        return gender;
    }

    public String getOccupation() {
        return occupation;
    }

    public String getPIN() {
        return PIN;
    }

    public int getRol() {
        return rol;
    }

    public boolean isFirstTime() {
        return firstTime;
    }
}

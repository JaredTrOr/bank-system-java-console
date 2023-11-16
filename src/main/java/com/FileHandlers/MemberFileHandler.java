package com.FileHandlers;

import com.classes.Member;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MemberFileHandler {

    private final ArrayList<Member> memberList = new ArrayList<>(); //Global list variable
    final String MEMBER_FILE_PATH = "src/main/java/com/csvfiles/Members.csv";


    public MemberFileHandler() {
        //When the program is initialize is going to set the member list to grab all the members
        //This is made to manipulate the data in the array, to perform searching
        setMemberListFromFile();
    }

    public void setMemberListFromFile() {

        this.memberList.clear();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.MEMBER_FILE_PATH));

            String line;

            try {
                while ((line = reader.readLine()) != null) {
                    String[] values = line.split(",");

                    Member member = new Member(
                            values[0], values[1], values[2], values[3], values[4], values[5],
                            values[6], values[7], values[8], Integer.parseInt(values[9])
                    );

                    this.memberList.add(member);
                }

            } catch(IOException e) {
                System.out.println("[Error]: There was an error getting the member list");
            }
        } catch (FileNotFoundException e) {
            System.out.println("[Error]: when opening the member file: "+e);
        }

    }

    public Map<String, Object> login (String username, String pin) {

        Map<String, Object> loginResponse = new HashMap<>();

        loginResponse.put("success", false);

        for (Member member : this.memberList)
        {
            if (username.equals(member.getUsername())) {
                if (pin.equals(member.getPIN())) {

                    loginResponse.put("success", true);
                    loginResponse.put("member", member);

                    return loginResponse;
                }

            }
        }

        return loginResponse;
    }

    public String createMember(Member member) {

        String response = "New member created successfully";

        String line = member.getMemberID()+","+member.getName()+","+member.getUsername()+","+member.getEmail()+","+member.getPhoneNumber()+","+member.getBirthDate()+","+member.getGender()+","+member.getOccupation()+","+member.getPIN()+","+member.getRol()+","+member.isFirstTime();

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(this.MEMBER_FILE_PATH, true));
            writer.write(line);
            writer.newLine();
            writer.flush();

            setMemberListFromFile();
            return response;

        } catch (FileNotFoundException e) {
            response = "Error when opening the member file";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return response;
    }

    public ArrayList<Member> getMemberList() {
        return this.memberList;
    }

}

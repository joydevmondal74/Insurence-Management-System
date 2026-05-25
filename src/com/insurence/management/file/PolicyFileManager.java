package com.insurence.management.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.insurence.management.enums.PolicyStatus;
import com.insurence.management.enums.PolicyType;
import com.insurence.management.models.Policy;

public class PolicyFileManager {
    public static void saveToFile(List<Policy> policies, String filePath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write("policyNumber,holderName,type,status,premiumAmount,effectiveDate,expirationDate");
            bw.newLine();

            for (Policy p : policies) {
                String line = String.format("%s,%s,%s,%s,%s,%s,%s", p.getPolicyNumber(), p.getHolderName(),
                        p.getType().name(), p.getStatus().name(), String.valueOf(p.getPremiumAmount()),
                        p.getEffectiveDate().toString(), p.getExpirationDate().toString());

                bw.write(line);
                bw.newLine();
            }
            System.out.println("Policies saved to{" + filePath + "}successsfully");
        } catch (IOException e) {
            System.err.println("Error writing to file : "+e.getMessage());

        }

    }

    public static List<Policy> loadFromFile(String filePath) {
        List<Policy> policiList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                if(line.trim().isEmpty()) continue;

                String[] parts = line.split(",");

                String policyNumber = parts[0];
                String holderName = parts[1];
                PolicyType type = PolicyType.valueOf(parts[2]);
                PolicyStatus status = PolicyStatus.valueOf(parts[3]);
                Double premiumAmount = Double.parseDouble(parts[4]);
                LocalDate effectiveDate = LocalDate.parse(parts[5]);
                LocalDate expirationDate = LocalDate.parse(parts[6]);
                Policy policy = new Policy(policyNumber, holderName, type, status, premiumAmount, effectiveDate,
                        expirationDate);
                policiList.add(policy);
            }

        } catch (FileNotFoundException e) {
            System.err.println("File not found :{" + filePath+"}");
            return policiList;
        } catch (IOException e) {
            System.err.println("Error reading CSV file " + e.getMessage());
        }
        return policiList;
    }

}

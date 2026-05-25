package com.insurence.management.main;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

import com.insurence.management.enums.PolicyStatus;
import com.insurence.management.enums.PolicyType;
import com.insurence.management.exceptions.DuplicatePolicyException;
import com.insurence.management.exceptions.PolicyNotFoundException;
import com.insurence.management.file.PolicyFileManager;
import com.insurence.management.models.Policy;
import com.insurence.management.services.PolicyService;

public class PolicyApp {
    public static void main(String[] args) {
        PolicyService policyService = new PolicyService();
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n=============================================");
            System.out.println("      INSURANCE POLICY MANAGEMENT SYSTEM      ");
            System.out.println("=============================================");
            System.out.println("1. Add New Policy");
            System.out.println("2. Remove Policy");
            System.out.println("3. Search by Policy Number");
            System.out.println("4. Search by Holder Name");
            System.out.println("5. View Expiring Policies");
            System.out.println("6. View Premium Summary by Type");
            System.out.println("7. View All Active Policies (Sorted)");
            System.out.println("8. Save Policies to File");
            System.out.println("9. Load Policies from File");
            System.out.println("0. Exit");
            System.out.println("=============================================");
            System.out.print("Enter your choice: ");

            int n;
            try {
                n = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice. Please enter a number 0-9.");
                continue;
            }

            switch (n) {
                case 1:
                    try {
                        System.out.println("\n---------Add New Policy---------- ");
                        System.out.println("Enter policy Number : ");
                        String policyNumber = sc.nextLine().trim();
                        if (policyNumber.isEmpty()) {
                            System.out.println("Error: Policy Number cannot be empty.");
                            break;
                        }

                        System.out.println("Enter Holder Name : ");
                        String holderName = sc.nextLine().trim();
                        if (holderName.isEmpty()) {
                            System.out.println("Error: Holder Name cannot be empty.");
                            break;
                        }

                        System.out.println("Enter Policy Type (1. AUTO, 2. HOME, 3. LIFE, 4 .HEALTH) : ");
                        int typeChoice = Integer.parseInt(sc.nextLine().trim());
                        PolicyType type = switch (typeChoice) {
                            case 1 -> PolicyType.AUTO;
                            case 2 -> PolicyType.HOME;
                            case 3 -> PolicyType.LIFE;
                            case 4 -> PolicyType.HEALTH;
                            default -> null;

                        };
                        if (type == null) {
                            System.out.println("Invalid Policy Type selection.");
                            break;
                        }
                        System.out.println("Enter Policy status (ACTIVE, EXPIRED, CANCELLED) : ");
                        PolicyStatus status = PolicyStatus.valueOf(sc.nextLine().trim().toUpperCase());

                        System.out.println("Enter premium Amount : ");
                        Double premiumAmount = Double.parseDouble(sc.nextLine().trim());
                        if (premiumAmount <= 0) {
                            System.out.println("Error : Premium amount must not be positive.");
                            break;
                        }

                        System.out.println("Enter Effective Date (yyyy-MM-dd): ");
                        LocalDate effectiveDate = LocalDate.parse(sc.nextLine().trim());

                        System.out.println("Enter Expiration Date (yyyy-MM-dd): ");
                        LocalDate expirationDate = LocalDate.parse(sc.nextLine().trim());
                        if (!expirationDate.isAfter(effectiveDate)) {
                            System.out.println("Error : Expiration date must be after effective date. ");
                            break;
                        }

                        Policy newPolicy = new Policy(policyNumber, holderName, type, status, premiumAmount,
                                effectiveDate,
                                expirationDate);

                        policyService.addPolicy(newPolicy);
                        System.out.println("Success: Policy added succesfully. ");
                    } catch (DuplicatePolicyException e) {
                        System.out.println("found duplicate" + e.getMessage());
                    } catch (DateTimeParseException e) {
                        System.out.println("Error: Date must be exactly in yyyy-MM-dd format.");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Validation Error: " + e.getMessage());

                    }
                    break;
                case 2:
                    try {
                        System.out.println("\n---------Remove Policy----------");
                        System.out.println("Enter policy Number  to delete : ");
                        String policyNumToRemove = sc.nextLine().trim();
                        
                        if (policyNumToRemove.isEmpty()) {
                            System.out.println("Error: Policy number can not be blank.");
                            break;
                            
                        }
                        policyService.removePolicy(policyNumToRemove);
                        System.out.println("Success : Policy removed successfully. ");
                    } catch (PolicyNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    try {
                        System.out.println("\n---------Search by Policy Number---------- ");
                        System.out.println("Enter policy Number : ");
                        String searchNum = sc.nextLine().trim();

                        Optional<Policy> policydetails = policyService.findByPolicyNumber(searchNum);

                        if (policydetails.isPresent()) {
                            Policy foundPolicy = policydetails.get();
                            System.out.printf(
                                    "Policy Number: %s | Holder: %s | Type: %s | Status: %s | Premium: $%.2f | Effective: %s | Expires: %s%n",
                                    foundPolicy.getPolicyNumber(), foundPolicy.getHolderName(), foundPolicy.getType(),
                                    foundPolicy.getStatus(), foundPolicy.getPremiumAmount(),
                                    foundPolicy.getEffectiveDate(), foundPolicy.getExpirationDate());
                        } else {
                            System.out.println("No poliy found.");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 4:
                    try {
                        System.out.println("\n---------Search by Holder Name---------- ");
                        System.out.println("Enter Holder Name : ");
                        String holderName = sc.nextLine().trim();
                        List<Policy> policyDetails = policyService.findByHolderName(holderName);

                        if (policyDetails.isEmpty()) {
                            System.out.println("No matching policy found.");

                        } else {
                            System.out.println("Policy found for " + policyDetails);

                            for (Policy policy : policyDetails) {
                                System.out.printf(
                                        "Policy Number %s | Holder Name %s | Policy Type %s | Policy Status %s |  Premium Amount    $%.2f |  Policy Effective Date %s |  Policy Expiration Date %s%n ",
                                        policy.getPolicyNumber(), policy.getHolderName(), policy.getType(),
                                        policy.getStatus(), policy.getPremiumAmount(), policy.getEffectiveDate(),
                                        policy.getExpirationDate());
                            }
                        }

                    } catch (Exception e) {
                        System.err.println("error occured");
                    }
                    break;

                case 5:
                    System.out.println("\n---------View Expirring Policies---------- ");

                    System.out.println("Within how many days ? ");
                    try {
                        int days = Integer.parseInt(sc.nextLine().trim());
                        if (days < 0) {
                            System.out.println("Error : Days can not be negetive.");
                            break;
                        }
                        List<Policy> expiring = policyService.getExpiringPolicies(days);

                        if (expiring.isEmpty()) {
                            System.out.println("No expiring policies.");
                        } else {
                            for (Policy policy : expiring) {
                                System.out.printf(
                                        "Policy Number %s | Holder Name %s | Policy Type %s | Policy Status %s |  Premium Amount    $%.2f |  Policy Effective Date %s |  Policy Expiration Date %s%n ",
                                        policy.getPolicyNumber(), policy.getHolderName(), policy.getType(),
                                        policy.getStatus(), policy.getPremiumAmount(), policy.getEffectiveDate(),
                                        policy.getExpirationDate());
                            }
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Please enter a valid whole number for days.");
                    }
                    break;

                case 6:
                    System.out.println("\n--- Premium Summary by Policy Type ---");
                    Map<PolicyType, Double> premiumSummary = policyService.getTotalPremiumByType();
                    if (premiumSummary.isEmpty()) {
                        System.out.println("No policy data available.");
                    } else {
                        System.out.println("-------------------------------------");
                        System.out.printf("| %-15s | %-15s |%n", "Policy Type", "Total Premium");
                        System.out.println("-------------------------------------");
                        for (Map.Entry<PolicyType, Double> entry : premiumSummary.entrySet()) {
                            System.out.printf("| %-15s | $%-14.2f |%n", entry.getKey(), entry.getValue());
                        }
                        System.out.println("-------------------------------------");
                    }
                    break;
                case 7:
                    System.out.println("\n--- View All Active Policies (Sorted by Premium) ---");

                    List<Policy> activeSorted = policyService.getActivePoliciesSorted();
                    if (activeSorted.isEmpty()) {
                        System.out.println("No active policies found.");
                    } else {
                        for (Policy p : activeSorted) {
                            System.out.printf("Policy Number: %s | Holder: %s | Premium: $%.2f | Status: %s%n",
                                    p.getPolicyNumber(), p.getHolderName(), p.getPremiumAmount(), p.getStatus());
                        }
                    }
                    break;
                case 8:
                    System.out.println("\n--- Saving Policies to File ---");

                    PolicyFileManager.saveToFile(policyService.getAllPolicies(), "policies.csv");
                    System.out.println("Success: Policies saved to 'policies.csv'.");
                    break;
                case 9:
                    System.out.println("\n--- Loading Policies from File ---");

                    List<Policy> loaded = PolicyFileManager.loadFromFile("policies.csv");
                    policyService.setPolicies(loaded);
                    System.out.println("Success: Policies loaded from 'policies.csv'.");
                    System.out.println("count loaded : " + loaded.size());
                    break;
                case 0:
                    System.out.println("Thank you for using Policy Management System. Goodbye!");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice. Please select a dynamic option between 0 and 9.");
                    break;

            }
        }
        sc.close();
    }

}

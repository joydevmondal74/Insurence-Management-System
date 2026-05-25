package com.insurence.management.services;

import java.util.*;
import java.util.stream.Collectors;
import java.time.LocalDate;

import com.insurence.management.enums.PolicyStatus;
import com.insurence.management.enums.PolicyType;
import com.insurence.management.exceptions.DuplicatePolicyException;
import com.insurence.management.exceptions.PolicyNotFoundException;
import com.insurence.management.models.Policy;

public class PolicyService {

    private List<Policy> policies = new ArrayList<>();

    public void addPolicy(Policy policy) throws DuplicatePolicyException {
        if (policies.contains(policy)) {
            throw new DuplicatePolicyException("Policy  " + policy + "  already exists.");
        } else {
            policies.add(policy);
            System.out.println("Policy" + policy + "added successfully.");
        }
    }

    public void removePolicy(String policyNumber) throws PolicyNotFoundException {
        if (policyNumber == null) {
            throw new PolicyNotFoundException("Policy number cannot be null");
        }
        Policy toRemove = null;
        for (Policy p : policies) {
            if (p.getPolicyNumber() != null && p.getPolicyNumber().trim().equalsIgnoreCase(policyNumber.trim())) {
                toRemove = p;
                break;

            }
        }
        if (toRemove == null) {
            throw new PolicyNotFoundException("Policy" + policyNumber + "not found");
        }
        policies.remove(toRemove);
        System.out.println("Policy " + policyNumber + " removed successfully.");
    }

    public Optional<Policy> findByPolicyNumber(String PolicyNumber) {

        return policies.stream().filter(p -> p.getPolicyNumber().equals(PolicyNumber)).findFirst();

    }

    public List<Policy> findByHolderName(String name) {
        return policies.stream().filter(p -> p.getHolderName().toLowerCase().contains(name.toLowerCase())).toList();

    }

    public List<Policy> getExpiringPolicies(int withinDays) {
        LocalDate today = LocalDate.now();
        LocalDate deadline = today.plusDays(withinDays);
        return policies.stream()
                .filter(p -> p.getStatus() == PolicyStatus.ACTIVE)
                .filter(p -> p.getExpirationDate().isAfter(today))
                .filter(p -> !p.getExpirationDate().isAfter(deadline)).toList();

    }

    public Map<PolicyType, Double> getTotalPremiumByType() {
        return policies.stream()
                .collect(Collectors.groupingBy(Policy::getType, Collectors.summingDouble(Policy::getPremiumAmount)));
    }

    public List<Policy> getActivePoliciesSorted() {
        return policies.stream()
                .filter(p -> p.getStatus() == PolicyStatus.ACTIVE)
                .sorted(Comparator.comparingDouble(Policy::getPremiumAmount).reversed()).toList();
    }

    public List<Policy> getAllPolicies() {
        return new ArrayList<>(policies);
    }

    public void setPolicies(List<Policy> newPolicies) {
        this.policies.clear();
        this.policies.addAll(newPolicies);
    }
}

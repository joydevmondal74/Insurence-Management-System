package com.insurence.management.models;

import java.time.LocalDate;
import java.util.Objects;

import com.insurence.management.enums.PolicyStatus;
import com.insurence.management.enums.PolicyType;

public class Policy implements Comparable<Policy> {

    private String PolicyNumber;
    private String HolderName;
    private PolicyType Type;
    private PolicyStatus Status;
    private double premiumAmount;
    private LocalDate effectiveDate;
    private LocalDate expirationDate;

    public Policy(String policyNumber, String holderName, PolicyType type, PolicyStatus status, double premiumAmount,
            LocalDate effectiveDate, LocalDate expirationDate) {

        if (policyNumber == null) {
            throw new IllegalArgumentException("Policy number can't be null");
        }
        if (policyNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Policy number can't be empty");

        }
        this.PolicyNumber = policyNumber;
        this.HolderName = holderName;
        this.Type = type;
        this.Status = status;
        this.premiumAmount = premiumAmount;
        this.effectiveDate = effectiveDate;
        this.expirationDate = expirationDate;
    }

    public String getPolicyNumber() {
        return PolicyNumber;
    }

    public String getHolderName() {
        return HolderName;
    }

    public PolicyType getType() {
        return Type;
    }

    public PolicyStatus getStatus() {
        return Status;
    }

    public double getPremiumAmount() {
        return premiumAmount;
    }

    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setStatus(PolicyStatus status) {
        Status = status;
    }

    public void setPremiumAmount(double premiumAmount) {
        this.premiumAmount = premiumAmount;
    }

    @Override
    public String toString() {
        return (PolicyNumber + "|" + HolderName + "|" + Type + "|" + Status + "|" + premiumAmount + "|" + effectiveDate
                + "to" + expirationDate);
        // "POL-001 | John Smith | AUTO | ACTIVE | $1200.50 | 2025-01-01 to 2026-01-01"

    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((PolicyNumber == null) ? 0 : PolicyNumber.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Policy))
            return false;
        Policy other = (Policy) obj;
        return Objects.equals(this.PolicyNumber, other.PolicyNumber);
    }

    @Override
    public int compareTo(Policy other) {
        if (other == null)
            throw new NullPointerException();
        return this.expirationDate.compareTo(other.expirationDate);
    }

}

# Insurance Policy Management System

A comprehensive Java-based console application for managing insurance policies. This system enables users to add, search, retrieve, and analyze insurance policies with full CRUD operations and advanced filtering capabilities.

## Table of Contents

- [Features](#features)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [Usage Guide](#usage-guide)
- [Core Components](#core-components)
- [Data Format](#data-format)
- [System Architecture](#system-architecture)
- [Error Handling](#error-handling)

## Features

### Core Functionality

1. **Policy Management**
   - Add new insurance policies with automatic duplicate validation
   - Remove existing policies by policy number
   - View all active policies sorted by premium amount

2. **Search & Retrieval**
   - Search policies by policy number (exact match)
   - Search policies by holder name (partial, case-insensitive)
   - View expiring policies within a specified number of days
   - Retrieve all policies from storage

3. **Analytics & Reporting**
   - View premium summary grouped by policy type
   - Generate reports on active policies
   - Track policy expiration dates
   - Premium aggregation by insurance type

4. **Data Persistence**
   - Save all policies to CSV file (`policies.csv`)
   - Load policies from CSV file
   - Automatic file I/O with error handling

### Policy Types

The system supports four types of insurance policies:

- **AUTO**: Automobile Insurance
- **HOME**: Homeowners Insurance
- **LIFE**: Life Insurance
- **HEALTH**: Health Insurance

### Policy Status

Each policy can have one of three statuses:

- **ACTIVE**: Currently valid policy
- **EXPIRED**: Policy has reached expiration date
- **CANCELLED**: Policy was manually terminated

## Project Structure

```
InsurenceManagementSystem/
├── src/
│   └── com/insurence/management/
│       ├── main/
│       │   └── PolicyApp.java              (Main entry point & UI)
│       ├── models/
│       │   └── Policy.java                 (Policy data model)
│       ├── services/
│       │   └── PolicyService.java          (Business logic)
│       ├── file/
│       │   └── PolicyFileManager.java      (CSV I/O operations)
│       ├── enums/
│       │   ├── PolicyType.java             (Policy type enumeration)
│       │   └── PolicyStatus.java           (Policy status enumeration)
│       └── exceptions/
│           ├── DuplicatePolicyException.java  (Duplicate policy error)
│           └── PolicyNotFoundException.java    (Policy not found error)
├── bin/                                    (Compiled classes)
├── policies.csv                            (Data storage file)
└── README.md                               (Documentation)
```

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 11 or higher
- Terminal/Command Prompt
- Text editor or IDE (recommended: VS Code, IntelliJ IDEA)

### Compilation

Navigate to the project root directory and compile all Java files:

```bash
javac -d bin src/com/insurence/management/**/*.java
```

Or compile specific modules:

```bash
javac -d bin src/com/insurence/management/enums/*.java
javac -d bin src/com/insurence/management/exceptions/*.java
javac -d bin src/com/insurence/management/models/*.java
javac -d bin src/com/insurence/management/services/*.java
javac -d bin src/com/insurence/management/file/*.java
javac -d bin src/com/insurence/management/main/*.java
```

### Running the Application

```bash
java -cp bin com.insurence.management.main.PolicyApp
```

## Usage Guide

### Main Menu

When you launch the application, you'll see this menu:

```
=============================================
      INSURANCE POLICY MANAGEMENT SYSTEM
=============================================
1. Add New Policy
2. Remove Policy
3. Search by Policy Number
4. Search by Holder Name
5. View Expiring Policies
6. View Premium Summary by Type
7. View All Active Policies (Sorted)
8. Save Policies to File
9. Load Policies from File
0. Exit
=============================================
```

### Option 1: Add New Policy

Creates a new policy record. You'll be prompted for:

- **Policy Number**: Unique identifier (cannot be duplicate or empty)
- **Holder Name**: Name of policy holder (cannot be empty)
- **Policy Type**: Select from AUTO, HOME, LIFE, or HEALTH
- **Policy Status**: ACTIVE, EXPIRED, or CANCELLED
- **Premium Amount**: Annual premium (must be positive)
- **Effective Date**: Start date in `yyyy-MM-dd` format
- **Expiration Date**: End date in `yyyy-MM-dd` format (must be after effective date)

**Example Input:**

```
Policy Number: POL-001
Holder Name: John Smith
Policy Type: 1 (AUTO)
Status: ACTIVE
Premium Amount: 1200.50
Effective Date: 2024-01-01
Expiration Date: 2025-01-01
```

### Option 2: Remove Policy

Deletes a policy by its policy number. Enter the exact policy number to remove.

### Option 3: Search by Policy Number

Finds and displays a single policy matching the exact policy number.

### Option 4: Search by Holder Name

Displays all policies for a given holder name (partial matches are supported, case-insensitive).

### Option 5: View Expiring Policies

Shows all ACTIVE policies expiring within a specified number of days from today.

**Input:** Number of days to check ahead

### Option 6: View Premium Summary by Type

Displays total premium amounts grouped by policy type:

```
-------------------------------------
| Policy Type     | Total Premium    |
-------------------------------------
| AUTO            | $5000.00         |
| HOME            | $3500.75         |
| LIFE            | $2100.25         |
| HEALTH          | $1200.00         |
-------------------------------------
```

### Option 7: View All Active Policies (Sorted)

Lists all ACTIVE policies sorted by premium amount in descending order (highest to lowest).

### Option 8: Save Policies to File

Exports all policies to `policies.csv` in CSV format with headers.

### Option 9: Load Policies from File

Imports policies from `policies.csv` into the system. This clears existing policies in memory and loads from file.

### Option 0: Exit

Safely closes the application and terminates the program.

## Core Components

### 1. Policy Model (`Policy.java`)

The core data model representing an insurance policy.

**Attributes:**

- `policyNumber` (String): Unique identifier
- `holderName` (String): Name of policy holder
- `type` (PolicyType): Category of insurance
- `status` (PolicyStatus): Current status
- `premiumAmount` (Double): Annual premium cost
- `effectiveDate` (LocalDate): Policy start date
- `expirationDate` (LocalDate): Policy end date

**Key Methods:**

- Getters for all attributes
- `setStatus()`: Update policy status
- `setPremiumAmount()`: Update premium
- `equals()`: Compare by policy number
- `compareTo()`: Sort by expiration date
- `toString()`: Formatted string representation

### 2. Policy Service (`PolicyService.java`)

Business logic layer handling all policy operations.

**Key Methods:**

- `addPolicy()`: Add new policy (validates duplicates)
- `removePolicy()`: Delete policy by number
- `findByPolicyNumber()`: Retrieve by ID (returns Optional)
- `findByHolderName()`: Search by holder name
- `getExpiringPolicies()`: Get expiring policies within days
- `getTotalPremiumByType()`: Aggregate premiums by type
- `getActivePoliciesSorted()`: Get active policies sorted by premium
- `getAllPolicies()`: Retrieve all policies
- `setPolicies()`: Batch update policies

### 3. Policy File Manager (`PolicyFileManager.java`)

Handles CSV file operations for data persistence.

**Methods:**

- `saveToFile()`: Export policies to CSV
- `loadFromFile()`: Import policies from CSV

**File Format:**

```
policyNumber,holderName,type,status,premiumAmount,effectiveDate,expirationDate
POL-001,John Smith,AUTO,ACTIVE,1200.50,2024-01-01,2025-01-01
```

### 4. Policy App (`PolicyApp.java`)

Main application class providing console-based user interface with menu-driven navigation and input validation.

## Data Format

### CSV Storage Format

Policies are stored in comma-separated values format:

```
policyNumber,holderName,type,status,premiumAmount,effectiveDate,expirationDate
POL-001,John Smith,AUTO,ACTIVE,1200.50,2024-01-01,2025-01-01
POL-002,Sarah Johnson,HOME,ACTIVE,850.00,2024-02-15,2025-02-15
POL-003,Michael Brown,LIFE,EXPIRED,5000.00,2022-01-01,2024-01-01
```

**Data Types:**

- `policyNumber`: String (unique)
- `holderName`: String
- `type`: Enum (AUTO, HOME, LIFE, HEALTH)
- `status`: Enum (ACTIVE, EXPIRED, CANCELLED)
- `premiumAmount`: Double (positive values)
- `effectiveDate`: LocalDate (yyyy-MM-dd)
- `expirationDate`: LocalDate (yyyy-MM-dd)

## System Architecture

### Layered Architecture

```
┌─────────────────────────────────────┐
│   Presentation Layer                │
│   (PolicyApp - Console UI)          │
├─────────────────────────────────────┤
│   Service Layer                     │
│   (PolicyService - Business Logic)  │
├─────────────────────────────────────┤
│   Model Layer                       │
│   (Policy - Data Model)             │
├─────────────────────────────────────┤
│   Persistence Layer                 │
│   (PolicyFileManager - File I/O)    │
└─────────────────────────────────────┘
```

### Design Patterns Used

1. **Service Pattern**: `PolicyService` encapsulates business logic
2. **Exception Handling**: Custom exceptions for specific error conditions
3. **Stream API**: Modern Java functional programming for filtering/aggregation
4. **Comparable Interface**: Natural ordering by expiration date
5. **Optional Pattern**: Safe null handling for search results

## Error Handling

### Exception Types

1. **DuplicatePolicyException**
   - Thrown when attempting to add a policy with duplicate policy number
   - Caught during policy addition

2. **PolicyNotFoundException**
   - Thrown when trying to remove or find non-existent policy
   - Caught during policy removal

### Input Validation

The application validates:

- Empty policy numbers and holder names
- Numeric inputs (policy type selection, days)
- Date format (yyyy-MM-dd)
- Premium amount positivity
- Expiration date after effective date
- File I/O operations with graceful error messages

### Error Messages

- `"Invalid choice. Please enter a number 0-9."` - Invalid menu selection
- `"Error: Policy Number cannot be empty."` - Empty policy number
- `"Error: Premium amount must be positive."` - Invalid premium
- `"Error: Date must be exactly in yyyy-MM-dd format."` - Date parsing error
- `"found duplicate"` - Duplicate policy detected
- `"No policy found."` - Policy not found in search

## Key Features in Detail

### Duplicate Prevention

The system prevents duplicate policies using:

- `hashCode()` based on policy number
- `equals()` comparing policy numbers
- Service-level validation before adding

### Sorted Active Policies

Active policies are displayed sorted by premium amount in descending order using:

```java
sorted(Comparator.comparingDouble(Policy::getPremiumAmount).reversed())
```

### Expiring Policies Logic

Finds policies where:

- Status is ACTIVE
- Expiration date is within specified days from today
- Expiration date has not passed

### Premium Summary

Aggregates premiums using streams and Collectors:

```java
groupingBy(Policy::getType, summingDouble(Policy::getPremiumAmount))
```

## Usage Tips

1. **Always save before exiting**: Use option 8 to save policies to file before closing the application to prevent data loss
2. **Use exact dates**: Follow the `yyyy-MM-dd` format strictly (e.g., 2024-01-15)
3. **Policy numbers**: Keep policy numbers unique and meaningful (e.g., POL-001, POL-002)
4. **Batch operations**: Use options 8 and 9 to export/import policies in bulk
5. **Search flexibility**: Option 4 (search by name) supports partial matches

## Notes

- The application uses in-memory storage; policies are lost on exit unless saved to file
- File operations create or overwrite `policies.csv` without backup
- All dates use the `LocalDate` format (no time information)
- Premium amounts are stored as Double with two-decimal precision for currency

---

**Version:** 1.0  
**Last Updated:** 2026-05-25  
**Author:** JOYDEV MONDAL

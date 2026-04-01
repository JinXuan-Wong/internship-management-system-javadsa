# Internship Management System (DSA)

A Java console-based Internship Management System developed for a Data Structures and Algorithms assignment. The system simulates a job application and recruitment platform, allowing employers to manage job postings, applicants to manage profiles, the system to match applicants with jobs, and interview schedules to be arranged.

## Project Overview

This project was designed to apply core data structures and algorithms in a practical recruitment scenario. Instead of relying only on Java built-in collections, the system uses custom Abstract Data Types (ADTs) to support efficient storage, searching, filtering, sorting, and scheduling operations.

The system includes four main modules:
- Job Management
- Applicant Management
- Matching Engine
- Interview Scheduling

## Objectives

- Apply custom ADTs in a real-world internship recruitment system
- Support applicant and job record management
- Match applicants with suitable jobs based on multiple criteria
- Schedule interviews based on priority and time-slot constraints
- Demonstrate searching, sorting, filtering, and reporting algorithms

## Core Data Structures and Algorithms

### Custom ADTs
- `HashedSetList`
- `HashedArrayList`
- `PriorityQueue`

### Algorithms Used
- Selection Sort
- Linear Search
- Bubble Sort
- Heap-based priority handling

## System Modules

### 1. Job Management Module
Allows employers to:
- Create new job postings
- Update job information
- Remove job postings
- Filter jobs by attributes such as location, status, job type, and salary
- Search jobs by category
- Sort jobs by salary, title, company, location, job type, or status
- Generate job reports

### 2. Applicant Management Module
Allows job seekers to:
- Add applicant profiles
- Update applicant details
- Remove applicant records
- Filter applicants by location, skills, CGPA, field of study, and internship type
- Sort applicants by name, CGPA, graduation year, and skills
- Search applicants by keyword or predefined criteria
- Generate applicant reports

### 3. Matching Engine Module
Supports:
- Matching applicants with jobs using multiple criteria
- Calculating matching scores
- Sorting matches by score
- Filtering matches by field, industry, location, and job type
- Generating reports grouped by criteria

### 4. Interview Scheduling Module
Supports:
- Creating interview schedules
- Prioritizing candidates using a custom priority queue
- Validating interview time slots
- Filtering top pending applicants
- Sorting interviews by date, applicant name, score, and status
- Generating interview reports

## My Contribution

Wong Jin Xuan focused on the Applicant Management module and custom ADT implementation, including:
- Applicant creation, update, removal, filtering, sorting, searching, and reporting
- ECB architecture for Applicant module
- Custom `HashedArrayList` implementation
- Selection sort and linear search integration in applicant operations

## Project Structure

```text
src/
├── adt/
│   ├── ArrayHashedSetList.java
│   ├── HashedArrayList.java
│   ├── HashedSetListInterface.java
│   ├── PriorityQueue.java
│   └── PriorityQueueInterface.java
├── boundary/
│   ├── ApplicantUI.java
│   ├── InterviewScheduleUI.java
│   ├── JobUI.java
│   ├── MainUI.java
│   └── MatchingUI.java
├── control/
│   ├── ApplicantManagement.java
│   ├── InterviewManagement.java
│   ├── JobManagement.java
│   └── JobMatching.java
├── dao/
│   ├── ApplicantInitializer.java
│   ├── InterviewInitializer.java
│   ├── JobInitializer.java
│   └── MatchInitializer.java
├── entity/
│   ├── Applicant.java
│   ├── Interview.java
│   ├── Job.java
│   └── JobApplicantMatch.java
├── utility/
│   ├── ApplicantOptions.java
│   ├── InterviewOptions.java
│   ├── JobMatchingEngine.java
│   ├── JobOptions.java
│   ├── MessageUI.java
│   └── Report.java
└── test/

```
### Technology Used
- Java
- NetBeans
- Object-Oriented Programming
- Custom Data Structures and Algorithms

### How to Run

## Requirements
- Java JDK 22 or above
- Apache NetBeans IDE 22 or above

## Steps
1. Open NetBeans IDE
2. Click File > Open Project
3. Open the project folder
4. Run the project
5. Use the console menu to navigate between modules

### Example Workflow
1. Employer adds job postings
2. Applicant registers profile details
3. Matching engine computes compatibility scores
4. Top candidates are identified
5. Interview schedules are created for shortlisted applicants

### Highlights
- Uses custom-built ADTs instead of relying only on Java collections
- Demonstrates practical application of DSA concepts
- Supports modular design using ECB architecture
- Covers multiple operations such as create, update, delete, filter, sort, search, and report generation

### Notes
This project was developed as an academic assignment for Data Structures and Algorithms.

### Author
My contribution: Wong Jin Xuan – Applicant Management module and HashedArrayList ADT.





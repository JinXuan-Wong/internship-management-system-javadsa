package boundary;

/**
 *
 * @author Dorcas Lim Yuan Yao
 */
import control.InterviewManagement;
import control.ApplicantManagement;
import entity.Interview;
import entity.Applicant;
import entity.Job;
import adt.HashedSetListInterface;
import adt.ArrayHashedSetList;
import adt.PriorityQueue;
import adt.PriorityQueueInterface;
import utility.InterviewOptions;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class InterviewScheduleUI {

    private InterviewManagement interviewManagement;
    private ApplicantManagement applicantManagement;
    private Scanner scanner;
    // Store the latest job requirements from the most recent interview scheduling
    private String[] lastSelectedSkills;
    private String[] lastSelectedFields;
    private double lastMinCGPA;
    private Job lastJob;

    // Initialize the UI with interview and applicant management systems
    public InterviewScheduleUI(InterviewManagement interviewManagement, ApplicantManagement applicantManagement) {
        this.interviewManagement = interviewManagement;
        this.applicantManagement = applicantManagement;
        this.scanner = new Scanner(System.in);
    }

    // Main menu that keeps running until user chooses to exit
    public void start() {
        int choice;
        do {
            displayMenu();
            choice = getChoice();

            switch (choice) {
                case 1 ->
                    scheduleNewInterview();
                case 2 ->
                    filterTopSuccessfulApplicants();
                case 3 ->
                    sortInterviews();
                case 4 ->
                    generateInterviewReport();
                case 0 ->
                    System.out.println("Returning to main menu...");
                default ->
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    // Schedule main menu
    private void displayMenu() {
        System.out.println("\n=== Interview Schedule Management ===");
        System.out.println("1. Schedule New Interview");
        System.out.println("2. Filter Top Pending Applicants");
        System.out.println("3. Sort Interviews");
        System.out.println("4. Generate Interview Report");
        System.out.println("0. Back to Main Menu");
        System.out.println("=====================================");
    }

    // Gets user input and makes sure it's a valid number
    private int getChoice() {
        System.out.print("Enter your choice: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next();
        }
        return scanner.nextInt();
    }

    // Step-by-step process to schedule a new interview:
    // 1. Get required skills
    // 2. Set minimum CGPA
    // 3. Choose required fields of study
    // 4. Create job posting
    // 5. Match candidates
    // 6. Option to auto-schedule interviews
    private void scheduleNewInterview() {
        System.out.println("\n=== Schedule New Interview ===");
        System.out.println("Please specify the candidate requirements:");

        // Step 1: Get required skills from user
        System.out.println("\nSelect Required Skills:");
        String skillBorder = "+" + "-".repeat(4) + "+" + "-".repeat(25) + "+" + "-".repeat(4) + "+"
                + "-".repeat(25) + "+" + "-".repeat(4) + "+" + "-".repeat(25) + "+";

        System.out.println(skillBorder);
        System.out.printf("| %-2s | %-23s | %-2s | %-23s | %-2s | %-23s |%n",
                "No", "Skill", "No", "Skill", "No", "Skill");
        System.out.println(skillBorder);

        // Display skills in a 3-column table
        String[][] skills = {
            {"1", "Java", "2", "Python", "3", "C++"},
            {"4", "SQL", "5", "Machine Learning", "6", "Data Analysis"},
            {"7", "Cybersecurity", "8", "Web Development", "9", "Cloud Computing"},
            {"10", "App Development", "11", "Artificial Intelligence", "12", "UI/UX Design"},
            {"13", "Networking", "14", "Blockchain", "15", "Big Data"},
            {"16", "Custom Skills", "", "", "", ""}
        };

        for (String[] row : skills) {
            System.out.printf("| %-2s | %-23s | %-2s | %-23s | %-2s | %-23s |%n",
                    row[0], row[1], row[2], row[3], row[4], row[5]);
        }
        System.out.println(skillBorder);

        System.out.print("\nEnter choices (e.g. 1,3,5) or '0' to skip: ");
        scanner.nextLine(); // Clear buffer
        String skillChoices = scanner.nextLine();
        String[] selectedSkills = getSkillsByChoices(skillChoices);

        // Step 2: Get minimum CGPA requirement
        System.out.print("\nEnter minimum CGPA requirement (0.0 - 4.0): ");
        double minCGPA = getValidCGPA();

        // Step 3: Get required fields of study
        System.out.println("\nSelect Required Fields of Study:");
        String fieldBorder = "+" + "-".repeat(4) + "+" + "-".repeat(25) + "+" + "-".repeat(4) + "+"
                + "-".repeat(25) + "+";

        System.out.println(fieldBorder);
        System.out.printf("| %-2s | %-23s | %-2s | %-23s |%n",
                "No", "Field", "No", "Field");
        System.out.println(fieldBorder);

        // Display fields in a 2-column table
        String[][] fields = {
            {"1", "Computer Science", "2", "Data Science"},
            {"3", "Software Engineering", "4", "Cybersecurity"},
            {"5", "Information Technology", "6", "Artificial Intelligence"},
            {"7", "Accounting", "8", "Finance"},
            {"9", "Business Administration", "10", "Marketing"},
            {"11", "Mechanical Engineering", "12", "Electrical Engineering"},
            {"13", "Civil Engineering", "", ""}
        };

        for (String[] row : fields) {
            System.out.printf("| %-2s | %-23s | %-2s | %-23s |%n",
                    row[0], row[1], row[2], row[3]);
        }
        System.out.println(fieldBorder);

        System.out.print("\nEnter choices (e.g. 1,3,5): ");
        String fieldChoices = scanner.nextLine();
        String[] selectedFields = getFieldsByChoices(fieldChoices);

        // Create job object with selected requirements
        Job job = new Job(1, "Software Engineer", "Tech Corp", "Kuala Lumpur",
                3500.0, "Full-time", "Software Engineer Position",
                selectedSkills != null ? String.join(", ", selectedSkills) : "",
                "Open", "6 months");

        // Store the requirements for later use
        this.lastSelectedSkills = selectedSkills;
        this.lastSelectedFields = selectedFields;
        this.lastMinCGPA = minCGPA;
        this.lastJob = job;

        // Step 5: Match candidates and display results
        System.out.println("\n=== Matching Results ===");
        System.out.println("Candidates matching your requirements (sorted by match score):");

        // Convert applicants to HashedSetList for processing
        HashedSetListInterface<Applicant> applicants = new ArrayHashedSetList<>();
        for (int i = 1001; i < 1001 + applicantManagement.getTotalApplicants(); i++) {
            Applicant applicant = applicantManagement.getApplicant(i);
            if (applicant != null) {
                applicants.add(applicant);
            }
        }

        // Match candidates with job requirements
        interviewManagement.matchAndCreateInterviews(applicants, job,
                selectedSkills, minCGPA, selectedFields);

        HashedSetListInterface<Interview> matches = interviewManagement.getPendingInterviews();

        if (matches.isEmpty()) {
            System.out.println("No candidates match your requirements.");
            return;
        }

        // Step 6: Sort candidates by match score using Priority Queue
        PriorityQueueInterface<Interview> priorityQueue = new PriorityQueue<>();
        for (int i = 1; i <= matches.getNumberOfEntries(); i++) {
            Interview interview = matches.getEntry(i);
            if (interview != null) {
                priorityQueue.enqueue(interview);
            }
        }

        // Create temporary array to store sorted interviews (highest score first)
        Interview[] sortedArray = new Interview[priorityQueue.getSize()];
        int index = sortedArray.length - 1;
        while (!priorityQueue.isEmpty()) {
            sortedArray[index--] = priorityQueue.dequeue();
        }

        // Add sorted interviews to HashedSetList in correct order
        HashedSetListInterface<Interview> sortedMatches = new ArrayHashedSetList<>();
        for (Interview interview : sortedArray) {
            if (interview != null) {
                sortedMatches.add(interview);
            }
        }

        displayMatchedCandidates(sortedMatches);

        // Step 7: Schedule interviews automatically if user chooses to
        System.out.print("\nWould you like to automatically schedule interviews for all candidates? (Y/N): ");
        String choice = scanner.nextLine().trim().toUpperCase();

        if (choice.equals("Y")) {
            System.out.println("\nAutomatically scheduling interviews for all candidates (in order of match score)...");

            // Set initial interview time starting from tomorrow at 12 PM
            LocalDateTime startTime = LocalDateTime.now().plusDays(1).withHour(12).withMinute(0);

            // Schedule interviews for each matched candidate
            for (int i = 1; i <= sortedMatches.getNumberOfEntries(); i++) {
                Interview interview = sortedMatches.getEntry(i);
                if (interview != null && interview.getApplicant() != null) {
                    System.out.println("-".repeat(50));
                    System.out.println("\nCandidate: " + interview.getApplicant().getName());
                    System.out.printf("Match Score: %.2f%n", interview.getMatchScore());
                    System.out.println("Preferred Interview Type: " + interview.getApplicant().getInternshipType());

                    boolean isScheduled = false;
                    while (!isScheduled) {
                        // Check if current time is past 4 PM, if so move to next day at 12 PM
                        if (startTime.getHour() >= 16) {
                            startTime = startTime.plusDays(1).withHour(12).withMinute(0);
                        }

                        if (interviewManagement.scheduleInterview(interview, startTime)) {
                            System.out.println("Interview scheduled successfully for: "
                                    + startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + "\n");
                            System.out.println("-".repeat(50));
                            startTime = startTime.plusHours(1); // Each interview takes 1 hour
                            isScheduled = true;
                        } else {
                            startTime = startTime.plusHours(1);
                        }
                    }
                }
            }
            System.out.println("\nAutomatic interview scheduling completed!");
            System.out.println("=".repeat(50));
        }
    }

    // Filter function that shows the best matching candidates based on:
    // 1. Skills match - shows candidate with highest skill match score
    // 2. Field of study match - shows candidate with matching field
    // Uses requirements from the last interview scheduling session
    private void filterTopSuccessfulApplicants() {
        if (lastJob == null) {
            System.out.println("\nNo interview requirements found. Please schedule a new interview first.");
            return;
        }

        System.out.println("\n=== Filter Top Pending Applicants ===");
        System.out.println("1. Filter by Skills");
        System.out.println("2. Filter by Field of Study");
        System.out.println("0. Back");

        System.out.print("\nEnter your choice: ");
        scanner.nextLine(); // Clear buffer
        int choice = getChoice();

        switch (choice) {
            case 1 -> {
                System.out.println("\nFinding best match based on required skills:");
                System.out.println("Required Skills: " + String.join(", ", lastSelectedSkills));
                Interview topSkillMatch = findTopMatchBySkills();
                displayTopMatch(topSkillMatch);
            }
            case 2 -> {
                System.out.println("\nFinding best match based on field of study:");
                System.out.println("Required Fields: " + String.join(", ", lastSelectedFields));
                Interview topFieldMatch = findTopMatchByField();
                displayTopMatch(topFieldMatch);
            }
            case 0 ->
                System.out.println("Returning to previous menu...");
            default ->
                System.out.println("Invalid choice. Please try again.");
        }
    }

    // Finds the candidate whose skills best match the requirements
    // Returns the interview object with the highest skill match score
    private Interview findTopMatchBySkills() {
        Interview topMatch = null;
        double highestSkillScore = 0.0;

        HashedSetListInterface<Interview> interviews = interviewManagement.getPendingInterviews();

        for (int i = 1; i <= interviews.getNumberOfEntries(); i++) {
            Interview interview = interviews.getEntry(i);
            if (interview != null && interview.getApplicant() != null) {
                Applicant applicant = interview.getApplicant();
                double skillScore = calculateSkillMatchScore(applicant.getSkills(), lastSelectedSkills);

                if (skillScore > highestSkillScore) {
                    highestSkillScore = skillScore;
                    topMatch = interview;
                }
            }
        }

        return topMatch;
    }

    // Finds the candidate whose field of study matches the requirements
    // Returns the interview object with matching field of study
    private Interview findTopMatchByField() {
        Interview topMatch = null;
        double highestFieldScore = 0.0;

        HashedSetListInterface<Interview> interviews = interviewManagement.getPendingInterviews();

        for (int i = 1; i <= interviews.getNumberOfEntries(); i++) {
            Interview interview = interviews.getEntry(i);
            if (interview != null && interview.getApplicant() != null) {
                Applicant applicant = interview.getApplicant();
                double fieldScore = calculateFieldMatchScore(applicant.getFieldOfStudy(), lastSelectedFields);

                if (fieldScore > highestFieldScore) {
                    highestFieldScore = fieldScore;
                    topMatch = interview;
                }
            }
        }

        return topMatch;
    }

    // Calculates how well a candidate's skills match the requirements
    // Score is based on the number of matching skills divided by total required skills
    // Returns a score between 0.0 (no match) and 1.0 (perfect match)
    private double calculateSkillMatchScore(String[] applicantSkills, String[] requiredSkills) {
        if (requiredSkills.length == 0) {
            return 1.0;
        }

        int matchCount = 0;
        for (String requiredSkill : requiredSkills) {
            for (String applicantSkill : applicantSkills) {
                if (applicantSkill.equalsIgnoreCase(requiredSkill)) {
                    matchCount++;
                    break;
                }
            }
        }
        return (double) matchCount / requiredSkills.length;
    }

    // Checks if candidate's field of study matches any of the required fields
    // Returns 1.0 if there's a match, 0.0 if no match
    private double calculateFieldMatchScore(String applicantField, String[] requiredFields) {
        if (requiredFields.length == 0) {
            return 1.0;
        }

        for (String requiredField : requiredFields) {
            if (applicantField.equalsIgnoreCase(requiredField)) {
                return 1.0;
            }
        }
        return 0.0;
    }

    // Shows detailed information about a matching candidate including:
    // - ID, Name, CGPA
    // - Field of study
    // - Skills (formatted to fit the display)
    // - Match score
    private void displayTopMatch(Interview topMatch) {
        if (topMatch == null) {
            System.out.println("\nNo matching applicants found.");
            return;
        }

        System.out.println("\n=== Best Matching Applicant ===");

        // Create table borders
        String horizontalLine = "+" + "-".repeat(6) + "+" + "-".repeat(15) + "+"
                + "-".repeat(8) + "+" + "-".repeat(25) + "+"
                + "-".repeat(25) + "+" + "-".repeat(10) + "+"
                + "-".repeat(15) + "+";

        // Print header
        System.out.println(horizontalLine);
        System.out.printf("| %-4s | %-13s | %-6s | %-23s | %-23s | %-8s | %-13s |%n",
                "ID", "Name", "CGPA", "Field", "Skills", "Score", "Preferred Type");
        System.out.println(horizontalLine);

        // Get applicant details
        Applicant applicant = topMatch.getApplicant();
        String skills = String.join(", ", applicant.getSkills());

        // Print the first line with basic info
        System.out.printf("| %-4d | %-13s | %-6.2f | %-23s | ",
                applicant.getId(),
                applicant.getName(),
                applicant.getCgpa(),
                truncateOrPad(applicant.getFieldOfStudy(), 23));

        // Handle skills display with wrapping
        String[] skillParts = splitSkills(skills, 23);
        System.out.printf("%-23s | %-8.2f | %-13s |%n",
                skillParts[0],
                topMatch.getMatchScore(),
                applicant.getInternshipType());

        // Print remaining skill parts with proper indentation
        for (int j = 1; j < skillParts.length; j++) {
            System.out.printf("| %-4s | %-13s | %-6s | %-23s | %-23s | %-8s | %-13s |%n",
                    "", "", "", "", skillParts[j], "", "");
        }

        System.out.println(horizontalLine);
    }

    // Sort all interviews by different criteria:
    // - Interview Date (earliest first)
    // - Applicant Name (A to Z)
    // - Match Score (highest first)
    // - Status
    private void sortInterviews() {
        System.out.println("\n=== Sort Interviews ===");
        System.out.println("Sort by:");
        for (int i = 0; i < InterviewOptions.SORT_CRITERIA.length; i++) {
            System.out.println((i + 1) + ". " + InterviewOptions.SORT_CRITERIA[i]);
        }
        System.out.println("0. Back");

        System.out.print("\nEnter your choice: ");
        int choice = getChoice();

        if (choice > 0 && choice <= InterviewOptions.SORT_CRITERIA.length) {
            sortInterviewsByCriteria(InterviewOptions.SORT_CRITERIA[choice - 1]);
        }
    }

    // Applies the chosen sorting criteria to the interview list
    // Uses bubble sort to arrange interviews in the correct order
    private void sortInterviewsByCriteria(String criteria) {
        HashedSetListInterface<Interview> interviews = interviewManagement.getScheduledInterviews();
        Interview[] sortedInterviews = new Interview[interviews.getNumberOfEntries()];

        // convert to array for sorting
        for (int i = 0; i < interviews.getNumberOfEntries(); i++) {
            sortedInterviews[i] = interviews.getEntry(i + 1);
        }

        // sort interviews based on the selected criteria
        for (int i = 0; i < sortedInterviews.length - 1; i++) {
            for (int j = 0; j < sortedInterviews.length - i - 1; j++) {
                if (shouldSwap(sortedInterviews[j], sortedInterviews[j + 1], criteria)) {
                    Interview temp = sortedInterviews[j];
                    sortedInterviews[j] = sortedInterviews[j + 1];
                    sortedInterviews[j + 1] = temp;
                }
            }
        }

        // display sorted interviews
        System.out.println("\n=== Sorted Interview List ===");
        for (Interview interview : sortedInterviews) {
            if (interview != null) {
                displayInterviewDetails(interview);
            }
        }
    }

    // Compares two interviews to determine their order when sorting
    // Returns true if interviews need to be swapped based on the criteria
    private boolean shouldSwap(Interview a, Interview b, String criteria) {
        // Null safety check - if either interview is null, no swap needed
        if (a == null || b == null) {
            return false;
        }

        // Perform sorting based on the selected criteria
        switch (criteria) {
            case "Interview Date" -> {
                LocalDateTime timeA = a.getScheduledTime();
                LocalDateTime timeB = b.getScheduledTime();
                // If either time is null, treat them as equal (no swap)
                if (timeA == null || timeB == null) {
                    return false;
                }
                // Sort by date in ascending order (earlier dates first)
                return timeA.isAfter(timeB);
            }
            case "Applicant Name" -> {
                // Null safety check for applicants
                if (a.getApplicant() == null || b.getApplicant() == null) {
                    return false;
                }
                // Sort by applicant name alphabetically (A to Z)
                String nameA = a.getApplicant().getName().toLowerCase();
                String nameB = b.getApplicant().getName().toLowerCase();
                return nameA.compareTo(nameB) > 0;
            }
            case "Match Score" -> {
                // Sort by match score in descending order (highest first)
                double scoreA = a.getMatchScore();
                double scoreB = b.getMatchScore();
                return scoreA < scoreB;
            }
            case "Status" -> {
                // Sort by status alphabetically (A to Z)
                String statusA = a.getStatus();
                String statusB = b.getStatus();
                // Null safety check for status
                if (statusA == null || statusB == null) {
                    return false;
                }
                return statusA.compareTo(statusB) > 0;
            }
            default -> {
                // If criteria not recognized, don't swap
                return false;
            }
        }
    }

    // Shows complete details of a single interview including:
    // - Interview ID
    // - Applicant name
    // - Current status
    // - Scheduled time (if any)
    // - Match score (as percentage)
    private void displayInterviewDetails(Interview interview) {
        System.out.println("\n---------------------------------");
        System.out.println("Interview ID: " + interview.getInterviewId());
        System.out.println("Applicant: " + interview.getApplicant().getName());
        System.out.println("Status: " + interview.getStatus());
        if (interview.getScheduledTime() != null) {
            System.out.println("Scheduled Time: "
                    + interview.getScheduledTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        }
        System.out.println("Match Score: " + String.format("%.2f%%", interview.getMatchScore()));
        System.out.println("---------------------------------");
    }

    // Generate reports about interviews:
    // 1. View all scheduled interviews
    // 2. View successful candidates
    private void generateInterviewReport() {
        System.out.println("\n=== Interview Schedule Report ===");
        System.out.println("1. View All Scheduled Interviews");
        System.out.println("2. View Successful Candidates");
        System.out.println("0. Back");

        System.out.print("\nEnter your choice: ");
        int choice = getChoice();

        switch (choice) {
            case 1 ->
                viewInterviewSchedule();
            case 2 ->
                viewSuccessfulCandidates();
            case 0 ->
                System.out.println("Returning to previous menu...");
            default ->
                System.out.println("Invalid choice. Please try again.");
        }
    }

    // Shows all scheduled interviews in chronological order
    // Displays each interview's details in a formatted table
    private void viewInterviewSchedule() {
        HashedSetListInterface<Interview> interviews = interviewManagement.getScheduledInterviews();
        System.out.println("\n=== Interview Schedule Report ===");

        if (interviews.isEmpty()) {
            System.out.println("No interviews have been scheduled.");
            return;
        }

        // Display header for interview schedule
        System.out.printf("%-6s %-15s %-8s %-25s %-10s %-20s %-15s%n",
                "ID", "Name", "CGPA", "Field", "Score", "Schedule", "Interview Type");
        System.out.println("=".repeat(100));

        // Sort interviews by scheduled time
        Interview[] sortedInterviews = new Interview[interviews.getNumberOfEntries()];
        for (int i = 0; i < interviews.getNumberOfEntries(); i++) {
            sortedInterviews[i] = interviews.getEntry(i + 1);
        }

        // Bubble sort interviews by scheduled time
        for (int i = 0; i < sortedInterviews.length - 1; i++) {
            for (int j = 0; j < sortedInterviews.length - i - 1; j++) {
                if (sortedInterviews[j] != null && sortedInterviews[j + 1] != null
                        && sortedInterviews[j].getScheduledTime() != null && sortedInterviews[j + 1].getScheduledTime() != null
                        && sortedInterviews[j].getScheduledTime().isAfter(sortedInterviews[j + 1].getScheduledTime())) {
                    Interview temp = sortedInterviews[j];
                    sortedInterviews[j] = sortedInterviews[j + 1];
                    sortedInterviews[j + 1] = temp;
                }
            }
        }

        // Display each scheduled interview
        for (Interview interview : sortedInterviews) {
            if (interview != null && interview.getApplicant() != null) {
                Applicant applicant = interview.getApplicant();
                String scheduleTime = interview.getScheduledTime() == null ? "Not Scheduled"
                        : interview.getScheduledTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

                System.out.printf("%-6d %-15s %-8.2f %-25s %-10.2f %-20s %-15s%n",
                        applicant.getId(),
                        applicant.getName(),
                        applicant.getCgpa(),
                        applicant.getFieldOfStudy(),
                        Math.round(interview.getMatchScore() * 100.0) / 100.0,
                        scheduleTime,
                        applicant.getInternshipType());
            }
        }
        System.out.println("=".repeat(100));
    }

    // Shows a list of candidates who successfully passed their interviews
    private void viewSuccessfulCandidates() {
        dao.InterviewInitializer.displaySuccessfulCases();
    }

    // Helper method: Converts user's numeric choices into skill names
    // Handles input validation and allows multiple skill selections
    private String[] getSkillsByChoices(String input) {
        while (true) {
            try {
                if (input.trim().isEmpty() || input.equals("0")) {
                    return new String[0];  // Return empty array instead of null
                }

                String[] choices = input.split(",");
                String[] skills = new String[choices.length];

                // Validate each choice
                for (int i = 0; i < choices.length; i++) {
                    int choice = Integer.parseInt(choices[i].trim());
                    if (choice < 1 || choice > 16) {
                        System.out.println("Invalid choice: " + choice + ". Please enter numbers between 1 and 16.");
                        System.out.print("Enter choices (comma-separated e.g., 1,3,5) or '0' to skip: ");
                        input = scanner.nextLine();
                        return getSkillsByChoices(input);
                    }

                    skills[i] = switch (choice) {
                        case 1 ->
                            "Java";
                        case 2 ->
                            "Python";
                        case 3 ->
                            "C++";
                        case 4 ->
                            "SQL";
                        case 5 ->
                            "Machine Learning";
                        case 6 ->
                            "Data Analysis";
                        case 7 ->
                            "Cybersecurity";
                        case 8 ->
                            "Web Development";
                        case 9 ->
                            "Cloud Computing";
                        case 10 ->
                            "App Development";
                        case 11 ->
                            "Artificial Intelligence";
                        case 12 ->
                            "UI/UX Design";
                        case 13 ->
                            "Networking";
                        case 14 ->
                            "Blockchain";
                        case 15 ->
                            "Big Data";
                        case 16 ->
                            "Custom Skills";
                        default ->
                            "Unknown";
                    };
                }
                return skills;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input format. Please enter numbers separated by commas.");
                System.out.print("Enter choices (comma-separated e.g., 1,3,5) or '0' to skip: ");
                input = scanner.nextLine();
                return getSkillsByChoices(input);
            }
        }
    }

    // Helper method: Makes sure the CGPA input is valid
    // Accepts values between 0.0 and 4.0 only
    private double getValidCGPA() {
        while (true) {
            try {
                String input = scanner.nextLine().trim();
                double cgpa = Double.parseDouble(input);

                if (cgpa < 0.0 || cgpa > 4.0) {
                    System.out.println("CGPA must be between 0.0 and 4.0.");
                    System.out.print("Enter minimum CGPA requirement (0.0 - 4.0): ");
                    continue;
                }

                return cgpa;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number (e.g., 3.5)");
                System.out.print("Enter minimum CGPA requirement (0.0 - 4.0): ");
            }
        }
    }

    // Helper method: Converts user's numeric choices into field of study names
    // Handles input validation and allows multiple field selections
    private String[] getFieldsByChoices(String input) {
        while (true) {
            try {
                if (input.trim().isEmpty() || input.equals("0")) {
                    return new String[0];  // Return empty array instead of null
                }

                String[] choices = input.split(",");
                String[] fields = new String[choices.length];

                // Validate each choice
                for (int i = 0; i < choices.length; i++) {
                    int choice = Integer.parseInt(choices[i].trim());
                    if (choice < 1 || choice > 13) {
                        System.out.println("Invalid choice: " + choice + ". Please enter numbers between 1 and 13.");
                        System.out.print("Enter choices (comma-separated e.g., 1,3,5): ");
                        input = scanner.nextLine();
                        return getFieldsByChoices(input);
                    }

                    fields[i] = switch (choice) {
                        case 1 ->
                            "Computer Science";
                        case 2 ->
                            "Data Science";
                        case 3 ->
                            "Software Engineering";
                        case 4 ->
                            "Cybersecurity";
                        case 5 ->
                            "Information Technology";
                        case 6 ->
                            "Artificial Intelligence";
                        case 7 ->
                            "Accounting";
                        case 8 ->
                            "Finance";
                        case 9 ->
                            "Business Administration";
                        case 10 ->
                            "Marketing";
                        case 11 ->
                            "Mechanical Engineering";
                        case 12 ->
                            "Electrical Engineering";
                        case 13 ->
                            "Civil Engineering";
                        default ->
                            "Unknown";
                    };
                }
                return fields;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input format. Please enter numbers separated by commas.");
                System.out.print("Enter choices (comma-separated e.g., 1,3,5): ");
                input = scanner.nextLine();
                return getFieldsByChoices(input);
            }
        }
    }

    // Shows all matched candidates in a formatted table
    // Includes ID, Name, CGPA, Field, Skills, Score, and Interview Type
    // Handles long skill lists by wrapping text
    private void displayMatchedCandidates(HashedSetListInterface<Interview> matches) {
        if (matches.isEmpty()) {
            System.out.println("\nNo matching candidates found.");
            return;
        }

        System.out.println("\n=== Matched Candidates ===");

        // Create table borders
        String horizontalLine = "+" + "-".repeat(6) + "+" + "-".repeat(15) + "+"
                + "-".repeat(8) + "+" + "-".repeat(25) + "+"
                + "-".repeat(25) + "+" + "-".repeat(10) + "+"
                + "-".repeat(15) + "+";

        // Print header
        System.out.println(horizontalLine);
        System.out.printf("| %-4s | %-13s | %-6s | %-23s | %-23s | %-8s | %-13s |%n",
                "ID", "Name", "CGPA", "Field", "Skills", "Score", "Preferred Type");
        System.out.println(horizontalLine);

        // Print each candidate's information
        for (int i = 1; i <= matches.getNumberOfEntries(); i++) {
            Interview interview = matches.getEntry(i);
            if (interview != null && interview.getApplicant() != null) {
                Applicant applicant = interview.getApplicant();
                String skills = String.join(", ", applicant.getSkills());

                // Print the first line with basic info
                System.out.printf("| %-4d | %-13s | %-6.2f | %-23s | ",
                        applicant.getId(),
                        applicant.getName(),
                        applicant.getCgpa(),
                        truncateOrPad(applicant.getFieldOfStudy(), 23));

                // Handle skills display with wrapping
                String[] skillParts = splitSkills(skills, 23);
                System.out.printf("%-23s | %-8.2f | %-13s |%n",
                        skillParts[0],
                        interview.getMatchScore(),
                        interview.getInterviewType());

                // Print remaining skill parts with proper indentation
                for (int j = 1; j < skillParts.length; j++) {
                    System.out.printf("| %-4s | %-13s | %-6s | %-23s | %-23s | %-8s | %-13s |%n",
                            "", "", "", "", skillParts[j], "", "");
                }

                // Add a separator line between candidates
                System.out.println(horizontalLine);
            }
        }
    }

    // Helper method to split skills into parts of specified width
    private String[] splitSkills(String skills, int width) {
        if (skills == null || skills.isEmpty()) {
            return new String[]{""};
        }

        // First, split skills by comma and space
        String[] individualSkills = skills.split(", ");

        // Create array to store result parts
        String[] result = new String[((skills.length() / width) + 1)];
        int resultIndex = 0;

        // Build each line
        String currentLine = individualSkills[0];
        for (int i = 1; i < individualSkills.length; i++) {
            String skill = individualSkills[i];
            // Check if adding the next skill would exceed the width
            if (currentLine.length() + 2 + skill.length() <= width) {
                currentLine += ", " + skill;
            } else {
                result[resultIndex++] = currentLine;
                currentLine = skill;
            }
        }

        // Add the last line
        if (!currentLine.isEmpty()) {
            result[resultIndex++] = currentLine;
        }

        // Create final array with exact size
        String[] finalResult = new String[resultIndex];
        System.arraycopy(result, 0, finalResult, 0, resultIndex);

        return finalResult;
    }

    // Helper method to truncate or pad a string to exact length
    private String truncateOrPad(String text, int length) {
        if (text == null) {
            return " ".repeat(length);
        }
        if (text.length() > length) {
            return text.substring(0, length - 3) + "...";
        }
        return text + " ".repeat(length - text.length());
    }
}

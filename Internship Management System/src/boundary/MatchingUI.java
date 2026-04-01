package boundary;
/**
 *
 * @author Tan Yen Ping
 */
import adt.HashedSetListInterface;
import control.ApplicantManagement;
import entity.Applicant;
import entity.Job;
import entity.JobApplicantMatch;
import control.JobMatching;
import utility.MessageUI;
import utility.ApplicantOptions;
import java.util.Scanner;

public class MatchingUI {

    private final JobMatching jobMatching;
    private HashedSetListInterface<JobApplicantMatch> jobApplicantMatches;
    private ApplicantManagement applicantManagement;
    private Scanner scanner;

    public MatchingUI(JobMatching jobMatching) {
        this.jobMatching = jobMatching;
        this.jobApplicantMatches = jobMatching.getJobApplicantMatches();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        int choice;
        do {
            displayMatchingMenu();
            choice = getIntegerInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    matchApplicantsWithJobs();
                    break;
                case 2:
                    sortMatchesByScore();
                    break;
                case 3:
                    showFilteringMenu(jobMatching, true);
                    break;
                case 4:
                    generateReportByLocation();
                    break;
                case 0:
                    MessageUI.displayMessage("\nReturning to Main Menu...");
                    break;
                default:
                    MessageUI.displayInvalidChoiceMessage();
            }
        } while (choice != 0);
    }

    private void displayMatchingMenu() {
        System.out.println("\n==== MATCHING ENGINE MODULE ====");
        System.out.println("1. Match Applicants with Jobs");
        System.out.println("2. Sort Matches by Score");
        System.out.println("3. Search Matches by Criteria");
        System.out.println("4. Generated Matching Report");
        System.out.println("0. Exit");
        System.out.println("================================");
    }

    private int getIntegerInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextInt();
    }
//-----------------------------option1 Match Applicant to Jobs----------------
    public void matchApplicantsWithJobs() {
        if (jobMatching.getJobs().isEmpty()) {
            System.out.println("No jobs available for matching.");
            return;
        }
        if (jobMatching.getApplicants().isEmpty()) {
            System.out.println("No applicants available for matching.");
            return;
        }

        for (int j = 0; j < jobMatching.getApplicants().getNumberOfEntries(); j++) {
            Applicant applicant = jobMatching.getApplicants().getEntry(j);

            for (int i = 0; i < jobMatching.getJobs().getNumberOfEntries(); i++) {
                Job job = jobMatching.getJobs().getEntry(i);
                jobMatching.matchApplicantToJob(applicant, job, false);
            }
        }

        this.jobApplicantMatches = jobMatching.getJobApplicantMatches();
        displayApplicantMatches(jobMatching);
    }

    public static void displayApplicantMatches(JobMatching jobMatching) {
        if (jobMatching == null || jobMatching.getJobApplicantMatches().isEmpty()) {
            System.out.println("No matches available.");
            return;
        }

        for (int i = 0; i < jobMatching.getApplicants().getNumberOfEntries(); i++) {
            Applicant applicant = jobMatching.getApplicants().getEntry(i);
            boolean matchFound = false;

            System.out.println("===================================");
            System.out.println("Name: " + applicant.getName());
            System.out.println("ID: " + applicant.getId());
            System.out.println("===================================");

            for (int j = 0; j < jobMatching.getJobApplicantMatches().getNumberOfEntries(); j++) {
                JobApplicantMatch match = jobMatching.getJobApplicantMatches().getEntry(j);

                if (match.getApplicant().equals(applicant) && match.getMatchScore() > 0) {
                    matchFound = true;
                    System.out.println(match.displayMatchInfo());
                    System.out.println("-----------------------------------");
                }
            }

            if (!matchFound) {
                System.out.println("No matches found for " + applicant.getName() + ".");
                System.out.println("-----------------------------------");
            }
            System.out.println();
        }
    }
//------------------------------option2 sortMatchesbyScore-----------------------------
    public void sortMatchesByScore() {
        HashedSetListInterface<JobApplicantMatch> allMatches = jobMatching.getJobApplicantMatches();

        if (allMatches.isEmpty()) {
            System.out.println("No matches available.");
            return;
        }

        JobApplicantMatch[] sortedMatches = new JobApplicantMatch[allMatches.getNumberOfEntries()];
        for (int i = 0; i < allMatches.getNumberOfEntries(); i++) {
            sortedMatches[i] = allMatches.getEntry(i);
        }

        for (int i = 0; i < sortedMatches.length - 1; i++) {
            for (int j = 0; j < sortedMatches.length - i - 1; j++) {
                if (sortedMatches[j].getMatchScore() < sortedMatches[j + 1].getMatchScore()) {
                    JobApplicantMatch temp = sortedMatches[j];
                    sortedMatches[j] = sortedMatches[j + 1];
                    sortedMatches[j + 1] = temp;
                }
            }
        }

        for (int i = 0; i < sortedMatches.length; i++) {
            System.out.println(sortedMatches[i].displayNameNId());
            System.out.println(sortedMatches[i].displayMatchInfo());
            System.out.println("-----------------------------------");
        }
    }
//---------------------option3 filtering Menu------------------------------------
    public static void showFilteringMenu(JobMatching jobMatching, boolean applyFilter) {
    Scanner scanner = new Scanner(System.in);
    HashedSetListInterface<JobApplicantMatch> jobApplicantMatches = jobMatching.getJobApplicantMatches();
    JobApplicantMatch[] results = new JobApplicantMatch[100];
    int resultCount;

    while (true) {
        System.out.println("\nFilter Applicants:");
        System.out.println("1. Field of Study");
        System.out.println("2. Preferred Industry");
        System.out.println("3. Match Job's with Preferred Location");
        System.out.println("4. Match Job's with Preferred Job Type");
        System.out.println("0. Exit");
        System.out.println("-----------------------");
        System.out.print("Enter your choice: ");

        int choice = Integer.parseInt(scanner.nextLine());
        if (choice == 0) return;

        String criteria = "";
        switch (choice) {
            case 1:
                printOptions(ApplicantOptions.FIELDS_OF_STUDY);
                System.out.print("Select Field of Study: ");
                criteria = ApplicantOptions.FIELDS_OF_STUDY[Integer.parseInt(scanner.nextLine()) - 1];
                break;
            case 2:
                printOptions(ApplicantOptions.PREFERRED_INDUSTRIES);
                System.out.print("Select Industry: ");
                criteria = ApplicantOptions.PREFERRED_INDUSTRIES[Integer.parseInt(scanner.nextLine()) - 1];
                break;
            case 3:
                printOptions(ApplicantOptions.LOCATIONS);
                System.out.print("Select Location: ");
                criteria = ApplicantOptions.LOCATIONS[Integer.parseInt(scanner.nextLine()) - 1];
                break;
            case 4:
                printOptions(ApplicantOptions.INTERNSHIP_PREFERENCES);
                System.out.print("Select Job Type: ");
                criteria = ApplicantOptions.INTERNSHIP_PREFERENCES[Integer.parseInt(scanner.nextLine()) - 1];
                break;
            default:
                System.out.println("Invalid choice.");
                continue;
        }

        resultCount = 0;
        for (int i = 0; i < jobApplicantMatches.getNumberOfEntries(); i++) {
            JobApplicantMatch match = jobApplicantMatches.getEntry(i);
            Applicant applicant = match.getApplicant();
            Job job = match.getJob();

            boolean isMatch = switch (choice) {
                case 1 -> applicant.getFieldOfStudy().equalsIgnoreCase(criteria);
                case 2 -> applicant.getPreferredIndustry().equalsIgnoreCase(criteria);
                case 3 -> applicant.getLocation().equalsIgnoreCase(criteria) && job.getLocation().equalsIgnoreCase(criteria);
                case 4 -> applicant.getInternshipType().equalsIgnoreCase(criteria) && job.getJobType().equalsIgnoreCase(criteria);
                default -> false;
            };

            if (isMatch) {
                results[resultCount++] = match;
            }
        }

        printMatchesGroupedByApplicant(results, resultCount);
    }
}

    private static void printMatchesGroupedByApplicant(JobApplicantMatch[] matches, int count) {
    if (count == 0) {
        System.out.println("No matching applicants found.");
        return;
    }

    Applicant[] uniqueApplicants = new Applicant[count];
    int uniqueCount = 0;

    for (int i = 0; i < count; i++) {
        Applicant applicant = matches[i].getApplicant();
        boolean exists = false;
        for (int j = 0; j < uniqueCount; j++) {
            if (uniqueApplicants[j].equals(applicant)) {
                exists = true;
                break;
            }
        }
        if (!exists) {
            uniqueApplicants[uniqueCount++] = applicant;
        }
    }

    for (int i = 0; i < uniqueCount; i++) {
        Applicant applicant = uniqueApplicants[i];
        System.out.println("==================================");
        System.out.println("Name: " + applicant.getName());
        System.out.println("ID: " + applicant.getId());
        System.out.println("==================================");

        for (int j = 0; j < count; j++) {
            if (matches[j].getApplicant().equals(applicant)) {
                System.out.println(matches[j].displayMatchInfo());
                System.out.println("-----------------------------------");
            }
        }
    }
}

//------------------------option4: GeneratedReport------------------------
    private void generateReportByLocation() {
    if (jobApplicantMatches == null || jobApplicantMatches.getNumberOfEntries() == 0) {
        System.out.println("No matching records available.");
        return;
    }

    Scanner scanner = new Scanner(System.in);

    System.out.println("\n=== Generate Matching Report by Location ===");
    System.out.println("Select the location:");
    ApplicantOptions.printOptions(ApplicantOptions.LOCATIONS);
    System.out.print("Enter your choice: ");
    int locationChoice = Integer.parseInt(scanner.nextLine());

    if (locationChoice < 1 || locationChoice > ApplicantOptions.LOCATIONS.length) {
        System.out.println("Invalid location choice.");
        return;
    }

    String selectedLocation = ApplicantOptions.LOCATIONS[locationChoice - 1];
    System.out.println("\nLocation: " + selectedLocation);
    System.out.println("=".repeat(100));

    // Table header
    System.out.printf("%-6s %-15s %-25s %-20s %-15s %-10s%n",
            "ID", "Name", "Field", "Company", "JobStatus", "Score" );
    System.out.println("=".repeat(100));

    boolean matchFound = false;

    for (int i = 0; i < jobApplicantMatches.getNumberOfEntries(); i++) {
        JobApplicantMatch match = jobApplicantMatches.getEntry(i);
        if (match != null && match.getJob() != null && match.getApplicant() != null) {
            Job job = match.getJob();
            Applicant applicant = match.getApplicant();

            if (job.getLocation().equalsIgnoreCase(selectedLocation)
                    && applicant.getLocation().equalsIgnoreCase(selectedLocation)) {
                matchFound = true;

                System.out.printf("%-6d %-15s %-25s %-20s %-15s %-10.2f%n",
                        match.getApplicant().getId(),
                        match.getApplicant().getName(),
                        match.getApplicant().getFieldOfStudy(),
                        match.getJob().getCompany(),
                        match.getJob().getStatus(),
                        Math.round(match.getMatchScore() * 100.0) / 100.0);
                        
                        
            }
        }
    }

    if (!matchFound) {
        System.out.println("No matches found for the selected location.");
    }

    System.out.println("=".repeat(100));
}
    private static void printOptions(String[] options) {
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
    }
}
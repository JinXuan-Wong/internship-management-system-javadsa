package boundary;

/**
 *
 * @author Low Qing Ying Wong Jin Xuan Tan Yen Ping Dorcas Lim Yuan Yao
 *
 */
import control.ApplicantManagement;
import control.JobManagement;
import control.InterviewManagement;
import control.JobMatching;
import utility.MessageUI;
import java.util.Scanner;

public class MainUI {

    private static final Scanner scanner = new Scanner(System.in);
    private static final ApplicantManagement applicantManagement = new ApplicantManagement();
    private static final JobManagement jobmanagement = new JobManagement();
    private static final InterviewManagement interviewManagement = new InterviewManagement();
    private static final JobMatching jobMatching = new JobMatching();

    public static void main(String[] args) {
        int choice;
        do {
            displayMainMenu();
            choice = getIntegerInput("Enter your choice: ");

            switch (choice) {
                case 1 -> {
                    MessageUI.displayMessage("\nWelcome, Employer!");
                    JobUI jobUI = new JobUI(jobmanagement);
                    jobUI.start();
                }
                case 2 -> {
                    MessageUI.displayMessage("\nWelcome, Job Seeker!");
                    ApplicantUI applicantUI = new ApplicantUI(applicantManagement); // Connect to ApplicantUI
                    applicantUI.start();
                }
                case 3 -> {
                    MessageUI.displayMessage("\nFind the Matching rate!");
                    MatchingUI matchingUI = new MatchingUI(jobMatching);
                    matchingUI.start();
                }
                case 4 -> {
                    MessageUI.displayMessage("\nWelcome to Interview Schedule Management");
                    InterviewScheduleUI interviewUI = new InterviewScheduleUI(interviewManagement, applicantManagement);
                    interviewUI.start();
                }
                case 0 ->
                    MessageUI.displayExitMessage();
                default ->
                    MessageUI.displayInvalidChoiceMessage();
            }
        } while (choice != 0);
    }

    private static void displayMainMenu() {
        System.out.println("\n=== Job Portal ===");
        System.out.println("1. Enter as Employer");
        System.out.println("2. Enter as Applicant");
        System.out.println("3. Matching System");
        System.out.println("4. Interview Schedule");
        System.out.println("0. Exit");
        System.out.println("==================");
    }

    private static int getIntegerInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            MessageUI.displayError("Invalid input. Please enter a number.");
            scanner.next(); // Clear invalid input
        }
        int num = scanner.nextInt();
        scanner.nextLine();
        return num;
    }
}

package dao;

/**
 *
 * @author Dorcas Lim Yuan Yao
 */
import entity.Interview;
import entity.Applicant;
import entity.Job;
import control.InterviewManagement;
import control.ApplicantManagement;
import control.JobManagement;
import java.time.LocalDateTime;
import adt.HashedSetListInterface;
import adt.ArrayHashedSetList;

/**
 * Handles the setup and management of interview data. Used to create sample
 * interviews and track successful ones.
 */
public class InterviewInitializer {

    private InterviewManagement interviewManagement;
    private ApplicantManagement applicantManagement;
    private JobManagement jobManagement;
    private static final HashedSetListInterface<Interview> successfulInterviews = new ArrayHashedSetList<>();
    private static boolean isInitialized = false;

    /**
     * Creates a new initializer with required management objects
     */
    public InterviewInitializer(InterviewManagement interviewManagement,
            ApplicantManagement applicantManagement,
            JobManagement jobManagement) {
        this.interviewManagement = interviewManagement;
        this.applicantManagement = applicantManagement;
        this.jobManagement = jobManagement;
    }

    /**
     * Sets up some example successful interviews. Only runs once to avoid
     * duplicates.
     */
    public static void initializeSuccessfulCases() {
        if (isInitialized) {
            return; // Don't run twice
        }

        // Create sample applicants
        Applicant applicant1 = new Applicant(1001, "John Lee", "Computer Science", 2024, 3.8,
                "Kuala Lumpur", new String[]{"Java", "Python"}, "Software Development", "Technical");

        Applicant applicant2 = new Applicant(1002, "Sarah Chen", "Data Science", 2024, 3.9,
                "Penang", new String[]{"Python", "Machine Learning"}, "Data Analytics", "Technical");

        // Create sample jobs
        Job job1 = new Job(1, "Software Engineer", "TechCorp", "Kuala Lumpur", 3000.0,
                "Full-time", "Software development position", "Java, Python", "Open", "6 months");

        Job job2 = new Job(2, "Data Scientist", "DataTech", "Penang", 3500.0,
                "Full-time", "Data science position", "Python, Machine Learning", "Open", "6 months");

        // Create successful interview records
        Interview successCase1 = new Interview(applicant1, job1, 92.5, "Technical");
        successCase1.setStatus("successful");
        successCase1.setScheduledTime(LocalDateTime.now().minusDays(7)); // 7 days ago
        successfulInterviews.add(successCase1);

        Interview successCase2 = new Interview(applicant2, job2, 95.0, "Technical");
        successCase2.setStatus("successful");
        successCase2.setScheduledTime(LocalDateTime.now().minusDays(5)); // 5 days ago
        successfulInterviews.add(successCase2);

        isInitialized = true;
    }

    /**
     * Gets all successful interview examples
     */
    public static HashedSetListInterface<Interview> getSuccessfulCases() {
        if (!isInitialized) {
            initializeSuccessfulCases();
        }
        return successfulInterviews;
    }

    /**
     * Shows examples of successful interviews with detailed information
     */
    public static void displaySuccessfulCases() {
        System.out.println("\n=== Successful Interview Cases ===");
        System.out.println("=================================");

        // Sarah Chen - Data Scientist
        System.out.println("Candidate: Sarah Chen");
        System.out.println("Field of Study: Data Science");
        System.out.println("CGPA: 3.9");
        System.out.println("Position: Data Scientist");
        System.out.println("Required Skills: Python, Machine Learning");
        System.out.println("Match Score: 95.0%");
        System.out.println("Interview Date: 2025-04-05");
        System.out.println("---------------------------------");

        // Add more successful cases here
        // Michael Zhang - Software Engineer
        System.out.println("Candidate: Michael Zhang");
        System.out.println("Field of Study: Computer Science");
        System.out.println("CGPA: 3.8");
        System.out.println("Position: Software Engineer");
        System.out.println("Required Skills: Java, Python, SQL");
        System.out.println("Match Score: 92.5%");
        System.out.println("Interview Date: 2025-04-06");
        System.out.println("---------------------------------");

        // Emily Wang - UI/UX Designer
        System.out.println("Candidate: Emily Wang");
        System.out.println("Field of Study: Information Technology");
        System.out.println("CGPA: 3.7");
        System.out.println("Position: UI/UX Designer");
        System.out.println("Required Skills: UI/UX Design, Web Development");
        System.out.println("Match Score: 90.0%");
        System.out.println("Interview Date: 2025-04-07");
        System.out.println("---------------------------------");
    }

    /**
     * Gets all interviews marked as successful from the system
     */
    public HashedSetListInterface<Interview> getSuccessfulInterviews() {
        HashedSetListInterface<Interview> allInterviews = interviewManagement.getScheduledInterviews();
        HashedSetListInterface<Interview> successfulInterviews = new ArrayHashedSetList<>();

        for (int i = 1; i <= allInterviews.getNumberOfEntries(); i++) {
            Interview interview = allInterviews.getEntry(i);
            if (interview != null && interview.getStatus().equalsIgnoreCase("successful")) {
                successfulInterviews.add(interview);
            }
        }

        return successfulInterviews;
    }

    /**
     * Shows all successful interviews in the system
     */
    public void displaySuccessfulInterviews() {
        HashedSetListInterface<Interview> successfulInterviews = getSuccessfulInterviews();

        if (successfulInterviews.isEmpty()) {
            System.out.println("\nNo successful interviews found.");
            return;
        }

        System.out.println("\n=== Successful Interviews ===");
        System.out.println("----------------------------");

        for (int i = 1; i <= successfulInterviews.getNumberOfEntries(); i++) {
            Interview interview = successfulInterviews.getEntry(i);
            System.out.println("Interview ID: " + interview.getInterviewId());
            System.out.println("Applicant: " + interview.getApplicant().getName());
            System.out.println("Job Position: " + interview.getJob().getTitle());
            System.out.println("Interview Type: " + interview.getInterviewType());
            System.out.println("Match Score: " + interview.getMatchScore());
            System.out.println("Status: " + interview.getStatus());
            System.out.println("----------------------------");
        }
    }

    /**
     * Finds an interview using its ID
     */
    public Interview getInterviewById(int interviewId) {
        return interviewManagement.getInterviewById(interviewId);
    }

    /**
     * Creates sample interviews in the system. Includes both completed and
     * upcoming interviews.
     */
    public static void initializeInterviews(InterviewManagement interviewManagement,
            ApplicantManagement applicantManagement,
            JobManagement jobManagement) {
        if (interviewManagement == null || interviewManagement.getScheduledInterviews().getNumberOfEntries() > 0) {
            return; // Don't initialize if already done
        }

        // Get existing applicants and jobs
        Applicant applicant1 = applicantManagement.getApplicant(1001);
        Applicant applicant2 = applicantManagement.getApplicant(1002);
        Applicant applicant3 = applicantManagement.getApplicant(1003);
        Job job1 = jobManagement.getJobByID(1);
        Job job2 = jobManagement.getJobByID(2);
        Job job3 = jobManagement.getJobByID(3);

        // Create completed successful interviews
        if (applicant1 != null && job1 != null) {
            Interview interview1 = new Interview(applicant1, job1, 92.5, "Technical");
            interview1.setStatus("successful");

            LocalDateTime pastTime1 = LocalDateTime.now().minusDays(1).withHour(14).withMinute(0);
            interviewManagement.scheduleInterview(interview1, pastTime1);
        }

        if (applicant2 != null && job2 != null) {
            Interview interview2 = new Interview(applicant2, job2, 88.0, "Technical");
            interview2.setStatus("successful");

            LocalDateTime pastTime2 = LocalDateTime.now().minusDays(2).withHour(15).withMinute(0);
            interviewManagement.scheduleInterview(interview2, pastTime2);
        }

        // Create an upcoming interview
        if (applicant3 != null && job3 != null) {
            Interview interview3 = new Interview(applicant3, job3, 75.0, "HR");

            LocalDateTime futureTime = LocalDateTime.now().plusDays(1).withHour(10).withMinute(0);
            interviewManagement.scheduleInterview(interview3, futureTime);
        }

        System.out.println("Sample interviews have been created!");
    }
}

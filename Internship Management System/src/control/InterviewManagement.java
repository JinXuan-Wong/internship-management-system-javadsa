package control;

/**
 *
 * @author Dorcas Lim Yuan Yao
 *
 */
import entity.Interview;
import entity.Applicant;
import entity.Job;
import adt.HashedSetListInterface;
import adt.ArrayHashedSetList;
import adt.PriorityQueue;
import java.time.LocalDateTime;

/**
 * InterviewManagement Control Class Manages the interview process, including
 * matching candidates with jobs, scheduling interviews, and maintaining
 * interview status. Uses custom ADTs for data management and prioritization.
 */
public class InterviewManagement {

    // ----------------- Class Properties -----------------
    private HashedSetListInterface<Interview> pendingInterviews;   // Interviews waiting to be scheduled
    private HashedSetListInterface<Interview> scheduledInterviews; // Interviews that have been scheduled
    private PriorityQueue<Interview> priorityQueue;               // Queue for managing interview priorities

    // ----------------- Constructor -----------------
    /**
     * Initializes the interview management system with empty collections
     */
    public InterviewManagement() {
        this.pendingInterviews = new ArrayHashedSetList<>();
        this.scheduledInterviews = new ArrayHashedSetList<>();
        this.priorityQueue = new PriorityQueue<>();
    }

    // ----------------- Matching and Creation -----------------
    /**
     * Matches applicants with a job based on various criteria and creates
     * interviews Calculates match scores for each applicant and stores them in
     * pending interviews
     *
     * @param applicants List of applicants to match
     * @param job The job position to match against
     * @param requiredSkills Required technical skills
     * @param minCGPA Minimum CGPA requirement
     * @param requiredFields Required fields of study
     */
    public void matchAndCreateInterviews(HashedSetListInterface<Applicant> applicants, Job job,
            String[] requiredSkills, double minCGPA,
            String[] requiredFields) {
        // Clear previous matching results
        pendingInterviews = new ArrayHashedSetList<>();
        priorityQueue = new PriorityQueue<>();

        // Calculate match scores for each applicant
        for (int i = 1; i <= applicants.getNumberOfEntries(); i++) {
            Applicant applicant = applicants.getEntry(i);
            if (applicant != null) {
                Interview interview = new Interview(applicant, job, 0, applicant.getInternshipType());
                interview.calculateMatchScore(null, requiredSkills, minCGPA,
                        requiredFields, null, null);

                pendingInterviews.add(interview);
                priorityQueue.enqueue(interview);
            }
        }
    }

    // ----------------- Interview Management Methods -----------------
    //Adds a new interview to the pending list and priority queue
    public void addPendingInterview(Interview interview) {
        pendingInterviews.add(interview);
        priorityQueue.enqueue(interview);
    }

    //Returns all pending interviews that haven't been scheduled
    public HashedSetListInterface<Interview> getPendingInterviews() {
        return pendingInterviews;
    }

    //Returns all interviews that have been scheduled
    public HashedSetListInterface<Interview> getScheduledInterviews() {
        return scheduledInterviews;
    }

    /**
     * Gets the next highest priority interview from the queue Removes it from
     * pending interviews when retrieved
     *
     * @return The next interview to be scheduled, or null if none available
     */
    public Interview getNextPendingInterview() {
        if (!priorityQueue.isEmpty()) {
            Interview next = priorityQueue.dequeue();
            // Find and remove the interview from the pending list
            for (int i = 1; i <= pendingInterviews.getNumberOfEntries(); i++) {
                if (pendingInterviews.getEntry(i).equals(next)) {
                    pendingInterviews.remove(i);
                    break;
                }
            }
            return next;
        }
        return null;
    }

    // ----------------- Scheduling Methods -----------------
    /**
     * Schedules an interview at the specified date and time
     *
     * @param interview The interview to schedule
     * @param dateTime The proposed date and time
     * @return true if scheduling was successful, false if time slot is
     * unavailable
     */
    public boolean scheduleInterview(Interview interview, LocalDateTime dateTime) {
        if (isTimeSlotAvailable(dateTime)) {
            interview.setScheduledTime(dateTime);
            scheduledInterviews.add(interview);
            return true;
        }
        return false;
    }

    /**
     * Checks if a time slot is available for scheduling Ensures no overlap with
     * existing interviews (1 hour buffer before and after)
     */
    private boolean isTimeSlotAvailable(LocalDateTime dateTime) {
        // Check if time slot is available (assuming 1-hour duration)
        for (int i = 1; i <= scheduledInterviews.getNumberOfEntries(); i++) {
            Interview scheduled = scheduledInterviews.getEntry(i);
            if (scheduled != null) {
                LocalDateTime scheduledTime = scheduled.getScheduledTime();
                if (scheduledTime != null) {
                    // Check for conflicts (within 1 hour before or after)
                    if (dateTime.isAfter(scheduledTime.minusHours(1))
                            && dateTime.isBefore(scheduledTime.plusHours(1))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    // ----------------- Query Methods -----------------
    //Retrieves all interviews (both pending and scheduled) of a specific type
    public HashedSetListInterface<Interview> getInterviewsByType(String type) {
        HashedSetListInterface<Interview> result = new ArrayHashedSetList<>();

        // Check pending interviews
        for (int i = 1; i <= pendingInterviews.getNumberOfEntries(); i++) {
            Interview interview = pendingInterviews.getEntry(i);
            if (interview.getInterviewType().equals(type)) {
                result.add(interview);
            }
        }

        // Check scheduled interviews
        for (int i = 1; i <= scheduledInterviews.getNumberOfEntries(); i++) {
            Interview interview = scheduledInterviews.getEntry(i);
            if (interview.getInterviewType().equals(type)) {
                result.add(interview);
            }
        }

        return result;
    }

    public Interview getInterviewById(int interviewId) {
        // Check pending interviews
        for (int i = 1; i <= pendingInterviews.getNumberOfEntries(); i++) {
            Interview interview = pendingInterviews.getEntry(i);
            if (interview != null && interview.getInterviewId() == interviewId) {
                return interview;
            }
        }

        // Check scheduled interviews
        for (int i = 1; i <= scheduledInterviews.getNumberOfEntries(); i++) {
            Interview interview = scheduledInterviews.getEntry(i);
            if (interview != null && interview.getInterviewId() == interviewId) {
                return interview;
            }
        }

        return null;
    }
}

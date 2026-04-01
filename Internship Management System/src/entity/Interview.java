package entity;

import java.time.LocalDateTime;

/**
 * @author Dorcas Lim Yuan Yao Represents an interview between an applicant and
 * a job position. Can be sorted based on match scores and other criteria.
 */

public class Interview implements Comparable<Interview> {

    // Basic information about the interview
    private Applicant applicant;
    private Job job;
    private double matchScore;          // How well they match the job requirements
    private String interviewType;       // IntetviewType: REMOTE, ON-SITE, or HYBRID
    private LocalDateTime scheduledTime; // When the interview will happen
    private String status;              // Status: pending, completed, or successful
    private int interviewId;            // Unique ID for this interview
    private static int nextInterviewId = 1; // Used to generate unique IDs

    /**
     * Creates a new interview with basic details
     */
    public Interview(Applicant applicant, Job job, double matchScore, String interviewType) {
        this.applicant = applicant;
        this.job = job;
        this.matchScore = matchScore;
        this.interviewType = interviewType;
        this.status = "pending";  // All interviews start as pending
        this.interviewId = nextInterviewId++;
    }

    // ----------------- Getters and Setters -----------------
    public Applicant getApplicant() {
        return applicant;
    }

    public Job getJob() {
        return job;
    }

    public double getMatchScore() {
        return matchScore;
    }

    public LocalDateTime getScheduledTime() {
        return scheduledTime;
    }

    public String getInterviewType() {
        return interviewType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getInterviewId() {
        return interviewId;
    }

    /**
     * Updates the interview's scheduled time
     */
    public void setScheduledTime(LocalDateTime scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    // ----------------- Comparison Methods -----------------
    /**
     * Sorts interviews by: 1. Match score (highest first) 2. If scores are
     * equal, sorts by applicant ID
     */
    @Override
    public int compareTo(Interview other) {
        // First sort by match score (descending order)
        int scoreCompare = Double.compare(other.matchScore, this.matchScore);
        if (scoreCompare != 0) {
            return scoreCompare;
        }
        // If scores are equal, sort by applicant ID (ascending order)
        return Integer.compare(this.applicant.getId(), other.applicant.getId());
    }

    // ----------------- Score Calculation -----------------
    /**
     * Calculates how well an applicant matches a job.
     *
     * Base Score (55 points): - 30 points: Having relevant skills - 15 points:
     * Meeting minimum CGPA - 10 points: Studying relevant field
     *
     * Bonus Points (up to 45 more): - Up to 30: Extra matching skills - Up to
     * 10: Higher CGPA - Up to 5: Perfect field match
     */
    public void calculateMatchScore(String requiredLocation, String[] requiredSkills,
            double minCGPA, String[] requiredFields,
            String preferredIndustry, String internshipType) {
        double score = 55.0; // Base score

        // Skills matching (base 30 + up to 30 bonus points)
        if (requiredSkills != null && requiredSkills.length > 0) {
            int matchedSkills = 0;
            for (String required : requiredSkills) {
                for (String skill : applicant.getSkills()) {
                    if (skill.equalsIgnoreCase(required)) {
                        matchedSkills++;
                        break;
                    }
                }
            }
            // Calculate bonus points based on matched skills ratio
            score += (matchedSkills * 30.0) / requiredSkills.length;
        }

        // CGPA evaluation (base 15 + up to 10 bonus points)
        if (applicant.getCgpa() >= minCGPA) {
            // Calculate bonus points based on how much the CGPA exceeds the minimum
            score += ((applicant.getCgpa() - minCGPA) * 10.0) / (4.0 - minCGPA);
        }

        // Field of study relevance (base 10 + up to 5 bonus points)
        if (requiredFields != null && requiredFields.length > 0) {
            for (String required : requiredFields) {
                if (applicant.getFieldOfStudy().equalsIgnoreCase(required)) {
                    // Add full bonus points for exact field match
                    score += 5.0;
                    break;
                }
            }
        }

        this.matchScore = score;
    }

    // ----------------- Display and Object Methods -----------------
    /**
     * Shows interview details in a readable format
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-8s %-15s ", applicant.getId(), applicant.getName()));

        // Study Details
        sb.append(String.format("Field: %-25s ", applicant.getFieldOfStudy()));
        sb.append(String.format("Grad Year: %-10s ", applicant.getGraduationYear()));
        sb.append(String.format("CGPA: %-5.2f ", matchScore));

        // Job Details
        sb.append(String.format("Location: %-20s ", job.getLocation()));
        sb.append(String.format("Skills: %-30s ", job.getRequirements()));
        sb.append(String.format("Industry: %-20s ", job.getJobType()));
        sb.append(String.format("Interview: %-10s", interviewType));

        if (scheduledTime != null) {
            sb.append(String.format("\nScheduled Time: %s", scheduledTime.toString()));
        }

        return sb.toString();
    }

    /**
     * Two interviews are the same if they have the same applicant and job
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Interview other = (Interview) obj;
        return this.applicant.getId() == other.applicant.getId()
                && this.job.getJobID() == other.job.getJobID();
    }

    /**
     * Creates a unique number for this interview based on applicant and job
     */
    @Override
    public int hashCode() {
        return 31 * this.applicant.getId() + this.job.getJobID();
    }
}

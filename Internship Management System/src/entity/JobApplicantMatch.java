package entity;

/**
 *
 * @author Tan Yen Ping
 */
public class JobApplicantMatch {

    private Applicant applicant;
    private Job job;
    private String matchStatus; // e.g., "Pending", "Selected", "Rejected"
    private double matchScore;  // A score representing how well the applicant matches the job

    // Constructor
    public JobApplicantMatch(Applicant applicant, Job job, String matchStatus, double matchScore) {
        this.applicant = applicant;
        this.job = job;
        this.matchStatus = matchStatus;
        this.matchScore = matchScore;
    }

    public double getMatchScore(Job job) {
        // Here you can calculate the match score between the applicant and the job.
        // For simplicity, we're just using the stored matchScore.
        // You could replace this with your own logic to dynamically calculate the score.
        return this.matchScore;
    }

    // Getters and Setters
    public Applicant getApplicant() {
        return applicant;
    }

    public Job getJob() {
        return job;
    }

    public String getMatchStatus() {
        return matchStatus;
    }

    public double getMatchScore() {
        return matchScore;
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public void setMatchStatus(String matchStatus) {
        this.matchStatus = matchStatus;
    }

    public void setMatchScore(double matchScore) {
        this.matchScore = matchScore;
    }

    // Display match details   
    public String displayNameNId() {
        return "Applicant Name: " + applicant.getName() + "\n"
                + "Id: " + applicant.getId();

    }

    public String displayMatchInfo() {
        return "Job: " + job.getTitle() + "\n"
                + "Company: " + job.getCompany() + "\n"
                + "Match Status: " + matchStatus + "\n"
                + "Match Score: " + String.format("%.2f", matchScore) + "%";
    }

    @Override
    public String toString() {
        return "Match between " + applicant.getName() + " and " + job.getTitle()
                + " at " + job.getCompany() + " - Status: " + matchStatus
                + " with Match Score: " + matchScore;
    }
    
   @Override
public boolean equals(Object obj) {
    // Check if the objects are the same
    if (this == obj) {
        return true;
    }
    // Check if the object is null or not the same class
    if (obj == null || getClass() != obj.getClass()) {
        return false;
    }

    // Cast to JobApplicantMatch for comparison
    JobApplicantMatch other = (JobApplicantMatch) obj;

    // Add null checks for applicant and job fields
    if (applicant == null || job == null || other.applicant == null || other.job == null) {
        return false;
    }

    // Check if the applicant IDs and job IDs are equal
    return applicant.getId() == other.applicant.getId() && 
           job.getJobID() == other.job.getJobID();
}

@Override
public int hashCode() {
    // Start with a prime number
    int result = 17;
    
    // Combine the applicant ID and job ID using a prime multiplier
    result = 31 * result + applicant.getId();
    result = 31 * result + job.getJobID();
    
    return result;
}

}

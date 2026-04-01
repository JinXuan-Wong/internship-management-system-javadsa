package entity;

import java.util.Objects;

/**
 *
 * @author Low Qing Ying
 */
public class Job {

    private int jobID;
    private String title;
    private String company;
    private String location;
    private double salary;
    private String jobType;
    private String description;
    private String requirements;
    private String status; // Open, Closed, or Filled
    private String duration; // Duration of the internship

    public Job(int jobID, String title, String company, String location, double salary, String jobType, String description, String requirements, String status, String duration) {
        this.jobID = jobID;
        this.title = title;
        this.company = company;
        this.location = location;
        this.salary = salary;
        this.jobType = jobType;
        this.description = description;
        this.requirements = requirements;
        this.status = status;
        this.duration = duration;
    }

    public Job(Job job) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public int getJobID() {
        return jobID;
    }

    public String getTitle() {
        return title;
    }

    public String getCompany() {
        return company;
    }

    public String getLocation() {
        return location;
    }

    public double getSalary() {
        return salary;
    }

    public String getJobType() {
        return jobType;
    }

    public String getDescription() {
        return description;
    }

    public String getRequirements() {
        return requirements;
    }

    public String getStatus() {
        return status;
    }

    public String getDuration() {
        return duration;
    }

    public void setJobID(int jobID) {
        this.jobID = jobID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    // Method for updating job details
    public void updateJobDetails(String title, String location, double salary, String jobType, String description, String requirements, String status, String duration) {
        this.title = title;
        this.location = location;
        this.salary = salary;
        this.jobType = jobType;
        this.description = description;
        this.requirements = requirements;
        this.status = status;
        this.duration = duration;
    }

    // Display job details
    public String displayJobInfo() {
        return "Job ID: " + jobID + "\nTitle: " + title + "\nCompany: " + company + "\nLocation: " + location
                + "\nSalary: " + salary + "\nType: " + jobType + "\nDescription: " + description
                + "\nRequirements: " + requirements + "\nStatus: " + status + "\nDuration: " + duration;
    }

    @Override
    public String toString() {
        return String.format("JOB[location:%s|salary:%.2f|type:%s|status:%s]",
                location.toLowerCase(),
                salary,
                jobType.toLowerCase(),
                status.toLowerCase());
    }

    public String toFilterString(String criteria) {
        switch (criteria.toLowerCase()) {
            case "location":
                return "LOCATION:" + location.toLowerCase();
            case "salary":
                return "SALARY:" + String.format("%.2f", salary);
            case "job type":
                return "JOBTYPE:" + jobType.toLowerCase();
            case "status":
                return "STATUS:" + status.toLowerCase();
            case "duration":
                return "DURATION:" + duration.toLowerCase();
            default:
                return toString();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Job job = (Job) o;
        return jobID == job.jobID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(jobID);
    }

}

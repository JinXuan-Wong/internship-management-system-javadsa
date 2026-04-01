package control;
/**
 *
 * @author Tan Yen Ping
 */
import adt.ArrayHashedSetList;
import adt.HashedSetListInterface;
import entity.Job;
import entity.JobApplicantMatch;
import entity.Applicant;
import dao.MatchInitializer;
import utility.ApplicantOptions;
import java.util.Scanner;
import utility.JobMatchingEngine;
import static utility.JobMatchingEngine.JOB_TITLES;
import static utility.JobMatchingEngine.RELEVANT_FIELDS_AND_INDUSTRIES;


public class JobMatching {

    private HashedSetListInterface<Job> jobs;
    private HashedSetListInterface<Applicant> applicants;
    public HashedSetListInterface<JobApplicantMatch> jobApplicantMatches;
    private boolean isInitialized = false;

    public JobMatching() {
        this.jobApplicantMatches = new ArrayHashedSetList<>();
        this.jobs = new ArrayHashedSetList<>();
        this.applicants = new ArrayHashedSetList<>();

        if (!isInitialized) {
            MatchInitializer.initialize(this);
            isInitialized = true;
        }
    }

    public void addApplicant(Applicant applicant) {
        applicants.add(applicant);
        for (int i = 0; i < jobs.getNumberOfEntries(); i++) {
            matchApplicantToJob(applicant, jobs.getEntry(i),true);
        }
    }

    public void addJob(Job job) {
        jobs.add(job);
        for (int i = 0; i < applicants.getNumberOfEntries(); i++) {
            matchApplicantToJob(applicants.getEntry(i), job, true);
        }
    }

//--------------------------------option1---------------------------------------
    public void matchApplicantToJob(Applicant applicant, Job job, boolean applyFilter) {
    int jobIndex = getJobTitleIndex(job.getTitle());

    // If filtering is required and the job has relevant fields/industries defined
    if (applyFilter && jobIndex != -1 && JobMatchingEngine.RELEVANT_FIELDS_AND_INDUSTRIES.length > jobIndex) {
        String[] relevantFields = JobMatchingEngine.RELEVANT_FIELDS_AND_INDUSTRIES[jobIndex];

        // Check if applicant's field or preferred industry is relevant to the job
        boolean isRelevant = false;
        for (String field : relevantFields) {
            if (field.equalsIgnoreCase(applicant.getFieldOfStudy()) ||
                field.equalsIgnoreCase(applicant.getPreferredIndustry())) {
                isRelevant = true;
                break;
            }
        }

        // Only calculate score and add match if relevant
        if (isRelevant) {
            double matchScore = calculateMatchScore(applicant, job);
            if (matchScore > 0) {
                String matchStatus = "Pending";
                JobApplicantMatch match = new JobApplicantMatch(applicant, job, matchStatus, matchScore);
                jobApplicantMatches.add(match);
            }
        }
    } else {
        // For option 1: Show all matches without filtering
        double matchScore = calculateMatchScore(applicant, job);
        if (matchScore > 0) {
            String matchStatus = "Pending";
            JobApplicantMatch match = new JobApplicantMatch(applicant, job, matchStatus, matchScore);
            jobApplicantMatches.add(match);
        }
    }
}



    public HashedSetListInterface<Job> getJobs() {
        return jobs;
    }

    public HashedSetListInterface<Applicant> getApplicants() {
        return applicants;
    }

    public HashedSetListInterface<JobApplicantMatch> getJobApplicantMatches() {
        return jobApplicantMatches;
    }

    public double calculateMatchScore(Applicant applicant, Job job) {
        double score = 0.0;

        // Match based on Field of Study category and Job Title relevance
        // Match Field of Study and Preferred Industry based on job title category
        int jobIndex = getJobTitleIndex(job.getTitle());
        if (jobIndex != -1) {
            String[] relevant = JobMatchingEngine.RELEVANT_FIELDS_AND_INDUSTRIES[jobIndex];
    
            boolean fieldMatched = false;
            boolean industryMatched = false;

        for (int i = 0; i < relevant.length; i++) {
            if (relevant[i].equalsIgnoreCase(applicant.getFieldOfStudy())) {
                fieldMatched = true;
            }
            if (relevant[i].equalsIgnoreCase(applicant.getPreferredIndustry())) {
                industryMatched = true;
            }
            
        }
        if(fieldMatched)score +=20.0;
        if(industryMatched)score +=20.0;
    }

        if (applicant.getLocation().equalsIgnoreCase(job.getLocation())) {
            score += 20.0;
        }
        if (applicant.getInternshipType().equalsIgnoreCase(job.getJobType())) {
            score += 20.0;
        }

        int applicantSkillsCount = applicant.getSkills().length;
        int maxSkills = 3;

        if (applicantSkillsCount > 0) {
            score += ((double) applicantSkillsCount / maxSkills) * 20.0;
        }


        return score;
    }
    public static boolean isFieldOrIndustryRelevant(String jobTitle, String fieldOfStudy, String preferredIndustry) {
    int jobIndex = getJobTitleIndex(jobTitle);
    if (jobIndex == -1) {
        return false; // Job title not found
    }

    // Check if the fieldOfStudy or preferredIndustry matches the relevant ones for this job
    for (String relevantField : RELEVANT_FIELDS_AND_INDUSTRIES[jobIndex]) {
        if (relevantField.equals(fieldOfStudy) || relevantField.equals(preferredIndustry)) {
            return true;
        }
    }
    return false;
}

    public void updateMatchStatus(JobApplicantMatch match, String newStatus) {
        match.setMatchStatus(newStatus);
    }

//-------------------------------option2---------------------------------
    public void sortMatchesByScore() {
        int n = jobApplicantMatches.getNumberOfEntries();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                JobApplicantMatch current = jobApplicantMatches.getEntry(j);
                JobApplicantMatch next = jobApplicantMatches.getEntry(j + 1);
                if (current.getMatchScore() < next.getMatchScore()) {
                    jobApplicantMatches.replace(j, next);
                    jobApplicantMatches.replace(j + 1, current);
                }
            }
        }
    }
//--------------------------------option3-----------------------------------
    public JobApplicantMatch[] applyAllFilters(Scanner scanner, int[] count) {
        System.out.println("Apply All Filters:");

        printOptions(ApplicantOptions.FIELDS_OF_STUDY);
        String field = ApplicantOptions.FIELDS_OF_STUDY[Integer.parseInt(scanner.nextLine()) - 1];

        printOptions(ApplicantOptions.PREFERRED_INDUSTRIES);
        String industry = ApplicantOptions.PREFERRED_INDUSTRIES[Integer.parseInt(scanner.nextLine()) - 1];

        printOptions(ApplicantOptions.LOCATIONS);
        String location = ApplicantOptions.LOCATIONS[Integer.parseInt(scanner.nextLine()) - 1];

        printOptions(ApplicantOptions.INTERNSHIP_PREFERENCES);
        String jobType = ApplicantOptions.INTERNSHIP_PREFERENCES[Integer.parseInt(scanner.nextLine()) - 1];

        int[] c1 = new int[1], c2 = new int[1], c3 = new int[1], c4 = new int[1];
        JobApplicantMatch[] result1 = filterByField(jobApplicantMatches, field, c1);
        JobApplicantMatch[] result2 = filterByIndustry(jobApplicantMatches, industry, c2);
        JobApplicantMatch[] result3 = filterByLocation(jobApplicantMatches, location, c3);
        JobApplicantMatch[] result4 = filterByJobType(jobApplicantMatches, jobType, c4);

        JobApplicantMatch[] temp = intersection(result1, c1[0], result2, c2[0], count);
        temp = intersection(temp, count[0], result3, c3[0], count);
        return intersection(temp, count[0], result4, c4[0], count);
    }

    private static JobApplicantMatch[] filterByField(HashedSetListInterface<JobApplicantMatch> allMatches, String field, int[] count) {
        JobApplicantMatch[] result = new JobApplicantMatch[allMatches.getNumberOfEntries()];
        int index = 0;
        for (int i = 0; i < allMatches.getNumberOfEntries(); i++) {
            JobApplicantMatch match = allMatches.getEntry(i);
            int jobIndex = getJobTitleIndex(match.getJob().getTitle());
            if (jobIndex != -1) {
                for (String relevant : RELEVANT_FIELDS_AND_INDUSTRIES[jobIndex]) {
                    if (relevant.equalsIgnoreCase(field)) {
                        result[index++] = match;
                        break;
                    }
                }
            }
        }
        count[0] = index;
        return result;
    }

    private static JobApplicantMatch[] filterByIndustry(HashedSetListInterface<JobApplicantMatch> allMatches, String industry, int[] count) {
        JobApplicantMatch[] result = new JobApplicantMatch[allMatches.getNumberOfEntries()];
        int index = 0;
        for (int i = 0; i < allMatches.getNumberOfEntries(); i++) {
            JobApplicantMatch match = allMatches.getEntry(i);
            int jobIndex = getJobTitleIndex(match.getJob().getTitle());
            if (jobIndex != -1) {
                for (String relevant : RELEVANT_FIELDS_AND_INDUSTRIES[jobIndex]) {
                    if (relevant.equalsIgnoreCase(industry)) {
                        result[index++] = match;
                        break;
                    }
                }
            }
        }
        count[0] = index;
    return result;
    }

    private static JobApplicantMatch[] filterByLocation(HashedSetListInterface<JobApplicantMatch> allMatches, String location, int[] count) {
        JobApplicantMatch[] result = new JobApplicantMatch[allMatches.getNumberOfEntries()];
        int index = 0;
        for (int i = 0; i < allMatches.getNumberOfEntries(); i++) {
            JobApplicantMatch match = allMatches.getEntry(i);
            if (match.getApplicant().getLocation().equalsIgnoreCase(location)
                    && match.getJob().getLocation().equalsIgnoreCase(location)) {
                result[index++] = match;
            }
        }
        count[0] = index;
        return result;
    }

    private static JobApplicantMatch[] filterByJobType(HashedSetListInterface<JobApplicantMatch> allMatches, String jobType, int[] count) {
        JobApplicantMatch[] result = new JobApplicantMatch[allMatches.getNumberOfEntries()];
        int index = 0;
        for (int i = 0; i < allMatches.getNumberOfEntries(); i++) {
            JobApplicantMatch match = allMatches.getEntry(i);
            if (match.getJob().getJobType().equalsIgnoreCase(jobType)
                    && match.getApplicant().getInternshipType().equalsIgnoreCase(jobType)) {
                result[index++] = match;
            }
        }
        count[0] = index;
        return result;
    }
    
    public JobApplicantMatch[] getFilteredMatchesByLocation(String location) {
        return filterByLocation(jobApplicantMatches, location, new int[1]);
    }

    public JobApplicantMatch[] getFilteredMatchesByJobType(String jobType) {
        return filterByJobType(jobApplicantMatches, jobType, new int[1]);
    }

    private static JobApplicantMatch[] intersection(JobApplicantMatch[] list1, int len1, JobApplicantMatch[] list2, int len2, int[] count) {
        JobApplicantMatch[] result = new JobApplicantMatch[Math.min(len1, len2)];
        int index = 0;
        for (int i = 0; i < len1; i++) {
            for (int j = 0; j < len2; j++) {
                if (list1[i] == list2[j]) {
                    result[index++] = list1[i];
                    break;
                }
            }
        }
        count[0] = index;
        return result;
    }

    // Helper method to get the index of a job title
    public static int getJobTitleIndex(String jobTitle) {
        for (int i = 0; i < JOB_TITLES.length; i++) {
            if (JOB_TITLES[i].equalsIgnoreCase(jobTitle)) {
                return i;
            }
        }
        return -1; // Return -1 if the job title is not found
    }
    
    private static void printOptions(String[] options) {
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
    }
}
package control;

/**
 * JobManagement class handles all job-related operations
 *
 * @author Low Qing Ying
 *
 */
import adt.ArrayHashedSetList;
import adt.HashedSetListInterface;
import entity.Job;
import utility.Report;
import dao.JobInitializer;

public class JobManagement {

    private final HashedSetListInterface<Job> jobs;
    private int nextJobID;

    public JobManagement() {
        this.jobs = new ArrayHashedSetList<>();
        this.nextJobID = 1;
    }

    //Initializes job data by loading from JobInitializes 
    public void initialize() {
        JobInitializer.initializeJobs(this);
        System.out.println("Jobs have been successfully initialized. \nTotal Jobs: " + jobs.getNumberOfEntries());
    }

//--------------Add Job----------------------------------------------------
    public void addJob(Job job) {
        jobs.add(job);
    }

//--------------Update Job----------------------------------------------------
    public boolean updateJob(int jobID, Job updatedJob) {
        int index = jobs.indexOf(getJobByID(jobID));
        if (index != -1) {
            return jobs.replace(index, updatedJob);
        }
        return false;
    }

//--------------Remove Job----------------------------------------------------
    public boolean removeJob(int jobID) {
        Job job = getJobByID(jobID);
        if (job != null) {
            return jobs.remove(jobs.indexOf(job));
        }
        return false;
        
    }
//--------------Filter Job----------------------------------------------------    
    public Job[] applyMultipleFilters(int numFilters, String[] criteriaList, String[] values) {
        HashedSetListInterface<Job> currentResults = new ArrayHashedSetList<>();
        for (int i = 0; i < jobs.getNumberOfEntries(); i++) {
            Job job = jobs.getEntry(i);
            if (job != null) {
                currentResults.add(job);
            }
        }

        for (int i = 0; i < numFilters; i++) {
            String criteria = criteriaList[i].toLowerCase();
            String value = values[i].toLowerCase();

            System.out.println("[DEBUG] Applying filter " + (i + 1) + ": " + criteria + " with value: " + value);

            if (criteria.equals("salary")) {
                currentResults = filterBySalaryRange(value, currentResults);
            } else {
                currentResults = filter(currentResults, criteria, value);
            }

            if (currentResults.isEmpty()) {
                System.out.println("[INFO] No jobs left after applying filter: " + criteria);
                break;
            }
        }

        return convertToJobArray(currentResults);
    }

    //filter jobs by the non-salary criteria
    private HashedSetListInterface<Job> filter(HashedSetListInterface<Job> source, String criteria, String value) {
        HashedSetListInterface<Job> results = new ArrayHashedSetList<>();
        Object[] filtered = source.filter(criteria, value);

        for (Object obj : filtered) {
            Job job = (Job) obj;
            if (job != null) {
                boolean match = false;

                // Manually check the actual field
                switch (criteria.toLowerCase()) {
                    case "status":
                        match = job.getStatus().equalsIgnoreCase(value);
                        break;
                    case "location":
                        match = job.getLocation().equalsIgnoreCase(value);
                        break;
                    case "jobtype":
                        match = job.getJobType().equalsIgnoreCase(value);
                        break;
                    default:
                        match = true;
                }

                if (match) {
                    results.add(job);
                }
            }
        }

        return results;
    }
    //filter jobs by salary range(min-max)

    private HashedSetListInterface<Job> filterBySalaryRange(String range, HashedSetListInterface<Job> source) {
        HashedSetListInterface<Job> results = new ArrayHashedSetList<>();
        String[] parts = range.split("-");

        if (parts.length != 2) {
            System.out.println("[ERROR] Invalid salary range format");
            return results;
        }

        try {
            double min = Double.parseDouble(parts[0].trim());
            double max = Double.parseDouble(parts[1].trim());

            for (int i = 0; i < source.getNumberOfEntries(); i++) {
                Job job = source.getEntry(i);
                if (job != null) {
                    double salary = job.getSalary();
                    if (salary >= min && salary <= max) {
                        results.add(job);
                    }
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("[ERROR] Invalid salary numbers in range");
        }

        return results;
    }

    private Job[] convertToJobArray(HashedSetListInterface<Job> jobs) {
        Job[] array = new Job[jobs.getNumberOfEntries()];
        for (int i = 0; i < jobs.getNumberOfEntries(); i++) {
            Job job = jobs.getEntry(i);
            if (job != null) {
                array[i] = job;
            }
        }
        return array;
    }

//--------------Sort Job----------------------------------------------------
    public HashedSetListInterface<Job> sortJobs(String criteria, boolean ascending) {
        criteria = criteria.toLowerCase();

        HashedSetListInterface<Job> jobList = new ArrayHashedSetList<Job>() {
            @Override
            public int compareByCriteria(Job job1, Job job2, String criteria) {
                switch (criteria) {
                    case "salary":
                        return Double.compare(job1.getSalary(), job2.getSalary());
                    case "title":
                        return job1.getTitle().compareToIgnoreCase(job2.getTitle());
                    case "company":
                        return job1.getCompany().compareToIgnoreCase(job2.getCompany());
                    case "location":
                        return job1.getLocation().compareToIgnoreCase(job2.getLocation());
                    case "job type":
                        return job1.getJobType().compareToIgnoreCase(job2.getJobType());
                    case "status":
                        return job1.getStatus().compareToIgnoreCase(job2.getStatus());
                    default:
                        return 0;
                }
            }
        };

        for (int i = 0; i < jobs.getNumberOfEntries(); i++) {
            jobList.add(jobs.getEntry(i));
        }

        jobList.sortBy(criteria, ascending);

        return jobList;
    }
//--------------Search Job----------------------------------------------------

    public Job[] searchJobs(String category, String searchTerm) {
        int matchCount = 0;
        searchTerm = searchTerm.toLowerCase().trim();

        for (int i = 0; i < jobs.getNumberOfEntries(); i++) {
            Job job = jobs.getEntry(i);
            if (job != null && matchesJobCriteria(job, category, searchTerm)) {
                matchCount++;
            }
        }

        Job[] results = new Job[matchCount];
        int resultIndex = 0;

        for (int i = 0; i < jobs.getNumberOfEntries(); i++) {
            Job job = jobs.getEntry(i);
            if (job != null && matchesJobCriteria(job, category, searchTerm)) {
                results[resultIndex++] = job;
            }
        }

        return results;
    }

    private boolean matchesJobCriteria(Job job, String category, String searchTerm) {
        if (job == null) {
            return false;
        }
        searchTerm = searchTerm.toLowerCase();

        // Get the appropriate field value based on the category
        String fieldValue;
        switch (category.toLowerCase()) {
            case "title":
                fieldValue = job.getTitle().toLowerCase();
                break;
            case "company":
                fieldValue = job.getCompany().toLowerCase();
                break;
            case "location":
                fieldValue = job.getLocation().toLowerCase();
                break;
            case "salary":
                fieldValue = String.valueOf(job.getSalary()).toLowerCase();
                break;
            case "job type":
                fieldValue = job.getJobType().toLowerCase();
                break;
            case "status":
                fieldValue = job.getStatus().toLowerCase();
                break;
            default:
                return false;
        }

        // Use keywordSearch to check if the field value matches the search term
        HashedSetListInterface<String> tempSet = new ArrayHashedSetList<>();
        tempSet.add(fieldValue);

        return tempSet.keywordSearch(searchTerm) != null;
    }
//--------------Generate Report----------------------------------------------------  
    //Generate Job Report based on selection criteria

    public Report generateReport(int choice, String criteria, double minSalary) {
        HashedSetListInterface<Job> filteredJobs = new ArrayHashedSetList<>();

        for (Job job : getAllJobs()) {
            boolean include = false;

            if (choice == 6) {
                include = job.getSalary() >= minSalary;
            } else {
                include = criteria.equalsIgnoreCase("all") || jobMatchesCriteria(job, criteria);
            }

            if (include) {
                filteredJobs.add(job);
            }
        }

        return generateReportContent("Job Report", filteredJobs);
    }

    //format of the report header and footer
    private Report generateReportContent(String reportTitle, HashedSetListInterface<Job> jobs) {
        StringBuilder reportContent = new StringBuilder();
        int count = 0;

        reportContent.append("=======================================================================================================================================================================================================\n");
        reportContent.append(String.format("%-163s %s%n", reportTitle, "Generated on: " + java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        reportContent.append("=======================================================================================================================================================================================================\n");

        reportContent.append(String.format("%-10s %-35s %-23s %-13s %-10s %-12s %-35s %-32s %-10s %-10s%n", "Job ID", "Title", "Company", "Location", "Salary", "Job Type", "Description",
                "Requirements", "Status", "Duration"
        ));
        reportContent.append("=======================================================================================================================================================================================================\n");

        for (int i = 0; i <= jobs.getNumberOfEntries(); i++) {
            Job job = jobs.getEntry(i);
            if (job != null) {
                reportContent.append(formatJobDetails(job)).append("\n");
                count++;
            }
        }

        reportContent.append("=======================================================================================================================================================================================================\n");
        reportContent.append(String.format("Total Jobs Listed: %d%n", count));

        return new Report(reportTitle, reportContent.toString());
    }

    //format of the Report Content
    private String formatJobDetails(Job job) {
        if (job == null) {
            return "";
        }
        HashedSetListInterface<String> wrappedDescription = wrapText(job.getDescription(), 35);
        HashedSetListInterface<String> wrappedRequirements = wrapText(job.getRequirements(), 35);
        int maxLines = Math.max(wrappedDescription.getNumberOfEntries(), wrappedRequirements.getNumberOfEntries());

        StringBuilder formattedJob = new StringBuilder();

        formattedJob.append(String.format("%-10d %-35s %-23s %-13s RM%-8.2f %-10s %-35s %-35s %-9s %-9s\n",
                job.getJobID(), job.getTitle(), job.getCompany(), job.getLocation(),
                job.getSalary(), job.getJobType(),
                wrappedDescription.isEmpty() ? "" : wrappedDescription.getEntry(0),
                wrappedRequirements.isEmpty() ? "" : wrappedRequirements.getEntry(0),
                job.getStatus(), job.getDuration()
        ));

        for (int i = 1; i < maxLines; i++) {
            formattedJob.append(String.format("%-10s %-35s %-23s %-13s %-10s %-10s %-35s %-35s %-9s %-9s\n",
                    "", "", "", "", "", "",
                    i < wrappedDescription.getNumberOfEntries() ? wrappedDescription.getEntry(i) : "",
                    i < wrappedRequirements.getNumberOfEntries() ? wrappedRequirements.getEntry(i) : "",
                    "", ""
            ));
        }
        return formattedJob.toString();
    }

    //make the description and requirement can present as paragraph
    private HashedSetListInterface<String> wrapText(String text, int maxLength) {
        HashedSetListInterface<String> lines = new ArrayHashedSetList<>();
        while (text.length() > maxLength) {
            int splitAt = text.lastIndexOf(" ", maxLength);
            if (splitAt == -1) {
                splitAt = maxLength;
            }
            lines.add(text.substring(0, splitAt).trim());
            text = text.substring(splitAt).trim();
        }
        if (!text.isEmpty()) {
            lines.add(text);
        }
        return lines;
    }

    private boolean jobMatchesCriteria(Job job, String criteria) {
        return job.getJobType().equalsIgnoreCase(criteria)
                || job.getLocation().equalsIgnoreCase(criteria)
                || job.getCompany().equalsIgnoreCase(criteria)
                || job.getStatus().equalsIgnoreCase(criteria);
    }

    //--------------Helper Method----------------------------------------------------     
    public int generateNextJobID() {
        return nextJobID++;
    }

    public int peekNextJobID() {
        return nextJobID;
    }

    public void setNextJobID(int jobID) {
        this.nextJobID = jobID;
    }

    public int getTotalJobs() {
        return jobs.getNumberOfEntries();
    }

    public Job getJobByID(int jobID) {
        for (int i = 0; i < jobs.getNumberOfEntries(); i++) {
            Job job = jobs.getEntry(i);
            if (job != null && job.getJobID() == jobID) {
                return job;
            }
        }
        return null;
    }

    public Job[] getAllJobs() {
        Job[] allJobs = new Job[jobs.getNumberOfEntries()];
        for (int i = 0; i < jobs.getNumberOfEntries(); i++) {
            allJobs[i] = jobs.getEntry(i);
        }
        return allJobs;
    }

}

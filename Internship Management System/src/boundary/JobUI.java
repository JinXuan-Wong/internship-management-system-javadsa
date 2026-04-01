package boundary;

/**
 *
 * @author Low Qing Ying
 */
import adt.HashedSetListInterface;
import control.JobManagement;
import utility.MessageUI;
import entity.Job;
import utility.JobOptions;
import utility.Report;

import java.util.Scanner;
import java.util.function.Predicate;

public class JobUI {

    public JobUI(JobManagement jobmanagement) {
    }
    private static final Scanner scanner = new Scanner(System.in);
    private static final JobManagement jobManager = new JobManagement();

    public void start() {
        jobManager.initialize();
        int choice;
        do {
            displayMenu();
            choice = getValidIntegerInput("Enter your choice: ", 0, 7);

            switch (choice) {
                case 1 ->
                    createJob();
                case 2 ->
                    updateJob();
                case 3 ->
                    removeJob();
                case 4 ->
                    filterJobs();
                case 5 ->
                    searchJobs();
                case 6 ->
                    sortJobs();
                case 7 ->
                    generateReport();
                case 0 ->
                    MessageUI.displayExitMessage();
                default ->
                    MessageUI.displayInvalidChoiceMessage();
            }
        } while (choice != 0);
    }

    private void displayMenu() {
        System.out.println("\n=== Job Management System ===");
        System.out.println("1. Create a Job Posting");
        System.out.println("2. Update a Job Posting");
        System.out.println("3. Remove a Job Posting");
        System.out.println("4. Filter Jobs");
        System.out.println("5. Search for a Job");
        System.out.println("6. Sort Jobs");
        System.out.println("7. Generate Job Report");
        System.out.println("0. Exit");
        System.out.println("=============================");
    }

//--------------Add Job----------------------------------------------------
    private void createJob() {
        System.out.println("\n=== Create a New Job Posting ===");

        // Store the current job ID before starting the process
        int currentJobID = jobManager.peekNextJobID();
        System.out.println("[Current Job ID: " + currentJobID + "]");
        System.out.println("(Enter 'cancel' at any time to abort)");

        // Collect all job data with cancellation check
        String title = getStringInputWithCancel("Enter Job Title: ");
        if (title == null) {
            return;
        }
        String company = getStringInputWithCancel("Enter Company Name: ");
        if (company == null) {
            return;
        }

        String location = getStringInputWithCancel("Enter Location: ");
        if (location == null) {
            return;
        }

        Double salary = getDoubleInputWithCancel("Enter Salary: ");
        if (salary == null) {
            return;
        }

        String jobType = getValidInputWithCancel("Enter Job Type (Hybrid/Full-Time/Remote): ", input
                -> input.equalsIgnoreCase("hybrid") || input.equalsIgnoreCase("full-time") || input.equalsIgnoreCase("remote"));
        if (jobType == null) {
            return;
        }

        String description = getStringInputWithCancel("Enter Job Description: ");
        if (description == null) {
            return;
        }

        String requirements = getStringInputWithCancel("Enter Job Requirements: ");
        if (requirements == null) {
            return;
        }

        String status = getValidInputWithCancel("Enter Job Status (Open/Closed/Filled): ", input
                -> input.equalsIgnoreCase("open") || input.equalsIgnoreCase("closed") || input.equalsIgnoreCase("filled"));
        if (status == null) {
            return;
        }

        String duration = getStringInputWithCancel("Enter Internship Duration: ");
        if (duration == null) {
            return;
        }

        // Generate the final job ID
        int finalJobID = jobManager.generateNextJobID();

        // 2. Create and confirm job
        Job newJob = new Job(finalJobID, title, company, location, salary, jobType,
                description, requirements, status, duration);

        System.out.println("\n=== Job Preview ===");
        System.out.println(newJob.displayJobInfo());

        // Ask for confirmation
        if (!getConfirmation("Confirm to add this job? (yes/no): ")) {
            System.out.println("Job creation cancelled.");

            // If cancelled, reset the next job ID to the original job ID (currentJobID)
            jobManager.setNextJobID(currentJobID);
            return;
        }

        // If confirmed, add the job
        jobManager.addJob(newJob);
        System.out.println("Job added successfully");
    }

    // Helper methods needed:
    // 1. Input method with cancellation support
    private String getStringInputWithCancel(String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        if (input.equalsIgnoreCase("cancel") || input.isEmpty()) {
            System.out.println("Job creation cancelled.");
            return null; // treat empty or 'cancel' as cancellation
        }
        return input;
    }

    // 2. Double input with cancellation
    private Double getDoubleInputWithCancel(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("cancel") || input.isEmpty()) {
                return null;
            }
            try {
                return Double.valueOf(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please try again or type 'cancel'");
            }
        }
    }

    // 3. Input validation with cancellation
    private String getValidInputWithCancel(String prompt, Predicate<String> validation) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            // Handle cancellation (empty input or "cancel")
            if (input.equalsIgnoreCase("cancel") || input.isEmpty()) {
                System.out.println("Job creation cancelled.");
                return null;
            }

            // Validate the input
            if (validation != null && !validation.test(input)) {
                System.out.println("Invalid input. Please try again.");
            } else {
                return input;  // Return the valid input
            }
        }
    }

    // 4. Confirmation dialog
    private boolean getConfirmation(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("yes")) {
                return true;
            }
            if (input.equals("no")) {
                return false;
            }
            System.out.println("Please enter 'yes' or 'no'");
        }
    }

//--------------Update Job----------------------------------------------------
    private void updateJob() {
        System.out.println("\n=== Update Job Posting ===");
        int jobID = getIntegerInput("Enter Job ID to update: ");
        Job job = jobManager.getJobByID(jobID);

        if (job == null) {
            MessageUI.displayError("Job with ID " + jobID + " not found.");
            return;
        }

        // Show current job details
        System.out.println("\n[INFO] Current Job Details:");
        System.out.println(job.displayJobInfo());

        // Confirm Update
        String confirm;
        while (true) {
            confirm = getStringInput("\nDo you want to update this job? (yes/no): ").trim().toLowerCase();

            if (confirm.equals("yes") || confirm.equals("y")) {
                break; // proceed with update
            } else if (confirm.equals("no") || confirm.equals("n")) {
                MessageUI.displayMessage("Update canceled.");
                return;
            } else {
                MessageUI.displayMessage("Invalid input. Please enter 'yes' or 'no'.");
            }
        }

        boolean updated = false;

        while (!updated) {
            System.out.println("\nSelect fields to update (comma-separated):");
            System.out.println("1. Title");
            System.out.println("2. Company");
            System.out.println("3. Location");
            System.out.println("4. Salary");
            System.out.println("5. Job Type");
            System.out.println("6. Description");
            System.out.println("7. Requirements");
            System.out.println("8. Status");
            System.out.println("9. Duration");
            System.out.println("10. Cancel Update");

            String input = getStringInput("Enter your choices: ");
            String[] choices = input.split(",");

            for (String choiceStr : choices) {
                try {
                    int choice = Integer.parseInt(choiceStr.trim());

                    if (choice < 1 || choice > 10) {
                        MessageUI.displayError("Invalid choice: " + choiceStr);
                        continue;
                    }

                    if (choice == 10) {
                        MessageUI.displayMessage("Update canceled.");
                        return;
                    }

                    switch (choice) {
                        case 1 ->
                            job.setTitle(getStringInput("Enter new title: "));
                        case 2 ->
                            job.setCompany(getStringInput("Enter new company: "));
                        case 3 ->
                            job.setLocation(getStringInput("Enter new location: "));
                        case 4 ->
                            job.setSalary(getDoubleInput("Enter new salary: "));
                        case 5 ->
                            job.setJobType(getValidJobType());
                        case 6 ->
                            job.setDescription(getStringInput("Enter new job description: "));
                        case 7 ->
                            job.setRequirements(getStringInput("Enter new job requirements: "));
                        case 8 ->
                            job.setStatus(getValidStatus());
                        case 9 ->
                            job.setDuration(getValidDuration());
                    }

                    updated = true;

                } catch (NumberFormatException e) {
                    MessageUI.displayError("Invalid input: " + choiceStr.trim() + ". Please enter numbers only.");
                }
            }

            if (!updated) {
                MessageUI.displayError("No valid choices entered. Please enter again.");
            }
        }

        if (jobManager.updateJob(jobID, job)) {
            MessageUI.displayMessage("Job updated successfully!");
        } else {
            MessageUI.displayError("Update failed. The job might already exist or there was an error.");
        }
    }

//--------------Remove Job----------------------------------------------------
    private void removeJob() {
        int jobID = getIntegerInput("Enter Job ID to remove: ");
        Job job = jobManager.getJobByID(jobID);

        if (job == null) {
            MessageUI.displayError("Job with ID " + jobID + " not found.");
            return;
        }

        // Show Job Details Before Removing
        System.out.println("\n[INFO] Job Details:");
        System.out.println(job.displayJobInfo());

        // Double Confirmation Before Removing
        String confirm = getStringInput("\nAre you sure you want to remove this job? (yes/no): ").trim().toLowerCase();
        if (!confirm.equals("yes")) {
            MessageUI.displayMessage("Job removal canceled.");
            return;
        }

        if (jobManager.removeJob(jobID)) {
            MessageUI.displayMessage("Job removed successfully!");
        } else {
            MessageUI.displayError("Job removal failed. Job ID not found.");
        }
    }

//--------------Filter Job----------------------------------------------------
    private void filterJobs() {
        System.out.println("\n=== Filter Jobs ===");

        int filterCount = getValidIntegerInput("How many filters do you want to apply? (1-3): ", 1, 3);
        String[] criteria = new String[filterCount];
        String[] values = new String[filterCount];

        for (int i = 0; i < filterCount; i++) {
            System.out.println("\nChoose filter " + (i + 1));
            for (int j = 0; j < JobOptions.FILTER_CATEGORIES.length; j++) {
                System.out.println((j + 1) + ". " + JobOptions.FILTER_CATEGORIES[j]);
            }

            int choice = getValidIntegerInput("Enter choice: ", 1, JobOptions.FILTER_CATEGORIES.length);
            criteria[i] = JobOptions.FILTER_CATEGORIES[choice - 1];

            if (criteria[i].equalsIgnoreCase("salary")) {
                values[i] = getStringInput("Enter salary range (min-max): ");
            } else {
                values[i] = getStringInput("Enter value to filter by: ");
            }
        }

        // Get filtered jobs
        Job[] filteredJobs = jobManager.applyMultipleFilters(filterCount, criteria, values);

        // Check if the array is empty
        if (filteredJobs == null || filteredJobs.length == 0) {
            MessageUI.displayMessage("No jobs matched the filter criteria.");
        } else {
            // Iterate through the filtered jobs array
            for (Job job : filteredJobs) {
                System.out.println("\n" + job.displayJobInfo());
            }
        }
    }

//--------------Search Job----------------------------------------------------
    private void searchJobs() {
        while (true) {
            System.out.println("\n=== Search Jobs ===");
            System.out.println("0. Exit to Main Menu");

            for (int i = 0; i < JobOptions.SEARCH_CATEGORIES.length; i++) {
                System.out.println((i + 1) + ". " + JobOptions.SEARCH_CATEGORIES[i]);
            }

            int choice = getValidIntegerInput("Enter choice: ", 0, JobOptions.SEARCH_CATEGORIES.length);

            if (choice == 0) {
                System.out.println("Returning to the main menu...");
                return; // Exit the searchJobs method
            }

            String category = JobOptions.SEARCH_CATEGORIES[choice - 1].toLowerCase();
            String searchTerm = getStringInput("Enter search keyword: ").toLowerCase();

            Job[] results = jobManager.searchJobs(category, searchTerm);
            if (results.length == 0) {
                MessageUI.displayMessage("No matching jobs found.");
            } else {
                for (Job job : results) {
                    System.out.println("\n" + job.displayJobInfo());
                }
            }
        }
    }

//--------------Sort Job----------------------------------------------------
    private void sortJobs() {
        while (true) {
            System.out.println("\n----------------------");
            System.out.println("Select sorting criteria:");
            System.out.println("----------------------");
            System.out.println("0. Exit to Main Menu");

            for (int i = 0; i < JobOptions.SORT_CRITERIA.length; i++) {
                System.out.printf("%-3d %-20s", (i + 1), JobOptions.SORT_CRITERIA[i]);
                if ((i + 1) % 3 == 0) {
                    System.out.println();
                }
            }
            System.out.println();

            int choice = getIntegerInput("\nEnter choice (0-" + JobOptions.SORT_CRITERIA.length + "): ");

            if (choice == 0) {
                System.out.println("Returning to the main menu...");
                return; // Exit the sortJobs method
            }

            if (choice < 1 || choice > JobOptions.SORT_CRITERIA.length) {
                MessageUI.displayError("Invalid choice. Please try again.");
                continue;
            }

            String criteria = JobOptions.SORT_CRITERIA[choice - 1].toLowerCase();
            boolean ascending = getSortingOrder();

            HashedSetListInterface<Job> sortedJobs = jobManager.sortJobs(criteria, ascending);

            System.out.println("\nJobs sorted by " + criteria + " (" + (ascending ? "Ascending" : "Descending") + "):");
            System.out.println("------------------------------------------------------");

            // ✅ Display the sorted jobs
            for (int i = 0; i < sortedJobs.getNumberOfEntries(); i++) {
                Job job = sortedJobs.getEntry(i);
                System.out.println(job.displayJobInfo());
                System.out.println();
            }
        }
    }

    private boolean getSortingOrder() {
        while (true) {
            String order = getStringInput("Sort order (asc/desc): ").toLowerCase();
            if (order.equals("asc")) {
                return true;
            }
            if (order.equals("desc")) {
                return false;
            }
            MessageUI.displayError("Invalid input. Please enter 'asc' or 'desc'.");
        }
    }

//--------------Generate Report----------------------------------------------------
    private void generateReport() {
        while (true) {
            System.out.println("\n=== Generate Job Report ===");
            System.out.println("0. Exit to Main Menu");
            System.out.println("1. Show All Jobs");
            System.out.println("2. Job Type");
            System.out.println("3. Location");
            System.out.println("4. Company");
            System.out.println("5. Status");
            System.out.println("6. Minimum Salary");

            int choice = getValidIntegerInput("Enter choice: ", 0, 6);

            if (choice == 0) {
                System.out.println("Returning to the main menu...");
                return; // Exit the method
            }

            String criteria = "";
            double minSalary = 0;

            switch (choice) {
                case 1 ->
                    criteria = "all";
                case 2 ->
                    criteria = getValidJobType();
                case 3 ->
                    criteria = getStringInput("Enter Location: ");
                case 4 ->
                    criteria = getStringInput("Enter Company: ");
                case 5 ->
                    criteria = getValidStatus();
                case 6 ->
                    minSalary = getDoubleInput("Enter minimum salary: ");
            }

            Report report = jobManager.generateReport(choice, criteria, minSalary);
            System.out.println(report.getContent());
        }
    }

//--------------Validation Input---------------------------------------------------
    private int getIntegerInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            MessageUI.displayError("Invalid input. Please enter a valid number.");
            scanner.next();
        }
        int num = scanner.nextInt();
        scanner.nextLine();
        return num;
    }

    private double getDoubleInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextDouble()) {
            MessageUI.displayError("Invalid input. Please enter a valid number.");
            scanner.next();
        }
        double num = scanner.nextDouble();
        scanner.nextLine();
        return num;
    }

    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private int getValidIntegerInput(String prompt, int min, int max) {
        int num;
        do {
            num = getIntegerInput(prompt);
            if (num < min || num > max) {
                MessageUI.displayError("Invalid choice. Please enter a number between " + min + " and " + max + ".");
            }
        } while (num < min || num > max);
        return num;
    }

    private String getValidStatus() {
        String status;
        do {
            status = getStringInput("Enter Job Status (Open/Closed/Filled): ").trim().toLowerCase();
        } while (!status.equals("open") && !status.equals("closed") && !status.equals("filled"));
        return status;
    }

    private String getValidDuration() {
        int duration;
        do {
            duration = getValidIntegerInput("Enter Internship Duration (1-12 months): ", 1, 12);
        } while (duration < 1 || duration > 12);
        return duration + " months";
    }

    private String getValidJobType() {
        String jobType;
        do {
            jobType = getStringInput("Enter Job Type (Hybrid/Full-Time/Remote): ").trim().toLowerCase();
        } while (!jobType.equals("hybrid") && !jobType.equals("full-time") && !jobType.equals("remote"));
        return jobType;
    }

}

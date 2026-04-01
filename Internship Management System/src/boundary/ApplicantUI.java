package boundary;

/**
 *
 * @author Wong Jin Xuan 
 * 
 * The ApplicantUI class provides a console interface for
 * managing applicants in an internship or job application system. It interacts
 * with the ApplicantManagement class to perform various operations, including
 * adding, updating, removing, filtering, sorting, searching, and generating
 * reports on applicants.
 *
 * Dependencies: - control.ApplicantManagement: Manages applicant operations
 * (add, update, remove, filter, sort, search, report). - entity.Applicant:
 * Represents individual applicant details. - utility.ApplicantOptions: Provides
 * predefined lists for user selection. - utility.Report: Generates reports
 * based on applicant data. - utility.MessageUI: Displays system messages and
 * prompts. - java.util.Scanner: Handles user input from the console.
 */
import control.ApplicantManagement;
import entity.Applicant;
import utility.ApplicantOptions;
import utility.Report;
import utility.MessageUI;
import java.util.Scanner;

public class ApplicantUI {

    private Scanner scanner;
    private ApplicantManagement applicantManagement;

    // Constructor
    public ApplicantUI(ApplicantManagement applicantManagement) {
        scanner = new Scanner(System.in);
        this.applicantManagement = applicantManagement;
    }

    // Applicant Main menu
    public void start() {
        while (true) {
            System.out.println("\n==== APPLICANT MANAGEMENT SYSTEM ====");
            System.out.println("1. Add Applicant");
            System.out.println("2. Update Applicant");
            System.out.println("3. Remove Applicant");
            System.out.println("4. Filter Applicants");
            System.out.println("5. Sort Applicants");
            System.out.println("6. Search Applicants by Keyword");
            System.out.println("7. Generate Report");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            int choice = getValidIntegerInput();

            switch (choice) {
                case 1 ->
                    addApplicant();
                case 2 ->
                    updateApplicant();
                case 3 ->
                    removeApplicant();
                case 4 ->
                    filterApplicants();
                case 5 ->
                    sortApplicants();
                case 6 ->
                    searchApplicants();
                case 7 ->
                    generateReport();
                case 0 -> {
                    System.out.println("Exit back to Main system.");
                    return;
                }
                default ->
                    MessageUI.displayInvalidChoiceMessage();
            }
        }
    }

    // ----------------- 1. Add an applicant -----------------
    private void addApplicant() {
        int id = applicantManagement.generateApplicantId();
        System.out.println("Applicant ID: " + id);

        System.out.print("Enter Name: ");
        String name = scanner.nextLine();

        String location = chooseOption("Select Location:", ApplicantOptions.LOCATIONS);
        String[] skills = chooseMultipleOptions("Select Skills:", ApplicantOptions.SKILLS);

        double cgpa = getValidCGPA("Enter CGPA (0.0 - 4.0): ", 0.0, 4.0);
        String fieldOfStudy = chooseOption("Select Field of Study:", ApplicantOptions.FIELDS_OF_STUDY);
        String industryPreference = chooseOption("Select Preferred Industry:", ApplicantOptions.PREFERRED_INDUSTRIES);
        String internshipType = chooseOption("Select Internship Type:", ApplicantOptions.INTERNSHIP_PREFERENCES);

        int graduationYear = getValidYear("Enter Expected Graduation Year (e.g. 2025): ", 2025, 2035);

        Applicant applicant = new Applicant(id, name, fieldOfStudy, graduationYear, cgpa, location, skills, industryPreference, internshipType);
        if (applicantManagement.addApplicant(applicant)) {
            System.out.println("Applicant added successfully!");
        } else {
            System.out.println("Applicant already exists or invalid data.");
        }
    }

    // ----------------- 2. Update an applicant -----------------
    private void updateApplicant() {
        System.out.print("Enter Applicant ID to update: ");
        String input = scanner.nextLine().trim();

        // Validate input: check if it contains only digits
        if (!input.matches("\\d+")) {
            System.out.println("Invalid input! Applicant ID must be a numeric value.");
            return;
        }

        int id = Integer.parseInt(input); // Convert to integer safely

        // Retrieve the existing applicant
        Applicant applicant = applicantManagement.getApplicant(id);
        if (applicant == null) {
            System.out.println("Applicant not found.");
            return;
        }

        boolean updating = true;
        boolean updated = false; // Track if any updates were made

        while (updating) {
            System.out.println("\nSelect the field to update:");
            System.out.println("1. Name");
            System.out.println("2. Location");
            System.out.println("3. Skills");
            System.out.println("4. CGPA");
            System.out.println("5. Field of Study");
            System.out.println("6. Preferred Industry");
            System.out.println("7. Internship Type");
            System.out.println("8. Graduation Year");
            System.out.println("9. Done (Exit Update)");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter New Name: ");
                    String newName = scanner.nextLine().trim();
                    if (!newName.isEmpty() && !newName.equals(applicant.getName())) {
                        applicant.setName(newName);
                        updated = true;
                    }
                    break;
                case 2:
                    String newLocation = chooseOption("Select New Location:", ApplicantOptions.LOCATIONS);
                    if (!newLocation.equals(applicant.getLocation())) {
                        applicant.setLocation(newLocation);
                        updated = true;
                    }
                    break;
                case 3:
                    String[] newSkills = chooseMultipleOptions("Select New Skills:", ApplicantOptions.SKILLS);
                    if (!java.util.Arrays.equals(newSkills, applicant.getSkills())) {
                        applicant.setSkills(newSkills);
                        updated = true;
                    }
                    break;
                case 4:
                    double newCgpa = getValidCGPA("Enter New CGPA (0.0 - 4.0): ", 0.0, 4.0);
                    if (newCgpa != applicant.getCgpa()) {
                        applicant.setCgpa(newCgpa);
                        updated = true;
                    }
                    break;
                case 5:
                    String newFieldOfStudy = chooseOption("Select New Field of Study:", ApplicantOptions.FIELDS_OF_STUDY);
                    if (!newFieldOfStudy.equals(applicant.getFieldOfStudy())) {
                        applicant.setFieldOfStudy(newFieldOfStudy);
                        updated = true;
                    }
                    break;
                case 6:
                    String newIndustry = chooseOption("Select New Preferred Industry:", ApplicantOptions.PREFERRED_INDUSTRIES);
                    if (!newIndustry.equals(applicant.getPreferredIndustry())) {
                        applicant.setPreferredIndustry(newIndustry);
                        updated = true;
                    }
                    break;
                case 7:
                    String newInternshipType = chooseOption("Select New Internship Type:", ApplicantOptions.INTERNSHIP_PREFERENCES);
                    if (!newInternshipType.equals(applicant.getInternshipType())) {
                        applicant.setInternshipType(newInternshipType);
                        updated = true;
                    }
                    break;
                case 8:
                    int newGradYear = getValidYear("Enter New Expected Graduation Year (e.g. 2025): ", 2025, 2035);
                    if (newGradYear != applicant.getGraduationYear()) {
                        applicant.setGraduationYear(newGradYear);
                        updated = true;
                    }
                    break;
                case 9:
                    updating = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        // Ensure update is only attempted if changes were made
        if (updated) {
            boolean success = applicantManagement.updateApplicant(id, applicant);
            System.out.println("Update status: " + success);
            if (success) {
                System.out.println("Applicant updated successfully!");
            } else {
                System.out.println("Update failed. Please try again.");
            }
        } else {
            System.out.println("No changes were made. Exiting update mode.");
        }
    }

    // ----------------- 3. Remove an applicant -----------------
    private void removeApplicant() {
        System.out.print("Enter Applicant ID to remove: ");
        String input = scanner.nextLine().trim();

        // Validate input: check if it contains only digits
        if (!input.matches("\\d+")) {
            System.out.println("Invalid input! Applicant ID must be a numeric value.");
            return;
        }

        int applicantId = Integer.parseInt(input);

        if (applicantManagement.removeApplicant(applicantId)) {
            System.out.println("Applicant removed successfully!");
        } else {
            System.out.println("Applicant not found.");
        }
    }

    // ----------------- 4. Filter applicants -----------------
    private void filterApplicants() {
        String[] criteriaOptions = {
            "Location", "Field of Study", "Graduation Year", "CGPA", "Skills",
            "Industry Preference", "Internship Type"
        };

        String criteria = chooseOption("Select filter criteria:", criteriaOptions);

        String value = ""; // To store the filter value
        boolean validInput = false; // Track input validity

        switch (criteria) {
            case "Location":
                value = chooseOption("Select a location:", ApplicantOptions.LOCATIONS);
                validInput = true;
                break;
            case "Field of Study":
                value = chooseOption("Select a field of study:", ApplicantOptions.FIELDS_OF_STUDY);
                validInput = true;
                break;
            case "Graduation Year":
                System.out.print("Enter graduation year (e.g., 2025): ");
                value = scanner.nextLine();
                if (value.matches("\\d{4}")) { // Ensure it's a 4-digit year
                    validInput = true;
                } else {
                    System.out.println("Invalid graduation year format.");
                }
                break;
            case "CGPA":
                System.out.print("Enter minimum CGPA (e.g., 3.5): ");
                value = scanner.nextLine();
                if (value.matches("^\\d+(\\.\\d+)?$")) { // Allows whole numbers & decimals (e.g., 3, 3.5, 4.0)
                    validInput = true;
                } else {
                    System.out.println("Invalid CGPA format.");
                }
                break;
            case "Skills":
                value = chooseOption("Select a skill:", ApplicantOptions.SKILLS);
                validInput = true;
                break;
            case "Industry Preference":
                value = chooseOption("Select an industry:", ApplicantOptions.PREFERRED_INDUSTRIES);
                validInput = true;
                break;
            case "Internship Type":
                value = chooseOption("Select internship type:", ApplicantOptions.INTERNSHIP_PREFERENCES);
                validInput = true;
                break;
            default:
                System.out.println("Invalid filter criteria.");
                return;
        }

        if (validInput) {
            Applicant[] filteredApplicants = applicantManagement.filterApplicants(criteria.toLowerCase(), value);

            if (filteredApplicants.length > 0) {
                System.out.println("\nFiltered Applicants:");
                for (Applicant applicant : filteredApplicants) {
                    System.out.println(applicant);
                }
            } else {
                System.out.println("No applicants match the filter criteria.");
            }
        } else {
            System.out.println("Filtering canceled due to invalid input.");
        }
    }

    // ----------------- 5. Sort applicants -----------------
    private void sortApplicants() {
        String criteria = chooseOption("Select Sorting Criteria:", ApplicantOptions.SORT_CRITERIA);

        int sortOrder;
        boolean ascending = true; // Default value

        // Validate input for sort order
        while (true) {
            System.out.print("Sort in: \n1. Ascending order \n2. Descending order \nEnter option: ");

            if (scanner.hasNextInt()) {
                sortOrder = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                if (sortOrder == 1) {
                    ascending = true;
                    break;
                } else if (sortOrder == 2) {
                    ascending = false;
                    break;
                } else {
                    System.out.println("Invalid option. Please enter 1 for Ascending or 2 for Descending.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number (1 or 2).");
                scanner.nextLine(); // Clear invalid input
            }
        }

        // Sort applicants based on chosen criteria and order
        applicantManagement.sortApplicants(criteria, ascending);

        System.out.println("\nApplicants sorted successfully!\n");
    }

    // ----------------- 6. Search applicants by category -----------------
    private void searchApplicants() {
        System.out.println("\n==== SEARCH MODE ====");
        System.out.println("1. Search by keyword");
        System.out.println("2. Search by predefined criteria");
        System.out.print("Enter choice (1 or 2): ");
        System.out.flush();

        int mode;
        while (true) {
            mode = getValidIntegerInput();
            if (mode == 1 || mode == 2) {
                break;
            } else {
                System.out.print("Invalid choice. Please enter 1 or 2: ");
            }
        }

        String category = chooseOption("Select a category to search by:", ApplicantOptions.SEARCH_CATEGORIES);
        String searchTerm = "";
        
        if (mode == 1) {
            // ---------- Keyword-based search ----------
            switch (category.toLowerCase()) {
                case "name":
                case "field of study":
                case "location":
                case "preferred industry":
                case "internship type":
                case "skills":
                    System.out.print("Enter search keyword: ");
                    searchTerm = scanner.nextLine();
                    break;

                case "graduation year":
                    System.out.print("Enter graduation year (e.g., 2025): ");
                    searchTerm = scanner.nextLine();
                    break;

                case "cgpa":
                    System.out.print("Enter minimum CGPA (e.g., 3.0): ");
                    searchTerm = scanner.nextLine();
                    break;

                default:
                    System.out.println("Invalid category. Returning to menu.");
                    return;
            }

        } else {
            // ---------- Predefined criteria search ----------
            switch (category.toLowerCase()) {
                case "location":
                    searchTerm = chooseOption("Select location:", ApplicantOptions.LOCATIONS);
                    break;

                case "field of study":
                    searchTerm = chooseOption("Select field of study:", ApplicantOptions.FIELDS_OF_STUDY);
                    break;

                case "graduation year":
                    String[] gradYears = {"2023", "2024", "2025", "2026"};
                    searchTerm = chooseOption("Select graduation year:", gradYears);
                    break;

                case "cgpa":
                    String[] cgpaRanges = {"2.0", "2.5", "3.0", "3.5", "4.0"}; 
                    searchTerm = chooseOption("Select minimum CGPA:", cgpaRanges);
                    break;

                case "skills":
                    searchTerm = chooseOption("Select skill:", ApplicantOptions.SKILLS);
                    break;

                case "preferred industry":
                    searchTerm = chooseOption("Select preferred industry:", ApplicantOptions.PREFERRED_INDUSTRIES);
                    break;

                case "internship type":
                    searchTerm = chooseOption("Select internship type:", ApplicantOptions.INTERNSHIP_PREFERENCES);
                    break;

                case "name":
                    System.out.print("Enter full/partial name: ");
                    searchTerm = scanner.nextLine();
                    break;

                default:
                    System.out.println("Invalid category. Returning to menu.");
                    return;
            }
        }

        // Perform the search
        Applicant[] results = applicantManagement.searchApplicants(category, searchTerm);

        // Display the results
        System.out.println("\n================ Search Results ================\n");
        if (results.length > 0) {
            for (Applicant applicant : results) {
                System.out.println(applicant);
            }
        } else {
            System.out.println("No matching applicants found.");
        }
    }

    // ----------------- 7. Generate report -----------------
    private void generateReport() {
        String criteria = chooseOption("Select Report Criteria:",
                new String[]{"All Applicants", "Location", "Field of Study", "Internship Type", "Industry Preference"});

        Report report = applicantManagement.generateReport(criteria.toLowerCase());
        System.out.println(report);
    }

    // ----------------- Helper method ----------------- 
    // Select a single option from a predefined list
    private String chooseOption(String title, String[] options) {
        System.out.println("\n----------------------");
        System.out.println(title);
        System.out.println("----------------------");

        for (int i = 0; i < options.length; i++) {
            System.out.printf("%-3d %-25s", (i + 1), options[i]); // Left-align
            if ((i + 1) % 3 == 0 || i == options.length - 1) {
                System.out.println(); // New line every 3 items
            }
        }

        System.out.print("\nEnter choice (1-" + options.length + "): ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (choice < 1 || choice > options.length) {
            System.out.println("Invalid choice. Please try again.");
            return chooseOption(title, options); // Recursive retry
        }

        return options[choice - 1];
    }

    // Select multiple options from a predefined list
    private String[] chooseMultipleOptions(String title, String[] options) {
        System.out.println("\n----------------------");
        System.out.println(title);
        System.out.println("----------------------");

        for (int i = 0; i < options.length; i++) {
            System.out.printf("%-3d %-25s", (i + 1), options[i]); // Align items
            if ((i + 1) % 3 == 0 || i == options.length - 1) {
                System.out.println(); // New line every 3 items
            }
        }
        System.out.println((options.length + 1) + ". Custom Skills"); // Add option for custom skills

        System.out.println("\nEnter choices (comma-separated e.g., 1,3,5) or '0' to skip: ");
        String input = scanner.nextLine().trim();

        if (input.equals("0")) {
            return new String[]{}; // Skip selection
        }

        String[] selectedIndices = input.split(",");
        String[] selectedOptions = new String[selectedIndices.length];
        int count = 0;

        for (int i = 0; i < selectedIndices.length; i++) {
            try {
                int index = Integer.parseInt(selectedIndices[i].trim());

                if (index == options.length + 1) { // If user selects "Custom Skills"
                    System.out.print("Enter custom skill: ");
                    String customSkill = scanner.nextLine().trim();
                    if (!customSkill.isEmpty()) {
                        selectedOptions[count++] = customSkill; // Add to list
                    }
                } else if (index >= 1 && index <= options.length) { // If selecting from predefined options
                    selectedOptions[count++] = options[index - 1];
                } else {
                    System.out.println("Invalid choice detected. Please try again.");
                    return chooseMultipleOptions(title, options); // Retry
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input format. Please enter numbers separated by commas.");
                return chooseMultipleOptions(title, options); // Retry
            }
        }

        // Resize the array to fit only valid selections
        String[] finalSkills = new String[count];
        for (int i = 0; i < count; i++) {
            finalSkills[i] = selectedOptions[i];
        }

        return finalSkills;
    }

    // Validation for Integer input
    private int getValidIntegerInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Invalid input! Please enter a valid number: ");
            }
        }
    }

    // Validates integer input within a given range
    private int getValidYear(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                int value = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                if (value >= min && value <= max) {
                    return value;
                }
            } else {
                scanner.next(); // Consume invalid input
            }
            System.out.println("Invalid year. Please enter a year between " + min + " and " + max + ".");
        }
    }

    // Validates double input within a given range
    private double getValidCGPA(String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextDouble()) {
                double value = scanner.nextDouble();
                scanner.nextLine(); // Consume newline
                if (value >= min && value <= max) {
                    return value;
                }
            } else {
                scanner.next(); // Consume invalid input
            }
            System.out.println("Invalid input. Please enter a number between " + min + " and " + max + ".");
        }
    }
}

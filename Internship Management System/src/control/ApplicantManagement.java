package control;

/**
 *
 * @author Wong Jin Xuan 
 * 
 * The ApplicantManagement class manages a collection of
 * applicants, providing functionalities to add, update, remove, and retrieve
 * (filtering sorting searching reporting) applicant information.
 *
 * Dependencies: - adt.HashedArrayList: Custom data structure for storing
 * applicants. - entity.Applicant: Represents individual applicant details. -
 * utility.Report: Used for generating applicant reports. -
 * dao.ApplicantInitializer: Handles applicant initialization.
 */
import adt.HashedArrayList;
import adt.HashedSetListInterface;
import entity.Applicant;
import utility.Report;
import dao.ApplicantInitializer;
import java.util.Arrays;
import java.util.Iterator;

public class ApplicantManagement {

    private HashedSetListInterface<Applicant> applicants;
    private int applicantIdCounter = 1001;

    // Constructor
    public ApplicantManagement() {
        applicants = new HashedArrayList<>();
        ApplicantInitializer.initializeApplicants(this); // Auto-load applicants
        System.out.println("Total applicants loaded: " + applicants.getNumberOfEntries());
    }

    // Generates a unique applicant ID
    public int generateApplicantId() {
        return applicantIdCounter++;
    }

    // Retrieve an applicant by ID
    public Applicant getApplicant(int id) {
        Iterator<Applicant> iterator = applicants.getIterator();

        while (iterator.hasNext()) {
            Applicant applicant = iterator.next();
            if (applicant != null && applicant.getId() == id) {
                return applicant;
            }
        }
        return null; // Return null if applicant is not found
    }

    // ------------------- Add Applicant -------------------
    public boolean addApplicant(Applicant applicant) {
        return applicants.add(applicant);
    }

    // --------------- Update Applicant by id --------------- 
    public boolean updateApplicant(int id, Applicant updatedApplicant) {
        if (updatedApplicant == null) {
            return false; // Invalid update
        }

        int index = applicants.indexOf(getApplicant(id)); // Find current index
        if (index == -1) {
            return false; // Applicant not found
        }

        return applicants.replace(index, updatedApplicant);
    }

    // --------------- Remove Applicant by id --------------- 
    public boolean removeApplicant(int id) {
        int index = applicants.indexOf(getApplicant(id));
        return (index != -1) && applicants.remove(index);
    }

    // --------------- Filter Applicants based on criteria --------------- 
    public Applicant[] filterApplicants(String criteria, String value) {
        Object[] broadFiltered;

        double minCgpa = 0.0;
        boolean isCgpaFilter = criteria.equalsIgnoreCase("CGPA");

        if (isCgpaFilter) {
            // Bypass ADT filter for CGPA, use all applicants
            broadFiltered = new Object[applicants.getNumberOfEntries()];
            for (int i = 0; i < applicants.getNumberOfEntries(); i++) {
                broadFiltered[i] = applicants.getEntry(i);
            }

            try {
                minCgpa = Double.parseDouble(value);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input for CGPA.");
                return new Applicant[0];
            }
        } else {
            // Use ADT filter for non-CGPA fields
            broadFiltered = applicants.filter(criteria, value);
        }

        @SuppressWarnings("unchecked")
        Applicant[] tempResults = new Applicant[broadFiltered.length];
        int count = 0;

        for (Object obj : broadFiltered) {
            if (obj instanceof Applicant) {
                Applicant applicant = (Applicant) obj;
                boolean matches = false;

                switch (criteria.toLowerCase()) {
                    case "cgpa":
                        matches = applicant.getCgpa() >= minCgpa;
                        break;
                    case "graduation year":
                        try {
                            matches = applicant.getGraduationYear() == Integer.parseInt(value);
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid graduation year input.");
                        }
                        break;
                    case "field of study":
                        matches = applicant.getFieldOfStudy().equalsIgnoreCase(value);
                        break;
                    case "location":
                        matches = applicant.getLocation().equalsIgnoreCase(value);
                        break;
                    case "skills":
                        for (String skill : applicant.getSkills()) {
                            if (skill.equalsIgnoreCase(value)) {
                                matches = true;
                                break;
                            }
                        }
                        break;
                    case "industry preference":
                        matches = applicant.getPreferredIndustry().equalsIgnoreCase(value);
                        break;
                    case "internship type":
                        matches = applicant.getInternshipType().equalsIgnoreCase(value);
                        break;
                    default:
                        matches = true;
                }

                if (matches) {
                    tempResults[count++] = applicant;
                }
            }
        }

        return Arrays.copyOf(tempResults, count);
    }

    // --------------- Sort Applicants by criteria --------------- 
    public HashedSetListInterface<Applicant> sortApplicants(String criteria, boolean ascending) {
        // Normalize criteria
        criteria = criteria.toLowerCase();

        // Create a temporary list with overridden comparison logic
        HashedSetListInterface<Applicant> sortedList = new HashedArrayList<Applicant>() {
            @Override
            public int compareByCriteria(Applicant a1, Applicant a2, String criteria) {
                switch (criteria) {
                    case "name":
                        return a1.getName().compareToIgnoreCase(a2.getName());
                    case "cgpa":
                        return Double.compare(a1.getCgpa(), a2.getCgpa());
                    case "graduation year":
                        return Integer.compare(a1.getGraduationYear(), a2.getGraduationYear());
                    case "skills":
                        int skillDiff = a1.getSkills().length - a2.getSkills().length;
                        if (skillDiff != 0) {
                            return skillDiff;
                        }
                        return a1.getName().compareToIgnoreCase(a2.getName());
                    default:
                        return a1.getName().compareToIgnoreCase(a2.getName()); // fallback
                }
            }
        };

        // Copy current applicants to temp list
        for (int i = 0; i < applicants.getNumberOfEntries(); i++) {
            sortedList.add(applicants.getEntry(i));
        }

        // Sort
        sortedList.sortBy(criteria, ascending);

        // Output results
        System.out.println("\nApplicants sorted by " + criteria + " (" + (ascending ? "Ascending" : "Descending") + "):");
        for (int i = 0; i < sortedList.getNumberOfEntries(); i++) {
            System.out.println(sortedList.getEntry(i));
        }

        return sortedList;
    }

    // --------------- Search Applicants by keyword ---------------    
    public Applicant[] searchApplicants(String category, String searchTerm) {
        int size = applicants.getNumberOfEntries();
        Applicant[] tempResults = new Applicant[size];
        int count = 0;

        searchTerm = searchTerm.toLowerCase();

        for (int i = 0; i < size; i++) {
            Applicant applicant = applicants.getEntry(i);
            boolean match = false;

            HashedArrayList<String> tempSet = new HashedArrayList<>();

            switch (category.toLowerCase()) {
                case "name":
                    tempSet.add(applicant.getName().toLowerCase());
                    match = tempSet.keywordSearch(searchTerm) != null;
                    break;
                case "field of study":
                    tempSet.add(applicant.getFieldOfStudy().toLowerCase());
                    match = tempSet.keywordSearch(searchTerm) != null;
                    break;
                case "graduation year":
                    tempSet.add(String.valueOf(applicant.getGraduationYear()).toLowerCase());
                    match = tempSet.keywordSearch(searchTerm) != null;
                    break;
                case "cgpa":
                    try {
                        double minCgpa = Double.parseDouble(searchTerm); // Parse searchTerm as a double
                        if (applicant.getCgpa() >= minCgpa) {
                            match = true;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid CGPA format.");
                        return new Applicant[0]; // Return empty array if invalid CGPA format
                    }
                    break;
                case "location":
                    tempSet.add(applicant.getLocation().toLowerCase());
                    match = tempSet.keywordSearch(searchTerm) != null;
                    break;
                case "skills":
                    for (String skill : applicant.getSkills()) {
                        tempSet.add(skill.toLowerCase());
                    }
                    match = tempSet.keywordSearch(searchTerm) != null;
                    break;
                case "preferred industry":
                    tempSet.add(applicant.getPreferredIndustry().toLowerCase());
                    match = tempSet.keywordSearch(searchTerm) != null;
                    break;
                case "internship type":
                    tempSet.add(applicant.getInternshipType().toLowerCase());
                    match = tempSet.keywordSearch(searchTerm) != null;
                    break;
                default:
                    System.out.println("Invalid search category.");
                    return new Applicant[0]; // Return empty array
            }

            if (match) {
                tempResults[count++] = applicant;
            }
        }

        return Arrays.copyOf(tempResults, count);
    }

    // ========================== Generate report based on criteria ==========================
    public Report generateReport(String criteria) {
        StringBuilder reportContent = new StringBuilder();

        if (criteria.equals("all applicants")) {
            reportContent.append(formatAllApplicants());
        } else {
            reportContent.append(formatGroupedReport(criteria));
        }

        return new Report("Applicant Report - " + criteria.toUpperCase(), reportContent.toString());
    }

    // Format ALL APPLICANTS without grouping
    private String formatAllApplicants() {
        StringBuilder result = new StringBuilder();
        result.append("=".repeat(100)).append("\n");
        result.append(String.format("%-5s %-15s %-35s %-30s\n", "ID", "Name", "Study Details", "Internship/Job Details"));
        result.append("=".repeat(100)).append("\n");

        Iterator<Applicant> iterator = applicants.getIterator();
        while (iterator.hasNext()) {
            Applicant a = iterator.next();
            if (a != null) {
                result.append(formatApplicantRow(a)).append("\n");
            }
        }

        result.append("Total Applicants: ").append(applicants.getNumberOfEntries()).append("\n");
        return result.toString();
    }

    // Format REPORT BY GROUPING based on unique values of the selected CRITERIA
    private String formatGroupedReport(String criteria) {
        StringBuilder result = new StringBuilder();
        String[] uniqueValues = getUniqueValues(criteria);

        for (String value : uniqueValues) {
            result.append("\n=== ").append(criteria.toUpperCase()).append(": ").append(value).append(" ===\n");
            result.append("=".repeat(100)).append("\n");
            result.append(String.format("%-5s %-15s %-35s %-30s\n", "ID", "Name", "Study Details", "Internship/Job Details"));
            result.append("=".repeat(100)).append("\n");

            int count = 0;
            Iterator<Applicant> iterator = applicants.getIterator();
            while (iterator.hasNext()) {
                Applicant a = iterator.next();
                if (a != null && matchesCriteria(a, criteria, value)) {
                    result.append(formatApplicantRow(a)).append("\n");
                    count++;
                }
            }

            result.append("\nTotal Applicants [").append(value).append("]: ").append(count).append("\n");
        }

        return result.toString();
    }

    // Extract unique values based on criteria (simulates a set)
    private String[] getUniqueValues(String criteria) {
        String[] values = new String[applicants.getNumberOfEntries()];
        int uniqueCount = 0;

        Iterator<Applicant> iterator = applicants.getIterator();
        while (iterator.hasNext()) {
            Applicant a = iterator.next();
            if (a == null) {
                continue;
            }

            String value = extractCriteriaValue(a, criteria);
            if (value != null && !arrayContains(values, value, uniqueCount)) {
                values[uniqueCount++] = value;
            }
        }

        return Arrays.copyOf(values, uniqueCount);
    }

    // Check if value exists in array (simulates a set)
    private boolean arrayContains(String[] arr, String value, int length) {
        for (int i = 0; i < length; i++) {
            if (arr[i] != null && arr[i].equals(value)) {
                return true;
            }
        }
        return false;
    }

    // Extracts the value of the criteria from an applicant
    private String extractCriteriaValue(Applicant a, String criteria) {
        switch (criteria) {
            case "location":
                return a.getLocation();
            case "field of study":
                return a.getFieldOfStudy();
            case "internship type":
                return a.getInternshipType();
            case "industry preference":
                return a.getPreferredIndustry();
            default:
                return null;
        }
    }

    // Check if an applicant matches the given criteria and value
    private boolean matchesCriteria(Applicant a, String criteria, String value) {
        switch (criteria) {
            case "location":
                return a.getLocation().equals(value);
            case "field of study":
                return a.getFieldOfStudy().equals(value);
            case "internship type":
                return a.getInternshipType().equals(value);
            case "industry preference":
                return a.getPreferredIndustry().equals(value);
            default:
                return false;
        }
    }

    // Format applicant details into a structured multi-line format
    private String formatApplicantRow(Applicant a) {
        String studyDetails = formatStudyDetails(a);
        String internshipDetails = formatInternshipDetails(a);

        // Split study and internship details into lines
        String[] studyLines = studyDetails.split("\n");
        String[] jobLines = internshipDetails.split("\n");

        // Determine the max number of lines to ensure proper row alignment
        int maxLines = Math.max(studyLines.length, jobLines.length);

        // Build the formatted output
        StringBuilder output = new StringBuilder();

        // Print ID and Name on the first row
        output.append(String.format("%-5d  %-15s %-35s %-30s\n",
                a.getId(), a.getName(), studyLines[0], jobLines[0]));

        // Print the remaining details line by line
        for (int i = 1; i < maxLines; i++) {
            String studyLine = (i < studyLines.length) ? studyLines[i] : "";
            String jobLine = (i < jobLines.length) ? jobLines[i] : "";
            output.append(String.format("%-5s  %-15s %-35s %-30s\n", "", "", studyLine, jobLine));
        }

        return output.toString();
    }

    // Format study details under "Study Details" section (Ensuring alignment)
    private String formatStudyDetails(Applicant a) {
        return String.format("Field: %-20s\nGrad Year: %-10d\nCGPA: %-10.2f",
                a.getFieldOfStudy(), a.getGraduationYear(), a.getCgpa());
    }

    // Format internship related details under "Internship/Job Details" section
    private String formatInternshipDetails(Applicant a) {
        return String.format("Location: %-20s\nSkills: %-20s\nIndustry: %-20s\nInternship: %-15s",
                a.getLocation(), String.join(", ", a.getSkills()), a.getPreferredIndustry(), a.getInternshipType());
    }

    // =====================================================================================
    // Get total number of applicants (used by ApplicantInitializer)
    public int getTotalApplicants() {
        return applicants.getNumberOfEntries();
    }
}

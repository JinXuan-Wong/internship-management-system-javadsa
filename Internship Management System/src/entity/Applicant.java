package entity;

/**
 *
 * @author Wong Jin Xuan 
 * 
 * The Applicant class represents an applicant in the
 * system, storing personal details, education background, skills, and
 * internship preferences.
 *
 * Dependencies: - None (Standalone entity class).
 */
public class Applicant {

    private int id;
    private String name;
    private String fieldOfStudy;
    private int graduationYear;
    private double cgpa;
    private String location;
    private String[] skills;
    private String preferredIndustry;
    private String internshipType;

    // Constructor
    public Applicant(int id, String name, String fieldOfStudy, int graduationYear,
            double cgpa, String location, String[] skills,
            String preferredIndustry, String internshipType) {
        this.id = id;
        this.name = name;
        this.fieldOfStudy = fieldOfStudy;
        this.graduationYear = graduationYear;
        this.cgpa = cgpa;
        this.location = location;
        this.skills = skills;
        this.preferredIndustry = preferredIndustry;
        this.internshipType = internshipType;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFieldOfStudy() {
        return fieldOfStudy;
    }

    public int getGraduationYear() {
        return graduationYear;
    }

    public double getCgpa() {
        return cgpa;
    }

    public String getLocation() {
        return location;
    }

    public String[] getSkills() {
        return skills;
    }

    public String getPreferredIndustry() {
        return preferredIndustry;
    }

    public String getInternshipType() {
        return internshipType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFieldOfStudy(String fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
    }

    public void setGraduationYear(int year) {
        this.graduationYear = year;
    }

    public void setCgpa(double cgpa) {
        this.cgpa = cgpa;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setSkills(String[] skills) {
        this.skills = skills;
    }

    public void setPreferredIndustry(String preferredIndustry) {
        this.preferredIndustry = preferredIndustry;
    }

    public void setInternshipType(String internshipType) {
        this.internshipType = internshipType;
    }

    // Display details
    // Convert skills array to a readable string
    public String getSkillsAsString() {
        return String.join(", ", skills);
    }

    @Override
    public String toString() {
        return "Applicant ID: " + id
                + "\nName: " + name
                + "\nField of Study: " + fieldOfStudy
                + "\nExpected Graduation Year: " + graduationYear
                + "\nCGPA: " + cgpa
                + "\nLocation Preference: " + location
                + "\nSkills: " + getSkillsAsString()
                + "\nPreferred Industry: " + preferredIndustry
                + "\nInternship Preference: " + internshipType + "\n";
    }

    // Equals method to check uniqueness (based on ID)
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Applicant applicant = (Applicant) obj;
        return id == applicant.id;
    }

    // HashCode method for fast lookups
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}

package utility;

/**
 *
 * @author Wong Jin Xuan 
 * ApplicantOptions provides predefined lists of skills,
 * locations, fields of study, preferred industries, and internship preferences
 * used in the applicant management system. It also includes sorting criteria
 * and search categories for filtering applicants.
 *
 * Dependencies: None (Static utility class).
 */
public class ApplicantOptions {

    public static final String[] SKILLS = {
        "Java", "Python", "C++", "SQL", "Machine Learning", "Data Analysis",
        "Cybersecurity", "Web Development", "Cloud Computing", "App Development",
        "Artificial Intelligence", "UI/UX Design", "Networking", "Blockchain", "Big Data"
    };

    public static final String[] LOCATIONS = {
        "Kuala Lumpur", "Selangor", "Penang", "Johor", "Melaka", "Negeri Sembilan",
        "Sarawak", "Sabah", "Perak", "Pahang", "Terengganu", "Kelantan", "Kedah"
    };

    public static final String[] FIELDS_OF_STUDY = {
        "Computer Science", "Data Science", "Software Engineering",
        "Cybersecurity", "Information Technology", "Artificial Intelligence",
        "Accounting", "Finance", "Business Administration", "Marketing",
        "Mechanical Engineering", "Electrical Engineering", "Civil Engineering"
    };

    public static final String[] PREFERRED_INDUSTRIES = {
        "Software Development", "Data Analytics", "Cybersecurity",
        "AI & Machine Learning", "E-commerce", "Finance & FinTech",
        "Cloud Computing", "Networking & IT Support", "Telecommunications",
        "Game Development", "Web & Mobile Development", "Blockchain & Cryptocurrency"
    };

    public static final String[] INTERNSHIP_PREFERENCES = {
        "Remote", "Full-Time", "Hybrid"
    };

    public static final String[] SORT_CRITERIA = {
        "Name", "Skills", "CGPA", "Graduation Year"
    };

    public static final String[] SEARCH_CATEGORIES = {
        "Name", "Field of Study", "Graduation Year", "CGPA", "Location", "Skills", "Preferred Industry", "Internship Type"
    };
    
    public static void printOptions(String[] options) {
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
    }
}

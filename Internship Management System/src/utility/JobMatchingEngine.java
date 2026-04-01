package utility;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author Tan Yen Ping
 */

public class JobMatchingEngine {

    // Define job titles, fields of study, and industries for matching
    public static final String[] JOB_TITLES = {
        "Data Science Intern", "Software Engineering Intern", 
        "AI Intern", "Cybersecurity Intern",
        "Financial Analyst Intern",
        "Accounting Intern", "Business Development Intern",
        "Digital Marketing Intern",
        "Mechanical Engineering Intern", "Civil Engineering Intern",
        "Pharmacy Intern", "Medical Research Intern",
        "Supply Chain Intern", "Hotel Management Intern",
        "Teaching Assistant Intern", "Graphic Design Intern"
    };

    public static final String[][] RELEVANT_FIELDS_AND_INDUSTRIES = {
        {"Computer Science", "Data Science", "Software Engineering", "Artificial Intelligence", "Data Analytics", "AI & Machine Learning", "Cloud Computing", "Networking & IT Support"},
        {"Computer Science", "Software Engineering", "Information Technology", "Mechanical Engineering", "Web & Mobile Development", "Game Development"},
        {"Artificial Intelligence", "Data Science", "Machine Learning", "AI & Machine Learning"},
        {"Cybersecurity", "Computer Science", "Cloud Computing", "Networking & IT Support"},
        {"Data Science", "Data Analytics", "Finance", "Finance & FinTech"},
        {"Accounting"},
        {"Finance", "Business Administration", "Marketing"},
        {"Business Administration", "Marketing", "E-commerce"},
        {"Mechanical Engineering"},
        {"Civil Engineering"},
        {},
        {},
        {"Blockchain & Cryptocurrency"},
        {}, {}, {}, {}, 
    };

}
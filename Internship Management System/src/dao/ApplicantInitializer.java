package dao;

/**
 *
 * @author Wong Jin Xuan 
 * 
 * ApplicantInitializer is responsible for initializing a
 * set of sample applicants into the ApplicantManagement system. It provides
 * convenience for system testing.
 *
 * Dependencies: - entity.Applicant: Represents individual applicant details. -
 * control.ApplicantManagement: Manages applicant operations.
 */
import entity.Applicant;
import control.ApplicantManagement;

public class ApplicantInitializer {

    public static void initializeApplicants(ApplicantManagement applicantManagement) {
        if (applicantManagement == null || applicantManagement.getTotalApplicants() > 0) {
            return; // Avoid re-initialization
        }

        applicantManagement.addApplicant(new Applicant(
                applicantManagement.generateApplicantId(),
                "Alice Tan",
                "Computer Science",
                2025,
                3.8,
                "Kuala Lumpur",
                new String[]{"Java", "Python"},
                "Software Development",
                "Full-Time"
        ));

        applicantManagement.addApplicant(new Applicant(
                applicantManagement.generateApplicantId(),
                "John Lim",
                "Artificial Intelligence",
                2026,
                3.5,
                "Penang",
                new String[]{"C++", "Machine Learning"},
                "Data Science",
                "Remote"
        ));

        applicantManagement.addApplicant(new Applicant(
                applicantManagement.generateApplicantId(),
                "Sarah Wong",
                "Information Technology",
                2025,
                3.7,
                "Johor Bahru",
                new String[]{"JavaScript", "React", "Node.js"},
                "Web Development",
                "Remote"
        ));

        applicantManagement.addApplicant(new Applicant(
                applicantManagement.generateApplicantId(),
                "Michael Chua",
                "Cybersecurity",
                2024,
                3.9,
                "Selangor",
                new String[]{"Cybersecurity", "Networking"},
                "Cybersecurity",
                "Hybrid"
        ));

        applicantManagement.addApplicant(new Applicant(
                applicantManagement.generateApplicantId(),
                "Emily Tan",
                "Data Science",
                2026,
                3.6,
                "Negeri Sembilan",
                new String[]{"Python", "SQL", "Data Analysis"},
                "Data Analytics",
                "Full-Time"
        ));

        applicantManagement.addApplicant(new Applicant(
                applicantManagement.generateApplicantId(),
                "Nathan Lee",
                "Software Engineering",
                2025,
                3.85,
                "Perak",
                new String[]{"Java", "C++", "Cloud Computing"},
                "Software Development",
                "Remote"
        ));

        applicantManagement.addApplicant(new Applicant(
                applicantManagement.generateApplicantId(),
                "Sophia Lim",
                "Business Administration",
                2025,
                3.7,
                "Kuala Lumpur",
                new String[]{"Marketing", "Finance"},
                "E-commerce",
                "Hybrid"
        ));

        applicantManagement.addApplicant(new Applicant(
                applicantManagement.generateApplicantId(),
                "Daniel Wong",
                "Finance",
                2024,
                3.9,
                "Melaka",
                new String[]{"Finance", "Accounting"},
                "Finance & FinTech",
                "Full-Time"
        ));

        applicantManagement.addApplicant(new Applicant(
                applicantManagement.generateApplicantId(),
                "Jessica Lee",
                "Marketing",
                2026,
                3.4,
                "Sarawak",
                new String[]{"Social Media Marketing", "UI/UX Design"},
                "E-commerce",
                "Remote"
        ));

        applicantManagement.addApplicant(new Applicant(
                applicantManagement.generateApplicantId(),
                "Rachel Tan",
                "Artificial Intelligence",
                2025,
                3.8,
                "Kedah",
                new String[]{"Machine Learning", "Deep Learning", "Python"},
                "AI & Machine Learning",
                "Full-Time"
        ));

        applicantManagement.addApplicant(new Applicant(
                applicantManagement.generateApplicantId(),
                "Kevin Cheng",
                "Cloud Computing",
                2026,
                3.5,
                "Pahang",
                new String[]{"AWS", "Google Cloud", "DevOps"},
                "Cloud Computing",
                "Remote"
        ));

        applicantManagement.addApplicant(new Applicant(
                applicantManagement.generateApplicantId(),
                "Fiona Lau",
                "Networking",
                2024,
                3.9,
                "Terengganu",
                new String[]{"CCNA", "Network Security"},
                "Networking & IT Support",
                "Hybrid"
        ));

        applicantManagement.addApplicant(new Applicant(
                applicantManagement.generateApplicantId(),
                "Vivian Ong",
                "Blockchain",
                2025,
                3.85,
                "Johor",
                new String[]{"Ethereum", "Smart Contracts"},
                "Blockchain & Cryptocurrency",
                "Remote"
        ));

        applicantManagement.addApplicant(new Applicant(
                applicantManagement.generateApplicantId(),
                "Eric Wong",
                "Game Development",
                2026,
                3.7,
                "Selangor",
                new String[]{"Unity", "Unreal Engine", "C#"},
                "Game Development",
                "Full-Time"
        ));

        applicantManagement.addApplicant(new Applicant(
                applicantManagement.generateApplicantId(),
                "Darren Teo",
                "Civil Engineering",
                2026,
                3.4,
                "Kuala Lumpur",
                new String[]{"AutoCAD", "Structural Design"},
                "Construction",
                "Hybrid"
        ));

        System.out.println("Sample applicants initialized successfully!");
    }
}

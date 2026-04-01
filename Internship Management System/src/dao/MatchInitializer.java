package dao;
/**
 *
 * @author Tan Yen Ping
 */
import entity.Applicant;
import entity.Job;
import control.JobMatching;

public class MatchInitializer {

    private static int applicantCounter = 1001;  // Start ID from 1000

    private static int generateApplicantId() {
        return applicantCounter++;
    }

    public static void initialize(JobMatching jobMatching) {
        if (jobMatching == null) {
            return;
        }

        jobMatching.addJob(new Job(1, "Software Developer Intern", "Google", "Kuala Lumpur",
                1500.00, "Hybrid", "Assist in software development and debugging tasks.",
                "Proficiency in Java and Python.", "Open", "3 Months"));

        jobMatching.addJob(new Job(2, "Cybersecurity Intern", "CyberSafe Inc.", "Selangor",
                900.00, "Remote", "Help analyze security threats and vulnerabilities.",
                "Basic knowledge of penetration testing and encryption.", "Open", "3 Months"));

        jobMatching.addJob(new Job(3, "Data Science Intern", "Shopee", "Penang",
                1200.00, "Full-Time", "Assist in data analysis and machine learning models.",
                "Knowledge in Python, SQL, and data visualization.", "Open", "6 Months"));

        // Finance & Banking Internships
        jobMatching.addJob(new Job(4, "Financial Analyst Intern", "Maybank", "Kuala Lumpur",
                1150.00, "Hybrid", "Assist in financial modeling and market research.",
                "Basic understanding of Excel, financial reports, and risk analysis.", "Open", "4 Months"));

        jobMatching.addJob(new Job(5, "Accounting Intern", "PwC", "Johor",
                950.00, "Full-Time", "Support the accounting team with tax and audit reports.",
                "Familiarity with accounting software and IFRS.", "Open", "3 Months"));

        // Marketing & Business Internships
        jobMatching.addJob(new Job(6, "Digital Marketing Intern", "Lazada", "Selangor",
                1000.00, "Remote", "Manage social media content and analyze marketing trends.",
                "Experience with Google Ads, SEO, and social media marketing.", "Open", "3 Months"));

        jobMatching.addJob(new Job(7, "Business Development Intern", "Tesla", "Kuala Lumpur",
                1100.00, "Hybrid", "Support sales strategies and customer engagement.",
                "Good communication skills and market analysis knowledge.", "Open", "4 Months"));

        // Engineering Internships
        jobMatching.addJob(new Job(8, "Mechanical Engineering Intern", "Intel", "Penang",
                1250.00, "Full-Time", "Assist in design and testing of mechanical components.",
                "Familiarity with SolidWorks and CAD software.", "Open", "5 Months"));

        jobMatching.addJob(new Job(9, "Civil Engineering Intern", "Gamuda", "Johor",
                750.00, "Full-Time", "Assist in structural analysis and project planning.",
                "Basic knowledge of construction materials and AutoCAD.", "Open", "6 Months"));

        // Healthcare Internships
        jobMatching.addJob(new Job(10, "Pharmacy Intern", "Guardian", "Kuala Lumpur",
                1300.00, "Full-Time", "Assist pharmacists in dispensing medications and consultations.",
                "Enrolled in a Pharmacy degree program.", "Open", "6 Months"));

        jobMatching.addJob(new Job(11, "Medical Research Intern", "Sunway Medical", "Selangor",
                1200.00, "Full-Time", "Assist in medical research and clinical trials.",
                "Basic understanding of research methods and statistics.", "Open", "6 Months"));

        // Logistics & Supply Chain Internships
        jobMatching.addJob(new Job(12, "Supply Chain Intern", "DHL", "Penang",
                800.00, "Full-Time", "Assist in inventory management and logistics planning.",
                "Basic knowledge of supply chain operations.", "Open", "3 Months"));

        // Hospitality & Tourism Internships
        jobMatching.addJob(new Job(13, "Hotel Management Intern", "Hilton", "Kuala Lumpur",
                1000.00, "Full-Time", "Assist in hotel operations and guest services.",
                "Enrolled in hospitality management studies.", "Open", "4 Months"));

        // Education & Training Internships
        jobMatching.addJob(new Job(14, "Teaching Assistant Intern", "Taylor’s University", "Selangor",
                900.00, "Full-Time", "Assist lecturers with course materials and student guidance.",
                "Strong academic background in related subjects.", "Open", "6 Months"));

        // Media & Design Internships
        jobMatching.addJob(new Job(15, "Graphic Design Intern", "MediaCorp", "Kuala Lumpur",
                1100.00, "Remote", "Create visual content for marketing materials.",
                "Proficiency in Photoshop, Illustrator, and Canva.", "Open", "3 Months"));

        jobMatching.addApplicant(new Applicant(
                generateApplicantId(),
                "Alice Tan",
                "Computer Science",
                2025,
                3.8,
                "Kuala Lumpur",
                new String[]{"Java", "Python"},
                "Software Development",
                "Full-Time"
        ));

        jobMatching.addApplicant(new Applicant(
                generateApplicantId(),
                "John Lim",
                "Artificial Intelligence",
                2026,
                3.5,
                "Penang",
                new String[]{"C++", "Machine Learning"},
                "Data Science",
                "Full-Time"
        ));

        jobMatching.addApplicant(new Applicant(
                generateApplicantId(),
                "Sarah Wong",
                "Information Technology",
                2025,
                3.7,
                "Johor",
                new String[]{"JavaScript", "React", "Node.js"},
                "Web Development",
                "Remote"
        ));

        jobMatching.addApplicant(new Applicant(
                generateApplicantId(),
                "Michael Chua",
                "Cybersecurity",
                2024,
                3.9,
                "Selangor",
                new String[]{"Cybersecurity", "Networking"},
                "Cybersecurity",
                "Hybrid"
        ));

        jobMatching.addApplicant(new Applicant(
                generateApplicantId(),
                "Emily Tan",
                "Data Science",
                2026,
                3.6,
                "Negeri Sembilan",
                new String[]{"Python", "SQL", "Data Analysis"},
                "Data Analytics",
                "Full-Time"
        ));

        jobMatching.addApplicant(new Applicant(
                generateApplicantId(),
                "Nathan Lee",
                "Software Engineering",
                2025,
                3.85,
                "Perak",
                new String[]{"Java", "C++", "Cloud Computing"},
                "Software Development",
                "Remote"
        ));

        jobMatching.addApplicant(new Applicant(
                generateApplicantId(),
                "Sophia Lim",
                "Business Administration",
                2025,
                3.7,
                "Kuala Lumpur",
                new String[]{"Marketing", "Finance"},
                "E-commerce",
                "Hybrid"
        ));

        jobMatching.addApplicant(new Applicant(
                generateApplicantId(),
                "Daniel Wong",
                "Finance",
                2024,
                3.9,
                "Melaka",
                new String[]{"Finance", "Accounting"},
                "Finance & FinTech",
                "Full-Time"
        ));

        jobMatching.addApplicant(new Applicant(
                generateApplicantId(),
                "Jessica Lee",
                "Marketing",
                2026,
                3.4,
                "Sarawak",
                new String[]{"Social Media Marketing", "UI/UX Design"},
                "E-commerce",
                "Remote"
        ));

        jobMatching.addApplicant(new Applicant(
                generateApplicantId(),
                "Rachel Tan",
                "Artificial Intelligence",
                2025,
                3.8,
                "Kedah",
                new String[]{"Machine Learning", "Deep Learning", "Python"},
                "AI & Machine Learning",
                "Full-Time"
        ));

        jobMatching.addApplicant(new Applicant(
                generateApplicantId(),
                "Kevin Cheng",
                "Cloud Computing",
                2026,
                3.5,
                "Pahang",
                new String[]{"AWS", "Google Cloud", "DevOps"},
                "Cloud Computing",
                "Remote"
        ));

        jobMatching.addApplicant(new Applicant(
                generateApplicantId(),
                "Fiona Lau",
                "Networking",
                2024,
                3.9,
                "Terengganu",
                new String[]{"CCNA", "Network Security"},
                "Networking & IT Support",
                "Hybrid"
        ));

        jobMatching.addApplicant(new Applicant(
                generateApplicantId(),
                "Vivian Ong",
                "Blockchain",
                2025,
                3.85,
                "Johor",
                new String[]{"Ethereum", "Smart Contracts"},
                "Blockchain & Cryptocurrency",
                "Remote"
        ));

        jobMatching.addApplicant(new Applicant(
                generateApplicantId(),
                "Eric Wong",
                "Game Development",
                2026,
                3.7,
                "Selangor",
                new String[]{"Unity", "Unreal Engine", "C#"},
                "Game Development",
                "Full-Time"
        ));

        jobMatching.addApplicant(new Applicant(
                generateApplicantId(),
                "Darren Teo",
                "Civil Engineering",
                2026,
                3.4,
                "Kuala Lumpur",
                new String[]{"AutoCAD", "Structural Design"},
                "Construction",
                "Hybrid"
        ));
        System.out.println("Data successfully initialized!");

        // ✅ Display updated matches (with Leo)
    }
}

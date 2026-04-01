package dao;

/**
 *
 * @author Low Qing Ying
 */
import entity.Job;
import control.JobManagement;

public class JobInitializer {

    public static void initializeJobs(JobManagement jobManagement) {
        if (jobManagement == null || jobManagement.getTotalJobs() > 0) {
            return; // Avoid re-initialization
        }

        // Technology & IT Internships
        jobManagement.addJob(new Job(jobManagement.generateNextJobID(), "Software Developer Intern", "Google", "Kuala Lumpur",
                1500.00, "Hybrid", "Assist in software development and debugging tasks.",
                "Proficiency in Java and Python.", "Open", "3 Months"));

        jobManagement.addJob(new Job(jobManagement.generateNextJobID(), "Cybersecurity Intern", "CyberSafe Inc.", "Selangor",
                900.00, "Remote", "Help analyze security threats and vulnerabilities.",
                "Basic knowledge of penetration testing and encryption.", "Closed", "3 Months"));

        jobManagement.addJob(new Job(jobManagement.generateNextJobID(), "Data Science Intern", "Shopee", "Penang",
                1200.00, "Full-Time", "Assist in data analysis and machine learning models.",
                "Knowledge in Python, SQL, and data visualization.", "Open", "6 Months"));

        // Finance & Banking Internships
        jobManagement.addJob(new Job(jobManagement.generateNextJobID(), "Financial Analyst Intern", "Maybank", "Kuala Lumpur",
                1150.00, "Hybrid", "Assist in financial modeling and market research.",
                "Basic understanding of Excel, financial reports, and risk analysis.", "Closed", "4 Months"));

        jobManagement.addJob(new Job(jobManagement.generateNextJobID(), "Accounting Intern", "PwC", "Johor Bahru",
                950.00, "Full-Time", "Support the accounting team with tax and audit reports.",
                "Familiarity with accounting software and IFRS.", "Open", "3 Months"));

        // Marketing & Business Internships
        jobManagement.addJob(new Job(jobManagement.generateNextJobID(), "Digital Marketing Intern", "Lazada", "Selangor",
                1000.00, "Remote", "Manage social media content and analyze marketing trends.",
                "Experience with Google Ads, SEO, and social media marketing.", "Open", "3 Months"));

        jobManagement.addJob(new Job(jobManagement.generateNextJobID(), "Business Development Intern", "Tesla", "Kuala Lumpur",
                1100.00, "Hybrid", "Support sales strategies and customer engagement.",
                "Good communication skills and market analysis knowledge.", "Open", "4 Months"));

        // Engineering Internships
        jobManagement.addJob(new Job(jobManagement.generateNextJobID(), "Mechanical Engineering Intern", "Intel", "Penang",
                1250.00, "Full-Time", "Assist in design and testing of mechanical components.",
                "Familiarity with SolidWorks and CAD software.", "Open", "5 Months"));

        jobManagement.addJob(new Job(jobManagement.generateNextJobID(), "Civil Engineering Intern", "Gamuda", "Johor Bahru",
                750.00, "Full-Time", "Assist in structural analysis and project planning.",
                "Basic knowledge of construction materials and AutoCAD.", "closed", "6 Months"));

        // Healthcare Internships
        jobManagement.addJob(new Job(jobManagement.generateNextJobID(), "Pharmacy Intern", "Guardian", "Kuala Lumpur",
                1300.00, "Full-Time", "Assist pharmacists in dispensing medications and consultations.",
                "Enrolled in a Pharmacy degree program.", "Open", "6 Months"));

        jobManagement.addJob(new Job(jobManagement.generateNextJobID(), "Medical Research Intern", "Sunway Medical", "Selangor",
                1200.00, "Full-Time", "Assist in medical research and clinical trials.",
                "Basic understanding of research methods and statistics.", "Open", "6 Months"));

        // Logistics & Supply Chain Internships
        jobManagement.addJob(new Job(jobManagement.generateNextJobID(), "Supply Chain Intern", "DHL", "Penang",
                800.00, "Full-Time", "Assist in inventory management and logistics planning.",
                "Basic knowledge of supply chain operations.", "Open", "3 Months"));

        // Hospitality & Tourism Internships
        jobManagement.addJob(new Job(jobManagement.generateNextJobID(), "Hotel Management Intern", "Hilton", "Kuala Lumpur",
                1000.00, "Full-Time", "Assist in hotel operations and guest services.",
                "Enrolled in hospitality management studies.", "Open", "4 Months"));

        // Education & Training Internships
        jobManagement.addJob(new Job(jobManagement.generateNextJobID(), "Teaching Assistant Intern", "Taylor’s University", "Selangor",
                900.00, "Full-Time", "Assist lecturers with course materials and student guidance.",
                "Strong academic background in related subjects.", "closed", "6 Months"));

        // Media & Design Internships
        jobManagement.addJob(new Job(jobManagement.generateNextJobID(), "Graphic Design Intern", "MediaCorp", "Kuala Lumpur",
                1100.00, "Remote", "Create visual content for marketing materials.",
                "Proficiency in Photoshop, Illustrator, and Canva.", "Open", "3 Months"));

    }
}

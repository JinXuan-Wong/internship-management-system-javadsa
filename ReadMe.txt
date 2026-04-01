DSAAssignment - Internship Management System
This Java console-based system simulates a job application and recruitment platform. It includes multiple modules to allow employers to manage jobs, job seekers to register and apply, and a matching engine to evaluate compatibility between candidates and jobs.
Designed for educational and practical use, this system demonstrates the application of custom data structures and object-oriented programming.
________________


Getting Started
Requirements
Before you can use the system, make sure you have the following installed:
* Java JDK (version 22+  or above)
* Apache NetBeans IDE (version 22+ recommended)
________________


How to Run the Application
1. Open NetBeans IDE.

2. Click File > Open Project....

3. Navigate to the extracted folder and open the DSAAssignment folder.

4. NetBeans will recognize it as a project.

5. Right-click the project name in the "Projects" panel and select Run.
________________


Using the System - Main Menu Overview
After you run the system, a main menu will appear in the NetBeans output console, prompting you with several options.
🔹 Option 1: Employer Access
Actions:
   * Add new job listings.

   * View or manage existing jobs.

   * Define job requirements like:

      * Job title

      * Minimum education level

      * Required experience

      * Skill set

         * Each job is stored and used later in the matching process.

🔹 Option 2: Job Seeker (Applicant) Access 
Actions:
            * Register a new applicant profile.

            * Enter personal details:

               * Name

               * Education

               * Experience

               * List of skills

                  * View or update applicant information.

🔹 Option 3: Match Applicants to Jobs
Functionality:
                     * Run the matching engine that compares applicants' qualifications against job listings.

                     * Calculates a match score based on:

                        * Skill compatibility

                        * Education level

                        * Work experience

                           * Displays a ranked list of applicants for each job.

🔹 Option 4: Manage Interviews
Actions:
                              * Schedule interviews between matched applicants and employers.

                              * Maintain a list of scheduled interviews.

                              * Reschedule or cancel interviews as needed.

🔹 Option 0: Exit System
Closes the program safely and exits the loop.
________________


How to Interact
                                 * All actions are done through keyboard input.

                                 * Simply type the number of the menu option and press Enter.

                                 * Follow the prompts on the screen for text-based input (e.g., name, skills).

                                 * The system guides you step by step.

________________


System Architecture
                                    * MainUI.java: Entry point and central controller for the UI.

                                    * JobUI.java: Interface for employers to manage jobs.

                                    * ApplicantUI.java: Interface for applicants to register and view details.

                                    * MatchingUI.java: Handles job-applicant matching.

                                    * InterviewScheduleUI.java: Manages interview scheduling.

All user inputs are processed through these classes, while data logic is handled in the control package and custom data structures in the adt package.
________________


Example Use Case
                                       1. An employer logs in, adds 3 job postings.

                                       2. Two job seekers register with their details.

                                       3. The admin selects Option 3 to run the matching engine.

                                       4. The system displays scores showing how well each applicant fits each job.

                                       5. The admin uses Option 4 to schedule interviews with top candidates.
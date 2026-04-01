package utility;

/**
 *
 * @author Low Qing Ying
 */
public class MessageUI {

    public static void displayInvalidChoiceMessage() {
        System.out.println("\nInvalid choice. Please try again.");
    }

    public static void displayExitMessage() {
        System.out.println("\nExiting system. Goodbye!");
    }

    public static void displayMessage(String message) {
        System.out.println("[INFO] " + message);
    }

    public static void displayError(String errorMessage) {
        System.err.println("[ERROR] " + errorMessage);
    }

}

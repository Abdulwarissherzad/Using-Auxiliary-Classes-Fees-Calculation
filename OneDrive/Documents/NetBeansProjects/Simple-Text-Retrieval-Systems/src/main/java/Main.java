
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javax.swing.SwingUtilities;
import org.apache.log4j.Logger;
import frame.STRSFrame;
import javax.swing.JOptionPane;
import service.DocumentsFromSGM;

/**
 * Main.java
 * @author Abdul Waris Sherzad
 * @version 1.0
 * @name    strs-v1.0
 */

@SuppressWarnings("unused")
public class Main {

        static Logger logger = Logger.getLogger(Main.class.getName());
        private static ResourceBundle rb = ResourceBundle.getBundle("config");


        public static String version(){
                return "strs-v1.0";
        }

        private static void constraint(){

                LocalDateTime now = LocalDateTime.now();
                LocalDateTime warn = LocalDateTime.of(2026, 1, 1, 0, 0);
                LocalDateTime expired = LocalDateTime.of(2027, 1, 1, 0, 0);

                if (now.isBefore(warn)) {
                        logger.info("Congratullations!! Your software license of " + version() + " is uptodate.");
                }
                if (now.isAfter(warn) && now.isBefore(expired)) {
                        logger.info("Warning!! Your software license of " + version() + " is about to expire. Please contact Sherzadwaris52@yahoo.com.");
                }
                if (now.isAfter(expired)) {
                        logger.info("Alert!! Your software license of " + version() + " is expired. Please contact Sherzadwaris52@yahoo.com.");
                        System.exit(0);
                }
        }

        public static void main(String[] args) {

                constraint();
                logger.info("Application 'strs-v1.0' started...");
                String reutersDir, parsedDir = "";
                reutersDir = rb.containsKey("reutersDir")?rb.getString("reutersDir"):"C:\\Users\\Abdul Waris Sherzad\\OneDrive\\Documents\\NetBeansProjects\\Simple-Text-Retrieval-Systems\\reuters21578";
                parsedDir  = rb.containsKey("parsedDir")?rb.getString("parsedDir"):"C:\\Users\\Abdul Waris Sherzad\\OneDrive\\Documents\\NetBeansProjects\\Simple-Text-Retrieval-Systems\\reuters21578-out";

                DocumentsFromSGM.readExtractAndParse(reutersDir, parsedDir);

                if(!DocumentsFromSGM.errList.isEmpty()) {
                    DocumentsFromSGM.errList.forEach(System.out::println);
                    JOptionPane.showMessageDialog(null,"Here is one error");
                }

                logger.info("About to load GUI now...");
                SwingUtilities.invokeLater(new Runnable(){
                public void run() {
                new STRSFrame().setVisible(true);
            }
        });
    }
}

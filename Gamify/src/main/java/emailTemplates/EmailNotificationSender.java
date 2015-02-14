package emailTemplates;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContext;





import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.core.controller.LmsGameController;
import com.core.exception.EmailSendingFailException;
import com.core.util.Emailer;



/**
 * The Class EmailNotificationSender used to send email notification.
 */
public class EmailNotificationSender {

     
      
	private static final Logger log = LoggerFactory.getLogger(LmsGameController.class);
  

    

      public static void sendResetPasswordMail(final Object[] args, final List<String> recipients,final String template) {

    	    
            final TimerTask task = new TimerTask() {
                  @Override
                  public void run() {
                	  
                	  String strLine = template;
                	  File file = new File(template);
                	 System.out.println(" ***************************************************  " + file.getAbsolutePath());
                        final String body = template;
                        final String subject = "LMS Credentials";

                        final Emailer emailer = new Emailer(null, null, subject, body, recipients);
                        try {
                              emailer.sendEmail();
                              
                        } catch (final Exception e) {
                              log.error("Error Sending E-Mail : ", e);
                              
                        }
                  }
            };

            final Timer timer = new Timer();
            timer.schedule(task, 0);

      }

      
     
      

     

     
}

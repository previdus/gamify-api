package emailTemplates;

import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.core.util.Emailer;

/**
 * The Class EmailNotificationSender used to send email notification.
 */
public class EmailNotificationSender {

	private static final Logger log = LoggerFactory
			.getLogger(EmailNotificationSender.class);
	
	public static void main(String[] args) {
		List<String> recp = new LinkedList<String>();
		recp.add("ricky.rungta@gmail.com");
		
		EmailNotificationSender.sendResetPasswordMail(null,recp , null);
	}

	public static void sendResetPasswordMail(final Object[] args,
			final List<String> recipients, final String template) {

		final TimerTask task = new TimerTask() {
			@Override
			public void run() {

				try{
				    EmailNotificationSender.sendEmail(recipients, template);
				}catch(Exception e){
					log.error("Exception while trying to asynchronously send email "+e);
				}
				
			}

			
		};

		final Timer timer = new Timer();
		timer.schedule(task, 0);

	}

	
	public static void sendEmailAsync(final List<String> recipients,
			final String template) throws Exception{
		final TimerTask task = new TimerTask() {
			@Override
			public void run() {

				try{
				    EmailNotificationSender.sendEmail(recipients, template);
				}catch(Exception e){
					log.error("Exception while trying to asynchronously send email "+e);
				}
				
			}
		};

		final Timer timer = new Timer();
		timer.schedule(task, 0);


	}
	
	
	public static void sendEmail(final List<String> recipients,
			final String template) throws Exception{
		//String strLine = template;
		//File file = new File(template);
//		System.out
//				.println(" ***************************************************  "
//						+ file.getAbsolutePath());
		final String body = template;
		final String subject = "LMS Credentials";

		final Emailer emailer = new Emailer(null, null, subject, body,
				recipients);
		
		emailer.sendEmail();


	}
}

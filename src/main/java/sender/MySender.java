package sender;

import javax.jms.*;
import javax.jms.QueueConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MySender {

	public static void main(String[] args) {
		
		try{
			
			ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContextJMS.xml");
			QueueConnectionFactory factory = (QueueConnectionFactory) applicationContext.getBean("connectionFactory");
			
			Queue queue = (Queue) applicationContext.getBean("queue");

			// Create a connection. See https://docs.oracle.com/javaee/7/api/javax/jms/package-summary.html
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost:8161/admin/");
			Connection connection = connectionFactory.createConnection();
			// Open a session without transaction and acknowledge automatic
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			// Start the connection
			connection.start();
			// Create a sender
			QueueSender sender = session.createSender(nomJNDIQueue);
			// Create a message
			String message = "Indra";
			// Send the message
			sender.send(message);
			// Close the session
			session.close();
			// Close the connection
			connection.close();

		}catch(Exception | BeansException | JMSException e){
			e.printStackTrace();
		}

	}

}

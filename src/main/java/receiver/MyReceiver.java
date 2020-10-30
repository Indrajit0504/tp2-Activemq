package receiver;

import javax.jms.*;

import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.sound.midi.Receiver;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyReceiver {

	public static void main(String[] args) {
		try{
			
			ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContextJMS.xml");
			QueueConnectionFactory factory = (QueueConnectionFactory) applicationContext.getBean("connectionFactory");
			
			Queue queue = (Queue) applicationContext.getBean("queue");
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost:8161/admin/");
			// Create a connection. See https://docs.oracle.com/javaee/7/api/javax/jms/package-summary.html
			Connection connection = connectionFactory.createConnection();
			connection.start();
			// Open a session
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			// start the connection
			// Create a receive
			Receiver Receiver = session.createReceiver(destination);
			// Receive the message
			Message message = Receiver.receive(1000);

			if (message instanceof TextMessage) {
				TextMessage textMessage = (TextMessage) message;
				String text = textMessage.getText();
				System.out.println("Received: " + text);
			} else {
				System.out.println("Received: " + message);
			}

			receiver.close();
			session.close();
			connection.close();

		}catch(Exception e){
			e.printStackTrace();
		}
	}

}

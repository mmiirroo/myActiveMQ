package com.xuwei.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Receiver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ConnectionFactory connectionFactory;
		Connection connection = null;
		Session session;
		Destination destination;
		MessageConsumer consumer;
		
		connectionFactory = new ActiveMQConnectionFactory(
				ActiveMQConnection.DEFAULT_USER,
				ActiveMQConnection.DEFAULT_PASSWORD,
				"tcp://localhost:61616");
		try {
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
			destination = session.createQueue("FirstQueue");
			consumer = session.createConsumer(destination);
			while(true) {
				TextMessage message = (TextMessage) consumer.receive(100000);
				if(null!=message) {
					System.out.println("receive message:"+ message.getText());
				}
				else
					break;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(null!=connection)
					connection.close();
			} catch (Throwable ignore) {
            }
		}
	}

}

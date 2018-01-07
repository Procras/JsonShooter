import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

/*
 * Original Tutorial:
 * http://activemq.apache.org/hello-world.html
 * 
*/
public class App {
	public static void main(String[] args) throws Exception {
		try {
			// Create a ConnectionFactory
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://10.26.1.208:61616");
					//Original address:"tcp://sjmspaysvil01.sisal.it:61616");
					
			// Create a Connection
			Connection connection = connectionFactory.createConnection();
			connection.start();

			// Create a Session
			Session session = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);

			// Create the destination (Topic or Queue)
			Queue destination = session.createQueue("9650075");

			// Create a MessageProducer from the Session to the Topic or Queue
			MessageProducer producer = session.createProducer(destination);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

			// Create a message
			String text = "{\"request\":{\"action\":\"PAYMENT\",\"type\":\"V\",\"cks\":-21189,\"terminal\":{\"id\":50075,\"time\":1509012848,\"label\":66,\"type\":4,\"host\":\"10.2.0.139\",\"port\":4359,\"sequenceNumber\":0,\"geo\":{\"appId\":57,\"endPoints\":[{\"id\":3,\"sessionId\":7,\"type\":\"SENDER\"},{\"id\":1,\"sessionId\":0,\"type\":\"RECEIVER\"}]},\"salePoint\":{\"id\":5000,\"zone\":11,\"type\":1}},\"cart\":{\"id\":0,\"paymentInstrument\":null,\"transactions\":[{\"id\":0,\"readMode\":2,\"service\":{\"id\":96,\"product\":{\"id\":\"096038000000\",\"amount\":0}},\"reference\":{\"type\":30,\"code\":{\"paymentCode\":\"38\",\"paymentId\":\"7427082407120600824411\",\"gln\":\"\"}},\"fee\":{\"salePoint\":0,\"sisal\":0,\"type\":0}}]},\"subject\":{\"masterData\":{\"acquisitionMode\":0,\"codes\":[{\"id\":\"\",\"name\":\"\",\"type\":\"OWNER\"},{\"id\":\"\",\"name\":\"\",\"type\":\"ORDER\"}]},\"card\":{\"maskedPAN\":\"\",\"encryptedPAN\":\"\",\"keyIndexRSA\":\"\"}},\"channel\":3}}";
			TextMessage message = session.createTextMessage(text);
			
			//{\"group\":\"DIGIPAY\",\"type\":\"PUSH_NOTIFICATION\",\"correlationID\":\"378312798432\",\"timestamp\":421234342342,\"tid\":378312798432,\"terminal\":{\"id\":15672,\"time\":0,\"sequenceNumber\":18,\"salePoint\":{\"id\":3366,\"zone\":3,\"type\":0}},\"details\":[{\"storeId\":23233232,\"amount\":112.1,\"consumerId\":\"2343243243232423\",\"consumerName\":\"Enzo\",\"consumerSurname\":\"Picci\",\"date\":1506433061280,\"customerTransactionId\":\"378312798432\",\"status\":\"PENDING\"}]}
			
			// Tell the producer to send the message
			System.out.println("Sent message: ");
			producer.send(message);

			// Clean up
			session.close();
			connection.close();
		}
		catch (Exception e) {
			System.err.println("Caught: " + e);
			e.printStackTrace();
		}
	}
}

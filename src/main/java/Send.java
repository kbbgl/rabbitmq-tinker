import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {

    private static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel  channel = connection.createChannel();
        try {

            /**
             * Declare a queue
             * @see com.rabbitmq.client.AMQP.Queue.Declare
             * @see com.rabbitmq.client.AMQP.Queue.DeclareOk
             * @param queue the name of the queue
             * @param durable true if we are declaring a durable queue (the queue will survive a server restart)
             * @param exclusive true if we are declaring an exclusive queue (restricted to this connection)
             * @param autoDelete true if we are declaring an autodelete queue (server will delete it when no longer in use)
             * @param arguments other properties (construction arguments) for the queue
             * @return a declaration-confirm method to indicate the queue was successfully declared
             * @throws java.io.IOException if an error is encountered
             */

            String message = "Hello World!";
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println("[x] sent '" + message + "'");

            if (connection.isOpen()){
                connection.close();
                System.out.println("Connection closed");
            }
            if (channel.isOpen()){
                channel.close();
                System.out.println("Channel closed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

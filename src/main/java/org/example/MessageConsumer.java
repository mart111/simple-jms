package org.example;

import javax.ejb.MessageDriven;
import javax.jms.JMSDestinationDefinition;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

@JMSDestinationDefinition(
        name = "java:global/queue/simpleQ",
        interfaceName = "javax.jms.Queue",
        destinationName = "simpleQ"
)
@MessageDriven(mappedName = "simpleQ")
public class MessageConsumer implements MessageListener {

    @Override
    public void onMessage(Message message) {
        try {
            System.out.println("Consumer1: " + message.getBody(String.class));
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}

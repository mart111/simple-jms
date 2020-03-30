package org.example;

import javax.annotation.Resource;
import javax.jms.*;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(urlPatterns = "/demo")
@JMSDestinationDefinition(
        name="java:global/queue/simpleT",
        interfaceName="javax.jms.Topic",
        destinationName = "simpleT"
)
public class DemoServlet extends HttpServlet {


    @Resource(name = "myJmsConnectionFactory")
    private ConnectionFactory factory;

    @Resource(lookup = "java:global/queue/simpleQ")
    private Queue queue;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Connection connection = factory.createConnection();
            System.out.println(queue.getQueueName());
            connection.start();
            QueueSession session =
                    ((QueueSession) connection
                            .createSession(false, Session.AUTO_ACKNOWLEDGE));
            MessageProducer producer = session.createProducer(queue);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            TextMessage textMessage = session.createTextMessage("Hello world!");
            producer.send(textMessage);
            session.close();
            connection.close();
            ServletOutputStream outputStream = resp.getOutputStream();
            resp.setContentType("text/plain");
            outputStream.println("VARDUHI");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}

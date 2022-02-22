package jandi.server.util.slack;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.model.Message;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

@PropertySource("classpath:app.properties")
public class ConversationsHistory {

    static String channelID= "C0300NWDLDB";

    @Value("${slack.token}")
    static String token;


    static Optional<List<Message>> conversationHistory = Optional.empty();

    /**
     * Fetch conversation history using ID from last example
     */
    static void fetchHistory(String id, String token) {
        // you can get this instance via ctx.client() in a Bolt app
        var client = Slack.getInstance().methods();
        var logger = LoggerFactory.getLogger("my-awesome-slack-app");
        try {
            // Call the conversations.history method using the built-in WebClient
            var result = client.conversationsHistory(r -> r
                    // The token you used to initialize your app
                    .token(token)
                    .channel(id)
            );
            conversationHistory = Optional.ofNullable(result.getMessages());
            // Print results
            logger.info("{} messages found in {}", conversationHistory.orElse(emptyList()).size(), id);
        } catch (IOException | SlackApiException e) {
            logger.error("error: {}", e.getMessage(), e);
        }
    }

    public static void main(String[] args) throws Exception {
        // Find conversation with a specified channel `name`
        fetchHistory(channelID, token);
    }

}
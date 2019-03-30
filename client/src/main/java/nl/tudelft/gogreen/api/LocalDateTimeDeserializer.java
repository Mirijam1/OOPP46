package nl.tudelft.gogreen.api;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.time.LocalDateTime;

@NoArgsConstructor
public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(JsonParser parser, DeserializationContext context)
        throws IOException {

        JsonNode tree = parser.getCodec().readTree(parser);
        int year = tree.get("year").asInt();
        int month = tree.get("monthValue").asInt();
        int dayOfMonth = tree.get("dayOfMonth").asInt();
        int hour = tree.get("hour").asInt();
        int minute = tree.get("minute").asInt();
        int second = tree.get("second").asInt();
        int nano = tree.get("nano").asInt();

        return LocalDateTime.of(year, month, dayOfMonth, hour, minute, second, nano);
    }
}

package nl.tudelft.gogreen.shared.tests;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.JsonNode;
import nl.tudelft.gogreen.shared.LocalDateTimeDeserializer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class LocalDateTimeDeserializerTest {
    private LocalDateTimeDeserializer deserializer;
    private JsonParser parser;

    @Before
    public void setUp() throws IOException {
        this.deserializer = new LocalDateTimeDeserializer();
        this.parser = Mockito.mock(JsonParser.class);
        ObjectCodec codec = Mockito.mock(ObjectCodec.class);
        JsonNode node = Mockito.mock(JsonNode.class);

        when(parser.getCodec()).thenReturn(codec);
        when(codec.readTree(any())).thenReturn(node);

        JsonNode yearNode = Mockito.mock(JsonNode.class);
        JsonNode monthNode = Mockito.mock(JsonNode.class);
        JsonNode dayOfMonthNode = Mockito.mock(JsonNode.class);
        JsonNode hourNode = Mockito.mock(JsonNode.class);
        JsonNode minuteNode = Mockito.mock(JsonNode.class);
        JsonNode secondNode = Mockito.mock(JsonNode.class);
        JsonNode nanoNode = Mockito.mock(JsonNode.class);

        when(node.get("year")).thenReturn(yearNode);
        when(node.get("monthValue")).thenReturn(monthNode);
        when(node.get("dayOfMonth")).thenReturn(dayOfMonthNode);
        when(node.get("hour")).thenReturn(hourNode);
        when(node.get("minute")).thenReturn(minuteNode);
        when(node.get("second")).thenReturn(secondNode);
        when(node.get("nano")).thenReturn(nanoNode);
        when(yearNode.asInt()).thenReturn(2019);
        when(monthNode.asInt()).thenReturn(1);
        when(dayOfMonthNode.asInt()).thenReturn(1);
        when(hourNode.asInt()).thenReturn(1);
        when(minuteNode.asInt()).thenReturn(1);
        when(secondNode.asInt()).thenReturn(1);
        when(nanoNode.asInt()).thenReturn(1);
    }

    @Test
    public void shouldReturnCorrectLocalDateTime() throws IOException {
        LocalDateTime time = deserializer.deserialize(parser, null);

        assertEquals(time.getYear(), 2019);
        assertEquals(time.getMonthValue(), 1);
        assertEquals(time.getMonth(), Month.JANUARY);
        assertEquals(time.getDayOfMonth(), 1);
        assertEquals(time.getHour(), 1);
        assertEquals(time.getMinute(), 1);
        assertEquals(time.getSecond(), 1);
        assertEquals(time.getNano(), 1);
    }
}

package by.ladz.gs.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeDeserializer {
    public static class LocalDateDeserializer extends JsonDeserializer<LocalDate> {
        private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy");

        @Override
        public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            String date = jsonParser.getText();
            return LocalDate.parse(date, formatter);
        }
    }

    public static class LocalTimeDeserializer extends JsonDeserializer<LocalTime> {
        private final DateTimeFormatter timeFormatterWithLeadingZero = DateTimeFormatter.ofPattern("HH:mm");
        private final DateTimeFormatter timeFormatterWithoutLeadingZero = DateTimeFormatter.ofPattern("H:mm");

        @Override
        public LocalTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            String time = jsonParser.getText();

            LocalTime parsedTime;
            try {
                parsedTime = LocalTime.parse(time, timeFormatterWithLeadingZero);
            } catch (DateTimeParseException e) {
                parsedTime = LocalTime.parse(time, timeFormatterWithoutLeadingZero);
            }
            return parsedTime;
        }
    }
}

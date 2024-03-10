package ua.goit;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


public class TimeZoneConverter {
    public String formatDateTime(String zoneId) {
        // Define the source date and timezone
        LocalDateTime localDateTime = LocalDateTime.now();
        ZoneId localTimeZone = ZoneId.systemDefault();

        // Convert the source date to the destination timezone
        ZoneId destinationTimeZone = ZoneId.of(zoneId);
        ZonedDateTime destinationDateTime = localDateTime.atZone(localTimeZone).withZoneSameInstant(destinationTimeZone);

        // Format the destination date as per the desired pattern
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss Z");
        String formattedDate = destinationDateTime.format(formatter);

        return formattedDate;


    }
}

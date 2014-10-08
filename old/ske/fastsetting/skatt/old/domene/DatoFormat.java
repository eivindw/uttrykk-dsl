package ske.fastsetting.skatt.old.domene;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class DatoFormat {
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public static LocalDate parseNorskDato(String dato) {
        return LocalDate.parse(dato, formatter);
    }
}

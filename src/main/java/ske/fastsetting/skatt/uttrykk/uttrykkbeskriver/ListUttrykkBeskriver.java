package ske.fastsetting.skatt.uttrykk.uttrykkbeskriver;

import ske.fastsetting.skatt.beregn.UttrykkResultat;
import ske.fastsetting.skatt.domene.Regel;
import ske.fastsetting.skatt.uttrykk.UttrykkBeskriver;

import java.util.ArrayList;
import java.util.List;

public class ListUttrykkBeskriver implements UttrykkBeskriver<List> {

    private final List<Entry> verdier;
    private ListUttrykkBeskriver indentingWriter;

    public ListUttrykkBeskriver(List<Entry> verdier) {
        this.verdier = verdier;
    }

    public ListUttrykkBeskriver() {
        this(new ArrayList<>());
    }

    public void skriv(String line) {
        verdier.add(new TekstEntry(line));
        indentingWriter = null;
    }

    public UttrykkBeskriver rykkInn() {
        //if (indentingWriter == null) {
            List<Entry> barn = new ArrayList<>();
            indentingWriter = new ListUttrykkBeskriver(barn);
            verdier.add(new ListEntry(barn));
        //}

        return indentingWriter;
    }

    public UttrykkBeskriver overskrift(String tekst) {
        //if (indentingWriter == null) {
            List<Entry> barn = new ArrayList<>();
            indentingWriter = new ListUttrykkBeskriver(barn);
            verdier.add(new ListTekstEntry(tekst, barn));
        //}

        return indentingWriter;
    }

    public List<Entry> liste() {
        return verdier;
    }

    @Override
    public List beskriv(UttrykkResultat<?> resultat) {
        return null;
    }

    public interface Entry {

    }

    public static class TekstEntry implements Entry {
        private final String tekst;

        private TekstEntry(String tekst) {
            this.tekst = tekst;
        }

        public String getTekst() {
            return tekst;
        }
    }

    public static class ListTekstEntry implements Entry {
        private final String tekst;
        private final List<Entry> liste;

        private ListTekstEntry(String tekst, List<Entry> liste) {
            this.tekst = tekst;
            this.liste = liste;
        }

        public String getTekst() {
            return tekst;
        }

        public List<Entry> getListe() {
            return liste;
        }

    }

    public static class ListEntry implements Entry {
        private final List<Entry> liste;

        private ListEntry(List<Entry> liste) {
            this.liste = liste;
        }

        public List<Entry> getListe() {
            return liste;
        }

    }
}

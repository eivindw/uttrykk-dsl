package ske.fastsetting.skatt.uttrykk.uttrykkbeskriver;

import ske.fastsetting.skatt.domene.Regel;
import ske.fastsetting.skatt.uttrykk.UttrykkBeskriver;

public class ConsoleUttrykkBeskriver implements UttrykkBeskriver {

    private int indentation;

    public ConsoleUttrykkBeskriver() {
    }

    private ConsoleUttrykkBeskriver(int indentation) {
        this.indentation = indentation;
    }

    @Override
    public void skriv(String line) {
        System.out.println(getInnrykk() +line);
    }

    @Override
    public UttrykkBeskriver rykkInn() {
        return new ConsoleUttrykkBeskriver(indentation+2);
    }

    @Override
    public void tags(String... tags) {
        System.out.println(getInnrykk()+tags);
    }

    @Override
    public void regler(Regel... regler) {
        System.out.println(getInnrykk()+regler);
    }

    @Override
    public UttrykkBeskriver overskrift(String line) {
        System.out.println(getInnrykk() + "***" + line + "***");
        return this;
    }

    private String getInnrykk() {
        return new String(new char[indentation]).replace("\0", " ");
    }
}

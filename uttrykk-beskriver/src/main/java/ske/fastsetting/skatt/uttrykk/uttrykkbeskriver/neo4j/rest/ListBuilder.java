package ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.neo4j.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
* Created by jorn ola birkeland on 05.06.15.
*/
public class ListBuilder {
    private ArrayList liste;

    public ListBuilder(ArrayList liste) {

        this.liste = liste;
    }

    public static ListBuilder liste() {
        return new ListBuilder(new ArrayList());
    }

    public ListBuilder value(String verdi) {
        liste.add(verdi);
        return this;
    }

    public ListBuilder value(int verdi) {
        liste.add(verdi);
        return this;
    }

    public ObjectBuilder object() {
        Map map = new HashMap();
        liste.add(map);
        return new ObjectBuilder(map);
    }

    public Object build() {
        return liste;
    }

    public ListBuilder values(Collection values) {
        liste.addAll(values);
        return this;
    }
}

package ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.neo4j.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.neo4j.rest.Value.string;

/**
* Created by jorn ola birkeland on 05.06.15.
*/
public class ListBuilder {
    private ArrayList<Object> liste;

    public ListBuilder(ArrayList<Object> liste) {

        this.liste = liste;
    }

    public static ListBuilder liste() {
        return new ListBuilder(new ArrayList<Object>());
    }

    public ListBuilder value(String verdi) {
        liste.add(string(verdi));
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

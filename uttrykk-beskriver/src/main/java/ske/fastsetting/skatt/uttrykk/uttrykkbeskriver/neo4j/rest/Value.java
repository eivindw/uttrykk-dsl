package ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.neo4j.rest;

/**
 * Created by jorn ola birkeland on 08.06.15.
 */
public class Value {
    public static Object string(String value) {
        return "\""+value+"\"";
    }


}

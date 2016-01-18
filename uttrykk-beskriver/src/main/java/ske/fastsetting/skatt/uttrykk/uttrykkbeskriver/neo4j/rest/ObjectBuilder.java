package ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.neo4j.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
* Created by jorn ola birkeland on 05.06.15.
*/
public class ObjectBuilder {

    private Map map;

    ObjectBuilder(Map map) {
        this.map = map;
    }

    public ObjectBuilder entry(String navn, Object verdi) {
        this.map.put(navn, verdi);
        return this;
    }

    public ObjectBuilder object(String navn) {
        Map map = new HashMap();
        this.map.put(navn,map);
        return new ObjectBuilder(map);
    }

    public ListBuilder array(String navn) {
        ArrayList liste = new ArrayList();
        map.put(navn, liste);
        return new ListBuilder(liste);
    }

    public Map build() {
        return map;
    }

    public static ObjectBuilder ny() {
        return new ObjectBuilder(new HashMap());
    }

    public ObjectBuilder entryIfValue(String navn, Object value) {
        if(value!=null) {
            map.put(navn,value);
        }
        return this;
    }
}

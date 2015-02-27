package ske.fastsetting.skatt.uttrykk.stedbundetBelop;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jorn ola birkeland on 27.02.15.
 */
public class Rec {

    Map<Key<?>,Object> map = new HashMap<>();

    public <T> void put(Key<T> key, T value)    {
        map.put(key,value);
    }

    public <T> T get(Key<T> key) {
       return (T)map.get(key);
    }

    public static class Key<T> {

    }

}

package ske.fastsetting.skatt.uttrykk.skalSlettes;

import java.util.HashMap;
import java.util.Map;

public class Rec<V> {

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

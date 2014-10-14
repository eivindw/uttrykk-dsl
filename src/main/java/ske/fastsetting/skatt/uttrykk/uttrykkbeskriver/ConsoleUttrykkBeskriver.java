package ske.fastsetting.skatt.uttrykk.uttrykkbeskriver;

import ske.fastsetting.skatt.beregn.UttrykkResultat;
import ske.fastsetting.skatt.beregn.util.IdUtil;
import ske.fastsetting.skatt.uttrykk.UttrykkBeskriver;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ConsoleUttrykkBeskriver implements UttrykkBeskriver<String> {

    public static void print(UttrykkResultat resultat) {
        System.out.println(new ConsoleUttrykkBeskriver().beskriv(resultat));
    }

    @Override
    public String beskriv(UttrykkResultat<?> resultat) {
        StringBuilder out = new StringBuilder();
        print(resultat, out);
        return out.toString();
    }

    private void print(UttrykkResultat<?> resultat, StringBuilder out) {
        print(resultat.start(), resultat, new HashSet<>(), out);
    }

    private void print(String id, UttrykkResultat<?> resultat, Set<String> behandledeIder, StringBuilder out) {
        Map map = resultat.uttrykk().get(id);
        Set<String> subIder = null;
        out.append(IdUtil.idLink(id));

        if (map.containsKey(UttrykkResultat.KEY_NAVN)) {
            out.append(", navn=").append(map.get(UttrykkResultat.KEY_NAVN));
        }

        if (map.containsKey(UttrykkResultat.KEY_VERDI)) {
            out.append(", verdi=").append(map.get(UttrykkResultat.KEY_VERDI));
        }

        if (map.containsKey(UttrykkResultat.KEY_UTTRYKK)) {
            String uttrykk = (String) map.get(UttrykkResultat.KEY_UTTRYKK);
            out.append(", uttrykk=").append(uttrykk);
            subIder = IdUtil.parseIder(uttrykk);
            subIder.removeAll(behandledeIder);
        }

        if(subIder != null) {
            behandledeIder.addAll(subIder);
            subIder.forEach(subId -> print(subId, resultat, behandledeIder, out.append("\n")));
        }
    }
}

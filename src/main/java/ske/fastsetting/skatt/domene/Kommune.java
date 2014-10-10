package ske.fastsetting.skatt.domene;

import java.util.EnumSet;
import java.util.stream.Stream;

public enum Kommune {

    Asker("0220"),
    Bærum("0219"),
    Lørenskog("0230"),
    Halden("0101"),
    Oslo("0301"),
    Stjørdal("1714"),

    // Nord-Troms
    Tromsø("1902"),
    Balsfjord("1933"),
    Karlsøy("1936"),
    Lyngen("1938"),
    Storfjord("1939"),
    Kåfjord("1940"),
    Skjervøy("1941"),
    Nordreisa("1942"),
    Kvænangen("1943"),

    // Finnmark
    Vardø("2002"),
    Vadsø("2003"),
    Hammerfest("2004"),
    Kautokeino("2011"),
    Alta("2012"),
    Loppa("2014"),
    Hasvik("2015"),
    Kvalsund("2017"),
    Måsøy("2018"),
    Nordkapp("2019"),
    Porsanger("2020"),
    Karasjok("2021"),
    Lebesby("2022"),
    Gamvik("2023"),
    Berlevåg("2024"),
    Tana("2025"),
    Nesseby("2027"),
    Båtsfjord("2028"),
    SørVaranger("2030"),
    Ukjent("0000");

    public static EnumSet<Kommune> NordTromsOgFinnmark = EnumSet.of(
        Tromsø,
        Balsfjord,
        Karlsøy,
        Lyngen,
        Storfjord,
        Kåfjord,
        Skjervøy,
        Nordreisa,
        Kvænangen,
        Vardø,
        Vadsø,
        Hammerfest,
        Kautokeino,
        Alta,
        Loppa,
        Hasvik,
        Kvalsund,
        Måsøy,
        Nordkapp,
        Porsanger,
        Karasjok,
        Lebesby,
        Gamvik,
        Berlevåg,
        Tana,
        Nesseby,
        Båtsfjord,
        SørVaranger
    );

    private final String kommunenummer;

    Kommune(String kommunenummer) {
        this.kommunenummer = kommunenummer;
    }

    public String getKommunenummer() {
        return kommunenummer;
    }

    public static Kommune fraKommunenummer(String kommunenummer) {
        return Stream.of(Kommune.values())
            .filter(k -> k.getKommunenummer().equals(kommunenummer))
            .findFirst()
            .orElse(Ukjent);
    }

    public static Kommune fraNavn(String kommuneNavn) {
        try {
            return Kommune.valueOf(kommuneNavn);
        } catch (IllegalArgumentException e) {
            return Ukjent;
        }
    }
}

# Uttrykk-dsl

## Komme i gang

### Enkel skatteberegning
Anta en verden med veldig enkle skattytere og enkel skatteberegning. Hver skattyter kan bare ha følgende skatteobjekter:
* lønnsinntekt
* renteinntekt
* renteutgift
* fagforeningskontingent

`Alminnelig inntekt` beregnes som
```
  lønninntekt
+ renteinntekt
- renteutgift
- fagforeningskontingent
```

Og skatten er 33% av `Alminnelig inntekt`

Anta videre en skattytere med følgende skatteobjekter:
* lønnsinntekt:           kr 78 100
* renteinntekt:              kr 270
* renteutgift:             kr 3 800
* fagforeningskontingent:  kr 3 400

Da kan følgende kode beregne skatten til skattyteren

``` java
import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;
import ske.fastsetting.skatt.uttrykk.tall.TallUttrykk;

import static ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk.kr;
import static ske.fastsetting.skatt.uttrykk.tall.ProsentUttrykk.prosent;

public class DSLTest {

    static final TallUttrykk FELLESSKATT_SATS = prosent(33);

    static final BelopUttrykk lonnsinntekt =          kr(78_100);
    static final BelopUttrykk renteinntekt =             kr(270);
    static final BelopUttrykk renteutgift =            kr(3_800);
    static final BelopUttrykk fagforeningskontingent = kr(3_400);

    static final BelopUttrykk alminneligInntekt = lonnsinntekt
                    .pluss(renteinntekt)
                    .minus(renteutgift)
                    .minus(fagforeningskontingent);

    static final BelopUttrykk fellesskatt =
            alminneligInntekt.multiplisertMed(FELLESSKATT_SATS);

    public static void main(String[] args) {
        SkattyterKontekst kontekst = SkattyterKontekst.ny();
        Belop fellesskattBelop = kontekst.verdiAv(fellesskatt);
        System.out.println(fellesskattBelop);
    }
}
```

Det første å legge merke til er at alle verdier og beregninger representeres som **uttrykk**, som kan være av forskjellige typer. Koden over viser to uttrykkstyper, `TallUttrykk´og `BelopUttrykk`,
som er to `interface` som arver fra `Uttrykk<T>`.

### Uttrykk
Alle `Uttrykk` må implementere to metoder:

``` java
public T eval(UttrykkContext kontekst)
public String beskriv(UttrykkContext)
```

`eval` kalles når verdien av uttrykket skal hentes ut, og `beskriv` returnerer beskrivelsen av uttrykket. Begge metoder tar en `UttrykkContext` som input. Konteksten har flere funksjoner,
men én er å gi parametre til uttrykket. Det kan illustreres med eksempel


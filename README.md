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

public class Skatteberegning {

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

Kjører du `main`-metoden, så bør du få følgende opp i konsollet:
```
kr 5 998
```

Det første å legge merke til er at alle verdier og beregninger er *uttrykk*. Et uttrykk _representerer_ en kalkulasjon,
som så kan kjedes sammen til større _uttrykkstrær_ avhengig av hvilke operasjoner en uttrykkstype støtter. De har altså ingen verdi, og hvis du kjører

```
    public static void main(String[] args) {
        System.out.println(fellesskatt);
    }
```

så vil noe sånt som følgende dukke opp i konsollet.
```
ske.fastsetting.skatt.uttrykk.belop.BelopMultiplikasjonsUttrykk@7106e68e
```


* <Ubrukt>


Koden over viser to uttrykkstyper, `TallUttrykk` og `BelopUttrykk`, som representarer kalkulasjoner med henholdsvis Belop og Tall.

Det er *først* når et uttrykk *evalueres* at verdien kalkuleres. Det skjer i `main`-metoden over:

``` java
SkattyterKontekst kontekst = SkattyterKontekst.ny();
Belop fellesskattBelop = kontekst.verdiAv(fellesskatt);

```

Alle uttrykk må evalueres på denne måten:
``` java
SkattyterKontekst kontekst = SkattyterKontekst.ny();
System.out.println(kontekst.verdiAv(fellesskatt));
System.out.println(kontekst.verdiAv(alminneligInntekt));
System.out.println(kontekst.verdiAv(fagforeningskontingent));

```

Skatteberegningen i eksempelet over bygger opp følgende uttrykkstre (uttrykkstypen i parentes):

```
<fellesskatt> (BelopMultiplikasjonsUttrykk)
|-<alminneligInntekt> (BelopDiffUttrykk)
| |-<anonym1> (BelopDiffUtrykk)
| | |-<anonym2> (BelopSumUttrykk)
| | | |-<lonnsinntekt> (KroneUttrykk) [kr 78 100]
| | | |-<renteinntekt> (KroneUttrykk) [kr 270]
| | |-<renteutgift> kr 3 800 (Kroneuttrykk) [kr 3 800]
| |-<fagforeningskontingent> (KroneUttrykk) [kr 3 400]
|-<FELLESSKATT_SATS> (ProsentUttrykk) [33 %]
```


For eksempel så støtter `BelopUttrykk` operasjonen `pluss`, som tar et annet BelopUttrykk som parameter, og returner et BelopUttrykk:

``` java
BelopUttrykk sum = kr(5).pluss(kr(10))

```

I realiteten, når vi fjerner `static import` og benytter de faktiske klassene som er involvert, så kan uttrykket skrives:
``` java
BelopSumUttrykk sum = KroneUttrykk.kr(5).pluss(KroneUttrykk.kr(10))

```



### Uttrykk
Alle `Uttrykk` må implementere to metoder:

``` java
public T eval(UttrykkContext kontekst)
public String beskriv(UttrykkContext)
```

`eval` kalles når verdien av uttrykket skal hentes ut, og `beskriv` returnerer beskrivelsen av uttrykket. Begge metoder tar en `UttrykkContext` som input. Konteksten har flere funksjoner,
men én er å gi parametre til uttrykket. Det kan illustreres med eksempel


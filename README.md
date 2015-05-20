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

        System.out.println(kontekst.verdiAv(fellesskatt));
        System.out.println(kontekst.verdiAv(alminneligInntekt));
        System.out.println(kontekst.verdiAv(fagforeningskontingent));
    }
}
```

Kjører du `main`-metoden, så bør du få følgende opp i konsollet:
```
kr 5 998
kr 74 970
kr 3 400
```

Det første å legge merke til er at alle verdier og beregninger er *uttrykk*. Et uttrykk _representerer_ en kalkulasjon,
som så kan kjedes sammen til større _uttrykkstrær_ avhengig av hvilke operasjoner en uttrykkstype støtter.

Det er først når et uttrykk *evalueres* at verdien kalkuleres. Det skjer i `main`-metoden over når `verdiAv`-metoden på `SkattyterKontekst`
kalles med et av uttrykkene:

``` java
        ...
        SkattyterKontekst kontekst = SkattyterKontekst.ny();

        System.out.println(kontekst.verdiAv(fellesskatt));
        ...
```

### Nytt uttrykk - en mer realistisk skatteberegning

Eksempelet over virker bare for denne ene skattyteren, og i virkelighetens verden
ønsker vi å hente skattyterenes verdier fra en ekstern datakilde, og kjøre skatteberegningene for alle.

Vi begynner med å anta at vi har et uttrykk som representer verdien for et skatteobjekt angitt med en ID. Altså i stedet for å si
``` java
    ...
    static final BelopUttrykk lonnsinntekt =          kr(78_100);
    static final BelopUttrykk renteinntekt =             kr(270);
    static final BelopUttrykk renteutgift =            kr(3_800);
    static final BelopUttrykk fagforeningskontingent = kr(3_400);
    ...
```
så kan vi si

``` java
    ...
    static final BelopUttrykk lonnsinntekt =           skatteobjekt("lønnsinntekt");
    static final BelopUttrykk renteinntekt =           skatteobjekt("renteinntekt");
    static final BelopUttrykk renteutgift =            skatteobjekt("renteutgift");
    static final BelopUttrykk fagforeningskontingent = skatteobjekt("fagforeningskontingent");
    ...
```

`skatteobjekt(...)` impliserer en `factory method` som returner et BelopUttrykk, så vi lager en klasse som oppfyller det

``` java
public class BelopSkatteobjektUttrykk implements BelopUttrykk {

    public static BelopSkatteobjektUttrykk skatteobjekt(String skatteobjekttype) {
        return new BelopSkatteobjektUttrykk(skatteobjekttype)
    }

    private final String skatteobjekttype;

    private BelopSkatteobjektUttrykk(String skatteobjekttype) {
        this.skatteobjekttype = skatteobjekttype;
    }
}
```

`BelopSkatteobjektUttrykk` er et `BelopUttrykk`, så `skatteobjekt(...)` kan returnere en ny instans av klassen.
Konstruktøren initialiser instansen med skatteobjektstypen som blir sendt inn.

Imidlertid vil ikke dette kompilere fordi `BelopUttrykk` (egentlig `Uttrykk`) krever at følgende metoder blir implementert:
* `V eval(UttrykkContext ctx);`
* `String beskriv(UttrykkContext ctx);`
* `String id();`
* `String navn();`
* `Set<String> tags();`
* `@Deprecated List<Regel> regler();`
* `List<Hjemmel> hjemler();`

Heldigvis finnes det en klasse som gir en god implementasjon av de fleste av disse metodene - `AbstractUttrykk`
- og som i tillegg gir noen andre nyttige egenskaper, som vi skal se senere. De eneste metodene `AbstractUttrykk` ikke implementerer, er:
* `V eval(UttrykkContext ctx);`
* `String beskriv(UttrykkContext ctx);`

Skjelettet av implementasjonen blir da før `eval` og `beskriv` implementeres:
``` java
public class BelopSkatteobjektUttrykk extends AbstractUttrykk<Belop,BelopSkatteobjektUttrykk> implements BelopUttrykk {

    public static BelopSkatteobjektUttrykk skatteobjekt(String skatteobjekttype) {
        return new BelopSkatteobjektUttrykk(skatteobjekttype)
    }

    private final String skatteobjekttype;

    private BelopSkatteobjektUttrykk(String skatteobjekttype) {
        this.skatteobjekttype = skatteobjekttype;
    }

    public Belop eval(UttrykkContext uc) {

    }

    public String beskriv(UttrykkContext uc) {

    }
}
```

Beskrivelsen kan være det vi vil, og her er det naturlig at uttrykket sier noe om at den representer et skatteobjekt av den skatteobjektstypen den er blitt initalisert med
``` java
public class BelopSkatteobjektUttrykk extends AbstractUttrykk<Belop,BelopSkatteobjektUttrykk> implements BelopUttrykk {

    private final String skatteobjekttype;

    ...

    public String beskriv(UttrykkContext uc) {
       return "Skatteobjekt "+skatteobjekttype;
    }
}
```

Da gjenstår `eval`-metoden, som skal gi verdien når uttrykket blir evaluert. Verdien skal vi få fra datakilden vår.

La oss si at vår eksterne datakilde gir oss skattyterdata som et `Skattegrunnlag`,
som vi antar er et enkelt `Map`-aktig interface som lar oss hente ut beløp for angitte skatteobjekt

``` java
Skattegrunnlag skattegrunnlag = ... // hent skattyters data fra ekstern kilde

Belop lonnsinntektSY1 = skattegrunnlag.skatteobjekt("lønnsinntekt")
Belop renteinntektSY1 = skattegrunnlag.skatteobjekt("renteinntekt")

```

Da har vi nesten det vi trenger for å implementere `eval`-metoden også:
``` java
public class BelopSkatteobjektUttrykk extends AbstractUttrykk<Belop,BelopSkatteobjektUttrykk> implements BelopUttrykk {

    private final String skatteobjekttype;

    ...

    public Belop eval(UttrykkContext uc) {
        Skattegrunnlag skattegrunnlag = ... // hent skattyters data fra ekstern kilde
        return skattegrunnlag.skatteobjekt(this.skatteobjekttype)
    }
}
```

`eval`-metoden _kunne_ gått rett mot den eksterne kilden, men det kan være en komplisert affære, og vi ønsker å holde uttrykkene enkle.
I stedet lar vi uttrykket rett og slett forlange at noen har gitt `Skattegrunnlag`'et som en _input-verdi_. `UttrykkContext`'en som sendes inn til `eval`-metoden, gir nettopp tilgang til input-verdier

Da har vi det vi trenger for å implementere `eval`-metoden også:
``` java
public class BelopSkatteobjektUttrykk extends AbstractUttrykk<Belop,BelopSkatteobjektUttrykk> implements BelopUttrykk {

    private final String skatteobjekttype;

    ...

    public Belop eval(UttrykkContext uc) {
        Skattegrunnlag skattegrunnlag = uc.input(Skattegrunnlag.class) // anta at noen har sendt med riktig input
        return skattegrunnlag.skatteobjekt(this.skatteobjekttype)
    }
}
```

Hvis noen mot formodning har glemt å sette et `Skattegrunnlag` som input, så vil evaluering av uttrykket feil med en exception.

En mer forsiktig tilnærming kan være å sjekke om input'en finnes
``` java
public class BelopSkatteobjektUttrykk extends AbstractUttrykk<Belop,BelopSkatteobjektUttrykk> implements BelopUttrykk {

    private final String skatteobjekttype;

    ...

    public Belop eval(UttrykkContext uc) {
        if(ic.harInput(Skattegrunnlag.class))   {
            Skattegrunnlag skattegrunnlag = uc.input(Skattegrunnlag.class) // vi vet vi har input nå
            return skattegrunnlag.skatteobjekt(this.skatteobjekttype)
        } else {
            return Belop.kr0();  // returner en default-verdi
        }
    }
}
```

Vi går likevel for den agressive varianten, og den komplette uttrykksklassen ser da slik ut:
``` java
public class BelopSkatteobjektUttrykk extends AbstractUttrykk<Belop,BelopSkatteobjektUttrykk> implements BelopUttrykk {

    public static BelopSkatteobjektUttrykk skatteobjekt(String skatteobjekttype) {
        return new BelopSkatteobjektUttrykk(skatteobjekttype)
    }

    private final String skatteobjekttype;

    private BelopSkatteobjektUttrykk(String skatteobjekttype) {
        this.skatteobjekttype = skatteobjekttype;
    }

    public Belop eval(UttrykkContext uc) {
        Skattegrunnlag skattegrunnlag = uc.input(Skattegrunnlag.class) // anta at noen har sendt med riktig input
        return skattegrunnlag.skatteobjekt(this.skatteobjekttype)
    }

    public String beskriv(UttrykkContext uc) {
       return "Skatteobjekt "+skatteobjekttype;
    }
}
```

Det eneste som gjenstår er da å få send inn input-verdien, og det gjør vi når vi oppretter `SkattyterKontekst`, som er en implementasjon av UttrykkContext


```
public static void main(String[] args) {

    Skattegrunnlag skattegrunnlag = ... // hent skattyters data fra ekstern kilde

    SkattyterKontekst kontekst = SkattyterKontekst.ny(skattegrunnlag); // opprett SkattyterKontekst med input

    System.out.println(kontekst.verdiAv(fellesskatt));

    ...
}
```

Den komplette beregningskoden ser da slik ut:

``` java
import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;
import ske.fastsetting.skatt.uttrykk.tall.TallUttrykk;

import static ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk.kr;
import static ske.fastsetting.skatt.uttrykk.tall.ProsentUttrykk.prosent;
import static ske.fastsetting.skatt.uttrykk.skattegrunnlag.BelopSkatteobjektUttrykk.skatteobjekt;

public class Skatteberegning {

    static final TallUttrykk FELLESSKATT_SATS = prosent(33);

    static final BelopUttrykk lonnsinntekt =           skatteobjekt("lønnsinntekt");
    static final BelopUttrykk renteinntekt =           skatteobjekt("renteinntekt");
    static final BelopUttrykk renteutgift =            skatteobjekt("renteutgift");
    static final BelopUttrykk fagforeningskontingent = skatteobjekt("fagforeningskontingent");

    static final BelopUttrykk alminneligInntekt = lonnsinntekt
                    .pluss(renteinntekt)
                    .minus(renteutgift)
                    .minus(fagforeningskontingent);

    static final BelopUttrykk fellesskatt =
            alminneligInntekt.multiplisertMed(FELLESSKATT_SATS);

    public static void main(String[] args) {

        Skattegrunnlag skattegrunnlag = ... // hent skattyters data fra ekstern kilde

        SkattyterKontekst kontekst = SkattyterKontekst.ny(skattegrunnlag); // opprett SkattyterKontekst med input

        System.out.println(kontekst.verdiAv(fellesskatt));
        System.out.println(kontekst.verdiAv(alminneligInntekt));
        System.out.println(kontekst.verdiAv(fagforeningskontingent));
    }
}
```



# Ubrukt

Uttrykkene selv har altså ingen verdi, og hvis du kjører

```
    public static void main(String[] args) {
        System.out.println(fellesskatt);
        System.out.println(alminneligInntekt);
        System.out.println(fagforeningskontingent);
    }
```

så vil noe sånt som følgende dukke opp i konsollet.
```
ske.fastsetting.skatt.uttrykk.belop.BelopMultiplikasjonsUttrykk@7530d0a
ske.fastsetting.skatt.uttrykk.belop.BelopDiffUttrykk@27bc2616
ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk@3941a79c
```



Koden over viser to uttrykkstyper, `TallUttrykk` og `BelopUttrykk`, som representarer kalkulasjoner med henholdsvis Belop og Tall.

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


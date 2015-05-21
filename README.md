# Uttrykk-dsl

## Komme i gang

### Enkel skatteberegning med uttrykk
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
| Skatteobjekt | Beløp |
---| ---:
lønnsinntekt |           kr 78 100
renteinntekt |              kr 270
renteutgift |             kr 3 800
fagforeningskontingent|  kr 3 400

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
Koden over viser to uttrykkstyper, `TallUttrykk` og `BelopUttrykk`, som representerer kalkulasjoner med henholdsvis Belop og Tall.

Det er først når et uttrykk *evalueres* at verdien kalkuleres. Det skjer i `main`-metoden over når `verdiAv`-metoden på `SkattyterKontekst`
kalles med et av uttrykkene:

``` java
...
SkattyterKontekst kontekst = SkattyterKontekst.ny();

System.out.println(kontekst.verdiAv(fellesskatt));
...
```

## Nytt uttrykk - en mer realistisk skatteberegning

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
        // hent skattyters data fra ekstern kilde
        Skattegrunnlag skattegrunnlag = ...
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
        // anta at noen har sendt med riktig input - feile hardt hvis ikke
        Skattegrunnlag skattegrunnlag = uc.input(Skattegrunnlag.class)
        return skattegrunnlag.skatteobjekt(this.skatteobjekttype)
    }
}
```

Hvis noen mot formodning har glemt å sette et `Skattegrunnlag` som input, så vil evaluering av uttrykket feile med en exception.

En mer forsiktig tilnærming kan være å sjekke om input'en finnes
``` java
public class BelopSkatteobjektUttrykk extends AbstractUttrykk<Belop,BelopSkatteobjektUttrykk> implements BelopUttrykk {

    private final String skatteobjekttype;

    ...

    public Belop eval(UttrykkContext uc) {
        if(uc.harInput(Skattegrunnlag.class))   {
            // vi vet vi har input nå
            Skattegrunnlag skattegrunnlag = uc.input(Skattegrunnlag.class)
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
        Skattegrunnlag skattegrunnlag = uc.input(Skattegrunnlag.class)
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

    // hent skattyters data fra ekstern kilde
    Skattegrunnlag skattegrunnlag = ...

    // opprett SkattyterKontekst med input
    SkattyterKontekst kontekst = SkattyterKontekst.ny(skattegrunnlag);

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

        // hent skattyters data fra ekstern kilde
        Skattegrunnlag skattegrunnlag = ...

        / opprett SkattyterKontekst med input
        SkattyterKontekst kontekst = SkattyterKontekst.ny(skattegrunnlag);

        System.out.println(kontekst.verdiAv(fellesskatt));
        System.out.println(kontekst.verdiAv(alminneligInntekt));
        System.out.println(kontekst.verdiAv(fagforeningskontingent));
    }
}
```
### Oppsummering - Nytt uttrykk
1. Opprett en klasse som arver fra `AbstractUttrykk` og implementer aktuelt interface
2. Implementer metodene `eval` og `beskriv`
3. Legg gjerne til en (eller flere) _factory methods_ som lar utvikleren lag en instans av klassen på en kompakt måte ved hjelp av _static imports_


## Testing med stub'ing

La oss nå skille forretningslogikken fra koden som setter opp og kjører logikken. Først flytter vi `main`-metoden til en egen klasse.

``` java
public class App {

    public static void main(String[] args) {

        // hent skattyters data fra ekstern kilde
        Skattegrunnlag skattegrunnlag = ...

        // opprett SkattyterKontekst med input
        SkattyterKontekst kontekst = SkattyterKontekst.ny(skattegrunnlag);

        System.out.println(kontekst.verdiAv(fellesskatt));
        System.out.println(kontekst.verdiAv(alminneligInntekt));
        System.out.println(kontekst.verdiAv(fagforeningskontingent));
    }
}
```

Forretningslogikken fordeler vi på to klasser, `Grunnlag` og `Skatteberegning`.

``` java
public class Grunnlag {
    static final BelopUttrykk lonnsinntekt =           skatteobjekt("lønnsinntekt");
    static final BelopUttrykk renteinntekt =           skatteobjekt("renteinntekt");
    static final BelopUttrykk renteutgift =            skatteobjekt("renteutgift");
    static final BelopUttrykk fagforeningskontingent = skatteobjekt("fagforeningskontingent");
}

public class Skatteberegning {

    static final TallUttrykk FELLESSKATT_SATS = prosent(33);

    static final BelopUttrykk alminneligInntekt = lonnsinntekt
                    .pluss(renteinntekt)
                    .minus(renteutgift)
                    .minus(fagforeningskontingent);

    static final BelopUttrykk fellesskatt =
            alminneligInntekt.multiplisertMed(FELLESSKATT_SATS);
}
```

Nå ønsker vi å teste fellesskatt-uttrykket i `Skatteberegning` og oppretter en testklasse

``` java
public class SkatteberegningTest {

    @Test
    public void testFellesskatt() {
        // Fyll på med testkode
    }
}
```

Utfordringen vi har nå er at `fellesskatt` er et uttrykk på toppen av et uttrykkstre. I vårt tilfelle er det ikke et veldig komplisert tre,
men det er uansett en utfordring at løvnodene i treet er `BelopSkatteobjektUttrykk`, som er avhengig av en input.
En metode som vil fungere er å lage input'en i testen, og hvis vi oppretter en klasse som implementerer `Skattegrunnlag`, så kan vi få til det.

``` java
public class SkatteberegningTest {

    @Test
    public void testFellesskatt() {
        TestSkattegrunnlag skattegrunnlag = new TestSkattegrunnlag();
        skattegrunnlag.leggTil("lønnsinntekt",Belop.kr(100));
        skattegrunnlag.leggTil("renteinntekt",Belop.kr(50));

        // opprett SkattyterKontekst med input
        SkattyterKontekst kontekst = SkattyterKontekst.ny(skattegrunnlag);

        assertEquals(Belop.kr(50),kontekst.verdiAv(fellesskatt).rundAvTilHeleKroner());
    }
}
```

Her implementerer `TestSkattegrunnlag` _inteface_'t `Skattegrunnlag` og tilbyr metoden `leggTil` for å sette verdier på skatteobjektene.
Legg merke til at vi i `assertEquals` runder av beløpet vi får fra beregningen, siden det ikke nødvendigvis er nøyaktig hele kroner (det er kr 49,50).
Denne fremgangsmåten er omstendig, men kanskje til å leve med i dette tilfellet, og vi kan se for oss å gjenbruke `TestSkattegrunnlag` i mange tilfeller.
Imidlertid var det vi ønsket å teste i dette tilfellet at _fellesskatt_ ble beregnet riktig, og den avhenger kun av _alminneligInntekt_ og skattesatsen på 33%.
Når uttrykkstreet vokser i kompleksitet og dybde, så blir det stadig vanskeligere å gi input som sørger for at vi kan teste interessante kombinasjoner av uttrykk nær toppen.
Heldigvis finnes det annen tilnærmingen som lar oss _kortslutte_ uttrykkstreet der vi ønsker.

``` java
public class SkatteberegningTest {

    @Test
    public void testFellesskatt() {
        // opprett SkattyterKontekst UTEN input
        SkattyterKontekst kontekst = SkattyterKontekst.ny();
        kontekst.overstyrVerdi(Skatteberegning.alminneligInntekt, Belop.kr(150))

        assertEquals(Belop.kr(50),kontekst.verdiAv(fellesskatt).rundAvTilHeleKroner());
    }
}
```

`overstyrVerdi` lar oss sette verdien som skal benyttes under evalueringen av et gitt uttrykk. Verdien *bør* overstyres før noe uttrykk evalueres fordi verdien
 av uttrykkene _caches_ på konteksten etter hvert som de blir evaluert. Merk at vi ikke lenger trenger å sende input på kontektsten
fordi alle uttrykk som benytter input, ikke lenger blir evaluert.

## Flere uttrykk
Vi kan legge til en ny test, som eksponerer en feil i koden vår

``` java
@Test
public void testNegativAlimnneligInntekt() {
    SkattyterKontekst kontekst = SkattyterKontekst.ny();

    // Negativ alminnelig inntekt ...
    kontekst.overstyrVerdi(Skatteberegning.alminneligInntekt, Belop.kr(-100))

    // ... bør gi 0 i skatt
    assertEquals(Belop.kr0(),kontekst.verdiAv(fellesskatt));
}
```

Testen over feiler fordi skatten beregnes til kr -33, som er en åpenbar feil fordi skatten ikke kan være negativ. Feilen ligger i beregningen av fellesskatt.

``` java
static final BelopUttrykk fellesskatt =
        alminneligInntekt.multiplisertMed(FELLESSKATT_SATS);
```

Her burde det vært en regel som sa at fellesskatten ikke kan være negativ. Det er flere måter å gjøre det med uttrykk. Første måte er med hvis-uttrykk

``` java
static final BelopUttrykk fellesskatt =
        hvis(alminneligInntekt.erStorreEnn(kr0()))
        .brukDa(alminneligInntekt.multiplisertMed(FELLESSKATT_SATS))
        .ellersBruk(kr0());
```
En annen måte er med begrens-uttrykk

``` java
static final BelopUttrykk fellesskatt =
        begrens(
            alminneligInntekt.multiplisertMed(FELLESSKATT_SATS))
        .nedad(kr0());
```
Å begrense nedad til kr 0 er så vanlig at det finnes en kortform for det - `nedre0(...)`
``` java
static final BelopUttrykk fellesskatt =
        nedre0(alminneligInntekt.multiplisertMed(FELLESSKATT_SATS));
```

### Uttrykk - beløp

Type | Eksempel  | Kommentar
--- |--- | ---
pluss        | `kr(5).pluss(kr(8))` |
sum          | `sum(kr(3),kr(4),kr(5))` |
minus        | `kr(6).minus(kr(3))` |
bytt fortegn | `kr(6).byttFortegn()` |
rund av      | `kr(4.75).rundAvTilHeleKroner()` |



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


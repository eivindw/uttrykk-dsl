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
  lønnsinntekt
+ renteinntekt
- renteutgift
- fagforeningskontingent
```

Og skatten er 33% av `Alminnelig inntekt`

Anta videre en skattyter med følgende skatteobjekter:

_Skatteobjekt_|_Beløp_
---| ----------:
lønnsinntekt |           kr 78_100
renteinntekt |              kr 270
renteutgift |             kr 3_800
fagforeningskontingent|  kr 3_400

Gitt at følgende avhengighet finnes i `pom.xml` (bytt til siste/aktuelle versjon)

``` xml
<dependencies>
    <dependency>
        <groupId>ske.fastsetting.skatt</groupId>
        <artifactId>uttrykk-kjerne</artifactId>
        <version>1.25</version>
    </dependency>
</dependencies>
```

Da kan følgende kode beregne skatten til skattyteren:

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

Heldigvis finnes det en klasse som gir en god implementasjon av de fleste av disse metodene - `AbstractUttrykk` -
og som i tillegg gir noen andre nyttige egenskaper, som vi skal se senere. `AbstractUttrykk` har to typeparametre. Den første
angir hvilken type som eval skal returnere. Her er det `Belop` siden evaluering av `BelopUttrykk` gir `Belop`. Den andre typeparameteren
er klassen selv. De eneste metodene `AbstractUttrykk` ikke implementerer, er:

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

En mer forsiktig tilnærming kan være å sjekke om input'en finnes:

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


``` java
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

## Flere uttrykk for beløp

### Begrensning
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

### Multisats

Vi forestiller oss at lovgiverne har bestemt at det skal innføres toppskatt. Den har to nivåer:

* ingen toppskatt på den delen av alminnelig inntekt som er under kr 550 000
* 5% på den delen av alminnelig inntekt som er over kr 550 000 og under kr 875 000
* 8% på den delen av alminnelig inntekt som er over kr 875 000

Dette finnes et eget uttrykk for å håndtere disse tilfellene.

``` java
static final BelopUttrykk toppskatt =
        multisatsFunksjonAv(alminneligInntekt)
            .medSats(prosent(0),kr(550_000))
            .medSats(prosent(5),kr(875_000))
            .medSats(prosent(8));
```

### Overføring mellom ektefeller

Lovgiverne innfører boligsparing for ungdom (BSU). Skattyteren kan spare inntil kr 25 000 til boligformål og få inntil 20% av beløpet i skattefradraget, altså maks kr 5 000.
Loven sier at hvis en av ektefellene ikke får utnyttet hele fradraget, så kan den ubenyttede delen overføres til ektefellen.
Skattyteren får ikke utnyttet fradraget hvis maks fradrag er større enn utliknet skatt.

Vi antar at det finnes et skatteobjekt i skattegrunnlaget, _sparebeløpBSU_, som inneholder sparebeløpet. Maks BSU-fradrag er gitt ved:

``` java
static final BelopUttrykk maksSparebelopBSU =
    begrens(skatteobjekt("sparebeløpBSU"))
    .oppad(kr(25_000))

static final BelopUttrykk maksFradragBSU =
    maksSparebelopBSU.multiplisertMed(prosent(20));
```

Fradraget kan ikke være større enn utliknet skatt:

``` java
static final BelopUttrykk utliknetSkatt =
    fellesskatt
    .pluss(toppskatt)

static final BelopUttrykk begrensetFradragBSU =
    begrens(maksfradrag).oppad(utliknetSkatt);
```

Vi lager et uttrykk som angir hva som evt skal overføres til ektefelle

``` java
static final BelopUttrykk fradragBSUTilOverforing =
    begrens(
        maksfradrag.minus(utliknetSkatt)
    ).nedad(kr0);
```

Og da har vi det vi trenger for å beregne det endelige fradraget

``` java
static final BelopUttrykk fradragBSU =
    begrens(
        begrensetFradragBSU
        .pluss(ektefelles(fradragBSUTilOverforing))
    ).oppad(utliknetSkatt);
```

Når vi skal beregne BSU-fradraget til en skattyter, så må vi også sende med informasjon om ektefellen.

``` java
public class App {

    public static void main(String[] args) {

        // hent skattyters og ektefelles data fra ekstern kilde
        Skattegrunnlag skattegrunnlag = ...
        Skattegrunnlag skattegrunnlagEktefelle =

        // opprett SkattyterKontekst med input
        SkattyterKontekst kontekst = SkattyterKontekst
            .ny(skattegrunnlag)
            .medEktefelle(skattgunnlagEktefelle)

        System.out.println(kontekst.verdiAv(fradragBSU));
    }
}
```

Eller vi kan gjøre det i en test:

``` java
public class FradragBSUTest {

    @Test
    public void testFellesskatt() {
        SkattyterKontekst kontekst = SkattyterKontekst
            .ny()
            .medEktefelle();

        kontekst.overstyrVerdi(alminneligInntekt, Belop.kr(500_000))
        kontekst.overstyrVerdi(maksSparebelopBSU, Belop.kr(25_000))

        kontekst.overstyrVerdiEktefelle(alminneligInntekt, Belop.kr(0))
        kontekst.overstyrVerdiEktefelle(maksSparebelopBSU, Belop.kr(25_000))

        assertEquals(Belop.kr(10_000),kontekst.verdiAv(fradragBSU));
    }
}
```

**GOTCHA** Merk at vi overstyrer `maksSparebelopBSU` og ikke `skatteobjekt("sparebeløpBSU")`. Det skyldes at `skatteobjekt(...)` returnerer
en ny instans av `BelopSkatteobjektUttrykk` hver gang metoden kalles, og konteksten cacher hver instans. Dermed ville

``` java
kontekst.overstyrVerdi(skatteobjekt("sparebeløpBSU"), Belop.kr(30_000))
```


overstyrt en annen verdi enn den som brukes i

``` java
static final BelopUttrykk maksSparebelopBSU =
    begrens(skatteobjekt("sparebeløpBSU"))
    .oppad(kr(25_000))
```

Det kan også løses ved å sikre at at kall til `skatteobjekt(...)` returner samme instans for en id:

``` java
public class BelopSkatteobjektUttrykk extends AbstractUttrykk<Belop,BelopSkatteobjektUttrykk> implements BelopUttrykk {


    // Bruk ConcurrentHashMap for å være trådsikker i alle tilfellers skyld
    private static final Map<String,BelopUttrykk> skatteobjekter = new ConcurrentHashMap<>();

    // Vi returner BelopUttrykk, som er immutable, i stedet for BelopSkatteobjektUttrykk
    public static BelopUttrykk skatteobjekt(String skatteobjekttype) {
        return skatteobjekter.computeIfAbsent(skatteobjekttype,sot->new BelopSkatteobjektUttrykk(skatteobjekttype));
    }

    ...
}
```

Da kan testen bli mer intuitiv:

``` java
public class FradragBSUTest {

    @Test
    public void testFellesskatt() {
        SkattyterKontekst kontekst = SkattyterKontekst
            .ny()
            .medEktefelle();

        kontekst.overstyrVerdi(alminneligInntekt, Belop.kr(500_000))
        kontekst.overstyrVerdi(skatteobjekt("sparebeløpBSU"), Belop.kr(25_000))

        kontekst.overstyrVerdiEktefelle(alminneligInntekt, Belop.kr(0))
        kontekst.overstyrVerdiEktefelle(skatteobjekt("sparebeløpBSU"), Belop.kr(25_000))

        assertEquals(Belop.kr(10_000),kontekst.verdiAv(fradragBSU));
    }
}
```

## Andre typer uttrykk

### Stedbunde beløp

Den enkle staten vår blir stadig mer kompleks. Politikerne bestemmer at det skal innføres kommuneskatt.
Lønnsinntekt og renteinntekter tilfaller alltid hjemstedskommunen, mens skatten på næringsinntekt tilfaller kommunen der den oppstår.
Fordelingsfradrag som renteutgifter og fagforeningskontingent fordeles på kommune forholdsmessig etter inntekten.
Satsen for kummeneskatt settes til 8% og fellesskatt-satsen reduseres tilsvarende til 25%.

Vi ser for oss en skattyter bosatt i Asker, som driver med jordbruk i Tønsberg. Hun har følgende skatteobjekter:

_Skatteobjekt_|_Beløp_ | _Sted_
---| ---: | :---
lønnsinntekt | kr 78 100 | (Hjemsted)
renteinntekt | kr 270 | (Hjemsted)
næringsinntekt | kr 56 000 | Tønsberg
renteutgift |  kr 3 800 | (Fordeles)
fagforeningskontingent|  kr 3 400 | (Fordeles)

Da kan følgende kode beregne skatten

``` java
import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;
import ske.fastsetting.skatt.uttrykk.stedbundetBelop.StedbundetBelopUttrykk;
import ske.fastsetting.skatt.uttrykk.tall.TallUttrykk;

import static ske.fastsetting.skatt.uttrykk.stedbundetBelop.StedbundetKroneUttrykk.kr;
import static ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk.kr;
import static ske.fastsetting.skatt.uttrykk.tall.ProsentUttrykk.prosent;

public class Skatteberegning {

    static final TallUttrykk FELLESSKATT_SATS = prosent(25);
    static final TallUttrykk KOMMUNESKATT_SATS = prosent(8);

    static final StedbundetBelopUttrykk<String> lonnsinntekt = kr(78_100, "Asker");
    static final StedbundetBelopUttrykk<String> renteinntekt = kr(270, "Asker");
    static final StedbundetBelopUttrykk<String> naeringsinntekt = kr(56_000, "Tønsberg");

    static final BelopUttrykk renteutgift = kr(3_800);
    static final BelopUttrykk fagforeningskontingent = kr(3_400);

    static final StedbundetBelopUttrykk<String> inntekt =
            lonnsinntekt
            .pluss(renteinntekt)
            .pluss(naeringsinntekt);

    static final BelopUttrykk fordelingsfradrag =
            renteutgift
            .pluss(fagforeningskontingent);

    static final StedbundetBelopUttrykk<String> alminneligInntekt =
            inntekt.minusProporsjonalt(fordelingsfradrag);

    static final BelopUttrykk fellesskatt =
            alminneligInntekt.steduavhengig().multiplisertMed(FELLESSKATT_SATS);

    static final StedbundetBelopUttrykk<String> kommuneskatt =
            alminneligInntekt.multiplisertMed(KOMMUNESKATT_SATS);

    public static void main(String[] args) {

        SkattyterKontekst kontekst = SkattyterKontekst.ny();

        System.out.println(kontekst.verdiAv(fellesskatt));
        System.out.println(kontekst.verdiAv(kommuneskatt));
        System.out.println(kontekst.verdiAv(kommuneskatt).get("Asker"));
    }
}
```

Kjører du `main`-metoden, så bør du få følgende opp i konsollet:

```
kr 31 793
{Tønsberg=kr 4 240, Asker=kr 5 934}
kr 5 934
```

Kode-eksempelet viser at du kan konvertere et `StedbundetBelopUttrykk` til et `BelopUttrykk` ved å kalle `steduavhengig()` på uttrykket.

Den forholdsmessige fordelingen av fordelingsfradrag mellom kommuner skjer i kallet

``` java
    static final StedbundetBelopUttrykk<String> alminneligInntekt =
            inntekt.minusProporsjonalt(fordelingsfradrag);
```


### Tekstuttrykk

Politikerne innfører forskuddstrekk. Foreløpig er det snakk om to forskuddsformer - Frikort og Prosentkort. Frikort får du hvis alminnelig inntekt er under kr 30 000

``` java
static final TekstUttrykk FORSKUDSSFORM_PROSENTKORT = tekst("Prosentkort");
static final TekstUttrykk FORSKUDSSFORM_FRIKORT = tekst("Frikort");

static final TekstUttrykk forskuddsform =
    hvis(alminneligInntekt.steduavhengig().erMindreEnn(kr(30_000)))
    .brukDa(FORSKUDSSFORM_FRIKORT)
    .ellersBruk(FORSKUDSSFORM_PROSENTKORT)
```

### Bolske uttrykk

Hvis skattyter har fått prosentkort, så skal trekkprosenten være lik utliknet skatt dividert med alminnelig inntekt (i prosent), men aldri mindre enn 5%.
Ellers blir trekkprosenten 0%.

``` java
static final TallUttrykk ujusterTrekkprosent = utliknetSkatt.dividertMedTilProsent(alminneligInntekt);

static final BolskUttrykk harProsentkort = forskuddsform.erLik(FORSKUDSSFORM_PROSENTKORT);
static final BolskUttrykk erUnder5Prosent = ujustertTrekkprosent.erMindreEnn(prosent(5));

static final TallUttrykk trekkprosent =
    hvis(harProsentkort.og(erUnder5Prosent))).brukDa(prosent(5))
    .ellersHvis(harProsentkort).brukDa(ujustertTrekkprosent)
    .ellersBruk(prosent(0))
```

### Distanseuttrykk

For å komme pendlerne i møte, så innføres det et reisefradrag ifm reise til og fra jobb. Det gis kr 1,50/km for reise
under 50 000 km, og deretter 0,70 kr/km opp til 75 000 km. Sakttyter trekkes en egenandel på kr 16 000, og fradraget er
begrenset oppad til kr 92 500 før egenandelen blir trukket.

Hvis vi antar at en skattyter reiser 56 000 km pr år, så kan reisefradraget beregnes slik:

```java

DistanseUttrykk reiseKm = km(56_000);

final BelopPerKvantitetUttrykk<Distanse> SATS_REISE_HOY = kr(1.50).per(km());
final BelopPerKvantitetUttrykk<Distanse> SATS_REISE_LAV = kr(0.70).per(km());

final BelopUttrykk MAKS_REISEUTGIFTER = kr(92_500);
final BelopUttrykk EGENANDEL_REISEUTGIFTER = kr(16_000);

final DistanseUttrykk OEVRE_GRENSE_SATS_REISE_HØY_KM = km(50_000);
final DistanseUttrykk OEVRE_GRENSE_SATS_REISE_LAV_KM = km(75_000);

BelopUttrykk bruttoReise = multisats(reiseKm)
  .medSats(SATS_REISE_HOY, OEVRE_GRENSE_SATS_REISE_HØY_KM)
  .medSats(SATS_REISE_LAV, OEVRE_GRENSE_SATS_REISE_LAV_KM);

BelopUttrykk begrensetBrutto = begrens(bruttoReise).oppad(MAKS_REISEUTGIFTER);

BelopUttrykk reisefradrag = begrens(begrensetBrutto.minus(EGENANDEL_REISEUTGIFTER)).nedad(kr(0));

```

## Dokumentasjon

Det kan genereres dokumentasjon fra ett eller flere uttrykkstrær. I tillegg til `beskriv`-metoden, som har ansvar for å beskrive uttrykket,
så kan også et uttrykk ha _navn_ og _tags_. Uttrykk som arver fra `AbstractUttrykk` har metoder for å sette navn og tags:

```java
BelopUttrykk inntektUttrykk = kr(45_000).navn("lønnsinntekt").tags("beregningsgrunnlag");
```

### Dokumentasjon til konsollet

```java
SkattyterKontekst kontekst = SkattyterKontekst.ny();

UttrykkResultat<Belop> resultat = kontekst.dokumenter(inntektUttrykk)

ConsoleUttrykkBeskriver.print(resultat);

```

### Wiki-dokumentasjon (Confluence)

```java
SkattyterKontekst kontekst = SkattyterKontekst.ny();

UttrykkResultat<Belop> resultat = kontekst.dokumenter(inntektUttrykk)

// Bør konvertere resultatet slik at det blir tilpasset wiki
UttrykkResultat<Belop> confluenceResultat = ConfluenceResultatKonverterer.konverterResultat(resultat);

ConfluenceUttrykkBeskriver beskriver = new ConfluenceUttrykkBeskriver("Hovedside");
final Map<String, ConfluenceUttrykkBeskriver.ConfluenceSide> sider = beskriver.beskriv(confluenceResultat);

sider.forEach((tittel, side) -> {
    System.out.println("### " + tittel + " ###");
    System.out.println(side);
});
```

### Excel-dokumentasjon

```java
SkattyterKontekst kontekst = SkattyterKontekst.ny();

UttrykkResultat<Belop> resultat = kontekst.dokumenter(inntektUttrykk)

// Bør konvertere resultatet slik at det blir tilpasset excel
UttrykkResultat<Belop> excelResultat = ExcelEnsligUttrykkResultatKonverterer.konverterResultat(resultat)

// Bruk evt ektefellekonverter
// ExcelEktefelleUttrykkResultatKonverterer ektefellekonverterer = new ExcelEktefelleUttrykkResultatKonverterer();
// UttrykkResultat<Belop> excelResultat = ektefellekonverter.konverterResultat(resultat)

ExcelUttrykkBeskriver beskriver = new ExcelUttrykkBeskriver();

final Workbook wb = beskriver.beskriv(excelResultat);

FileOutputStream out = new FileOutputStream("workbook.xlsx");
wb.write(out);
out.close();
```

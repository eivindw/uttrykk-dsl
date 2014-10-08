package ske.fastsetting.skatt.old.uttrykk;

import ske.fastsetting.skatt.old.domene.Regel;

public interface UttrykkBeskriver {
    UttrykkBeskriver overskrift(String overskrift);
    void skriv(String line);
    UttrykkBeskriver rykkInn();

    void tags(String... tags);
    void regler(Regel... regler);
}

/*
regel:alminnelig inntekt
verdi: kr 56000
grunnlag: sum av liste:
    regel:post2,1,1
    verdi:kr 13000
    regel:post2,1,2
    verdi:kr 45223
    grunnlag: multplikasjon av:
        regel: 2.1.6
        verdi: kr 4334


fordi
    alder er mellom 17 og 67
så brukes
    kr 56655 ("regel"), som er sum av:
        "kr 45",
        "kr 56"


{ grunnlag: [
 {"tekst": "kr 45000 (alminnelig inntekt), fordi:"}
 {"tekst": "alder til skattyter er større enn 17"}
 {"tekst": "så brukes"}
 {
 ]
 }



{ "regel":"alminnelig inntekt",
  "verdi":"kr 45 000",
  "regler":[{regel:"2.4.1",verdi:"kr
}


 */

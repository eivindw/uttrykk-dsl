package ske.fastsetting.skatt.uttrykk.uttrykkbeskriver;

import ske.fastsetting.skatt.uttrykk.UttrykkResultat;

public interface UttrykkBeskriver<T> {
    T beskriv(UttrykkResultat<?> resultat);
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

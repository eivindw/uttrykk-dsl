package ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.excel;

import org.apache.poi.ss.usermodel.Cell;

public interface ExcelUttrykk {
    void skrivTilCelle(Cell celle);
    String tilTekst();
}

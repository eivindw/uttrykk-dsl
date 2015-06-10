package ske.fastsetting.skatt.uttrykk;

public class UttrykkException extends RuntimeException {

    private final String callChain;
    private final Throwable originalException;

    public UttrykkException(Throwable e, Uttrykk<?> uttrykk) {
        super(uttrykk.navn()!=null ? uttrykk.navn(): "()",e);
        callChain = uttrykk.navn()!=null ? "'"+uttrykk.navn()+"'": "''";
        originalException = e;
    }

    public UttrykkException(UttrykkException ue, Uttrykk<?> uttrykk) {
        super((uttrykk.navn()!=null ? "'"+uttrykk.navn()+"'" : "''")+"->"+ ue.callChain,ue.originalException);

        callChain = (uttrykk.navn()!=null ? "'"+uttrykk.navn()+"'" : "''")+"->"+ ue.callChain;
        originalException = ue.originalException;
    }

}

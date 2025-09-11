public class ResultadoOrdenacao {
    private String casoAtual;
    private long tempoExecucao; // Em nanossegundos

    public ResultadoOrdenacao(String casoAtual, long tempoExecucao) {
        this.casoAtual = casoAtual;
        this.tempoExecucao = tempoExecucao;
    }

    public String getCasoAtual() {
        return casoAtual;
    }

    public long getTempoExecucao() {
        return tempoExecucao;
    }
}

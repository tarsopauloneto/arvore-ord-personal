public class RelatorioSorts {
    private String casoAtual;
    private long tempoExecucao;
    private int comparacoes;
    private int trocas;

    // Getters
    public String getCasoAtual() {
        return casoAtual;
    }

    public long getTempoExecucao() {
        return tempoExecucao;
    }

    public int getComparacoes() {
        return comparacoes;
    }

    public int getTrocas() {
        return trocas;
    }

    // Resetar métricas
    private void resetarMetricas() {
        tempoExecucao = 0;
        comparacoes = 0;
        trocas = 0;
        casoAtual = "";
    }

    // Método auxiliar para identificar o caso atual do vetor
    private static String classificaCasoAtual(int[] vetor) {
        boolean crescente = true;
        boolean decrescente = true;

        for (int i = 0; i < vetor.length - 1; i++) {
            if (vetor[i] > vetor[i + 1])
                crescente = false;
            if (vetor[i] < vetor[i + 1])
                decrescente = false;
        }

        if (crescente)
            return "Melhor caso";
        if (decrescente)
            return "Pior caso";
        return "Caso médio";
    }

    // -------------------------
    // Insertion Sort
    // -------------------------
    public void insertionSort(int[] arr) {
        resetarMetricas();
        casoAtual = classificaCasoAtual(arr);
        long inicio = System.nanoTime();

        for (int i = 1; i < arr.length; i++) {
            int chave = arr[i];
            int j = i - 1;

            while (j >= 0) {
                comparacoes++;
                if (arr[j] > chave) {
                    arr[j + 1] = arr[j];
                    trocas++;
                    j--;
                } else {
                    break;
                }
            }
            arr[j + 1] = chave;
        }

        long fim = System.nanoTime();
        tempoExecucao = fim - inicio;
    }

    // -------------------------
    // Bubble Sort
    // -------------------------
    public void bubbleSort(int[] arr) {
        resetarMetricas();
        casoAtual = classificaCasoAtual(arr);
        long inicio = System.nanoTime();

        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                comparacoes++;
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    trocas++;
                }
            }
        }

        long fim = System.nanoTime();
        tempoExecucao = fim - inicio;
    }

    // -------------------------
    // Selection Sort
    // -------------------------
    public void selectionSort(int[] arr) {
        resetarMetricas();
        casoAtual = classificaCasoAtual(arr);
        long inicio = System.nanoTime();

        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                comparacoes++;
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                int temp = arr[minIndex];
                arr[minIndex] = arr[i];
                arr[i] = temp;
                trocas++;
            }
        }

        long fim = System.nanoTime();
        tempoExecucao = fim - inicio;
    }
}

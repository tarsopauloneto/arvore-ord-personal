public class Sorts {

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

    // ---------------- Ordenações ----------------

    public static void bubbleSort(int[] vetor) {
        for (int i = 0; i < vetor.length - 1; i++) {
            for (int j = 0; j < vetor.length - i - 1; j++) {
                if (vetor[j] > vetor[j + 1]) {
                    int temp = vetor[j];
                    vetor[j] = vetor[j + 1];
                    vetor[j + 1] = temp;
                }
            }
        }
    }

    public static void insertionSort(int[] vetor) {
        for (int i = 1; i < vetor.length; i++) {
            int chave = vetor[i];
            int j = i - 1;

            while (j >= 0 && vetor[j] > chave) {
                vetor[j + 1] = vetor[j];
                j--;
            }
            vetor[j + 1] = chave;
        }
    }

    public static void selectionSort(int[] vetor) {
        for (int i = 0; i < vetor.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < vetor.length; j++) {
                if (vetor[j] < vetor[minIndex]) {
                    minIndex = j;
                }
            }
            int temp = vetor[minIndex];
            vetor[minIndex] = vetor[i];
            vetor[i] = temp;
        }
    }

    // ---------------- Métodos que retornam ResultadoOrdenacao ----------------

    public static ResultadoOrdenacao getCasoAtualTempoBubble(int[] vetorOriginal) {
        int[] vetor = vetorOriginal.clone();
        String casoAtual = classificaCasoAtual(vetor);

        long inicio = System.nanoTime();
        bubbleSort(vetor);
        long fim = System.nanoTime();

        return new ResultadoOrdenacao(casoAtual, fim - inicio);
    }

    public static ResultadoOrdenacao getCasoAtualTempoInsertion(int[] vetorOriginal) {
        int[] vetor = vetorOriginal.clone();
        String casoAtual = classificaCasoAtual(vetor);

        long inicio = System.nanoTime();
        insertionSort(vetor);
        long fim = System.nanoTime();

        return new ResultadoOrdenacao(casoAtual, fim - inicio);
    }

    public static ResultadoOrdenacao getCasoAtualTempoSelection(int[] vetorOriginal) {
        int[] vetor = vetorOriginal.clone();
        String casoAtual = classificaCasoAtual(vetor);

        long inicio = System.nanoTime();
        selectionSort(vetor);
        long fim = System.nanoTime();

        return new ResultadoOrdenacao(casoAtual, fim - inicio);
    }
}

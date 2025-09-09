public class Ordenacao {
    public static long insertionSort(int[] valores) {
        long start = System.nanoTime();
        for (int i = 1; i < valores.length; i++) {
            int aux = valores[i];
            int j = i - 1;
            while (j >= 0 && valores[j] > aux) {
                valores[j + 1] = valores[j];
                j--;
            }
            valores[j + 1] = aux;
        }
        return System.nanoTime() - start;
    }

    public static long selectionSort(int[] valores) {
        long start = System.nanoTime();
        for (int i = 0; i < valores.length - 1; i++) {
            int menor = i;
            for (int j = i + 1; j < valores.length; j++) {
                if (valores[j] < valores[menor])
                    menor = j;
            }
            int aux = valores[menor];
            valores[menor] = valores[i];
            valores[i] = aux;
        }
        return System.nanoTime() - start;
    }

    public static long bubbleSort(int[] valores) {
        long start = System.nanoTime();
        boolean trocou;
        for (int i = 0; i < valores.length - 1; i++) {
            trocou = false;
            for (int j = 0; j < valores.length - 1 - i; j++) {
                if (valores[j] > valores[j + 1]) {
                    int aux = valores[j];
                    valores[j] = valores[j + 1];
                    valores[j + 1] = aux;
                    trocou = true;
                }
            }
            if (!trocou)
                break;
        }
        return System.nanoTime() - start;
    }
}

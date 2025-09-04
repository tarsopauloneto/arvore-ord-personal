public class Ordenacao {

    // Método auxiliar para imprimir o array
    private static void printArray(int[] valores) {
        for (int valor : valores) {
            System.out.print(valor + " ");
        }
        System.out.println();
    }

    // Insertion Sort
    public static void insertionSort(int[] valores) {
        System.out.println("Insertion Sort:");
        for (int i = 1; i < valores.length; i++) {
            int aux = valores[i];
            int j = i - 1;
            while (j >= 0 && valores[j] > aux) {
                valores[j + 1] = valores[j];
                j--;
            }
            valores[j + 1] = aux;
            System.out.print("Passada " + i + ": ");
            printArray(valores);
        }
        System.out.println();
    }

    // Selection Sort
    public static void selectionSort(int[] valores) {
        System.out.println("Selection Sort:");
        for (int i = 0; i < valores.length - 1; i++) {
            int indiceMenor = i;
            for (int j = i + 1; j < valores.length; j++) {
                if (valores[j] < valores[indiceMenor]) {
                    indiceMenor = j;
                }
            }
            if (indiceMenor != i) {
                int aux = valores[i];
                valores[i] = valores[indiceMenor];
                valores[indiceMenor] = aux;
            }
            System.out.print("Passada " + (i + 1) + ": ");
            printArray(valores);
        }
        System.out.println();
    }

    // Bubble Sort
    public static void bubbleSort(int[] valores) {
        System.out.println("Bubble Sort:");
        int n = valores.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                if (valores[j] > valores[j + 1]) {
                    int aux = valores[j];
                    valores[j] = valores[j + 1];
                    valores[j + 1] = aux;
                }
            }
            System.out.print("Passada " + (i + 1) + ": ");
            printArray(valores);
        }
        System.out.println();
    }
}

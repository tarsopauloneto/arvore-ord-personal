import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.print("Digite a altura da árvore: ");
        int altura = scanner.nextInt();

        ArvoreBinaria arvore = new ArvoreBinaria(altura);
        int quantidadeNos = arvore.calcularNumeroMaximoDeNos();
        int[] valores = new int[quantidadeNos];

        System.out.println("\nGerando valores aleatórios para preencher a árvore:");
        for (int i = 0; i < quantidadeNos; i++) {
            valores[i] = random.nextInt(100); // valores entre 0 e 99
            System.out.print(valores[i] + " ");
        }
        System.out.println("\n");

        // Insertion Sort
        int[] numeros1 = valores.clone();
        Ordenacao.insertionSort(numeros1);

        // Selection Sort
        int[] numeros2 = valores.clone();
        Ordenacao.selectionSort(numeros2);

        // Bubble Sort
        int[] numeros3 = valores.clone();
        Ordenacao.bubbleSort(numeros3);

        // System.out.println("Escolha o método de inserção:");
        // System.out.println("1 - Pré-ordem");
        // System.out.println("2 - Pós-ordem");
        // System.out.println("3 - Por nível");
        // System.out.print("Opção: ");
        // int opcao = scanner.nextInt();

        // switch (opcao) {
        // case 1:
        // arvore.inserirPreOrdem(valores);
        // System.out.println("\nTravessia em pré-ordem:");
        // arvore.preOrdem(arvore.raiz);
        // break;

        // case 2:
        // arvore.inserirPosOrdem(valores);
        // System.out.println("\nTravessia em pós-ordem:");
        // arvore.posOrdem(arvore.raiz);
        // break;

        // case 3:
        // arvore.inserirPorNivel(valores);
        // System.out.println("\nTravessia por nível:");
        // arvore.porNivel();
        // break;

        // default:
        // System.out.println("Opção inválida!");
        // }

        scanner.close();
    }
}

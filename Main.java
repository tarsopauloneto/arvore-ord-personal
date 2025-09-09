import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            while (true) {
                System.out.print("Digite a altura da árvore (0 para sair): ");
                int altura = sc.nextInt();
                if (altura == 0)
                    break;

                // calcular número de nós
                int numNos = (int) Math.pow(2, altura) - 1;
                System.out.println("Número de nós criados: " + numNos);

                // gerar vetor de valores aleatórios
                int[] valores = new int[numNos];
                for (int i = 0; i < numNos; i++) {
                    valores[i] = (int) (Math.random() * 100);
                }
                System.out.println("Vetor original: " + ArvoreBinaria.arrToStr(valores));

                // construir árvores (8 cenários: percurso × completa/desbalanceada)
                ArvoreBinaria[] arvores = new ArvoreBinaria[8];
                String[] percursos = { "Pré-ordem", "In-ordem", "Pós-ordem", "Em nível" };
                String[] formas = { "Completa", "Desbalanceada" };
                int idx = 0;

                for (int p = 0; p < percursos.length; p++) {
                    for (int f = 0; f < formas.length; f++) {
                        ArvoreBinaria arv = new ArvoreBinaria();
                        for (int v : valores) {
                            switch (p) {
                                case 0:
                                    arv.inserirPreOrder(v, f == 0);
                                    break;
                                case 1:
                                    arv.inserirInOrder(v, f == 0);
                                    break;
                                case 2:
                                    arv.inserirPostOrder(v, f == 0);
                                    break;
                                case 3:
                                    arv.inserirEmNivel(v, f == 0);
                                    break;
                            }
                        }
                        arvores[idx++] = arv;
                    }
                }

                // exibir vetor ordenado apenas uma vez (usando InsertionSort, por exemplo)
                int[] copia = valores.clone();
                long t0 = System.nanoTime();
                Ordenacao.insertionSort(copia);
                long t1 = System.nanoTime();
                System.out.println("Vetor ordenado (InsertionSort): " + ArvoreBinaria.arrToStr(copia));
                System.out.println("Tempo (InsertionSort, referência): " + (t1 - t0) + " ns");

                // exibir tabela comparativa
                System.out.println("\n===== Tabela Comparativa =====\n");
                System.out.printf("%-15s %-15s %-15s %-15s %-15s %-20s%n",
                        "Percurso", "Forma", "Algoritmo", "Melhor caso", "Pior caso", "Tempo real (ns)\n");

                idx = 0;
                for (int p = 0; p < percursos.length; p++) {
                    for (int f = 0; f < formas.length; f++) {
                        ArvoreBinaria arv = arvores[idx++];
                        int[] vetorPercorrido;
                        switch (p) {
                            case 0:
                                vetorPercorrido = arv.preOrdem();
                                break;
                            case 1:
                                vetorPercorrido = arv.inOrdem();
                                break;
                            case 2:
                                vetorPercorrido = arv.posOrdem();
                                break;
                            default:
                                vetorPercorrido = arv.emNivel();
                                break;
                        }

                        executarOrdenacao("InsertionSort", percursos[p], formas[f],
                                vetorPercorrido, "O(n)", "O(n²)");
                        executarOrdenacao("SelectionSort", percursos[p], formas[f],
                                vetorPercorrido, "O(n²)", "O(n²)");
                        executarOrdenacao("BubbleSort", percursos[p], formas[f],
                                vetorPercorrido, "O(n)", "O(n²)");
                    }
                }

                // menu interativo para explorar
                while (true) {
                    System.out.println("\n===== Menu de Visualização =====");
                    System.out.println("1 - Visualizar estrutura de árvore");
                    System.out.println("2 - Visualizar vetor de travessia");
                    System.out.println("0 - Encerrar programa");
                    System.out.print("Escolha: ");
                    int op = sc.nextInt();
                    if (op == 0)
                        return;

                    if (op == 1 || op == 2) {
                        System.out.println("\nEscolha o percurso:");
                        for (int p = 0; p < percursos.length; p++) {
                            System.out.println((p + 1) + " - " + percursos[p]);
                        }
                        int p = sc.nextInt() - 1;

                        System.out.println("\nEscolha a forma da árvore:");
                        for (int f = 0; f < formas.length; f++) {
                            System.out.println((f + 1) + " - " + formas[f]);
                        }
                        int f = sc.nextInt() - 1;

                        int pos = p * 2 + f;
                        ArvoreBinaria arv = arvores[pos];

                        if (op == 1) {
                            System.out.println("\nEstrutura da árvore:");
                            arv.imprimirEstrutura();
                        } else {
                            int[] vetorPercorrido;
                            switch (p) {
                                case 0:
                                    vetorPercorrido = arv.preOrdem();
                                    break;
                                case 1:
                                    vetorPercorrido = arv.inOrdem();
                                    break;
                                case 2:
                                    vetorPercorrido = arv.posOrdem();
                                    break;
                                default:
                                    vetorPercorrido = arv.emNivel();
                                    break;
                            }
                            System.out.println("\nVetor de travessia: " + ArvoreBinaria.arrToStr(vetorPercorrido));
                        }
                    }
                }
            }
        }
    }

    private static void executarOrdenacao(String algoritmo, String percurso, String forma,
            int[] vetor, String melhorCaso, String piorCaso) {
        int[] copia = vetor.clone();
        long inicio = System.nanoTime();
        if (algoritmo.equals("InsertionSort"))
            Ordenacao.insertionSort(copia);
        else if (algoritmo.equals("SelectionSort"))
            Ordenacao.selectionSort(copia);
        else
            Ordenacao.bubbleSort(copia);
        long fim = System.nanoTime();
        long tempo = fim - inicio;

        System.out.printf("%-15s %-15s %-15s %-15s %-15s %-20d%n",
                percurso, forma, algoritmo, melhorCaso, piorCaso, tempo);
    }
}

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // labels usados por toda a Main
        String[] percursos = { "Pré-ordem", "In-ordem", "Pós-ordem", "Em nível" };
        String[] formas = { "Completa", "Desbalanceada" };

        while (true) {
            System.out.print("Digite a altura da árvore (0 para sair): ");
            int altura = sc.nextInt();
            if (altura == 0)
                break;

            int totalNos = (int) Math.pow(2, altura) - 1;
            System.out.println("Número de nós criados: " + totalNos);

            // gerar vetor de valores aleatórios
            int[] valores = new int[totalNos];
            for (int i = 0; i < totalNos; i++)
                valores[i] = (int) (Math.random() * 100);
            System.out.println("Vetor original: " + ArvoreBinaria.arrToStr(valores));

            // Construir árvores: 2 (formas) x 4 (percursos) = 8 árvores
            ArvoreBinaria[][] arvores = new ArvoreBinaria[2][4];
            for (int f = 0; f < 2; f++) { // 0 = completa, 1 = desbalanceada
                for (int p = 0; p < 4; p++) {
                    ArvoreBinaria arv = new ArvoreBinaria();
                    // inserir valores usando os dois métodos separados
                    if (f == 0) { // completa
                        for (int v : valores)
                            arv.inserirCompleta(v);
                    } else { // desbalanceada (força à esquerda)
                        for (int v : valores)
                            arv.inserirDesbalanceada(v);
                    }
                    arvores[f][p] = arv;
                }
            }

            // exibir vetor ordenado apenas uma vez (InsertionSort)
            int[] vetorOrdenado = valores.clone();
            long t0 = System.nanoTime();
            Ordenacao.insertionSort(vetorOrdenado);
            long t1 = System.nanoTime();
            System.out.println("Vetor ordenado (InsertionSort): " + ArvoreBinaria.arrToStr(vetorOrdenado));
            System.out.println("Tempo (InsertionSort, referência): " + (t1 - t0) + " ns");

            // tabela comparativa
            System.out.println("\n===== Tabela Comparativa =====\n");
            System.out.printf("%-12s %-14s %-15s %-12s %-12s %-15s%n",
                    "Percurso", "Forma", "Algoritmo", "Melhor caso", "Pior caso", "Tempo real (ns)");

            for (int f = 0; f < 2; f++) {
                for (int p = 0; p < 4; p++) {
                    ArvoreBinaria arv = arvores[f][p];
                    int[] vetorPercorrido = obterVetorPorPercurso(arv, p, totalNos);
                    String formaStr = (f == 0) ? "Completa" : "Desbalanceada";

                    executarOrdenacao("InsertionSort", percursos[p], formaStr, vetorPercorrido, "O(n)", "O(n²)");
                    executarOrdenacao("SelectionSort", percursos[p], formaStr, vetorPercorrido, "O(n²)", "O(n²)");
                    executarOrdenacao("BubbleSort", percursos[p], formaStr, vetorPercorrido, "O(n)", "O(n²)");
                }
            }

            // menu interativo: visualizar estrutura (escolha apenas forma) ou vetores
            // (escolha percurso + forma)
            boolean volta = false;
            while (!volta) {
                System.out.println("\n===== Menu de Visualização =====");
                System.out.println("1 - Visualizar estrutura de árvore (escolha: Completa/Desbalanceada)");
                System.out.println("2 - Visualizar vetor de travessia (escolha percurso e forma)");
                System.out.println("0 - Voltar (nova execução / alterar altura)");
                System.out.print("Escolha: ");
                int op = sc.nextInt();
                if (op == 0)
                    break;

                if (op == 1) {
                    System.out.println("\nEscolha a forma da árvore para visualizar:");
                    System.out.println("1 - Completa");
                    System.out.println("2 - Desbalanceada");
                    int escolha = sc.nextInt();
                    int fidx = (escolha == 1) ? 0 : 1;
                    // qualquer percurso serve para mostrar a estrutura da forma (todas iguais por
                    // forma)
                    System.out.println("\nEstrutura (" + formas[fidx] + "):");
                    arvores[fidx][0].imprimirEstrutura(); // mostramos a árvore do índice de percurso 0 (Pré-ordem) —
                                                          // equivalente para a forma
                } else if (op == 2) {
                    System.out.println("\nEscolha o percurso:");
                    for (int i = 0; i < percursos.length; i++) {
                        System.out.println((i + 1) + " - " + percursos[i]);
                    }
                    int pidx = sc.nextInt() - 1;
                    System.out.println("\nEscolha a forma:");
                    System.out.println("1 - Completa");
                    System.out.println("2 - Desbalanceada");
                    int fidx = (sc.nextInt() == 1) ? 0 : 1;

                    int[] vet = obterVetorPorPercurso(arvores[fidx][pidx], pidx, totalNos);
                    System.out.println("\nVetor de travessia (" + percursos[pidx] + ", " + formas[fidx] + "):");
                    System.out.println(ArvoreBinaria.arrToStr(vet));
                } else {
                    System.out.println("Opção inválida. Tente novamente.");
                }
            }
            // volta para solicitar nova altura
        }

        sc.close();
        System.out.println("Programa encerrado.");
    }

    // p: 0=pre,1=in,2=pos,3=porNivel
    private static int[] obterVetorPorPercurso(ArvoreBinaria arv, int p, int totalNos) {
        switch (p) {
            case 0:
                return arv.preOrdem(totalNos);
            case 1:
                return arv.inOrdem(totalNos);
            case 2:
                return arv.posOrdem(totalNos);
            default:
                return arv.porNivel(totalNos);
        }
    }

    private static void executarOrdenacao(String algoritmo, String percurso, String forma,
            int[] vetor, String melhorCaso, String piorCaso) {
        int[] copia = vetor.clone();
        long inicio = System.nanoTime();
        if ("InsertionSort".equals(algoritmo))
            Ordenacao.insertionSort(copia);
        else if ("SelectionSort".equals(algoritmo))
            Ordenacao.selectionSort(copia);
        else
            Ordenacao.bubbleSort(copia);
        long fim = System.nanoTime();
        long tempo = fim - inicio;

        System.out.printf("%-12s %-14s %-15s %-12s %-12s %-15d%n",
                percurso, forma, algoritmo, melhorCaso, piorCaso, tempo);
    }
}

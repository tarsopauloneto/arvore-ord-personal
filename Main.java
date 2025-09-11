import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String[] percursos = { "Pré-ordem", "In-ordem", "Pós-ordem", "Em nível" };
        String[] formas = { "Completa", "Desbalanceada" };

        while (true) {
            System.out.print("Digite a altura da árvore (0 para sair): ");
            int altura = sc.nextInt();
            if (altura == 0)
                break;

            int totalNos = (int) Math.pow(2, altura) - 1;
            System.out.println("Número de nós criados: " + totalNos);

            int[] valores = new int[totalNos];
            for (int i = 0; i < totalNos; i++)
                valores[i] = (int) (Math.random() * 100);
            System.out.println("Vetor original: " + ArvoreBinaria.arrToStr(valores));

            // Construir árvores
            ArvoreBinaria[][] arvores = new ArvoreBinaria[2][4];
            for (int f = 0; f < 2; f++) {
                for (int p = 0; p < 4; p++) {
                    ArvoreBinaria arv = new ArvoreBinaria();
                    if (f == 0) {
                        for (int v : valores)
                            arv.inserirCompleta(v);
                    } else {
                        for (int v : valores)
                            arv.inserirDesbalanceada(v);
                    }
                    arvores[f][p] = arv;
                }
            }

            // exibir vetor ordenado como referência
            int[] vetorOrdenado = valores.clone();
            long t0 = System.nanoTime();
            Sorts.insertionSort(vetorOrdenado);
            long t1 = System.nanoTime();
            System.out.println("Vetor ordenado (InsertionSort): " + ArvoreBinaria.arrToStr(vetorOrdenado));
            System.out.println("Tempo (InsertionSort, referência): " + (t1 - t0) + " ns");

            // tabela comparativa
            System.out.println("\n===== Tabela Comparativa =====\n");

            // cabeçalho centralizado
            System.out.printf("| %-15s | %-15s | %-15s | %-15s | %-15s | %-15s | %-15s |\n",
                    "Percurso", "Forma", "Algoritmo", "Melhor caso", "Pior caso", "Caso atual", "Tempo(ns)");
            System.out.println(
                    "|-----------------|-----------------|-----------------|-----------------|-----------------|-----------------|-----------------|");

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

            // menu de visualização
            boolean volta = false;
            while (!volta) {
                System.out.println("\n===== Menu de Visualização =====");
                System.out.println("1 - Visualizar estrutura da árvore");
                System.out.println("2 - Visualizar vetor por travessia");
                System.out.println("0 - Voltar (nova execução)");
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
                    System.out.println("\nEstrutura (" + formas[fidx] + "):");
                    arvores[fidx][0].imprimirEstrutura();
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
        }

        sc.close();
        System.out.println("Programa encerrado.");
    }

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

        ResultadoOrdenacao resultado;
        if ("InsertionSort".equals(algoritmo))
            resultado = Sorts.insertionSort(vetor);
        else if ("SelectionSort".equals(algoritmo))
            resultado = Sorts.selectionSort(vetor);
        else
            resultado = Sorts.bubbleSort(vetor);

        System.out.printf("| %-15s | %-15s | %-15s | %-15s | %-15s | %-15s | %-15d |\n",
                percurso, forma, algoritmo, melhorCaso, piorCaso,
                resultado.getCasoAtual(), resultado.getTempoExecucao());
    }
}

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String[] percursos = { "Pré-ordem", "In-ordem", "Pós-ordem", "Em nível" };
        String[] formas = { "Completa", "Desbalanceada" };

        int[] ultimoVetor = null; // guarda o vetor da execução anterior

        while (true) {
            // escolha de altura
            System.out.print("Digite a altura da árvore (0 para sair): ");
            int altura = sc.nextInt();
            if (altura == 0)
                break;

            int totalNos = (int) Math.pow(2, altura) - 1;
            System.out.println("Número de nós criados: " + totalNos);

            // vetor de valores (aleatório ou reaproveitado)
            int[] valores;
            if (ultimoVetor == null || ultimoVetor.length != totalNos) {
                valores = new int[totalNos];
                for (int i = 0; i < totalNos; i++)
                    valores[i] = (int) (Math.random() * 100);
            } else {
                valores = ultimoVetor.clone();
            }

            // loop para permitir reexibir interface com mesmo vetor em ordem
            // crescente/decrescente
            boolean repetirInterface = true;
            while (repetirInterface) {

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
                RelatorioSorts refSort = new RelatorioSorts();
                refSort.insertionSort(vetorOrdenado);
                System.out.println("Vetor ordenado (InsertionSort): " + ArvoreBinaria.arrToStr(vetorOrdenado));

                // tabela comparativa
                System.out.println("\n===== Tabela Comparativa =====\n");

                // cabeçalho com espaçamento uniforme
                String formato = "| %-12s | %-13s | %-13s | %-12s | %-12s | %-12s | %-10s | %-12s | %-8s |\n";

                System.out.printf(formato,
                        "Percurso", "Forma", "Algoritmo", "Melhor caso",
                        "Pior caso", "Caso atual", "Tempo(ns)", "Comparações", "Trocas");
                System.out.println(
                        "+--------------+---------------+---------------+--------------+--------------+--------------+------------+--------------+----------+");

                // linhas da tabela
                for (int f = 0; f < 2; f++) {
                    for (int p = 0; p < 4; p++) {
                        ArvoreBinaria arv = arvores[f][p];
                        int[] vetorPercorrido = obterVetorPorPercurso(arv, p, totalNos);
                        String formaStr = (f == 0) ? "Completa" : "Desbalanceada";

                        executarOrdenacao("InsertionSort", percursos[p], formaStr, vetorPercorrido, "O(n)", "O(n²)",
                                formato);
                        executarOrdenacao("SelectionSort", percursos[p], formaStr, vetorPercorrido, "O(n²)", "O(n²)",
                                formato);
                        executarOrdenacao("BubbleSort", percursos[p], formaStr, vetorPercorrido, "O(n)", "O(n²)",
                                formato);
                    }
                }

                // menu de visualização
                boolean sairMenu = false;
                while (!sairMenu) {
                    System.out.println("\n===== Menu de Visualização =====");
                    System.out.println("1 - Visualizar estrutura da árvore");
                    System.out.println("2 - Visualizar vetor por travessia");
                    System.out.println("0 - Voltar (nova execução)");
                    System.out.print("Escolha: ");
                    int op = sc.nextInt();

                    switch (op) {
                        case 0 -> { // Voltar (nova execução) -> mostrar opções sobre o vetor
                            System.out.println("\n===== Opções de Nova Execução =====");
                            System.out.println("1 - Novo vetor aleatório (volta a pedir altura)");
                            System.out.println(
                                    "2 - Mesmo vetor (crescente)");
                            System.out.println(
                                    "3 - Mesmo vetor (decrescente)");
                            System.out.print("Escolha: ");
                            int escolha = sc.nextInt();

                            switch (escolha) {
                                case 1 -> { // novo vetor: voltar ao loop externo para pedir altura
                                    ultimoVetor = null;
                                    repetirInterface = false; // sai do loop de interface
                                    sairMenu = true;
                                }
                                case 2 -> { // ordenar crescente e reexibir interface
                                    Arrays.sort(valores);
                                    ultimoVetor = valores.clone();
                                    sairMenu = true;
                                }
                                case 3 -> { // ordenar decrescente e reexibir interface
                                    Arrays.sort(valores);
                                    for (int i = 0; i < valores.length / 2; i++) {
                                        int temp = valores[i];
                                        valores[i] = valores[valores.length - 1 - i];
                                        valores[valores.length - 1 - i] = temp;
                                    }
                                    ultimoVetor = valores.clone();
                                    sairMenu = true;
                                }
                                default -> { // default: tratar como novo vetor
                                    ultimoVetor = null;
                                    repetirInterface = false;
                                    sairMenu = true;
                                }
                            }
                        }
                        case 1 -> { // visualizar estrutura
                            System.out.println("\nEscolha a forma da árvore para visualizar:");
                            System.out.println("1 - Completa");
                            System.out.println("2 - Desbalanceada");
                            int escolha = sc.nextInt();
                            int fidx = (escolha == 1) ? 0 : 1;
                            System.out.println("\nEstrutura (" + formas[fidx] + "):");
                            arvores[fidx][0].imprimirEstrutura();
                        }
                        case 2 -> { // visualizar vetor por travessia
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
                        }
                        default -> System.out.println("Opção inválida. Tente novamente.");
                    }
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
            int[] vetor, String melhorCaso, String piorCaso, String formato) {

        int[] copia = vetor.clone();
        RelatorioSorts relatorio = new RelatorioSorts();

        switch (algoritmo) {
            case "InsertionSort" -> relatorio.insertionSort(copia);
            case "SelectionSort" -> relatorio.selectionSort(copia);
            default -> relatorio.bubbleSort(copia);
        }

        System.out.printf(formato,
                percurso, forma, algoritmo, melhorCaso, piorCaso,
                relatorio.getCasoAtual(), relatorio.getTempoExecucao(),
                relatorio.getComparacoes(), relatorio.getTrocas());
    }
}

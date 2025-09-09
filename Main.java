import java.util.Scanner;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean repetir = true;

        while (repetir) {
            System.out.print("Digite a altura da árvore: ");
            int altura = sc.nextInt();
            int numeroNos = 1;
            for (int i = 0; i < altura; i++)
                numeroNos *= 2;
            numeroNos -= 1; // número de nós para árvore cheia
            System.out.println("Número de nós = 2^" + altura + "-1 = " + numeroNos);

            int[] valores = new int[numeroNos];
            Random rand = new Random();
            for (int i = 0; i < numeroNos; i++) {
                valores[i] = rand.nextInt(100);
            }

            // Exibe vetor gerado
            System.out.print("Vetor gerado: [");
            for (int i = 0; i < valores.length; i++) {
                System.out.print(valores[i]);
                if (i < valores.length - 1)
                    System.out.print(", ");
            }
            System.out.println("]");

            String[] percursos = { "Pré-ordem", "Pós-ordem", "Em ordem", "Em nível" };
            String[] formas = { "Completa", "Desbalanceada" };
            String[] algoritmos = { "InsertionSort", "SelectionSort", "BubbleSort" };

            // Tabela fixa: 4 percursos × 2 formas × 3 algoritmos = 24 linhas × 6 colunas
            String[][] tabelaResultados = new String[24][6];
            int linhaTabela = 0;

            for (int p = 0; p < percursos.length; p++) {
                String percurso = percursos[p];

                for (int f = 0; f < formas.length; f++) {
                    String forma = formas[f];

                    // Cria árvore
                    ArvoreBinaria arvore = new ArvoreBinaria();

                    // Inserção de acordo com percurso e forma
                    for (int i = 0; i < valores.length; i++) {
                        int v = valores[i];
                        boolean balanceado = forma.equals("Completa");
                        switch (percurso) {
                            case "Pré-ordem":
                                arvore.inserirPreOrder(v, balanceado);
                                break;
                            case "Pós-ordem":
                                arvore.inserirPostOrder(v, balanceado);
                                break;
                            case "Em ordem":
                                arvore.inserirInOrder(v, balanceado);
                                break;
                            case "Em nível":
                                arvore.inserirEmNivel(v, balanceado);
                                break;
                        }
                    }

                    // Obtém vetor do percurso
                    int[] vetorPercorrido;
                    switch (percurso) {
                        case "Pré-ordem":
                            vetorPercorrido = arvore.preOrdem();
                            break;
                        case "Pós-ordem":
                            vetorPercorrido = arvore.posOrdem();
                            break;
                        case "Em ordem":
                            vetorPercorrido = arvore.inOrdem();
                            break;
                        default:
                            vetorPercorrido = arvore.emNivel();
                            break;
                    }

                    // Aplica cada algoritmo de ordenação
                    for (int a = 0; a < algoritmos.length; a++) {
                        String algoritmo = algoritmos[a];

                        // Copia vetor manualmente
                        int[] vetorOrdenado = new int[vetorPercorrido.length];
                        for (int i = 0; i < vetorPercorrido.length; i++) {
                            vetorOrdenado[i] = vetorPercorrido[i];
                        }

                        // Mede tempo de execução
                        long inicio = System.nanoTime();
                        if (algoritmo.equals("InsertionSort")) {
                            Ordenacao.insertionSort(vetorOrdenado);
                        } else if (algoritmo.equals("SelectionSort")) {
                            Ordenacao.selectionSort(vetorOrdenado);
                        } else if (algoritmo.equals("BubbleSort")) {
                            Ordenacao.bubbleSort(vetorOrdenado);
                        }
                        long tempo = System.nanoTime() - inicio;

                        // Exibe vetores antes e depois da ordenação
                        System.out.println("\n--- " + percurso + " | " + forma + " | " + algoritmo + " ---");
                        System.out.print("Antes: [");
                        for (int i = 0; i < vetorPercorrido.length; i++) {
                            System.out.print(vetorPercorrido[i]);
                            if (i < vetorPercorrido.length - 1)
                                System.out.print(", ");
                        }
                        System.out.println("]");
                        System.out.print("Depois: [");
                        for (int i = 0; i < vetorOrdenado.length; i++) {
                            System.out.print(vetorOrdenado[i]);
                            if (i < vetorOrdenado.length - 1)
                                System.out.print(", ");
                        }
                        System.out.println("]");
                        System.out.println("Tempo: " + tempo + " ns");

                        // Preenche tabela
                        String melhorCaso = (algoritmo.equals("InsertionSort") || algoritmo.equals("BubbleSort"))
                                ? "O(n)"
                                : "O(n²)";
                        String piorCaso = "O(n²)";

                        tabelaResultados[linhaTabela][0] = percurso;
                        tabelaResultados[linhaTabela][1] = forma;
                        tabelaResultados[linhaTabela][2] = algoritmo;
                        tabelaResultados[linhaTabela][3] = melhorCaso;
                        tabelaResultados[linhaTabela][4] = piorCaso;
                        tabelaResultados[linhaTabela][5] = String.valueOf(tempo);
                        linhaTabela++;
                    }
                }
            }

            // Exibe tabela final
            System.out.println("\n=== TABELA COMPARATIVA FINAL ===");
            System.out.printf("%-12s %-14s %-15s %-12s %-12s %-15s%n",
                    "Percurso", "Forma", "Algoritmo", "Melhor Caso", "Pior Caso", "Tempo Real (ns)");
            for (int i = 0; i < linhaTabela; i++) {
                System.out.printf("%-12s %-14s %-15s %-12s %-12s %-15s%n",
                        tabelaResultados[i][0],
                        tabelaResultados[i][1],
                        tabelaResultados[i][2],
                        tabelaResultados[i][3],
                        tabelaResultados[i][4],
                        tabelaResultados[i][5]);
            }

            System.out.print("\nDeseja repetir o processo? (s/n): ");
            repetir = sc.next().equalsIgnoreCase("s");
        }

        sc.close();
    }
}

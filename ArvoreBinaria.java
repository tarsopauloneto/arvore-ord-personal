import java.util.Queue;
import java.util.LinkedList;

public class ArvoreBinaria {
    No raiz;
    int altura;

    public ArvoreBinaria(int altura) {
        this.altura = altura;
        this.raiz = null;
    }

    // Calcula a quantidade máxima de nós para uma árvore cheia/perfeita
    public int calcularNumeroMaximoDeNos() {
        return (int) Math.pow(2, altura) - 1;
    }

    // -------- MÉTODOS DE INSERÇÃO --------

    // Inserção por pré-ordem (raiz -> esquerda -> direita)
    public void inserirPreOrdem(int[] valores) {
        System.out.println("Inserindo em pré-ordem:");
        raiz = inserirPreOrdemRec(valores, 0);
    }

    private No inserirPreOrdemRec(int[] valores, int indice) {
        if (indice >= valores.length)
            return null;

        No atual = new No(valores[indice]);
        System.out.println("Inserido: " + atual.valor);

        // Filho esquerdo
        atual.esquerdo = inserirPreOrdemRec(valores, 2 * indice + 1);
        // Filho direito
        atual.direito = inserirPreOrdemRec(valores, 2 * indice + 2);

        return atual;
    }

    // Inserção por pós-ordem (esquerda -> direita -> raiz)
    public void inserirPosOrdem(int[] valores) {
        System.out.println("Inserindo em pós-ordem:");
        raiz = inserirPosOrdemRec(valores, 0);
    }

    private No inserirPosOrdemRec(int[] valores, int indice) {
        if (indice >= valores.length)
            return null;

        // Cria filhos primeiro
        No esquerdo = inserirPosOrdemRec(valores, 2 * indice + 1);
        No direito = inserirPosOrdemRec(valores, 2 * indice + 2);

        // Depois cria o nó atual
        No atual = new No(valores[indice]);
        atual.esquerdo = esquerdo;
        atual.direito = direito;
        System.out.println("Inserido: " + atual.valor);

        return atual;
    }

    // Inserção por nível
    public void inserirPorNivel(int[] valores) {
        System.out.println("Inserindo por nível:");
        if (valores.length == 0)
            return;

        raiz = new No(valores[0]);
        System.out.println("Inserido: " + raiz.valor);

        // Simula a fila usando LinkedList
        Queue<No> fila = new LinkedList<>();
        fila.add(raiz);

        int i = 1; // próximo valor a inserir

        while (i < valores.length) {
            No atual = fila.poll();

            // Sempre cria filho esquerdo
            atual.esquerdo = new No(valores[i++]);
            System.out.println("Inserido: " + atual.esquerdo.valor);
            fila.add(atual.esquerdo);

            // Sempre cria filho direito
            atual.direito = new No(valores[i++]);
            System.out.println("Inserido: " + atual.direito.valor);
            fila.add(atual.direito);
        }
    }

    // -------- MÉTODOS DE TRAVESSIA --------

    public void preOrdem(No no) {
        if (no == null)
            return;
        System.out.print(no.valor + " ");
        preOrdem(no.esquerdo);
        preOrdem(no.direito);
    }

    public void posOrdem(No no) {
        if (no == null)
            return;
        posOrdem(no.esquerdo);
        posOrdem(no.direito);
        System.out.print(no.valor + " ");
    }

    public void porNivel() {
        if (raiz == null)
            return;

        Queue<No> fila = new LinkedList<>();
        fila.add(raiz);
        while (!fila.isEmpty()) {
            No atual = fila.poll();
            System.out.print(atual.valor + " ");
            if (atual.esquerdo != null)
                fila.add(atual.esquerdo);
            if (atual.direito != null)
                fila.add(atual.direito);
        }
    }
}

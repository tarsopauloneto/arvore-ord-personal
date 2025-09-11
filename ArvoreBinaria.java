import java.util.LinkedList;
import java.util.Queue;

public class ArvoreBinaria {
    public No raiz;

    public ArvoreBinaria() {
        this.raiz = null;
    }

    // ---------- MÉTODOS DE INSERÇÃO ----------
    // Insere um único valor na árvore na forma 'completa' (preenche por níveis,
    // esquerda antes da direita)
    public void inserirCompleta(int valor) {
        No novo = new No(valor);
        if (raiz == null) {
            raiz = novo;
            return;
        }
        Queue<No> fila = new LinkedList<>();
        fila.add(raiz);
        while (!fila.isEmpty()) {
            No atual = fila.poll();
            if (atual.esq == null) {
                atual.esq = novo;
                return;
            }
            if (atual.dir == null) {
                atual.dir = novo;
                return;
            }
            fila.add(atual.esq);
            fila.add(atual.dir);
        }
    }

    // Insere um único valor na forma 'desbalanceada' (força o desbalanceamento para
    // a ESQUERDA)
    // Implementação: segue a cadeia da esquerda até achar uma posição nula e insere
    // lá (lista à esquerda).
    public void inserirDesbalanceada(int valor) {
        No novo = new No(valor);
        if (raiz == null) {
            raiz = novo;
            return;
        }
        No atual = raiz;
        while (atual.esq != null) {
            atual = atual.esq;
        }
        atual.esq = novo;
    }

    // ---------- TRAVESSIAS (RETORNAM VETORES) ----------
    // pre-ordem: raiz, esq, dir
    public int[] preOrdem(int totalNos) {
        int[] percorrido = new int[totalNos];
        preOrdemRec(raiz, percorrido, new int[] { 0 });
        return percorrido;
    }

    private void preOrdemRec(No node, int[] percorrido, int[] idx) {
        if (node == null)
            return;
        percorrido[idx[0]++] = node.valor;
        preOrdemRec(node.esq, percorrido, idx);
        preOrdemRec(node.dir, percorrido, idx);
    }

    // in-ordem: esq, raiz, dir
    public int[] inOrdem(int totalNos) {
        int[] percorrido = new int[totalNos];
        inOrdemRec(raiz, percorrido, new int[] { 0 });
        return percorrido;
    }

    private void inOrdemRec(No node, int[] percorrido, int[] idx) {
        if (node == null)
            return;
        inOrdemRec(node.esq, percorrido, idx);
        percorrido[idx[0]++] = node.valor;
        inOrdemRec(node.dir, percorrido, idx);
    }

    // pos-ordem: esq, dir, raiz
    public int[] posOrdem(int totalNos) {
        int[] percorrido = new int[totalNos];
        posOrdemRec(raiz, percorrido, new int[] { 0 });
        return percorrido;
    }

    private void posOrdemRec(No node, int[] percorrido, int[] idx) {
        if (node == null)
            return;
        posOrdemRec(node.esq, percorrido, idx);
        posOrdemRec(node.dir, percorrido, idx);
        percorrido[idx[0]++] = node.valor;
    }

    // por nível
    public int[] porNivel(int totalNos) {
        int[] percorrido = new int[totalNos];
        if (raiz == null)
            return percorrido;
        Queue<No> fila = new LinkedList<>();
        fila.add(raiz);
        int i = 0;
        while (!fila.isEmpty() && i < totalNos) {
            No atual = fila.poll();
            percorrido[i++] = atual.valor;
            if (atual.esq != null)
                fila.add(atual.esq);
            if (atual.dir != null)
                fila.add(atual.dir);
        }
        return percorrido;
    }

    // ---------- IMPRESSÃO DA ESTRUTURA (DIAGRAMA) ----------
    public void imprimirEstrutura() {
        if (raiz == null) {
            System.out.println("(árvore vazia)");
            return;
        }
        imprimirRec(raiz, "", true);
    }

    // prefixo para desenho; 'ultimo' indica se o nó é o último filho no nível para
    // desenhar └── ou ├──
    private void imprimirRec(No node, String prefixo, boolean ultimo) {
        if (node == null)
            return;
        System.out.println(prefixo + (ultimo ? "└── " : "├── ") + node.valor);
        // Se existirem filhos, desenhar: primeiro esquerdo (não-último), depois direito
        // (último)
        if (node.esq != null || node.dir != null) {
            // ajustar prefixo para filhos
            String novoPrefixo = prefixo + (ultimo ? "    " : "│   ");
            if (node.esq != null && node.dir != null) {
                imprimirRec(node.esq, novoPrefixo, false);
                imprimirRec(node.dir, novoPrefixo, true);
            } else if (node.esq != null) {
                imprimirRec(node.esq, novoPrefixo, true);
            } else {
                imprimirRec(node.dir, novoPrefixo, true);
            }
        }
    }

    // ---------- UTILITÁRIO ----------
    public static String arrToStr(int[] a) {
        if (a == null)
            return "[]";
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < a.length; i++) {
            sb.append(a[i]);
            if (i < a.length - 1)
                sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}

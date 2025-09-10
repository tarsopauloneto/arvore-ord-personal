import java.util.LinkedList;
import java.util.Queue;

public class ArvoreBinaria {
    public No raiz;

    public ArvoreBinaria() {
        this.raiz = null;
    }

    // ---------- MÉTODOS DE INSERÇÃO ----------
    // Insere um único valor na árvore no modo 'completa' (preenche por níveis,
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

    // Insere um único valor no modo 'desbalanceada' (força o desbalanceamento para
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
        int[] out = new int[totalNos];
        preOrdemRec(raiz, out, new int[] { 0 });
        return out;
    }

    private void preOrdemRec(No node, int[] out, int[] idx) {
        if (node == null)
            return;
        out[idx[0]++] = node.valor;
        preOrdemRec(node.esq, out, idx);
        preOrdemRec(node.dir, out, idx);
    }

    // in-ordem: esq, raiz, dir
    public int[] inOrdem(int totalNos) {
        int[] out = new int[totalNos];
        inOrdemRec(raiz, out, new int[] { 0 });
        return out;
    }

    private void inOrdemRec(No node, int[] out, int[] idx) {
        if (node == null)
            return;
        inOrdemRec(node.esq, out, idx);
        out[idx[0]++] = node.valor;
        inOrdemRec(node.dir, out, idx);
    }

    // pos-ordem: esq, dir, raiz
    public int[] posOrdem(int totalNos) {
        int[] out = new int[totalNos];
        posOrdemRec(raiz, out, new int[] { 0 });
        return out;
    }

    private void posOrdemRec(No node, int[] out, int[] idx) {
        if (node == null)
            return;
        posOrdemRec(node.esq, out, idx);
        posOrdemRec(node.dir, out, idx);
        out[idx[0]++] = node.valor;
    }

    // por nível (BFS)
    public int[] porNivel(int totalNos) {
        int[] out = new int[totalNos];
        if (raiz == null)
            return out;
        Queue<No> fila = new LinkedList<>();
        fila.add(raiz);
        int i = 0;
        while (!fila.isEmpty() && i < totalNos) {
            No atual = fila.poll();
            out[i++] = atual.valor;
            if (atual.esq != null)
                fila.add(atual.esq);
            if (atual.dir != null)
                fila.add(atual.dir);
        }
        return out;
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

public class ArvoreBinaria {
    No raiz;

    public ArvoreBinaria() {
        this.raiz = null;
    }

    public void inserirPreOrder(int valor, boolean balanceado) {
        if (raiz == null) {
            raiz = new No(valor);
            return;
        }
        No[] pilha = new No[1024 * 16];
        int topo = 0;
        pilha[topo++] = raiz;
        while (topo > 0) {
            No atual = pilha[--topo];
            if (atual.esq == null) {
                atual.esq = new No(valor);
                return;
            } else if (atual.dir == null) {
                atual.dir = new No(valor);
                return;
            } else {
                if (balanceado) {
                    pilha[topo++] = atual.dir;
                    pilha[topo++] = atual.esq;
                } else {
                    pilha[topo++] = atual.dir;
                    pilha[topo++] = atual.esq;
                }
            }
        }
    }

    public void inserirInOrder(int valor, boolean balanceado) {
        if (raiz == null) {
            raiz = new No(valor);
            return;
        }
        No[] pilha = new No[1024 * 16];
        No atual = raiz;
        int topo = 0;
        while (atual != null || topo > 0) {
            while (atual != null) {
                pilha[topo++] = atual;
                atual = atual.esq;
            }
            atual = pilha[--topo];
            if (atual.esq == null) {
                atual.esq = new No(valor);
                return;
            } else if (atual.dir == null) {
                atual.dir = new No(valor);
                return;
            }
            atual = atual.dir;
        }
        No node = raiz;
        while (node.dir != null)
            node = node.dir;
        node.dir = new No(valor);
    }

    public void inserirPostOrder(int valor, boolean balanceado) {
        if (raiz == null) {
            raiz = new No(valor);
            return;
        }
        No[] pilha = new No[1024 * 16];
        int topo = 0;
        pilha[topo++] = raiz;
        while (topo > 0) {
            No atual = pilha[--topo];
            if (atual.esq == null) {
                atual.esq = new No(valor);
                return;
            } else if (atual.dir == null) {
                atual.dir = new No(valor);
                return;
            } else {
                if (balanceado) {
                    pilha[topo++] = atual.esq;
                    pilha[topo++] = atual.dir;
                } else {
                    pilha[topo++] = atual.esq;
                    pilha[topo++] = atual.dir;
                }
            }
        }
    }

    public void inserirEmNivel(int valor, boolean balanceado) {
        if (raiz == null) {
            raiz = new No(valor);
            return;
        }
        No[] fila = new No[1024 * 16];
        int inicio = 0, fim = 0;
        fila[fim++] = raiz;
        while (inicio < fim) {
            No atual = fila[inicio++];
            if (atual.esq == null) {
                atual.esq = new No(valor);
                return;
            } else if (atual.dir == null) {
                atual.dir = new No(valor);
                return;
            } else {
                fila[fim++] = atual.esq;
                fila[fim++] = atual.dir;
            }
        }
    }

    public int[] preOrdem() {
        int tamanho = contarNos(raiz);
        int[] out = new int[tamanho];
        preOrdemRec(raiz, out, 0);
        return out;
    }

    private int preOrdemRec(No node, int[] out, int idx) {
        if (node == null)
            return idx;
        out[idx++] = node.valor;
        idx = preOrdemRec(node.esq, out, idx);
        idx = preOrdemRec(node.dir, out, idx);
        return idx;
    }

    public int[] inOrdem() {
        int tamanho = contarNos(raiz);
        int[] out = new int[tamanho];
        inOrdemRec(raiz, out, 0);
        return out;
    }

    private int inOrdemRec(No node, int[] out, int idx) {
        if (node == null)
            return idx;
        idx = inOrdemRec(node.esq, out, idx);
        out[idx++] = node.valor;
        idx = inOrdemRec(node.dir, out, idx);
        return idx;
    }

    public int[] posOrdem() {
        int tamanho = contarNos(raiz);
        int[] out = new int[tamanho];
        posOrdemRec(raiz, out, 0);
        return out;
    }

    private int posOrdemRec(No node, int[] out, int idx) {
        if (node == null)
            return idx;
        idx = posOrdemRec(node.esq, out, idx);
        idx = posOrdemRec(node.dir, out, idx);
        out[idx++] = node.valor;
        return idx;
    }

    public int[] emNivel() {
        int tamanho = contarNos(raiz);
        int[] out = new int[tamanho];
        if (raiz == null)
            return out;
        No[] fila = new No[tamanho + 5];
        int inicio = 0, fim = 0, idx = 0;
        fila[fim++] = raiz;
        while (inicio < fim) {
            No atual = fila[inicio++];
            out[idx++] = atual.valor;
            if (atual.esq != null)
                fila[fim++] = atual.esq;
            if (atual.dir != null)
                fila[fim++] = atual.dir;
        }
        return out;
    }

    public int contarNos(No node) {
        if (node == null)
            return 0;
        return 1 + contarNos(node.esq) + contarNos(node.dir);
    }

    public static String arrToStr(int[] a) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < a.length; i++) {
            sb.append(a[i]);
            if (i < a.length - 1)
                sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}

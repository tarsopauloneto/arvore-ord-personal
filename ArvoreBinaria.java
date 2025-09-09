public class ArvoreBinaria {
    No raiz;

    public ArvoreBinaria() {
        this.raiz = null;
    }

    // ----- INSERÇÃO PRE-ORDEM -----
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
            if (atual.esq == null && balanceado) {
                atual.esq = new No(valor);
                return;
            } else if (atual.dir == null && balanceado) {
                atual.dir = new No(valor);
                return;
            } else if (!balanceado) {
                // desbalanceada: tenta sempre à direita primeiro
                if (atual.dir == null) {
                    atual.dir = new No(valor);
                    return;
                } else if (atual.esq == null) {
                    atual.esq = new No(valor);
                    return;
                }
            }
            if (balanceado) {
                pilha[topo++] = atual.dir;
                pilha[topo++] = atual.esq;
            } else {
                pilha[topo++] = atual.dir;
                pilha[topo++] = atual.esq;
            }
        }
    }

    // ----- INSERÇÃO IN-ORDEM -----
    public void inserirInOrder(int valor, boolean balanceado) {
        if (raiz == null) {
            raiz = new No(valor);
            return;
        }
        No atual = raiz;
        No pai = null;
        while (atual != null) {
            pai = atual;
            if (balanceado) {
                if (contarNos(atual.esq) <= contarNos(atual.dir))
                    atual = atual.esq;
                else
                    atual = atual.dir;
            } else {
                atual = atual.dir; // cresce sempre para a direita
            }
        }
        if (pai == null)
            raiz = new No(valor);
        else if (balanceado) {
            if (pai.esq == null)
                pai.esq = new No(valor);
            else
                pai.dir = new No(valor);
        } else {
            pai.dir = new No(valor);
        }
    }

    // ----- INSERÇÃO PÓS-ORDEM -----
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
            if (atual.esq == null && balanceado) {
                atual.esq = new No(valor);
                return;
            } else if (atual.dir == null && balanceado) {
                atual.dir = new No(valor);
                return;
            } else if (!balanceado) {
                if (atual.dir == null) {
                    atual.dir = new No(valor);
                    return;
                } else if (atual.esq == null) {
                    atual.esq = new No(valor);
                    return;
                }
            }

            if (balanceado) {
                pilha[topo++] = atual.esq;
                pilha[topo++] = atual.dir;
            } else {
                pilha[topo++] = atual.dir;
                pilha[topo++] = atual.esq;
            }
        }
    }

    // ----- INSERÇÃO EM NÍVEL -----
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
            if (balanceado) {
                if (atual.esq == null) {
                    atual.esq = new No(valor);
                    return;
                } else if (atual.dir == null) {
                    atual.dir = new No(valor);
                    return;
                }
                fila[fim++] = atual.esq;
                fila[fim++] = atual.dir;
            } else {
                if (atual.dir == null) {
                    atual.dir = new No(valor);
                    return;
                } else if (atual.esq == null) {
                    atual.esq = new No(valor);
                    return;
                }
                fila[fim++] = atual.dir;
                fila[fim++] = atual.esq;
            }
        }
    }

    // ----- TRAVESSIAS (pre, in, pos, nivel) -----
    public int[] preOrdem() {
        int[] out = new int[contarNos(raiz)];
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
        int[] out = new int[contarNos(raiz)];
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
        int[] out = new int[contarNos(raiz)];
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

    // ----- CONTAGEM DE NÓS -----
    public int contarNos(No node) {
        if (node == null)
            return 0;
        return 1 + contarNos(node.esq) + contarNos(node.dir);
    }

    // ----- IMPRESSÃO DE ARRAYS -----
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

    // ----- IMPRESSÃO DA ESTRUTURA DA ÁRVORE -----
    public void imprimirEstrutura() {
        imprimirRec(raiz, "", true);
    }

    private void imprimirRec(No node, String prefixo, boolean isTail) {
        if (node == null)
            return;
        System.out.println(prefixo + (isTail ? "└── " : "├── ") + node.valor);
        if (node.esq != null || node.dir != null) {
            if (node.esq != null && node.dir != null) {
                imprimirRec(node.esq, prefixo + (isTail ? "    " : "│   "), false);
                imprimirRec(node.dir, prefixo + (isTail ? "    " : "│   "), true);
            } else if (node.esq != null) {
                imprimirRec(node.esq, prefixo + (isTail ? "    " : "│   "), true);
            } else {
                imprimirRec(node.dir, prefixo + (isTail ? "    " : "│   "), true);
            }
        }
    }
}

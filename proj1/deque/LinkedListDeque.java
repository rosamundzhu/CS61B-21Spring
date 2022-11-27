package deque;

public class LinkedListDeque<T> implements Deque<T> {

    private TNode sentinel;
    private int size;

    private class TNode {
        public TNode pre;
        public T ts;
        public TNode next;
        public TNode(TNode m, T i, TNode n) {
            pre = m;
            ts = i;
            next = n;
        }
    }

    public LinkedListDeque() {
        sentinel = new TNode(null,null, null);
        size = 0;
    }
//
//    public LinkedListDeque(T i) {
//        sentinel = new TNode(null,null, null);
//        TNode newTNode = new TNode(sentinel, i, sentinel);
//        sentinel.pre = newTNode;
//        sentinel.next = newTNode;
//        size = 1;
//    }

    public void addFirst(T item) {
        TNode newTNode = new TNode(sentinel, item, sentinel.next);
        sentinel.next = newTNode;
        if (size != 0) {
            sentinel.next.pre = newTNode;
        } else {
            sentinel.pre = newTNode;
        }
        size += 1;
    }

    public void addLast(T item) {
        TNode newTNode = new TNode(sentinel.pre, item, sentinel);
        if (size != 0) {
            sentinel.pre.next = newTNode;
        } else {
            sentinel.next = newTNode;
        }
        sentinel.pre = newTNode;
        size += 1;
    }

    public int size() {
        return size;
    }

    public T removeFirst() {
        if (size() > 0) {
            TNode removeNode = sentinel.next;
            sentinel.next = sentinel.next.next;
            size -= 1;
            return removeNode.ts;
        } else {
            return null;
        }
    }

    public T removeLast() {
        if (size() > 0) {
            TNode removeNode = sentinel.pre;
            sentinel.pre = sentinel.pre.pre;
            sentinel.pre.next = sentinel;
            size -= 1;
            return removeNode.ts;
        } else {
            return null;
        }
    }

    public T get(int index) {
        if (index < size()) {
            TNode p = sentinel;
            for (int i = 0; i <= index; i ++) {
                p = p.next;
            }
            return p.ts;
        } else {
            return null;
        }
    }

    public void printDeque() {
        if (size() > 0) {
            TNode p = sentinel;
            for (int i = 0; i < size; i ++) {
                p = p.next;
                System.out.print(p.ts + " ");
            }
            System.out.println();
        }
    }

    public T getRecursive(int r) {
        return getRecursive(r, sentinel);
    }
    private T getRecursive(int r, TNode p) {
        if (r > size() - 1) {
            return null;
        } else if (r == 0) {
            return p.next.ts;
        } else {
            return getRecursive(r - 1, p.next);
        }
    }

    // public Iterator<T> iterator()

    // public boolean equals(Object o)

}

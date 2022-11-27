package deque;
import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T>, Iterable<T> {

    private TNode sentinel;
    private int size;

    private class TNode {
        private TNode pre;
        private T ts;
        private TNode next;
        TNode(TNode m, T i, TNode n) {
            pre = m;
            ts = i;
            next = n;
        }
    }

    public LinkedListDeque() {
        sentinel = new TNode(null, null, null);
        sentinel.pre = sentinel;
        sentinel.next = sentinel;
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
        if (size != 0) {
            sentinel.next.pre = newTNode;
        } else {
            sentinel.pre = newTNode;
        }
        sentinel.next = newTNode;
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
            sentinel.next = removeNode.next;
            removeNode.next.pre = sentinel;
            size -= 1;
            return removeNode.ts;
        } else {
            return null;
        }
    }

    public T removeLast() {
        if (size() > 0) {
            TNode removeNode = sentinel.pre;
            sentinel.pre = removeNode.pre;
            removeNode.pre.next = sentinel;
            size -= 1;
            return removeNode.ts;
        } else {
            return null;
        }
    }

    public T get(int index) {
        if (index < size) {
            TNode p = sentinel;
            for (int i = 0; i <= index; i++) {
                p = p.next;
            }
            return p.ts;
        } else {
            return null;
        }
    }

    public void printDeque() {
        if (size > 0) {
            TNode p = sentinel;
            for (int i = 0; i < size; i++) {
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

    public Iterator<T> iterator() {
        return new LinkedListDeque.LLDequeIterator();
    }

    private class LLDequeIterator implements Iterator<T> {
        TNode pos = sentinel;
        public boolean hasNext() {
            if (size == 0) {
                return false;
            }
            return pos.next != sentinel;
        }
        public T next() {
            T x = pos.next.ts;
            pos = pos.next;
            return x;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof LinkedListDeque)) {
            return false;
        }
        LinkedListDeque oll = (LinkedListDeque) o;
        if (oll.size() != this.size()) {
            return false;
        }
        Iterator<Object> otherIterator = oll.iterator();
        for (final Object a1 : this) {
            // guaranteed to work, because both lists have the same size:
            final Object a2 = otherIterator.next();
//            if (!Objects.equals(a1, a2)) {
//                return false;
//            }
            if (a1 == a2) {
                continue;
            }
            if (a2 == null) {
                return false;
            }
            if (a1.getClass() != a2.getClass()) {
                return false;
            }
            if (!a1.equals(a2))
                return false;
        }
        return true;
    }

//    private boolean deepEquals(Object a1, Object a2) {
//        boolean deq;
//        if (a1 instanceof Deque) {
//            deq = a1.equals(a2);
//        } else {
//            if (a1 == a2) {
//                return true;
//            }
//            return false;
//        }
//        return deq;
//    }
}

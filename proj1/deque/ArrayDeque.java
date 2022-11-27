package deque;

import java.util.Iterator;

/** double-ended queue.
 * Double-ended queues are sequence containers with dynamic sizes that
 * can be expanded or contracted on both ends (either its front or its back).
 */

public class ArrayDeque<T> implements Deque<T>, Iterable<T> {
    private T[] ts;
    private int size;

    private int stposition;
    private int firposition;
    private int lastposition;

    public ArrayDeque() {
        ts = (T[]) new Object[8];
        size = 0;
        stposition = Math.round(ts.length / 2);
        firposition = stposition;
        lastposition = stposition;
    }
    public ArrayDeque(int c) {
        ts = (T[]) new Object[c];
        size = 0;
        stposition = 0;
        firposition = 0;
        lastposition = 0;
    }

    public void addFirst(T item) {
        if (size == ts.length) {
            int capacity = size * 2;
            resize(capacity);
        }
        if (ts[firposition] != null) {
            firposition = (firposition - 1 + ts.length) % ts.length;
        }
        ts[firposition] = item;
        size += 1;
    }

    public T removeFirst() {
        if (size > 0) {
            T x = ts[firposition];
            ts[firposition] = null;
            size -= 1;
            if (size != 0) {
                firposition = (firposition + 1) % ts.length;
            }
            useCheck();
            return x;
        }
        return null;
    }

    public void addLast(T item) {
        if (size == ts.length) {
            resize(ts.length * 2);
        }
        if (ts[lastposition] != null) {
            lastposition = (lastposition + 1) % ts.length;
        }
        ts[lastposition] = item;
        size += 1;
    }

    public T removeLast() {
        if (size() > 0) {
            T x = ts[lastposition];
            ts[lastposition] = null;
            size -= 1;
            if (size != 0) {
                lastposition = (lastposition - 1 + ts.length) % ts.length;
            }
            useCheck();
            return x;
        } else {
            return null;
        }
    }

    public T get(int i) {
        if (size < i | size == 0) {
            return null;
        }
        int pos = (firposition + i) % ts.length;
        return ts[pos];
    }

    public void resize(int capacity) {
        T[] a = (T[]) new Object[capacity];
        System.arraycopy(ts, firposition, a, stposition, size - firposition);
        System.arraycopy(ts, 0, a, stposition + size - firposition, firposition);
        ts = a;
        firposition = stposition;
        lastposition = stposition + size - 1;
    }

    public void useCheck() {
        if ((size < ts.length / 4) && (size > 4)) {
            int m = firposition;
            int n = lastposition;
            int capacity = Math.round(ts.length / 2);
            stposition = Math.round(capacity / 2);
            T[] a = (T[]) new Object[capacity];
            if (m < n) {
                System.arraycopy(ts, m, a, stposition, size);
            } else {
                System.arraycopy(ts, m, a, stposition, ts.length - m);
                System.arraycopy(ts, 0, a,stposition + ts.length - m - 1, n + 1);
            }
            ts = a;
            firposition = stposition;
            lastposition = (stposition + size - 1) % capacity;
        }
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        if (size == 0) {
            return;
        }
        if (firposition < lastposition) {
            for (int i = firposition; i <= lastposition; i++) {
                System.out.print(ts[i]);
            }
        } else {
            for (int i = firposition; i < ts.length; i++) {
                System.out.print(ts[i]);
            }
            for (int i = lastposition; i >= 0; i--) {
                System.out.print(ts[i]);
            }
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }
    private class ArrayDequeIterator implements Iterator<T> {
        private int pos0 = firposition;
        public boolean hasNext() {
            if (size == 0) {
                return false;
            }
            if (firposition < lastposition) {
                if (pos0 < lastposition) {
                    return true;
                }
            } else {
                if (pos0 + 1 < ts.length) {
                    return true;
                }
            }
            return false;
        }
        public T next() {
            T x = ts[pos0];
            pos0 = (pos0 + 1) % ts.length;
            return x;
        }
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (o == null) {
            return false;
        } else if (o.getClass() != ArrayDeque.class) {
            return false;
        }
        ArrayDeque<T> oArray = (ArrayDeque) o;
        if (oArray.size() != this.size()) {
            return false;
        }
        for (int i = 0; i < this.size(); i++) {
            if (oArray.get(i) != this.get(i)) {
                return false;
            }
        }
        return true;
    }
}

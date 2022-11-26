package deque;


// double-ended queue.
// Double-ended queues are sequence containers with dynamic sizes that
//      can be expanded or contracted on both ends (either its front or its back).

public class ArrayDeque<T> {
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

    public void addFirst(T item) {
        if (size == ts.length) {
            int capacity = size * 2;
            resize(capacity);
        }
        if (firposition < 0) {
            firposition = ts.length - 1;
        }
        if (ts[firposition] != null) {
            firposition -= 1;
        }
        ts[firposition] = item;
        firposition -= 1;
        size += 1;
        // useCheck();
    }

    public T removeFirst() {
        if (size() > 0) {
            T x;
            if (firposition + 1 < ts.length) {
                firposition += 1;
            } else {
                firposition = 0;
            }
            x = ts[firposition];
            ts[firposition] = null;
            size -= 1;
            useCheck();
            return x;
        }
        return null;
    }

    public void addLast(T item) {
        if (size == ts.length) {
            resize( ts.length * 2);
        }
        if (lastposition == ts.length) {
            lastposition = 0;
        }
        if (ts[lastposition] != null) {
            lastposition += 1;
        }
        ts[lastposition] = item;
        lastposition += 1;
        size += 1;
        // useCheck();
    }

    public T removeLast() {
        T x;
        if (size() > 0) {
            if (lastposition - 1 < 0){
                lastposition = ts.length - 1;
            } else {
                lastposition -= 1;
            }
            x = ts[lastposition];
            ts[lastposition] = null;
            size -= 1;
            useCheck();
            return x;
        } else {
            return null;
        }
    }

    public T get(int i) {
        if (firposition == stposition) {
            firposition -= 1;
        }
        if (firposition + 1 + i < ts.length) {
            return ts[firposition + 1 + i];
        } else {
            return ts[(firposition + 1 + i) - ts.length];
        }
    }

    public void resize(int capacity) {
        if ( firposition == stposition) {
            firposition -= 1;
        }
        int m = firposition;
        T[] a = (T[]) new Object[capacity];
        System.arraycopy(ts, m + 1, a, stposition, size - m - 1);
        System.arraycopy(ts, 0, a, stposition + size - m - 1, m + 1);
        ts = a;
        firposition = stposition - 1;
        lastposition = stposition + size;
    }

    public void useCheck() {
        if ((size < ts.length / 4) && (size > 4)) {
            if (firposition == stposition) {
                firposition -= 1;
            }
            if (lastposition == stposition) {
                lastposition += 1;
            }
            int m = firposition;
            int n = lastposition;
            int capacity = (int) Math.round(ts.length / 2);
            stposition = (int) Math.round(capacity/2);
            T[] a = (T[]) new Object[capacity];
            if (m < n) {
                System.arraycopy(ts, m + 1, a, stposition, size);
            } else {
                System.arraycopy(ts, m + 1, a, stposition, ts.length - m -1);
                System.arraycopy(ts, 0, a,stposition + ts.length - m - 1, n - 1);
            }
            ts = a;
            firposition = stposition - 1;
            lastposition = stposition + size;
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}

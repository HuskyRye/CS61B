class IntList {
    int first;
    IntList rest;
    public IntList(int f, IntList r) {
        first = f;
        rest = r;
    }
    public static IntList square(IntList L) {
        if (L == null)
            return null;
        return new IntList(L.first * L.first, square(L.rest));
    }

    public static IntList squareDestructive(IntList L) {
        IntList p = L;
        while (p != null) {
            p.first *= p.first;
            p = p.rest;
        }
        return L;
    }

    public static IntList squareIteratively(IntList L) {
        if (L == null)
            return L;
        IntList start = new IntList(L.first * L.first, null);
        IntList last = start;
        IntList p = L.rest;
        while (p != null) {
            last.rest = new IntList(p.first * p.first, null);
            last = last.rest;
            p = p.rest;
        }
        return start;
    }

    public static IntList squareDestructiveRecursively(IntList L) {
        if (L == null)
            return null;
        else {
            L.first *= L.first;
            squareDestructiveRecursively(L.rest);
        }
        return L;
    }
}

import org.junit.Test;
import static org.junit.Assert.*;

public class TestUnionFind {
    @Test
    public void tests(){
        UnionFind u = new UnionFind(5);
        u.union(0, 1);
        u.union(1, 2);
        assertTrue(u.connected(0, 2));
        u.union(3, 4);
        assertFalse(u.connected(0, 4));
        u.union(0, 3);
        assertEquals((int) 1, u.find(3));
    }

    @Test
    public void pathCompressionTest() {
        UnionFind uf = new UnionFind(16);
        uf.union(1, 0);
        uf.union(3, 2);
        uf.union(3, 1);
        uf.union(5, 4);
        uf.union(7, 6);
        uf.union(7, 5);
        uf.union(4, 0);
        uf.connected(7, 3);
        assertEquals(-8, uf.parent(0));
        assertEquals(0, uf.parent(1));
        assertEquals(0, uf.parent(2));
        assertEquals(0, uf.parent(3));
        assertEquals(0, uf.parent(4));
        assertEquals(4, uf.parent(5));
        assertEquals(0, uf.parent(6));
        assertEquals(0, uf.parent(7));
    }
}
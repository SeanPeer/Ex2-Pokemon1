package api;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class Tests {

    @Test
    public void testNode() {
        DWGraph_DS graph = new DWGraph_DS();
        for (int i = 0; i < 10; i++) {
            Node node = new Node(i);
            graph.addNode(node);

        }
        assertEquals(10,graph.nodeSize());
    }

}

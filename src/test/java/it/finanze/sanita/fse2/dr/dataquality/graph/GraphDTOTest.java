package it.finanze.sanita.fse2.dr.dataquality.graph;

import it.finanze.sanita.fse2.dr.dataquality.dto.graph.EdgeDTO;
import it.finanze.sanita.fse2.dr.dataquality.dto.graph.GraphDTO;
import it.finanze.sanita.fse2.dr.dataquality.dto.graph.IGraphResourceDTO;
import it.finanze.sanita.fse2.dr.dataquality.dto.graph.NodeDTO;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GraphDTOTest extends AbstractGraphTest {

    @Test
    void getNode() {
        // Generate graph
        GraphDTO graph = generateGraph();
        // Obtain first node
        NodeDTO source = graph.getNodes().get(0);
        // Verify the same is returned
        NodeDTO target = graph.getNode(source.getId());
        // Assert reference
        assertEquals(source, target, "Object reference mismatch, wrong object returned");
    }

    @Test
    void getEdgesWithSource() {
        // Generate graph
        GraphDTO graph = generateGraph();
        // Obtain first node
        NodeDTO source = graph.getNodes().get(0);
        // Verify the same is returned
        List<EdgeDTO> target = graph.getEdgesWithSource(source);
        // Assert reference
        assertEquals(target.size(), EDGE_SIZE_EACH_NODE, "Edges size mismatch from the expected one");
    }

    @Test
    void getFirstNotTraversedEdgeIsNull() {
        // Generate graph
        GraphDTO graph = generateGraph();
        // Obtain first node
        NodeDTO source = graph.getNodes().get(0);
        // Verify is not returned
        EdgeDTO target = graph.getFirstNotTraversedEdge(source, source, TARGET_PATH);
        // Assert reference
        assertNull(target, "Object reference mismatch, no object should be returned");
    }

    @Test
    void getNotTraversedResource() {
        // Generate random graph
        GraphDTO graph = generateGraph();
        // Verify the same is returned
        List<IGraphResourceDTO> resources = graph.getNotTraversedResources();
        // Assert reference
        assertFalse(resources.isEmpty(), "At least one resource should return");
    }

    @Test
    void getStartNode() {
        // Generate random graph
        GraphDTO graph = generateGraph();
        // Verify the same is returned
        NodeDTO start = graph.getStartNode();
        // Assert reference
        assertNotNull(start, "At least one resource should return");
    }

    @Test
    void setNodeTraversed() {
        // Generate random graph
        GraphDTO graph = generateGraph();
        // Obtain first node
        NodeDTO source = graph.getNodes().get(0);
        // Set as traversed
        graph.setNodeTraversed(source);
        // Check if so
        List<NodeDTO> resources = graph.getNotTraversedNodes();
        // Assert size
        assertEquals(graph.getNodes().size() - 1, resources.size(), "Resource was not set as traversed");
    }

    @Test
    void setEdgeTraversed() {
        // Generate random graph
        GraphDTO graph = generateGraph();
        // Obtain first node
        EdgeDTO source = graph.getEdges().get(0);
        // Set as traversed
        graph.setEdgeTraversed(source);
        // Check if so
        List<EdgeDTO> resources = graph.getNotTraversedEdges();
        // Assert size
        assertEquals(graph.getEdges().size() - 1, resources.size(), "Resource was not set as traversed");
    }

}

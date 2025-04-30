/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 * 
 * Copyright (C) 2023 Ministero della Salute
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package it.finanze.sanita.fse2.dr.dataquality.graph;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.Test;

import it.finanze.sanita.fse2.dr.dataquality.dto.graph.EdgeDTO;
import it.finanze.sanita.fse2.dr.dataquality.dto.graph.GraphDTO;
import it.finanze.sanita.fse2.dr.dataquality.dto.graph.IGraphResourceDTO;
import it.finanze.sanita.fse2.dr.dataquality.dto.graph.NodeDTO;

class GraphDTOTest extends AbstractGraphTest {

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
        assertEquals(EDGE_SIZE_EACH_NODE, target.size(), "Edges size mismatch from the expected one");
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

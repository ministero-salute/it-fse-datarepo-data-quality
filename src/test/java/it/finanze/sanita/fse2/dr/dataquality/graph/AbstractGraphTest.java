package it.finanze.sanita.fse2.dr.dataquality.graph;

import it.finanze.sanita.fse2.dr.dataquality.dto.graph.EdgeDTO;
import it.finanze.sanita.fse2.dr.dataquality.dto.graph.GraphDTO;
import it.finanze.sanita.fse2.dr.dataquality.dto.graph.NodeDTO;
import it.finanze.sanita.fse2.dr.dataquality.dto.graph.ReferenceDTO;
import org.hl7.fhir.r4.model.DocumentReference;
import org.hl7.fhir.r4.model.Patient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static it.finanze.sanita.fse2.dr.dataquality.dto.graph.GraphDTO.START_NODE;

public abstract class AbstractGraphTest {

    public static final int EDGE_SIZE_EACH_NODE = 1;
    public static final String TARGET_REF = "target";
    public static final String TARGET_PATH = "path.test";

    protected GraphDTO generateGraph() {
        List<NodeDTO> nodes = generateNodes();
        List<EdgeDTO> edges = generateEdges(nodes);
        return new GraphDTO(nodes, edges);
    }

    private List<NodeDTO> generateNodes() {
        List<NodeDTO> nodes = new ArrayList<>();
        // Add some patients
        for (int i = 0; i < 4; ++i) {
            Patient p = new Patient();
            p.setId(randomId());
            nodes.add(new NodeDTO(p));
        }
        // Add start node
        DocumentReference ref = new DocumentReference();
        ref.setId(START_NODE + randomId());
        nodes.add(new NodeDTO(ref));

        return nodes;
    }

    private List<EdgeDTO> generateEdges(List<NodeDTO> nodes) {
        List<EdgeDTO> edges = new ArrayList<>();
        for(NodeDTO tmp: nodes) {
            edges.add(
                new EdgeDTO(tmp, new ReferenceDTO(
                    tmp.getResource(), TARGET_REF, TARGET_PATH)
                )
            );
        }
        return edges;
    }

    private String randomId() {
        return String.valueOf(ThreadLocalRandom.current().nextInt(0, 9998 + 1));
    }

}

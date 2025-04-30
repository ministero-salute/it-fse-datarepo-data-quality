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

import static it.finanze.sanita.fse2.dr.dataquality.dto.graph.GraphDTO.START_NODE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.hl7.fhir.r4.model.DocumentReference;
import org.hl7.fhir.r4.model.Patient;

import it.finanze.sanita.fse2.dr.dataquality.dto.SearchParamResourceDTO;
import it.finanze.sanita.fse2.dr.dataquality.dto.SearchParamsResponseDTO;
import it.finanze.sanita.fse2.dr.dataquality.dto.graph.EdgeDTO;
import it.finanze.sanita.fse2.dr.dataquality.dto.graph.GraphDTO;
import it.finanze.sanita.fse2.dr.dataquality.dto.graph.NodeDTO;
import it.finanze.sanita.fse2.dr.dataquality.dto.graph.ReferenceDTO;

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
        for (NodeDTO tmp : nodes) {
            edges.add(
                    new EdgeDTO(tmp, new ReferenceDTO(
                            tmp.getResource(), TARGET_REF, TARGET_PATH)));
        }
        return edges;
    }

    private String randomId() {
        return String.valueOf(ThreadLocalRandom.current().nextInt(0, 9998 + 1));
    }

    protected SearchParamsResponseDTO getSearchParamsResponse() {
        return new SearchParamsResponseDTO(getSearchParamResource(getSearchParams()));
    }

    private List<SearchParamResourceDTO> getSearchParamResource(List<String> path) {
        List<SearchParamResourceDTO> params = new ArrayList<>();
        for (String s : path) {
            SearchParamResourceDTO res = new SearchParamResourceDTO();
            res.setName(s);
            res.setParameters(Collections.singletonList(String.format("null.%s", s.toLowerCase())));
            params.add(res);
        }
        return params;
    }

    private List<String> getSearchParams() {
        return Arrays.asList(
                "Encounter");
    }

}

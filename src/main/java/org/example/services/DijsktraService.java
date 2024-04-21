package org.example.services;

import org.example.exceptions.GraphException;
import org.example.models.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Logger;

@Service
public class DijsktraService {

    private static final Logger LOGGER = Logger.getLogger(DijsktraService.class.getName());


    public ResponseDTO calculate(GraphDTO graph) throws GraphException {
        LOGGER.info("l16");
        String source = graph.getSource();
        String target = graph.getTarget();
        Set<String> nodes = new HashSet<>(graph.getNodes());
        List<EdgeDTO> edges = graph.getEdges();

        // create a map of nodes and their edges
        Map<String, List<String[]>> edgesMap = new HashMap<>();

        String edgeSource = "";
        String edgeTarget = "";
        String edgeValue = "";
        List<String[]> sub;
        for (EdgeDTO edge : edges) {
            edgeSource = edge.getSource();
            edgeTarget = edge.getTarget();
            edgeValue = edge.getValue();
            //check if both source and target are in the set
            if (!nodes.contains(edgeSource) || !nodes.contains(edgeTarget)) {
                throw new GraphException("Source and target nodes do not match");
            }
            if (!edgesMap.containsKey(edgeSource)) {
                sub = new ArrayList<>();

            } else {
                sub = edgesMap.get(edgeSource);

            }
            sub.add(new String[]{edgeTarget, edgeValue});
            edgesMap.put(edgeSource, sub);
        }

        Map<String, Integer> distances = new HashMap<>();
        distances.put(source, 0);
        for (String node : nodes) {
            if (!distances.containsKey(node)) {
                distances.put(node, Integer.MAX_VALUE);
            }
        }

        Set<String> visited = new HashSet<>();
        PriorityQueue<String[]> pq = new PriorityQueue<>((a, b) -> Integer.parseInt(a[1]) - Integer.parseInt(b[1]));
        pq.add(new String[]{source, "0"});

        Map<String, String> path = new HashMap<>();
        LOGGER.info(edgesMap.toString());
        while (!pq.isEmpty()) {
            String[] node = pq.poll();
            LOGGER.info(node[0]);
            // get all adjacent nodes from this node and check if they aren't visited
            if (!visited.contains(node[0])) {
                visited.add(node[0]);
                if (edgesMap.containsKey(node[0])) {
                    for (String[] edge : edgesMap.get(node[0])) {
                        if (distances.get(node[0]) + Integer.parseInt(edge[1]) < distances.get(edge[0])) {
                            distances.put(edge[0], distances.get(node[0]) + Integer.parseInt(edge[1]));
                            path.put(edge[0], node[0]);
                        }
                        if (!visited.contains(edge[0])) {
                            pq.add(new String[]{edge[0], distances.get(edge[0]) + ""});
                        }

                    }
                }

            }
        }

        Map<String, String> pathTail = new HashMap<>();
        if (distances.get(target) == Integer.MAX_VALUE) {
            return new ResponseDTO(new HashMap<>(), new HashMap<>());
        }
        String key = target;
        while (!path.get(key).equals(source)) {
            pathTail.put(key, path.get(key));
            key = path.get(key);
        }

        pathTail.put(key, source);
        LOGGER.info(graph.toString());
        LOGGER.info(distances.toString());
        return new ResponseDTO(pathTail, distances);

    }

}
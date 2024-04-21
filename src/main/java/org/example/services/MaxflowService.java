package org.example.services;

import org.example.exceptions.GraphException;
import org.example.models.EdgeDTO;
import org.example.models.GraphDTO;
import org.example.models.ResponseDTO;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Logger;

@Service
public class MaxflowService {

    private final Logger LOGGER = Logger.getLogger(MaxflowService.class.getName());

    public ResponseDTO calculate(GraphDTO graph) throws GraphException {
        String source = graph.getSource();
        String target = graph.getTarget();
        Set<String> nodes = new HashSet<>(graph.getNodes());
        List<EdgeDTO> edges = graph.getEdges();
        int maxflow = 0;

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
                throw new GraphException("Source and Sink nodes do not match");
            }
            if (!edgesMap.containsKey(edgeSource)) {
                sub = new ArrayList<>();

            } else {
                sub = edgesMap.get(edgeSource);

            }
            sub.add(new String[]{edgeTarget, edgeValue});
            edgesMap.put(edgeSource, sub);
        }

        Map<String, List<String[]>> backEdgeResidualMap = new HashMap<>();


        //we will update the edgesMap as we get augmenting flow
        while( true ){
            List<String> pathToTarget = dfs(edgesMap,source,target,new HashSet<>(), new ArrayList<>());
            if(pathToTarget!=null && !pathToTarget.isEmpty()){
                if( pathToTarget.contains(target) ){

                    //get the min weight along the path
                    int min = Integer.MAX_VALUE;
                    for(int i=1;i<pathToTarget.size();i++){
                        List<String[]> adj = edgesMap.get(pathToTarget.get(i-1));
                        for(String[] arr:adj){
                            if( arr[0].equals( pathToTarget.get(i) ) ){
                                min = Math.min( min, Integer.parseInt( arr[1] ) );
                            }
                        }
                    }
                    maxflow+=min;
                    //update edgeMap
                    for(int i=1;i<pathToTarget.size();i++){
                        List<String[]> adj = edgesMap.get(pathToTarget.get(i-1));
                        List<String[]> adjUpd = new ArrayList<>();
                        for(String[] arr:adj){
                            if( arr[0].equals( pathToTarget.get(i) ) ){
                                if( Integer.parseInt(arr[1])-min > 0 ){
                                    adjUpd.add( new String[]{arr[0],
                                            Integer.toString( Integer.parseInt(arr[1])-min ) } );
                                }
                                List<String[]> revEdges = new ArrayList<>();
                                if(backEdgeResidualMap.containsKey(arr[0])){
                                    revEdges = backEdgeResidualMap.get(arr[0]);
                                    List<String[]> revUpd = new ArrayList<>();
                                    for( String[] revArr:revEdges ){
                                        if( revArr[0].equals( pathToTarget.get(i-1) ) ){
                                            revUpd.add( new String[]{ revArr[0],
                                                    Integer.toString(Integer.parseInt(revArr[1])+min) } );
                                        }else{
                                            revUpd.add(revArr);
                                        }
                                    }
                                    backEdgeResidualMap.put( arr[0], revUpd );
                                }else{
                                    revEdges.add(new String[]{ pathToTarget.get(i-1) , min+""});
                                }
                                backEdgeResidualMap.put(arr[0],revEdges);

                            }
                        }
                        if(!adjUpd.isEmpty()){
                            edgesMap.put(pathToTarget.get(i-1), adjUpd );
                        }else edgesMap.remove( pathToTarget.get(i-1) );
                    }

                }else break;
            }
        }
        for(String[] edge : backEdgeResidualMap.get(source)){
            LOGGER.info(edge[0]+"  value:"+edge[1]);

        }

        LOGGER.info(maxflow+": maxflow found");

        return new ResponseDTO(new HashMap<>(), new HashMap<>());

    }



    public List<String> dfs(Map<String, List<String[]>> edges, String curr,
                            String target, Set<String> visited, List<String> path){
        if(visited.contains(curr) || visited.contains(target)){
            return path;
        }else{
            visited.add(curr);

            if(curr.equals(target)){
                path.add(curr);
                return path;
            }else{
                path.add(curr);
                List<String[]> adj = edges.get(curr);
                for(String[] edge : adj){
                    if( !visited.contains(edge[0]) ){

                        path = dfs(edges,edge[0], target, visited, path);
                    }

                }
            }

        }
        return path;
    }
}

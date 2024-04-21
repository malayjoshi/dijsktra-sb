package org.example.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MaxflowCommon {
    private int maxflow = 0;
    private Map<String, Map<String,Integer>> edgeMap;
    private Map<String, Map<String,Integer>> backEdgeMap;
}

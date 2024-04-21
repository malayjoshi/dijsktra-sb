package org.example.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class GraphDTO {

    private String calculationType;

    private String source;

    private String target;

    private List<String> nodes;

    private List<EdgeDTO> edges;
}






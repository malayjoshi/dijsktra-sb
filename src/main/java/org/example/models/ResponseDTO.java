package org.example.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO {
    private Map<String,String> path;
    private Map<String, Integer> distances;
}

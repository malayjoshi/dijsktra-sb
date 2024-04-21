package org.example.models;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class EdgeDTO {
    @NonNull
    private String source;
    @NonNull
    private String target;
    @NonNull
    private String value;
}


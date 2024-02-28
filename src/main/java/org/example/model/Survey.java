package org.example.model;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Survey {

    private Integer surveyId;
    private String name;
    private Integer expectedCompletes;
    private Integer completionPoints;
    private Integer filteredPoints;
}

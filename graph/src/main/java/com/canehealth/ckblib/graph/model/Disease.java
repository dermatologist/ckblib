package com.canehealth.ckblib.graph.model;

@Node
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Disease {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String content;

    @Id
    private final String title;

    @Property("tagline")
    private final String description;

    @Relationship(type = "ACTED_IN", direction = INCOMING)
    private Map<PersonEntity, Roles> actorsAndRoles = new HashMap<>();

    @Relationship(type = "DIRECTED", direction = INCOMING)
    private List<PersonEntity> directors = new ArrayList<>();

    @CreatedDate
    private LocalDateTime createdDate;
}

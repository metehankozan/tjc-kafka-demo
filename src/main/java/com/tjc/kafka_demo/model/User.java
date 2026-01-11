package com.tjc.kafka_demo.model;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String id;
    private String userName;
    private Integer age;

}

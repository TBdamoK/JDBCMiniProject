package kz.bitlab.springboot.jdbcpractice.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Course {
    private Long id;
    private String name;
    private String description;
    private int price;
}

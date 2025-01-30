package kz.bitlab.springboot.jdbcpractice.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApplicationRequest {
    private Long id;
    private String userName;
    private String commentary;
    private String phone;
    private boolean handled;
    Course course;
}

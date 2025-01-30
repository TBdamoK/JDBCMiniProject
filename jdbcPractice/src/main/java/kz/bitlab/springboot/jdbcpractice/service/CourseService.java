package kz.bitlab.springboot.jdbcpractice.service;

import kz.bitlab.springboot.jdbcpractice.model.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CourseService {
    private final Connection connection;

    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();

        try {

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM t_courses");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Course course = Course
                        .builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
                        .description(resultSet.getString("description"))
                        .price(resultSet.getInt("price"))
                        .build();

                courses.add(course);
            }

            statement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return courses;
    }

    public Course getCourseById(Long id) {
        Course course = null;

        try {

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM t_courses WHERE id = ?");

            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                course = Course
                        .builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
                        .description(resultSet.getString("description"))
                        .price(resultSet.getInt("price"))
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return course;
    }
}

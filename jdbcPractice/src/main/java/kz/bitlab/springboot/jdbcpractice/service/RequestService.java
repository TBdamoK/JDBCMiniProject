package kz.bitlab.springboot.jdbcpractice.service;

import kz.bitlab.springboot.jdbcpractice.model.ApplicationRequest;
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
public class RequestService {
    private final Connection connection;

    public boolean addRequest(ApplicationRequest r) {

        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO t_application_request (id, user_name, commentary, phone, handled, course_id) VALUES (DEFAULT,?,?,?,DEFAULT,?);");

            statement.setString(1, r.getUserName());
            statement.setString(2, r.getCommentary());
            statement.setString(3, r.getPhone());
            statement.setLong(4, r.getCourse().getId());

            statement.executeUpdate();
            statement.close();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<ApplicationRequest> getAllRequests() {
        List<ApplicationRequest> requestArrayList = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT r.id AS request_id, r.user_name, r.commentary, r.phone, r.handled, r.course_id AS course_id, c.name AS course_name, c.description AS course_description, c.price AS course_price FROM t_application_request AS r INNER JOIN t_courses AS c ON r.course_id = c.id;");

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                ApplicationRequest applicationRequest = ApplicationRequest
                                                        .builder()
                                                        .id(resultSet.getLong("request_id"))
                                                        .userName(resultSet.getString("user_name"))
                                                        .commentary(resultSet.getString("commentary"))
                                                        .phone(resultSet.getString("phone"))
                                                        .handled(resultSet.getBoolean("handled"))
                                                        .course(
                                                                Course
                                                                .builder()
                                                                .id(resultSet.getLong("course_id"))
                                                                .name(resultSet.getString("course_name"))
                                                                .description(resultSet.getString("course_description"))
                                                                .price(resultSet.getInt("course_price"))
                                                                .build()
                                                        )
                                                        .build();

                requestArrayList.add(applicationRequest);
            }

            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return requestArrayList;
    }

    public ApplicationRequest getRequestById(Long id) {
        ApplicationRequest applicationRequest = null;

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT r.id AS request_id, r.user_name, r.commentary, r.phone, r.handled, r.course_id AS course_id, c.name AS course_name, c.description AS course_description, c.price AS course_price FROM t_application_request AS r INNER JOIN t_courses AS c ON r.course_id = c.id WHERE r.id=?;");

            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){
                applicationRequest = ApplicationRequest
                        .builder()
                        .id(resultSet.getLong("request_id"))
                        .userName(resultSet.getString("user_name"))
                        .commentary(resultSet.getString("commentary"))
                        .phone(resultSet.getString("phone"))
                        .handled(resultSet.getBoolean("handled"))
                        .course(
                                Course
                                        .builder()
                                        .id(resultSet.getLong("course_id"))
                                        .name(resultSet.getString("course_name"))
                                        .description(resultSet.getString("course_description"))
                                        .price(resultSet.getInt("course_price"))
                                        .build()
                        )
                        .build();

                statement.close();

            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return applicationRequest;
    }

    public boolean updateRequest(ApplicationRequest r) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE t_application_request SET user_name=?,commentary=?,phone=?,handled=?,course_id=? WHERE id=?");
            statement.setString(1, r.getUserName());
            statement.setString(2, r.getCommentary());
            statement.setString(3, r.getPhone());
            statement.setBoolean(4, r.isHandled());
            statement.setLong(5, r.getCourse().getId());
            statement.setLong(6, r.getId());

            statement.executeUpdate();

            statement.close();

            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteRequest(Long id) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM t_application_request WHERE id = ?");

            statement.setLong(1, id);

            statement.executeUpdate();

            statement.close();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

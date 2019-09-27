package com.example.security_demo.repository;

import com.example.security_demo.model.Role;
import com.example.security_demo.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepository {

  private final JdbcTemplate jdbcTemplate;

  private final PasswordEncoder passwordEncoder;

  public User loadUserByEmail(String username) {
    String sql = "SELECT * FROM customer "
        + "WHERE email = ? and enable";

    return jdbcTemplate.queryForObject(sql, new Object[]{username}, getUserRowMapper());
  }

  private RowMapper<User> getUserRowMapper() {
    return (rs, rowNum) -> User.builder()
        .userId(rs.getInt("user_id"))
        .email(rs.getString("email"))
        .password(rs.getString("password"))
        .firstName(rs.getString("first_name"))
        .lastName(rs.getString("last_name"))
        .enable(rs.getBoolean("enable"))
        .role(Role.valueOf(rs.getString("role")))
        .build();
  }

  public void saveUser(User user) {
    String sql = "INSERT INTO CUSTOMER " +
        "(email, password, first_name,last_name,role, enable ) VALUES (?, ?, ?, ?,?,?)";

    int update = jdbcTemplate
        .update(sql, user.getEmail(), passwordEncoder.encode(user.getPassword()), user.getFirstName(), user.getLastName(), Role.USER.name(),
            user.getEnable());

    if (update != 1) {
      throw new RuntimeException("failed to update customer table");
    }
  }
}

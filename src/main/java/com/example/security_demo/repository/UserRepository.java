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

  private static final String GET_ALL_USERS = "SELECT * FROM users WHERE email = ? and enable";

  private static final String INSERT_USER = "INSERT INTO users (email, password, first_name,last_name,role, enable ) VALUES (?, ?, ?, ?,?,?)";

  private static final String ENABLE_USER = "UPDATE users SET enable = true WHERE users.email = ?";

  private static final String UPDATE_PASSWORD = "UPDATE users SET password = ? WHERE users.email = ?";

  private final JdbcTemplate jdbcTemplate;

  private final PasswordEncoder passwordEncoder;

  public User loadUserByEmail(String email) {
    User user = jdbcTemplate.queryForObject(GET_ALL_USERS, new Object[]{email}, getUserRowMapper());
    if (user == null) {
      throw new RuntimeException("Failed to execute query");
    }
    return user;
  }


  public void saveUser(User user) {
    int update = jdbcTemplate
        .update(INSERT_USER, user.getEmail(),
            passwordEncoder.encode(user.getPassword()),
            user.getFirstName(),
            user.getLastName(),
            Role.USER.name(),
            user.getEnable());
    if (update != 1) {
      throw new RuntimeException("Failed to update users table");
    }
  }

  public void enableUser(String email) {
    int update = jdbcTemplate.update(ENABLE_USER, email);
    if (update != 1) {
      throw new RuntimeException("Failed to enable users table");
    }
  }

  public void updatePassword(String email, String newPassword) {
    int update = jdbcTemplate.update(UPDATE_PASSWORD, passwordEncoder.encode(newPassword), email);
    if (update != 1) {
      throw new RuntimeException("Failed to update users table");
    }
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
}

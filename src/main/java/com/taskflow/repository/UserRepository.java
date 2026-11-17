package com.taskflow.repository;

import com.taskflow.entity.Role;
import com.taskflow.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**a
     * Tìm user theo email
     */
    Optional<User> findByEmail(String email);

    /**
     * Tìm user theo username
     */
    Optional<User> findByUsername(String username);

    /**
     * Kiểm tra email đã tồn tại chưa
     */
    boolean existsByEmail(String email);

    /**
     * Kiểm tra username đã tồn tại chưa
     */
    boolean existsByUsername(String username);

    /**
     * Tìm danh sách user theo role
     */
    List<User> findByRole(Role role);

    /**
     * Tìm username gần đúng
     */
    List<User> findByUsernameContainingIgnoreCase(String keyword);

    /**
     * Xóa user theo email
     */
    void deleteByEmail(String email);
}
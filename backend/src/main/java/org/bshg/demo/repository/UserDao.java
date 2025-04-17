package org.bshg.demo.repository;
import org.bshg.demo.entity.core.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
public interface UserDao extends JpaRepository<User, Long> {
int deleteByIdIn(List<Long> ids);
}
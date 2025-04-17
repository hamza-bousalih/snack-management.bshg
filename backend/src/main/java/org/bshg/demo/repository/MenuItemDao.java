package org.bshg.demo.repository;
import org.bshg.demo.entity.core.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
public interface MenuItemDao extends JpaRepository<MenuItem, Long> {
int deleteByIdIn(List<Long> ids);
@Query("SELECT NEW MenuItem(item.id,item.name) FROM MenuItem item")
List<MenuItem> findAllOptimized();
}
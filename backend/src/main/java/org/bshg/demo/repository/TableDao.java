package org.bshg.demo.repository;
import org.bshg.demo.entity.core.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
public interface TableDao extends JpaRepository<Table, Long> {
int deleteByIdIn(List<Long> ids);
}
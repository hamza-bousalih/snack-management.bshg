package org.bshg.demo.services.impl;
import org.bshg.demo.entity.core.Table;
import org.bshg.demo.repository.TableDao;
import org.bshg.demo.services.facade.TableService;
import org.bshg.demo.zutils.service.ServiceHelper;
import org.bshg.demo.zutils.pagination.Pagination;
import org.bshg.demo.exceptions.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.ArrayList;
@Service
public class TableServiceImpl implements TableService {
//--------------- FIND -------------------------------------
public Table findById(Long id) {
return repository.findById(id).orElse(null);
}
public List<Table> findAll() {
return repository.findAll();
}
public List<Table> findAllOptimized() {
return findAll();
}
@Override
public Pagination<Table> findPaginated(int page, int size) {
var pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");
var found = repository.findAll(pageable);
return new Pagination<>(found);
}
//--------------- CREATE -----------------------------------
@Transactional(rollbackFor = Exception.class)
public Table create(Table item) {
if (item == null) return null;
return repository.save(item);
}
@Transactional(rollbackFor = Exception.class)
public List<Table> create(List<Table> items) {
List<Table> result = new ArrayList<>();
if (items == null || items.isEmpty()) return result;
items.forEach(it -> result.add(create(it)));
return result;
}
//--------------- UPDATE -----------------------------------
@Transactional(rollbackFor = Exception.class)
public Table update(Table item) {
if (item == null || item.getId() == null) return null;
var oldItem = findById(item.getId());
if (oldItem == null) throw new NotFoundException("Unknown Table To Be Updated!");
return repository.save(item);
}
@Transactional(rollbackFor = Exception.class)
public List<Table> update(List<Table> items) {
List<Table> result = new ArrayList<>();
if (items == null || items.isEmpty()) return result;
items.forEach(it -> result.add(update(it)));
return result;
}
//--------------- DELETE -----------------------------------
@Transactional(rollbackFor = Exception.class)
public void deleteById(Long id) {
Table item = findById(id);
if (item != null) delete(item);
}
@Transactional(rollbackFor = Exception.class)
public void delete(Table item) {
repository.delete(item);
}
@Transactional(rollbackFor = Exception.class)
public void delete(List<Table> items) {
if (items == null || items.isEmpty()) return;
items.forEach(this::delete);
}
@Transactional(rollbackFor = Exception.class)
public void deleteByIdIn(List<Long> ids) {
repository.deleteByIdIn(ids);
}
//--------------- FIND AND DELETE BYs ----------------------
//----------------------------------------------------------
//----------------------------------------------------------
@Autowired private TableDao repository;
}
package org.bshg.demo.services.impl;
import org.bshg.demo.entity.core.Order;
import org.bshg.demo.repository.OrderDao;
import org.bshg.demo.services.facade.OrderService;
import org.bshg.demo.entity.core.User;
import org.bshg.demo.services.facade.UserService;
import org.bshg.demo.entity.core.Table;
import org.bshg.demo.services.facade.TableService;
import org.bshg.demo.entity.core.OrderItem;
import org.bshg.demo.services.facade.OrderItemService;
import org.bshg.demo.entity.core.Bill;
import org.bshg.demo.services.facade.BillService;
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
public class OrderServiceImpl implements OrderService {
//--------------- FIND -------------------------------------
public Order findById(Long id) {
return repository.findById(id).orElse(null);
}
public List<Order> findAll() {
return repository.findAll();
}
public List<Order> findAllOptimized() {
return findAll();
}
@Override
public Pagination<Order> findPaginated(int page, int size) {
var pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");
var found = repository.findAll(pageable);
return new Pagination<>(found);
}
//--------------- CREATE -----------------------------------
@Transactional(rollbackFor = Exception.class)
public Order create(Order item) {
if (item == null) return null;
// check if table exists
var table = item.getTable();
if (table != null) {
if(table.getId() == null) item.setTable(null);
else {
var found = tableService.findById(table.getId());
if (found == null) throw new NotFoundException("Unknown Given Table");
item.setTable(found);
}
}
createAssociatedObject(item);
Order saved = repository.save(item);
createAssociatedList(saved);
return saved;
}
@Transactional(rollbackFor = Exception.class)
public List<Order> create(List<Order> items) {
List<Order> result = new ArrayList<>();
if (items == null || items.isEmpty()) return result;
items.forEach(it -> result.add(create(it)));
return result;
}
//--------------- UPDATE -----------------------------------
@Transactional(rollbackFor = Exception.class)
public Order update(Order item) {
if (item == null || item.getId() == null) return null;
var oldItem = findById(item.getId());
if (oldItem == null) throw new NotFoundException("Unknown Order To Be Updated!");
// update creator
var creator = item.getCreator();
var oldCreator = oldItem.getCreator();
if (oldCreator == null) {
if (creator != null) userService.create(creator);
} else {
// if (creator == null) userService.delete(oldCreator);
if (creator != null) {
creator.setId(oldCreator.getId());
userService.update(creator);
}
}
Order saved = repository.save(item);
updateAssociatedList(saved);
return saved;
}
@Transactional(rollbackFor = Exception.class)
public List<Order> update(List<Order> items) {
List<Order> result = new ArrayList<>();
if (items == null || items.isEmpty()) return result;
items.forEach(it -> result.add(update(it)));
return result;
}
//--------------- DELETE -----------------------------------
@Transactional(rollbackFor = Exception.class)
public void deleteById(Long id) {
Order item = findById(id);
if (item != null) delete(item);
}
@Transactional(rollbackFor = Exception.class)
public void delete(Order item) {
deleteAssociated(item);
repository.delete(item);
}
@Transactional(rollbackFor = Exception.class)
public void delete(List<Order> items) {
if (items == null || items.isEmpty()) return;
items.forEach(this::delete);
}
@Transactional(rollbackFor = Exception.class)
public void deleteByIdIn(List<Long> ids) {
ids.forEach(id -> {
Order item = findById(id);
if (item != null) {
deleteAssociated(item);
}
});
repository.deleteByIdIn(ids);
}
//--------------- FIND AND DELETE BYs ----------------------
@Override
@Transactional(rollbackFor = Exception.class)
public int deleteByCreatorId(Long id){
Order found = findByCreatorId(id);
if (found == null) return 0;
this.deleteAssociated(found);
return repository.deleteByCreatorId(id);
}
@Override
public Order findByCreatorId(Long id){
return repository.findByCreatorId(id);
}
@Override
@Transactional(rollbackFor = Exception.class)
public int deleteByTableId(Long id){
if (id == null) return 0;
List<Order> found = findByTableId(id);
if (found == null) return 0;
found.forEach(this::deleteAssociated);
return repository.deleteByTableId(id);
}
@Override
public List<Order> findByTableId(Long id){
return repository.findByTableId(id);
}
//----------------------------------------------------------
public void createAssociatedObject(Order item) {
if (item == null) return;
ServiceHelper.createObject(item, Order::getCreator, userService::create);
}
public void createAssociatedList(Order item) {
if (item == null || item.getId() == null) return;
ServiceHelper.createList(item, Order::getOrderItem, OrderItem::setOrder, orderItemService::create);
}
public void updateAssociatedList(Order item) {
if (item == null || item.getId() == null) return;
ServiceHelper.updateList(
item, orderItemService.findByOrderId(item.getId()),
item.getOrderItem(), OrderItem::setOrder,
orderItemService::update,
orderItemService::delete
);
}
@Transactional(rollbackFor = Exception.class)
public void deleteAssociated(Order item) {
deleteAssociatedList(item);
deleteAssociatedObjects(item);
}
public void deleteAssociatedList(Order item) {
orderItemService.deleteByOrderId(item.getId());
}
public void deleteAssociatedObjects(Order item) {
ServiceHelper.nullifyInContainer(item.getId(), billService::findByOrderId, Bill::setOrder, (Bill value) -> billService.update(value));
ServiceHelper.deleteById(item.getCreator(), User::getId, this.userService::deleteById);
}
//----------------------------------------------------------
@Autowired private OrderDao repository;
@Lazy @Autowired private UserService userService;
@Lazy @Autowired private TableService tableService;
@Lazy @Autowired private OrderItemService orderItemService;
@Lazy @Autowired private BillService billService;
}
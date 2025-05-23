package org.bshg.demo.webservice.controller;
import org.bshg.demo.entity.core.OrderItem;
import org.bshg.demo.services.facade.OrderItemService;
import org.bshg.demo.webservice.converter.OrderItemConverter;
import org.bshg.demo.webservice.dto.OrderItemDto;
import org.bshg.demo.zutils.pagination.Pagination;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import java.util.List;
@RestController
@RequestMapping("orderitem")
public class OrderItemApi {
@Autowired private OrderItemService service;
@Autowired private OrderItemConverter converter;
@GetMapping("/id/{id}")
public ResponseEntity<OrderItemDto> findById(@PathVariable Long id) {
var result = service.findById(id);
var resultDto = converter.toDto(result);
return ResponseEntity.ok(resultDto);
}
@GetMapping
public ResponseEntity<List<OrderItemDto>> findAll() {
var result = service.findAll();
var resultDto = converter.toDto(result);
return ResponseEntity.ok(resultDto);
}
@GetMapping("/optimized")
public ResponseEntity<List<OrderItemDto>> findAllOptimized() {
var result = service.findAllOptimized();
var resultDto = converter.toDto(result);
return ResponseEntity.ok(resultDto);
}
@GetMapping("/paginated")
public ResponseEntity<Pagination<OrderItemDto>> findPaginated(
@RequestParam(name = "page", defaultValue = "0", required = false) int page,
@RequestParam(name = "size", defaultValue = "10", required = false) int size
) {
var result = service.findPaginated(page, size);
return ResponseEntity.ok(result.convert(converter::toDto));
}
@PostMapping
public ResponseEntity<OrderItemDto> save(@RequestBody OrderItemDto dto) {
if (dto == null) return ResponseEntity.status(HttpStatus.CONFLICT).build();
var item = converter.toItem(dto);
var result = service.create(item);
var resultDto = converter.toDto(result);
return ResponseEntity.ok(resultDto);
}
@PostMapping("/all")
public ResponseEntity<List<OrderItemDto>> save(@RequestBody List<OrderItemDto> dtos) {
if (dtos == null) return ResponseEntity.status(HttpStatus.CONFLICT).build();
var item = converter.toItem(dtos);
var result = service.create(item);
var resultDto = converter.toDto(result);
return ResponseEntity.ok(resultDto);
}
@PutMapping()
public ResponseEntity<OrderItemDto> update(@RequestBody OrderItemDto dto) {
if (dto == null) return ResponseEntity.status(HttpStatus.CONFLICT).build();
var item = converter.toItem(dto);
var result = service.update(item);
var resultDto = converter.toDto(result);
return ResponseEntity.ok(resultDto);
}
@PutMapping("/all")
public ResponseEntity<List<OrderItemDto>> update(@RequestBody List<OrderItemDto> dtos) {
if (dtos == null) return ResponseEntity.status(HttpStatus.CONFLICT).build();
var item = converter.toItem(dtos);
var result = service.update(item);
var resultDto = converter.toDto(result);
return ResponseEntity.ok(resultDto);
}
@DeleteMapping
public ResponseEntity<OrderItemDto> delete(@RequestBody OrderItemDto dto) {
if (dto == null) return ResponseEntity.status(HttpStatus.CONFLICT).build();
var item = converter.toItem(dto);
service.delete(item);
return ResponseEntity.ok(dto);
}
@DeleteMapping("/all")
public ResponseEntity<List<OrderItemDto>> delete(@RequestBody List<OrderItemDto> dtos) {
if (dtos == null) return ResponseEntity.status(HttpStatus.CONFLICT).build();
var item = converter.toItem(dtos);
service.delete(item);
return ResponseEntity.ok(dtos);
}
@DeleteMapping("/id/{id}")
public ResponseEntity<Long> deleteById(@PathVariable Long id) {
service.deleteById(id);
return ResponseEntity.ok(id);
}
@DeleteMapping("/ids")
public ResponseEntity<List<Long>> deleteByIdIn(@RequestParam("id") List<Long> ids) {
service.deleteByIdIn(ids);
return ResponseEntity.ok(ids);
}
@DeleteMapping("/order/id/{id}")
public ResponseEntity<Long> deleteByOrderId(@PathVariable Long id){
service.deleteByOrderId(id);
return ResponseEntity.ok(id);
}
@GetMapping("/order/id/{id}")
public ResponseEntity<List<OrderItemDto>> findByOrderId(@PathVariable Long id){
var result = service.findByOrderId(id);
var resultDto = converter.toDto(result);
return ResponseEntity.ok(resultDto);
}
@DeleteMapping("/menuitem/id/{id}")
public ResponseEntity<Long> deleteByMenuItemId(@PathVariable Long id){
service.deleteByMenuItemId(id);
return ResponseEntity.ok(id);
}
@GetMapping("/menuitem/id/{id}")
public ResponseEntity<List<OrderItemDto>> findByMenuItemId(@PathVariable Long id){
var result = service.findByMenuItemId(id);
var resultDto = converter.toDto(result);
return ResponseEntity.ok(resultDto);
}
}
package org.bshg.demo.webservice.controller;
import org.bshg.demo.entity.core.Order;
import org.bshg.demo.services.facade.OrderService;
import org.bshg.demo.webservice.converter.OrderConverter;
import org.bshg.demo.webservice.dto.OrderDto;
import org.bshg.demo.zutils.pagination.Pagination;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import java.util.List;
@RestController
@RequestMapping("order")
public class OrderApi {
@Autowired private OrderService service;
@Autowired private OrderConverter converter;
@GetMapping("/id/{id}")
public ResponseEntity<OrderDto> findById(@PathVariable Long id) {
var result = service.findById(id);
var resultDto = converter.toDto(result);
return ResponseEntity.ok(resultDto);
}
@GetMapping
public ResponseEntity<List<OrderDto>> findAll() {
var result = service.findAll();
var resultDto = converter.toDto(result);
return ResponseEntity.ok(resultDto);
}
@GetMapping("/optimized")
public ResponseEntity<List<OrderDto>> findAllOptimized() {
var result = service.findAllOptimized();
var resultDto = converter.toDto(result);
return ResponseEntity.ok(resultDto);
}
@GetMapping("/paginated")
public ResponseEntity<Pagination<OrderDto>> findPaginated(
@RequestParam(name = "page", defaultValue = "0", required = false) int page,
@RequestParam(name = "size", defaultValue = "10", required = false) int size
) {
var result = service.findPaginated(page, size);
return ResponseEntity.ok(result.convert(converter::toDto));
}
@PostMapping
public ResponseEntity<OrderDto> save(@RequestBody OrderDto dto) {
if (dto == null) return ResponseEntity.status(HttpStatus.CONFLICT).build();
var item = converter.toItem(dto);
var result = service.create(item);
var resultDto = converter.toDto(result);
return ResponseEntity.ok(resultDto);
}
@PostMapping("/all")
public ResponseEntity<List<OrderDto>> save(@RequestBody List<OrderDto> dtos) {
if (dtos == null) return ResponseEntity.status(HttpStatus.CONFLICT).build();
var item = converter.toItem(dtos);
var result = service.create(item);
var resultDto = converter.toDto(result);
return ResponseEntity.ok(resultDto);
}
@PutMapping()
public ResponseEntity<OrderDto> update(@RequestBody OrderDto dto) {
if (dto == null) return ResponseEntity.status(HttpStatus.CONFLICT).build();
var item = converter.toItem(dto);
var result = service.update(item);
var resultDto = converter.toDto(result);
return ResponseEntity.ok(resultDto);
}
@PutMapping("/all")
public ResponseEntity<List<OrderDto>> update(@RequestBody List<OrderDto> dtos) {
if (dtos == null) return ResponseEntity.status(HttpStatus.CONFLICT).build();
var item = converter.toItem(dtos);
var result = service.update(item);
var resultDto = converter.toDto(result);
return ResponseEntity.ok(resultDto);
}
@DeleteMapping
public ResponseEntity<OrderDto> delete(@RequestBody OrderDto dto) {
if (dto == null) return ResponseEntity.status(HttpStatus.CONFLICT).build();
var item = converter.toItem(dto);
service.delete(item);
return ResponseEntity.ok(dto);
}
@DeleteMapping("/all")
public ResponseEntity<List<OrderDto>> delete(@RequestBody List<OrderDto> dtos) {
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
@DeleteMapping("/creator/id/{id}")
public ResponseEntity<Long> deleteByCreatorId(@PathVariable Long id){
service.deleteByCreatorId(id);
return ResponseEntity.ok(id);
}
@GetMapping("/creator/id/{id}")
public ResponseEntity<OrderDto> findByCreatorId(@PathVariable Long id){
var result = service.findByCreatorId(id);
var resultDto = converter.toDto(result);
return ResponseEntity.ok(resultDto);
}
@DeleteMapping("/table/id/{id}")
public ResponseEntity<Long> deleteByTableId(@PathVariable Long id){
service.deleteByTableId(id);
return ResponseEntity.ok(id);
}
@GetMapping("/table/id/{id}")
public ResponseEntity<List<OrderDto>> findByTableId(@PathVariable Long id){
var result = service.findByTableId(id);
var resultDto = converter.toDto(result);
return ResponseEntity.ok(resultDto);
}
}
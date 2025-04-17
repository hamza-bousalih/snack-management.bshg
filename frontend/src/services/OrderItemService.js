import BaseService from 'src/services/base/BaseService'

export default class OrderItemService extends BaseService {
  constructor() {
    super("orderItem");
  }

  async findAll() {
    return await this.GET('');
  }

  async findById(id) {
    return await this.GET(`/id/${id}`);
  }

  async findAllOptimized() {
    return await this.GET(`/optimized`);
  }

  async findPaginated(page = 0, size = 10) {
    return await this.GET(`/paginated?page=${page}&size=${size}`);
  }

  async create(body) {
    return await this.POST('', body);
  }

  async createList(body) {
    return await this.POST(`/all`, body);
  }

  async update(body) {
    return await this.PUT('', body);
  }

  async updateList(body) {
    return await this.PUT(`/all`, body);
  }

  async delete(body) {
    return await this.DELETE('', body);
  }

  async deleteAll(body) {
    return await this.DELETE('', body);
  }

  async deleteById(id) {
    return await this.DELETE(`/id/${id}`);
  }

  async deleteByOrderId(id) {
    return await this.DELETE(`/order/${id}`);
  }

  async findByOrderId(id) {
    return await this.GET(`/order/${id}`);
  }
  async deleteByMenuItemId(id) {
    return await this.DELETE(`/menuitem/${id}`);
  }

  async findByMenuItemId(id) {
    return await this.GET(`/menuitem/${id}`);
  }
}

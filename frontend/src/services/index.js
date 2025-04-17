import AuthService from "src/services/auth/AuthService";
import UserService from "src/services/UserService";
import TableService from "src/services/TableService";
import OrderItemService from "src/services/OrderItemService";
import OrderService from "src/services/OrderService";
import BillService from "src/services/BillService";
import MenuItemService from "src/services/MenuItemService";

export default {
  authService: new AuthService(),
  userService: new UserService(),
  tableService: new TableService(),
  orderItemService: new OrderItemService(),
  orderService: new OrderService(),
  billService: new BillService(),
  menuItemService: new MenuItemService(),
}

export interface OrderItemRequest {
  itemId: string;
  name: string;
  price: number;
  quantity: number;
}
export interface OrderRequest {
  customerName: string;
  phoneNumber: string;
  cartItems: OrderItemRequest[];
  subtotal: number;
  tax: number;
  grandTotal: number;
  paymentMethod: string; // Use 'CASH' or 'UPI'
}
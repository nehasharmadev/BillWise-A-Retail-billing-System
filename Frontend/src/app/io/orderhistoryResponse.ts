export interface Item {
  itemId: string;
  name: string;
  price: number;
  quantity: number;
}

// export interface PaymentDetails {
//   razorpayOrderId: string | null;
//   razorpayPaymentId: string | null;
//   razorpaySignature: string | null;
//   status: string; // e.g., "COMPLETED"
// }

export interface Order {
  orderId: string;
  customerName: string;
  phoneNumber: string;
  items: Item[];
  subtotal: number;
  tax: number;
  grandTotal: number;
  paymentMethod: string;
  createdAt: string; 
  paymentDetails: any; 
  showDetails?: boolean; 
}

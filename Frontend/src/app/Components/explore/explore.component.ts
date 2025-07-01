
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ItemService } from '../../Services/item.service';
import { FormsModule } from '@angular/forms';
import { CategoryServiceComponent } from '../../Services/category-service/category-service.component';
import { OrderItemRequest } from '../../io/OrderRequest';
import { OrderRequest } from '../../io/OrderRequest';
import { OrderhistoryService } from '../../Services/orderhistory.service';
import { InvoiceComponent } from '../invoice/invoice.component';
import { ToastrService } from 'ngx-toastr';
@Component({
  selector: 'app-explore',
  templateUrl: './explore.component.html',
  styleUrls: ['./explore.component.css'],
  imports:[CommonModule, FormsModule, InvoiceComponent ]
})
export class ExploreComponent implements OnInit {
  categories: string[] = [];
  items: any= [];
  selectedItems: any= [];
  selectedCategory: string = '';
  customerName: string = '';
  mobileNumber: string = '';
  selectedPaymentMethod : string = 'CASH';
  showInvoice = false;
  currentOrder: any={};
  invoiceNumber = 'INV-' + Math.floor(Math.random() * 1000000);
  constructor( private categoryService:  CategoryServiceComponent, private itemService: ItemService, private orderService:OrderhistoryService, private toast:ToastrService ) {}

  ngOnInit(): void {
    //fetch categories
    this.categoryService.getAllCategory().subscribe({
      next:(res)=>{
        res.map((cat:any)=>{
          this.categories.push(cat.name)
        })
      }
    });

   //fetch items

   this.itemService.getItems().subscribe({
    next:(res)=>{
       this.items = res;
    }
   })
  }

//   paymentMethod(method:string){
// this.selectedPaymentMethod = method;
// if(this.selectedPaymentMethod == "UPI"){
//   let details = new FormData();
//   details.append("amount", this.currentOrder.)
//   this.orderService.createUpiOrder()
// }
//   }
  selectCategory(category: string): void {
    this.selectedCategory = category;
  }

  // addItem(item: any): void {
  //   this.selectedItems.push(item);
  //   console.log("added item :", item)
  // }

  addItem(item: any): void {
  const existingItem = this.selectedItems.find((i: any) => i.itemId === item.itemId);
  
  if (existingItem) {
    // If the item already exists, increase the quantity
    existingItem.quantity += 1;
  } else {
    // If the item does not exist, add it with quantity 1
    this.selectedItems.push({ ...item, quantity: 1 });
  }
  
  console.log("Added item:", this.selectedItems);
}

 removeItem(item: any): void {
  const existingItem = this.selectedItems.find((i: any) => i.itemId === item.itemId);
  
  if (existingItem) {
    if (existingItem.quantity > 1) {
      // Decrease the quantity if more than 1
      existingItem.quantity -= 1;
    } else {
      // Remove the item if quantity is 1
      this.selectedItems = this.selectedItems.filter((i: any) => i.itemId !== item.itemId);
    }
  }
}
 
  calculateSubtotal(): number {
  return this.selectedItems.reduce((sum:number, item:any) => sum + (item.price*item.quantity), 0);
}

calculateTax(): number {
  return this.calculateSubtotal() * 0.01; // 1% tax
}

calculateTotal(): number {
  return this.calculateSubtotal() + this.calculateTax();
}


  // placeOrder(): void {
  //   console.log('Order placed:', {
  //     customerName: this.customerName,
  //     mobileNumber: this.mobileNumber,
  //     items: this.selectedItems,
  //     total: this.calculateTotal()
  //   });

  //   const orderItems: OrderItemRequest[] = this.selectedItems.map((item:any) => ({
  //     itemId: item.itemId, 
  //     name: item.name,
  //     price: item.price,
  //     quantity: item.quantity
  //   }));
  //   const orderRequest: OrderRequest = {
  //     customerName: this.customerName,
  //     phoneNumber: this.mobileNumber,
  //     cartItems: orderItems,
  //     subtotal: this.calculateSubtotal(),
  //     tax: this.calculateTax(),
  //     grandTotal: this.calculateTotal(),
  //     paymentMethod: this.selectedPaymentMethod 
  //   };
  //   console.log("orderRequest:" , orderRequest)
  //   this.orderService.addOrder(orderRequest).subscribe({
  //     next:(res)=>{
  //       console.log(res)
  //       this.toast.success("Order successfull");
  //        this.showInvoice = true;
  //        this.currentOrder= res;
  //        console.log("currect order = ", this.currentOrder);
  //     },
  //     error:(err)=>{
  //       this.toast.error("error while making the order");
  //     }
  //   })


  // }
  placeOrder(): void {
  console.log('Order placed:', {
    customerName: this.customerName,
    mobileNumber: this.mobileNumber,
    items: this.selectedItems,
    total: this.calculateTotal()
  });

  const orderItems: OrderItemRequest[] = this.selectedItems.map((item: any) => ({
    itemId: item.itemId,
    name: item.name,
    price: item.price,
    quantity: item.quantity
  }));

  const orderRequest: OrderRequest = {
    customerName: this.customerName,
    phoneNumber: this.mobileNumber,
    cartItems: orderItems,
    subtotal: this.calculateSubtotal(),
    tax: this.calculateTax(),
    grandTotal: this.calculateTotal(),
    paymentMethod: this.selectedPaymentMethod
  };

  console.log("orderRequest:", orderRequest);

  // If UPI is selected, create the UPI order first
  if (this.selectedPaymentMethod === "UPI") {
    const details = new FormData();
    // details.append("amount", this.calculateTotal().toString());
    details.append("amount", (this.calculateTotal() * 100).toFixed(0));
    details.append("currency", "INR");

    this.orderService.createUpiOrder(details).subscribe({
      next: (razorpayOrderResponse:any) => {
        console.log("UPI Order created successfully:", razorpayOrderResponse);
        // Proceed to place the order after UPI order is created


//new code

 const options = {
        key: '', // Enter the Key ID generated from the Dashboard
        amount: razorpayOrderResponse.amount, // Amount is in currency subunits. Default currency is INR.
        currency: 'INR',
        name: 'Billwise',
        description: 'Test Transaction',
        order_id: razorpayOrderResponse.id, // Pass the order ID obtained from the createOrder API
        handler: (response: any) => {
          // Handle successful payment here
          // this.verifyPayment(response, razorpayOrderResponse.id, orderRequest);\\
          console.log(response);
        },
        prefill: {
          name: this.customerName,
          email: 'customer@example.com', // You can replace this with the actual email
          contact: this.mobileNumber
        },
        notes: {
          address: 'Customer Address'
        },
        theme: {
          color: '#F37254'
        }
      };
      const razorpay = new Razorpay(options);
      razorpay.open();
    


//new code ends


        this.orderService.addOrder(orderRequest).subscribe({
          next: (res) => {
            console.log(res);
            this.toast.success("Order successful");
            this.showInvoice = true;
            this.currentOrder = res;
            console.log("current order =", this.currentOrder);
          },
          error: (err) => {
            this.toast.error("Error while making the order");
          }
        });
      },
      error: (error) => {
        console.error("Error creating UPI order:", error);
        this.toast.error("Error creating UPI order");
      }
    });
  } else {
    // If not UPI, directly place the order
    this.orderService.addOrder(orderRequest).subscribe({
      next: (res) => {
        console.log(res);
        this.toast.success("Order successful");
        this.showInvoice = true;
        this.currentOrder = res;
        console.log("current order =", this.currentOrder);
      },
      error: (err) => {
        this.toast.error("Error while making the order");
      }
    });
  }
}

 closeInvoice(): void {
    this.showInvoice = false;
    // Optional: Reset the form
    this.selectedItems = [];
    this.customerName = '';
    this.mobileNumber = '';
  }
   
}

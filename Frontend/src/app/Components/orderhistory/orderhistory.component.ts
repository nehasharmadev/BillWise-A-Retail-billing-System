// import { Component } from '@angular/core';

// @Component({
//   selector: 'app-orderhistory',
//   imports: [],
//   templateUrl: './orderhistory.component.html',
//   styleUrl: './orderhistory.component.css'
// })
// export class OrderhistoryComponent {

// }

import { Component, OnInit } from '@angular/core';
import { OrderhistoryService } from '../../Services/orderhistory.service';
import { Order } from '../../io/orderhistoryResponse';
import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
@Component({
  selector: 'app-orderhistory',
  imports: [CommonModule, FormsModule],
  templateUrl: './orderhistory.component.html',
  styleUrl: './orderhistory.component.css'
})
export class OrderhistoryComponent {
  orders: Order[] = []; // Array to hold order history

  constructor(private orderService: OrderhistoryService, private http:HttpClient, private toast:ToastrService) {}

  ngOnInit(): void {
    this.fetchOrderHistory(); // Fetch order history on component initialization
  }

  fetchOrderHistory(): void {
    this.orderService.getOrder().subscribe({
      next: (orders) => {
        // Add showDetails property to each order
        this.orders = orders.map((order:any) => ({
          ...order,
          showDetails: false // Initialize showDetails to false
        }));

        console.log(this.orders)
      },
      error: (error) => {
        console.error('Error fetching order history:', error);
      }
    });
  }

  toggleDetails(orderId: string): void {
    const order = this.orders.find(o => o.orderId === orderId);
    if (order) {
      order.showDetails = !order.showDetails; // Toggle the showDetails property
    }
  }


  subscriptionEmail: string = '';

subscribe() {
  this.http.post('http://localhost:8080/api/v1.0/subscribe', { email: this.subscriptionEmail },{
  withCredentials: true
})
    .subscribe({
      next: (res) => {

        console.log(res);
        this.toast.success("Subscribed successfully!")
      },
      error: (err) => {
  if (err.status === 409) {
    this.toast.error("You're already subscribed!");
  } else if (err.status === 400) {
    this.toast.error("Please enter a valid email.");
  } else {
    console.log(err);
    this.toast.error("Subscription failed.");
  }
}
    });
}

unsubscribe() {
  this.http.post('http://localhost:8080/api/v1.0/unsubscribe', { email: this.subscriptionEmail },{
  withCredentials: true
})
    .subscribe({
      next: () => this.toast.success("Unsubscribed successfully!"),
      error: () => this.toast.error("Unsubscription failed")
    });
}
}

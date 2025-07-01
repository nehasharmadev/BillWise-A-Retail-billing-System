import { Component, Input } from '@angular/core';
// import { OrderRequest } from '../order-request.model';
import { OrderRequest } from '../../io/OrderRequest';
import { CommonModule } from '@angular/common';
import { OrderItemRequest } from '../../io/OrderRequest';
@Component({
  selector: 'app-invoice',
  templateUrl: './invoice.component.html',
  styleUrls: ['./invoice.component.css'],
  imports:[CommonModule]
})
export class InvoiceComponent {
  @Input() order: any;
  @Input() invoiceNumber: string = '';
  currentDate = new Date();

  printInvoice(): void {
    console.log(this.order.items);
    window.print();
  }
}

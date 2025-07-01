import { Injectable } from '@angular/core';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Observable } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class OrderhistoryService {

  BaseUrl : string = 'http://localhost:8080/api/v1.0';
  constructor(private http: HttpClient) { }

  addOrder(formData:any){
    return this.http.post<any>(`${this.BaseUrl}/orders`, formData);
  }

  getOrder(){
    return this.http.get<any>(`${this.BaseUrl}/orders/latest`);
  }

  deleteOrder(id:string){
    return this.http.delete<any>(`${this.BaseUrl}/orders/admin/${id}`);
  }

  // createUpiOrder(details:any){
  //   return this.http.post<any>('http://localhost:8080/api/v1.0/create-order', details);
  // }
  createUpiOrder(orderDetails: any) {
     return this.http.post<any>('http://localhost:8080/api/v1.0/create-order', orderDetails);
   }
}

import { Injectable } from '@angular/core';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ItemRequest } from '../io/ItemRequest';
import { ItemResponse } from '../io/ItemResponse';
@Injectable({
  providedIn: 'root'
})
export class ItemService {
  BaseUrl : string = 'http://localhost:8080/api/v1.0';
  constructor(private http: HttpClient) { }

  addItem(itemRequest: ItemRequest, file: File): Observable<ItemResponse>{
    const formData: FormData = new FormData();
    formData.append('item', JSON.stringify(itemRequest)); 
    formData.append('file', file);
     return this.http.post<any>(`${this.BaseUrl}/admin/addItem`, formData);
  }

  getItems():Observable<ItemResponse>{
    return this.http.get<any>(`${this.BaseUrl}/getItems`);
  }

  deleteItem(id:string){
    return this.http.delete<any>(`${this.BaseUrl}/admin/deleteItem/${id}`);
  }
}

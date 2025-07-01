import { Component } from '@angular/core';
import { Injectable } from '@angular/core';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn:'root'
})
export class CategoryServiceComponent {
  BaseUrl : string = 'http://localhost:8080/api/v1.0';
  constructor( private http : HttpClient){}

  addCategory(formData : any) : Observable<any>{
    console.log("add category request made");
    const response =  this.http.post(`${this.BaseUrl}/admin/addCategory`, formData);
    console.log(response);
    return response;
  }

  getAllCategory() : Observable<any>{
      console.log("get all request made");
      const response = this.http.get(`${this.BaseUrl}/getCategories`);
      console.log(response);
      return response;
  }

  deleteCategory(id:any) : Observable<any>{
    console.log("Deleting the category");
    return this.http.delete(`${this.BaseUrl}/admin/deleteCategory/${id}`);
  }
}

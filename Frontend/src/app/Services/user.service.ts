import { Injectable } from '@angular/core';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Observable } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http:HttpClient) { }

 BaseUrl : string = 'http://localhost:8080/api/v1.0';

 registeUser(user:any):Observable<any>{
  const response = this.http.post<any>(`${this.BaseUrl}/admin/register`, user);
  console.log(response);
  return response;
 }

 getAllUsers():Observable<any>{
  return this.http.get<any>(`${this.BaseUrl}/admin/users`);
 
 }

 deleteUser(id:string):Observable<any>{
  return this.http.delete<any>(`${this.BaseUrl}/admin/users/${id}`)
 }
}

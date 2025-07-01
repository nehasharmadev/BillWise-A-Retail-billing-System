import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, tap , throwError} from 'rxjs';
import { Router } from '@angular/router';
import { catchError, switchMap } from 'rxjs/operators';
// import { JwtHelperService } from '@auth0/angular-jwt';
import { jwtDecode } from 'jwt-decode';
// import * as jwt_decode from 'jwt-decode';
@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private readonly API_URL = 'http://localhost:8080/api/v1.0';
  private readonly TOKEN_KEY = 'auth_token';
  private readonly ROLE_KEY = 'user_role';
  // private tokenSubject = new BehaviorSubject<string>(this.getToken());
  // private roleSubject = new BehaviorSubject<string>(this.getRole());
   constructor(
    private http: HttpClient,
    private router: Router,
    // private jwtHelper: JwtHelperService
  ) {}

  login(email: string, password: string): Observable<any> {
    return this.http.post<any>(`${this.API_URL}/login`, { email, password }, { withCredentials: true } ).pipe(
      tap(response => {
        this.storeAuthData(response.token, response.role, response.refreshToken);
      })
    );
  }

   private storeAuthData(token: string, role: string, refreshToken:string): void {
    localStorage.setItem(this.TOKEN_KEY, token);
    localStorage.setItem(this.ROLE_KEY, role);
    localStorage.setItem("refreshToken", refreshToken);
    // this.tokenSubject.next(token);
    // this.roleSubject.next(role);
  }

  refreshToken(): Observable<any> {
  const refreshToken = localStorage.getItem('refreshToken');
  return this.http.post<any>(`${this.API_URL}/refresh`, {}, { withCredentials: true }).pipe(
    tap(response => {
      this.storeAuthData(response.token, response.role, response.refreshToken); // Update tokens
    })
  );
}

// isTokenExpired(token: string): boolean {
//   if (!token) return true;
//   const decoded: any = jwtDecode(token); 
//   const expirationTime = decoded.exp * 1000; // Convert to milliseconds
//   return Date.now() >= expirationTime; // Check if the current time is past the expiration time
// }
// private handleApiCallWithRefresh<T>(apiCall: Observable<T>): Observable<T> {
//   return apiCall.pipe(
//     catchError(error => {
//       if (error.status === 401) { 
//         return this.refreshToken().pipe(
//           switchMap(() => apiCall) 
//         );
//       }
//       return throwError(error); 
//     })
//   );
// }
  getToken(): string {
    return localStorage.getItem(this.TOKEN_KEY) || '';
  }
  getRole(): string {
    return localStorage.getItem(this.ROLE_KEY) || '';
  }
  getRefreshToken(): string {
    return localStorage.getItem("refreshToken") || '';
  }
  //  getTokenSubject(): BehaviorSubject<string> {
  //   return this.tokenSubject;
  // }
  // getRoleSubject(): BehaviorSubject<string> {
  //   return this.roleSubject;
  // }
  // isAuthenticated(): boolean {
  //   const token = this.getToken();
  //   return !!token && !this.jwtHelper.isTokenExpired(token);
  // }

    isAdmin(): boolean {
    return this.getRole() === 'ADMIN';
  }
  logout(): void {
    localStorage.removeItem(this.TOKEN_KEY);
    localStorage.removeItem(this.ROLE_KEY);
    localStorage.removeItem("refreshToken");
    // this.tokenSubject.next('');
    // this.roleSubject.next('');
    this.router.navigate(['/login']);
  }
  getAuthHeaders(): { [header: string]: string } {
    return {
      'Authorization': `Bearer ${this.getToken()}`
    };
  }

  
}

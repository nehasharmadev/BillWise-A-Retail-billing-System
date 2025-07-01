// import { Injectable } from '@angular/core';
// import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
// @Injectable({
//   providedIn: 'root'
// })
// export class AuthGuard implements CanActivate {
//   constructor(private router: Router) {}
//   canActivate(
//     route: ActivatedRouteSnapshot,
//     state: RouterStateSnapshot): boolean {
    
//     const userRole = localStorage.getItem('user_role'); // Assuming you store the role in local storage
//     // Check if the user is an admin
//     if (userRole === 'ADMIN') {
//       return true; // Admin can access all routes
//     }
//      // Check if the user is a regular user
//     if (userRole === 'USER') {
//       const allowedRoutes = ['/dashboard', '/explore', '/logout'];
//       if (allowedRoutes.includes(state.url)) {
//         return true; // User can access these routes
//       }
//     }
//     // If the user is not authorized, redirect to a different page (e.g., login)
//     this.router.navigate(['/login']);
//     return false;
//   }
// }


import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(private router: Router) {}
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    
    const userRole = localStorage.getItem('user_role');
    console.log("got the user", userRole);
      if (state.url === '/create-order') {
      return true; // Allow access to this route
    }
    // If no role, redirect to login
    if (!userRole) {
      this.router.navigate(['/login']);
      return false;
    } 
      const adminRoutes = [
      '/manage-items',
      '/manage-categories',
      '/manage-users'
    ];
    // If user is USER and trying to access admin route, deny access
    if (userRole === 'USER' && adminRoutes.includes(state.url)) {
      this.router.navigate(['/dashboard']); // or '/unauthorized'
      return false;
    }
    // For all other cases, allow access
    return true;
  }
}
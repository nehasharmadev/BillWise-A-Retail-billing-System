
// import { Injectable } from '@angular/core';
// import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
// import { Observable } from 'rxjs';
// import { tap } from 'rxjs';
// import { HttpResponse } from '@angular/common/http';
// @Injectable()
// export class AuthInterceptor implements HttpInterceptor {
//   intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
//     console.log('AuthInterceptor: Intercepting request');
//     const token = localStorage.getItem('auth_token') || '';
//     console.log("token:",token);
//     const authReq = req.clone({
//       setHeaders: {
//         Authorization: `Bearer ${token}`
//       }
//     });
//     // const authReq = req.clone({
//     //   req.headers.append('Authorization',token);
//     // })
//      return next.handle(authReq).pipe(
//     tap(event => {
//       if (event instanceof HttpResponse) {
//         console.log('AuthInterceptor: Response received', event);
//       }
//     })
//   );
//   }
// }

import { HttpInterceptorFn } from '@angular/common/http';
import { AuthService } from '../Services/auth.service';
import { HttpRequest, HttpHandlerFn, HttpErrorResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { catchError, switchMap, throwError, Observable } from 'rxjs';

// export const authInterceptor: HttpInterceptorFn = (req, next) => {
//   const token = localStorage.getItem('auth_token') || '';
//   if (token) {
//     console.log("token found!!");
//     const authReq = req.clone({
//       setHeaders: {
//         Authorization: `Bearer ${token}`
//       }
//     });
//     return next(authReq);
//   }
  
//   return next(req);
// };

export const authInterceptor: HttpInterceptorFn = (req: HttpRequest<any>, next: HttpHandlerFn): Observable<any> => {
  const authService = inject(AuthService);
  const token = authService.getToken();

  // let authReq = req;
  let authReq = req.clone({
  withCredentials: true  // ⬅️ include cookies
});
  if (token) {
    authReq = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
  }

  return next(authReq).pipe(
    catchError((error: HttpErrorResponse) => {
      if (error.status === 401 && !req.url.includes('/refresh') && !req.url.includes('/login')) {
        // Token might be expired, try to refresh it
        return authService.refreshToken().pipe(
          switchMap(() => {
            const newToken = authService.getToken();
            const retryReq = req.clone({
              setHeaders: {
                Authorization: `Bearer ${newToken}`
              },
              withCredentials: true 
            });
            return next(retryReq); // Retry the original request
          }),
          catchError(err => {
            console.log("error while creating refresh token");
            console.log(err);
            authService.logout(); // If refresh fails, force logout
            return throwError(() => err);
          })
        );
      }
      return throwError(() => error);
    })
  );
};

import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { provideHttpClient , HTTP_INTERCEPTORS, withInterceptors} from '@angular/common/http';

import { provideAnimations } from '@angular/platform-browser/animations';
import { provideToastr } from 'ngx-toastr';

import { authInterceptor } from './Intercept/auth.interceptor';
// import { AuthInterceptor } from './Intercept/auth.interceptor';

import { withInterceptorsFromDi } from '@angular/common/http';

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    //  provideHttpClient(withInterceptorsFromDi(),
         provideHttpClient(withInterceptors([authInterceptor])),
// AuthInterceptor,
    //  {
    //   provide: HTTP_INTERCEPTORS,
    //   useClass: AuthInterceptor,
    //   multi: true 
    // },
    provideAnimations(), 
    provideToastr({
      timeOut: 3000,
      positionClass: 'toast-top-center',
      preventDuplicates: true,
      progressBar: true,
    })]
};

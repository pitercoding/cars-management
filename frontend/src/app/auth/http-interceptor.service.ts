import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, throwError } from 'rxjs';

export const myhttpInterceptor: HttpInterceptorFn = (request, next) => {
  let router = inject(Router);

  let token = localStorage.getItem('token');

  console.log('[HTTP INTERCEPTOR] Attaching JWT token to Authorization header');
  if (token && !router.url.includes('/login')) {
    request = request.clone({
      setHeaders: { Authorization: 'Bearer ' + token },
    });
  }

  return next(request).pipe(
    catchError((err: any) => {
      if (err instanceof HttpErrorResponse) {
        console.log('[HTTP INTERCEPTOR] HTTP error intercepted', err.status);

        if (err.status === 401) {
          window.alert('[HTTP INTERCEPTOR] 401 Unauthorized - redirecting to login.');
          router.navigate(['/login']);
        } else if (err.status === 403) {
          console.warn('[HTTP INTERCEPTOR] 403 Forbidden', err.error);
        } else {
          console.error('HTTP error:', err);
        }
      } else {
        console.error('An error occurred:', err);
      }

      return throwError(() => err);
    })
  );
};

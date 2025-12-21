import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, throwError } from 'rxjs';
import Swal from 'sweetalert2';

export const myhttpInterceptor: HttpInterceptorFn = (request, next) => {
  const router = inject(Router);
  const token = localStorage.getItem('token');

  if (token && !router.url.includes('/login')) {
    request = request.clone({ setHeaders: { Authorization: 'Bearer ' + token } });
  }

  return next(request).pipe(
    catchError((err: any) => {
      if (err instanceof HttpErrorResponse) {
        const msg = err.error?.message || 'An error occurred';
        if (err.status === 401) {
          Swal.fire('Unauthorized', msg, 'warning');
          router.navigate(['/login']);
        } else if (err.status === 403) {
          Swal.fire('Forbidden', msg, 'warning');
        } else {
          Swal.fire('HTTP Error', msg, 'error');
        }
        console.error('[HTTP INTERCEPTOR]', err);
      } else {
        Swal.fire('Error', 'An unexpected error occurred', 'error');
        console.error(err);
      }
      return throwError(() => err);
    })
  );
};

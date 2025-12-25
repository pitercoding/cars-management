import { HttpErrorResponse, HttpInterceptorFn } from "@angular/common/http";
import { inject } from "@angular/core";
import { Router } from "@angular/router";
import { catchError, throwError } from "rxjs";
import Swal from "sweetalert2";

export const myhttpInterceptor: HttpInterceptorFn = (request, next) => {
  const router = inject(Router);
  const token = localStorage.getItem('token');

  if (token && !request.url.includes('/api/login')) {
    request = request.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`,
      },
    });
  }

  return next(request).pipe(
    catchError((err: HttpErrorResponse) => {
      if (err.status === 401) {
        localStorage.removeItem('token');
        Swal.fire('Unauthorized', 'Please login again', 'warning')
          .then(() => router.navigate(['/login']));
      } else if (err.status === 403) {
        Swal.fire('Forbidden', 'You do not have permission', 'warning');
      }
      return throwError(() => err);
    })
  );
};

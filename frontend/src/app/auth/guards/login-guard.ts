import { CanActivateFn, CanActivateChildFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import Swal from 'sweetalert2';
import { LoginService } from '../services/login.service';

export const loginGuard: CanActivateFn = (route, state) => {
  const loginService = inject(LoginService);
  const router = inject(Router);

  if (loginService.hasRole('STANDARD_USER') && state.url === '/admin/users') {
    Swal.fire('Access Denied', 'You do not have permission to access this route.', 'warning')
      .then(() => router.navigate(['/admin/cars']));
    return false;
  }
  return true;
};

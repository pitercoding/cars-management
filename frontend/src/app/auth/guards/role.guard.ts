import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import Swal from 'sweetalert2';
import { LoginService } from '../services/login.service';

export const adminGuard: CanActivateFn = () => {
  const loginService = inject(LoginService);
  const router = inject(Router);

  if (!loginService.hasRole('ADMIN')) {
    Swal.fire('Access Denied', 'Admin access only', 'warning')
      .then(() => router.navigate(['/admin/cars']));
    return false;
  }

  return true;
};

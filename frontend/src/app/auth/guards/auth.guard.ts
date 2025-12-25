import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import Swal from 'sweetalert2';
import { LoginService } from '../services/login.service';

export const authGuard: CanActivateFn = () => {
  const loginService = inject(LoginService);
  const router = inject(Router);

  if (!loginService.isLoggedIn()) {
    Swal.fire('Session expired', 'Please login again', 'warning')
      .then(() => router.navigate(['/login']));
    return false;
  }

  return true;
};

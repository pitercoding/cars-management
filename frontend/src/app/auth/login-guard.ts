import { inject } from '@angular/core';
import { CanActivateFn } from '@angular/router';
import { LoginService } from './login.service';

export const loginGuard: CanActivateFn = (route, state) => {

  let loginService = inject(LoginService);

  if(loginService.hasRole("STANDARD_USER") && state.url == '/admin/users') {
    alert('You do not have permission to acess this route');
    return false;
  }
  return true;
};

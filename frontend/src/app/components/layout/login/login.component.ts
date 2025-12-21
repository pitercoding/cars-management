import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { MdbFormsModule } from 'mdb-angular-ui-kit/forms';
import { LoginService } from '../../../auth/services/login.service';
import { Login } from '../../../auth/login';
import Swal from 'sweetalert2';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-login',
  imports: [MdbFormsModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class LoginComponent {
  loginAttempt: Login = { username: '', password: '' };

  router = inject(Router);
  loginService = inject(LoginService);

  constructor() {
    this.loginService.removeToken();
  }

  logon() {
    this.loginService.login(this.loginAttempt).subscribe({
      next: (token) => {
        if (token) {
          this.loginService.addToken(token);
          Swal.fire('Success', 'Logged in successfully!', 'success');
          this.router.navigate(['/admin/cars']);
        }
      },
      error: (err: HttpErrorResponse) => {
        const msg =
          err.error?.message ||
          (err.status === 401
            ? 'Invalid username or password.'
            : err.status === 403
            ? 'Access denied.'
            : err.status === 0 ? 'Unable to connect to the server.' : 'An unexpected error occurred.');
        Swal.fire('Login Error', msg, 'error');
        console.error('[LOGIN] Authentication error:', err);
      },
    });
  }
}

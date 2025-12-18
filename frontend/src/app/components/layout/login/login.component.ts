import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { MdbFormsModule } from 'mdb-angular-ui-kit/forms';
import { LoginService } from '../../../auth/login.service';
import { Login } from '../../../auth/login';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-login',
  imports: [MdbFormsModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class LoginComponent {
  loginAttempt: Login = {
    username: '',
    password: '',
  };

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
          this.router.navigate(['/admin/cars']);
        }
      },
      error: (err: HttpErrorResponse) => {
        if (err.status === 401) {
          alert('Invalid username or password.');
        } else if (err.status === 403) {
          alert('Access denied.');
        } else if (err.status === 0) {
          alert('Unable to connect to the server. Please try again later.');
        } else {
          alert('An unexpected error occurred. Please try again.');
        }

        console.error('[LOGIN] Authentication error:', err);
      },
    });
  }
}

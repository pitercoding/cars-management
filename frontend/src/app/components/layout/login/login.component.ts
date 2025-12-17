import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { MdbFormsModule } from 'mdb-angular-ui-kit/forms';
import { LoginService } from '../../../auth/login.service';
import { Login } from '../../../auth/login';

@Component({
  selector: 'app-login',
  imports: [MdbFormsModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class LoginComponent {
  loginAttempt: Login = {
    username: '',
    password: ''
  };

  router = inject(Router);
  loginService = inject(LoginService);

  logon() {
    this.loginService.login(this.loginAttempt).subscribe({
      next: token => {
        if (token) {
          this.loginService.addToken(token);
          this.router.navigate(['/admin/cars']);
        } else {
          alert('Incorrect user or password!');
        }
      },
      error: () => alert('ERROR!')
    });
  }
}

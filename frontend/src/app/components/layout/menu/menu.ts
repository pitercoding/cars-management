import { Component, inject } from '@angular/core';
import { RouterLink, RouterModule } from '@angular/router';
import { MdbCollapseModule } from 'mdb-angular-ui-kit/collapse';
import { LoginService } from '../../../auth/services/login.service';
import { User } from '../../../auth/user';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-menu',
  imports: [MdbCollapseModule, RouterModule, RouterLink],
  templateUrl: './menu.html',
  styleUrl: './menu.scss',
})
export class Menu {
  loginService = inject(LoginService);
  user: User;

  constructor() {
    this.user = this.loginService.getCurrentUser() ?? {
      id: 0,
      username: '',
      fullName: '',
      password: '',
      role: '',
    };
  }

  async changePassword() {
    const { value: passwords } = await Swal.fire({
      title: 'Change Password',
      html:
        `<input id="new-password" type="password" class="swal2-input" placeholder="New password">` +
        `<input id="confirm-password" type="password" class="swal2-input" placeholder="Confirm password">`,
      focusConfirm: false,
      showCancelButton: true,
      confirmButtonText: 'Change',
      confirmButtonColor: '#499b75ff',
      cancelButtonColor: '#b44f59ff',
      preConfirm: () => {
        const newPassword = (document.getElementById('new-password') as HTMLInputElement).value;
        const confirmPassword = (document.getElementById('confirm-password') as HTMLInputElement)
          .value;

        if (!newPassword || !confirmPassword) {
          Swal.showValidationMessage('All fields are required');
          return;
        }

        if (newPassword.length < 4) {
          Swal.showValidationMessage('Password must be at least 4 characters');
          return;
        }

        if (newPassword !== confirmPassword) {
          Swal.showValidationMessage('Passwords do not match');
          return;
        }

        return newPassword;
      },
    });

    if (!passwords) return;

    this.loginService.updatePassword(passwords).subscribe({
      next: async () => {
        await Swal.fire({
          icon: 'success',
          title: 'Password changed',
          text: 'Please login again.',
          confirmButtonText: 'OK',
        });

        this.loginService.removeToken();
        window.location.href = '/login';
      },
      error: () => Swal.fire('Error', 'Could not update password', 'error'),
    });
  }
}

import { Component, inject } from '@angular/core';
import { RouterLink, RouterModule } from '@angular/router';
import { MdbCollapseModule } from 'mdb-angular-ui-kit/collapse';
import { LoginService } from '../../../auth/services/login.service';
import { User } from '../../../auth/user';

@Component({
  selector: 'app-menu',
  imports: [MdbCollapseModule, RouterModule, RouterLink],
  templateUrl: './menu.html',
  styleUrl: './menu.scss',
})
export class Menu {
  loginService = inject(LoginService);
  user!: User;

  constructor() {
    const currentUser = this.loginService.getCurrentUser();
    if (currentUser) {
      this.user = currentUser;
    } else {
      this.user = { id: 0, username: '', fullName: '', password: '', role: '' };
    }
  }
}

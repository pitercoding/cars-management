import { Component, EventEmitter, inject, Input, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MdbFormsModule } from 'mdb-angular-ui-kit/forms';
import { User } from '../../user';
import { UserService } from '../../services/user.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-users-details',
  standalone: true,
  imports: [MdbFormsModule, FormsModule],
  templateUrl: './users-details.html',
  styleUrl: './users-details.scss',
})
export class UsersDetails {
  @Input() user: User = new User();
  @Output() return = new EventEmitter<void>();

  private userService = inject(UserService);

  saveUser(): void {
    if (this.user.id) {
      this.update();
    } else {
      this.create();
    }
  }

  private create(): void {
    this.userService.postUser(this.user).subscribe({
      next: () => {
        Swal.fire('Saved successfully!', '', 'success');
        this.return.emit();
      },
      error: (err) => {
        const msg = err.error?.message || 'Failed to create this user.';
        Swal.fire('Error', msg, 'error');
      },
    });
  }

  private update(): void {
    this.userService.updateUser(this.user.id!, this.user).subscribe({
      next: () => {
        Swal.fire('Successfully edited!', '', 'success');
        this.return.emit();
      },
      error: (err) => {
        const msg = err.error?.message || 'Failed to update this user.';
        Swal.fire('Error', msg, 'error');
      },
    });
  }
}

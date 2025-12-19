import { CommonModule } from '@angular/common';
import { Component, inject, TemplateRef, ViewChild } from '@angular/core';
import { MdbFormsModule } from 'mdb-angular-ui-kit/forms';
import { MdbModalModule, MdbModalRef, MdbModalService } from 'mdb-angular-ui-kit/modal';
import { MdbRippleModule } from 'mdb-angular-ui-kit/ripple';
import { MdbValidationModule } from 'mdb-angular-ui-kit/validation';
import { UsersDetails } from '../users-details/users-details';
import { User } from '../../user';
import { UserService } from '../../services/user.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-users-list',
  standalone: true,
  imports: [
    CommonModule,
    MdbRippleModule,
    MdbModalModule,
    MdbFormsModule,
    MdbValidationModule,
    UsersDetails,
  ],
  templateUrl: './users-list.html',
  styleUrl: './users-list.scss',
})
export class UsersList {
  list: User[] = [];
  userEdit!: User;

  @ViewChild('modalUsersList') modalUsersList!: TemplateRef<any>;
  modalRef!: MdbModalRef<any>;

  private modalService = inject(MdbModalService);
  private userService = inject(UserService);

  constructor() {
    this.getAllUsers();
  }

  getAllUsers(): void {
    this.userService.getAllUsers().subscribe({
      next: (users) => (this.list = users),
      error: () => Swal.fire('Failed to retrieve the user list.', '', 'error'),
    });
  }

  new(): void {
    this.userEdit = new User();
    this.userEdit.role = 'ROLE_STANDARD_USER';
    this.modalRef = this.modalService.open(this.modalUsersList);
  }

  edit(user: User): void {
    this.userEdit = { ...user };
    this.modalRef = this.modalService.open(this.modalUsersList);
  }

  deleteById(user: User): void {
    Swal.fire({
      title: 'Are you sure?',
      icon: 'warning',
      showConfirmButton: true,
      showCancelButton: true,
      confirmButtonText: 'Yes',
    }).then((result) => {
      if (result.isConfirmed) {
        this.userService.deleteUser(user.id!).subscribe({
          next: () => {
            this.list = this.list.filter((b) => b.id !== user.id);
            Swal.fire('Successfully deleted!', '', 'success');
          },
          error: () => Swal.fire('Failed to delete this user.', '', 'error'),
        });
      }
    });
  }

  returnDetails(): void {
    this.getAllUsers();
    this.modalRef.close();
  }
}

import { CommonModule } from '@angular/common';
import { Component, inject, TemplateRef, ViewChild } from '@angular/core';
import { MdbFormsModule } from 'mdb-angular-ui-kit/forms';
import { MdbModalModule, MdbModalRef, MdbModalService } from 'mdb-angular-ui-kit/modal';
import { MdbRippleModule } from 'mdb-angular-ui-kit/ripple';
import { MdbValidationModule } from 'mdb-angular-ui-kit/validation';
import { OwnersDetails } from '../owners-details/owners-details';
import { Owner } from '../../../models/owner';
import { OwnerService } from '../../../services/owner.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-owners-list',
  standalone: true,
  imports: [
    CommonModule,
    MdbRippleModule,
    MdbModalModule,
    MdbFormsModule,
    MdbValidationModule,
    OwnersDetails
  ],
  templateUrl: './owners-list.html',
  styleUrl: './owners-list.scss',
})
export class OwnersList {
  list: Owner[] = [];
  ownerEdit!: Owner;

  @ViewChild('modalOwnersList') modalOwnersList!: TemplateRef<any>;
  modalRef!: MdbModalRef<any>;

  private modalService = inject(MdbModalService);
  private ownerService = inject(OwnerService);

  constructor() {
    this.getAllOwners();
  }

  getAllOwners(): void {
    this.ownerService.getAllOwners().subscribe({
      next: (owners) => (this.list = owners),
      error: () => Swal.fire('Failed to retrieve the owner list.', '', 'error'),
    });
  }

  new(): void {
    this.ownerEdit = new Owner();
    this.modalRef = this.modalService.open(this.modalOwnersList);
  }

  edit(owner: Owner): void {
    this.ownerEdit = { ...owner };
    this.modalRef = this.modalService.open(this.modalOwnersList);
  }

  deleteById(owner: Owner): void {
    Swal.fire({
      title: 'Are you sure?',
      icon: 'warning',
      showConfirmButton: true,
      showCancelButton: true,
      confirmButtonText: 'Yes',
    }).then((result) => {
      if (result.isConfirmed) {
        this.ownerService.deleteOwner(owner.id!).subscribe({
          next: () => {
            this.list = this.list.filter((b) => b.id !== owner.id);
            Swal.fire('Successfully deleted!', '', 'success');
          },
          error: () => Swal.fire('Failed to delete this owner.', '', 'error'),
        });
      }
    });
  }

  returnDetails(): void {
    this.getAllOwners();
    this.modalRef.close();
  }
}

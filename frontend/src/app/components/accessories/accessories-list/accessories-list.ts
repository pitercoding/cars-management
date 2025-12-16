import { CommonModule } from '@angular/common';
import { Component, inject, TemplateRef, ViewChild } from '@angular/core';
import { MdbFormsModule } from 'mdb-angular-ui-kit/forms';
import { MdbModalModule, MdbModalRef, MdbModalService } from 'mdb-angular-ui-kit/modal';
import { MdbRippleModule } from 'mdb-angular-ui-kit/ripple';
import { MdbValidationModule } from 'mdb-angular-ui-kit/validation';
import { AccessoriesDetails } from '../accessories-details/accessories-details';
import { Accessory } from '../../../models/accessory';
import { AccessoryService } from '../../../services/accessory.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-accessories-list',
  imports: [
    CommonModule,
    MdbRippleModule,
    MdbModalModule,
    MdbFormsModule,
    MdbValidationModule,
    AccessoriesDetails,
  ],
  templateUrl: './accessories-list.html',
  styleUrl: './accessories-list.scss',
})
export class AccessoriesList {
  list: Accessory[] = [];
  accessoryEdit!: Accessory;

  @ViewChild('modalAccessoriesList') modalAccessoriesList!: TemplateRef<any>;
  modalRef!: MdbModalRef<any>;

  private modalService = inject(MdbModalService);
  private accessoryService = inject(AccessoryService);

  constructor() {
    this.getAllAccessories();
  }

  getAllAccessories(): void {
    this.accessoryService.getAllAccessories().subscribe({
      next: (accessories) => {
        this.list = accessories.sort((a, b) => a.id! - b.id!);
      },
      error: () => Swal.fire('Failed to retrieve the Accessory list.', '', 'error'),
    });
  }

  new(): void {
    this.accessoryEdit = new Accessory();
    this.modalRef = this.modalService.open(this.modalAccessoriesList);
  }

  edit(accessory: Accessory): void {
    this.accessoryEdit = { ...accessory };
    this.modalRef = this.modalService.open(this.modalAccessoriesList);
  }

  deleteById(accessory: Accessory): void {
    Swal.fire({
      title: 'Are you sure?',
      icon: 'warning',
      showConfirmButton: true,
      showCancelButton: true,
      confirmButtonText: 'Yes',
    }).then((result) => {
      if (result.isConfirmed) {
        this.accessoryService.deleteAccessory(accessory.id!).subscribe({
          next: () => {
            this.list = this.list.filter((a) => a.id !== accessory.id);
            Swal.fire('Successfully deleted!', '', 'success');
          },
          error: () => Swal.fire('Failed to delete this accessory.', '', 'error'),
        });
      }
    });
  }

  returnDetails(): void {
    this.getAllAccessories();
    this.modalRef.close();
  }
}

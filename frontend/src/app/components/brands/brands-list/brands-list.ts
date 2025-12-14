import { Component, inject, TemplateRef, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import Swal from 'sweetalert2';

import { MdbModalModule, MdbModalRef, MdbModalService } from 'mdb-angular-ui-kit/modal';
import { MdbRippleModule } from 'mdb-angular-ui-kit/ripple';
import { MdbFormsModule } from 'mdb-angular-ui-kit/forms';
import { MdbValidationModule } from 'mdb-angular-ui-kit/validation';

import { BrandsDetails } from '../brands-details/brands-details';
import { Brand } from '../../../models/brand';
import { BrandService } from '../../../services/brand.service';

@Component({
  selector: 'app-brands-list',
  standalone: true,
  imports: [
    CommonModule,
    MdbRippleModule,
    MdbModalModule,
    MdbFormsModule,
    MdbValidationModule,
    BrandsDetails,
  ],
  templateUrl: './brands-list.html',
  styleUrl: './brands-list.scss',
})
export class BrandsList {
  list: Brand[] = [];
  brandEdit!: Brand;

  @ViewChild('modalBrandsList') modalBrandsList!: TemplateRef<any>;
  modalRef!: MdbModalRef<any>;

  private modalService = inject(MdbModalService);
  private brandService = inject(BrandService);

  constructor() {
    this.getAllBrands();
  }

  getAllBrands(): void {
    this.brandService.getAllBrands().subscribe({
      next: (brands) => (this.list = brands),
      error: () =>
        Swal.fire('Failed to retrieve the brand list.', '', 'error'),
    });
  }

  new(): void {
    this.brandEdit = new Brand();
    this.modalRef = this.modalService.open(this.modalBrandsList);
  }

  edit(brand: Brand): void {
    this.brandEdit = { ...brand };
    this.modalRef = this.modalService.open(this.modalBrandsList);
  }

  deleteById(brand: Brand): void {
    Swal.fire({
      title: 'Are you sure?',
      icon: 'warning',
      showConfirmButton: true,
      showCancelButton: true,
      confirmButtonText: 'Yes',
    }).then((result) => {
      if (result.isConfirmed) {
        this.brandService.deleteBrand(brand.id!).subscribe({
          next: () => {
            this.list = this.list.filter((b) => b.id !== brand.id);
            Swal.fire('Successfully deleted!', '', 'success');
          },
          error: () =>
            Swal.fire('Failed to delete this brand.', '', 'error'),
        });
      }
    });
  }

  returnDetails(): void {
    this.getAllBrands();
    this.modalRef.close();
  }
}

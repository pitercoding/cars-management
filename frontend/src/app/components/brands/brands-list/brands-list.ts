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
  brandEdit: Brand = new Brand(0, '', '');

  @ViewChild('modalBrandsList') modalBrandsList!: TemplateRef<any>;
  modalRef!: MdbModalRef<any>;

  private modalService = inject(MdbModalService);
  brandService = inject(BrandService);

  constructor() {
    this.getAllBrands();
  }

  getAllBrands() {
    this.brandService.getAllBrands().subscribe({
      next: (brandList) => { // Success
        this.list = brandList;
      },
      error: (err) => { // Fail
        Swal.fire({
              title: 'Failed to retrieve the brand list.',
              icon: 'error',
              confirmButtonText: 'Ok',
            });
        alert('');
      },
    });
  }

  deleteById(brand: Brand) {
    Swal.fire({
      title: 'Are you sure that you want to delete this record?',
      icon: 'warning',
      showConfirmButton: true,
      showDenyButton: true,
      confirmButtonText: 'Yes',
      cancelButtonText: 'No',
    }).then((result) => {
      if (result.isConfirmed) {
        this.brandService.deleteBrand(brand.id).subscribe({
          next: () => { // Success
            this.list = this.list.filter(b => b.id !== brand.id);
            Swal.fire({
              title: 'Successfully deleted!',
              icon: 'success',
              confirmButtonText: 'Ok',
            });
          },
          error: (err) => { // Fail
            Swal.fire({
              title: 'Failed to delete this brand.',
              icon: 'error',
              confirmButtonText: 'Ok',
            });
          },
        });
      }
    });
  }

  new() {
    this.brandEdit = new Brand(0, '', '');
    this.modalRef = this.modalService.open(this.modalBrandsList);
  }

  edit(brand: Brand) {
    this.brandEdit = Object.assign({}, brand);
    this.modalRef = this.modalService.open(this.modalBrandsList);
  }

  returnDetails(brand: Brand) {
    this.getAllBrands();
    this.modalRef.close();
  }
}

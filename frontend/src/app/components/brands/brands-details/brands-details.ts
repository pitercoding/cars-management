import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MdbFormsModule } from 'mdb-angular-ui-kit/forms';
import { Brand } from '../../../models/brand';
import { inject } from '@angular/core';
import Swal from 'sweetalert2';
import { BrandService } from '../../../services/brand.service';


@Component({
  selector: 'app-brands-details',
  standalone: true,
  imports: [MdbFormsModule, FormsModule],
  templateUrl: './brands-details.html',
  styleUrl: './brands-details.scss',
})
export class BrandsDetails {
  @Input() brand: Brand = new Brand(0, '', '');
  @Output() return = new EventEmitter<Brand>();

  brandService = inject(BrandService);

  saveBrand() {
    if (this.brand.id > 0) {
      this.brandService.updateBrand(this.brand, this.brand.id).subscribe({
        next: () => {
          Swal.fire({
            title: 'Successfully edited!',
            icon: 'success',
            confirmButtonText: 'Ok',
          });
          this.return.emit(this.brand);
        },
        error: () => {
          Swal.fire({
            title: 'Failed to update this brand.',
            icon: 'error',
            confirmButtonText: 'Ok',
          });
        },
      });
    } else {
      this.brandService.postBrand(this.brand).subscribe({
        next: () => {
          Swal.fire({
            title: 'Saved successfully!',
            icon: 'success',
            confirmButtonText: 'Ok',
          });
          this.return.emit(this.brand);
        },
        error: () => {
          Swal.fire({
            title: 'Failed to save this brand.',
            icon: 'error',
            confirmButtonText: 'Ok',
          });
        },
      });
    }
  }
}

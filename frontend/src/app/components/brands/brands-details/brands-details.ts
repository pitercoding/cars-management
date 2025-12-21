import { Component, EventEmitter, Input, Output, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MdbFormsModule } from 'mdb-angular-ui-kit/forms';
import Swal from 'sweetalert2';

import { Brand } from '../../../models/brand';
import { BrandService } from '../../../services/brand.service';

@Component({
  selector: 'app-brands-details',
  standalone: true,
  imports: [MdbFormsModule, FormsModule],
  templateUrl: './brands-details.html',
  styleUrl: './brands-details.scss',
})
export class BrandsDetails {
  @Input() brand: Brand = new Brand();
  @Output() return = new EventEmitter<void>();

  private brandService = inject(BrandService);

  saveBrand(): void {
    if (this.brand.id) {
      this.update();
    } else {
      this.create();
    }
  }

  private create(): void {
    this.brandService.postBrand(this.brand).subscribe({
      next: () => {
        Swal.fire('Saved successfully!', '', 'success');
        this.return.emit();
      },
      error: (err) => {
        const msg = err.error?.message || 'Failed to save this brand.';
        Swal.fire('Error', msg, 'error');
      },
    });
  }

  private update(): void {
    this.brandService.updateBrand(this.brand.id!, this.brand).subscribe({
      next: () => {
        Swal.fire('Successfully edited!', '', 'success');
        this.return.emit();
      },
      error: (err) => {
        const msg = err.error?.message || 'Failed to update this brand.';
        Swal.fire('Error', msg, 'error');
      },
    });
  }
}

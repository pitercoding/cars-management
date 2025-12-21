import { Component, EventEmitter, inject, Input, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MdbFormsModule } from 'mdb-angular-ui-kit/forms';
import { Owner } from '../../../models/owner';
import { OwnerService } from '../../../services/owner.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-owners-details',
  standalone: true,
  imports: [MdbFormsModule, FormsModule],
  templateUrl: './owners-details.html',
  styleUrl: './owners-details.scss',
})
export class OwnersDetails {
  @Input() owner: Owner = new Owner();
  @Output() return = new EventEmitter<void>();

  private ownerService = inject(OwnerService);

  saveOwner(): void {
    if (this.owner.id) {
      this.update();
    } else {
      this.create();
    }
  }

  private create(): void {
    this.ownerService.postOwner(this.owner).subscribe({
      next: () => {
        Swal.fire('Saved successfully!', '', 'success');
        this.return.emit();
      },
      error: (err) => {
      const msg = err.error?.message || 'Failed to save this owner.';
      Swal.fire('Error', msg, 'error');
      },
    });
  }

  private update(): void {
    this.ownerService.updateOwner(this.owner.id!, this.owner).subscribe({
      next: () => {
        Swal.fire('Successfully edited!', '', 'success');
        this.return.emit();
      },
      error: (err) => {
        const msg = err.error?.message || 'Failed to update this owner.';
      Swal.fire('Error', msg, 'error');
      },
    });
  }
}

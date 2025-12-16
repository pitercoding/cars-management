import { AccessoryService } from '../../../services/accessory.service';
import { Component, EventEmitter, inject, Input, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MdbFormsModule } from 'mdb-angular-ui-kit/forms';
import { Accessory } from '../../../models/accessory';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-accessories-details',
  standalone: true,
  imports: [MdbFormsModule, FormsModule],
  templateUrl: './accessories-details.html',
  styleUrl: './accessories-details.scss',
})
export class AccessoriesDetails {
  @Input() accessory: Accessory = new Accessory();
  @Output() return = new EventEmitter<void>();

  private accessoryService = inject(AccessoryService);

  saveAccessory(): void {
    if (this.accessory.id) {
      this.update();
    } else {
      this.create();
    }
  }

  private create(): void {
    this.accessoryService.postAccessory(this.accessory).subscribe({
      next: () => {
        Swal.fire('Saved successfully!', '', 'success');
        this.return.emit();
      },
      error: () => {
        Swal.fire('Failed to save this accessory.', '', 'error');
      },
    });
  }

  private update(): void {
    this.accessoryService.updateAccessory(this.accessory.id!, this.accessory).subscribe({
      next: () => {
        Swal.fire('Successfully edited!', '', 'success');
        this.return.emit();
      },
      error: () => {
        Swal.fire('Failed to update this accessory.', '', 'error');
      },
    });
  }
}

import { Component, inject, TemplateRef, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import Swal from 'sweetalert2';

import { MdbModalModule, MdbModalRef, MdbModalService } from 'mdb-angular-ui-kit/modal';
import { MdbRippleModule } from 'mdb-angular-ui-kit/ripple';
import { MdbFormsModule } from 'mdb-angular-ui-kit/forms';
import { MdbValidationModule } from 'mdb-angular-ui-kit/validation';

import { CarsDetails } from '../cars-details/cars-details';
import { Car } from '../../../models/car';
import { CarService } from '../../../services/car.service';

@Component({
  selector: 'app-cars-list',
  standalone: true,
  imports: [
    CommonModule,
    MdbRippleModule,
    MdbModalModule,
    MdbFormsModule,
    MdbValidationModule,
    CarsDetails,
  ],
  templateUrl: './cars-list.html',
  styleUrls: ['./cars-list.scss'],
})
export class CarsList {
  list: Car[] = [];
  carEdit: Car = new Car();
  expandedCarId: number | null = null;

  @ViewChild('modalCarsList') modalCarsList!: TemplateRef<any>;
  modalRef!: MdbModalRef<any>;

  private modalService = inject(MdbModalService);
  private carService = inject(CarService);

  constructor() {
    this.getAllCars();
  }

  // ======================
  // LOAD
  // ======================
  getAllCars(): void {
    this.carService.getAllCars().subscribe({
      next: (cars) => (this.list = cars),
      error: (err) => {
        const msg = err.error?.message || 'Failed to retrieve the car list.';
        Swal.fire('Error', msg, 'error');
      },
    });
  }

  // ======================
  // CREATE / EDIT
  // ======================
  new(): void {
    this.carEdit = new Car({
      name: '',
      model: '',
      manufactureYear: new Date().getFullYear(),
      brand: undefined,
    });

    setTimeout(() => {
      this.modalRef = this.modalService.open(this.modalCarsList, {
        modalClass: 'modal-lg',
      });
    });
  }

  edit(car: Car): void {
    this.carEdit = new Car({ ...car });

    setTimeout(() => {
      this.modalRef = this.modalService.open(this.modalCarsList, {
        modalClass: 'modal-lg',
      });
    });
  }

  returnDetails(car: Car | undefined): void {
    if (car) {
      this.getAllCars();
    }
    this.modalRef.close();
  }

  // ======================
  // DETAILS (ACCORDION)
  // ======================
  toggleDetails(car: Car): void {
    this.expandedCarId = this.expandedCarId === car.id ? null : car.id ?? null;
  }

  // ======================
  // DELETE (PROFESSIONAL)
  // ======================
  confirmDelete(car: Car): void {
    const hasOwner = !!car.owner;
    const hasAccessories = !!car.accessories?.length;

    let message = 'Are you sure you want to delete this car?';

    if (hasOwner || hasAccessories) {
      message =
        'This car has related data.\n\n' +
        'To delete it, you must first remove:\n' +
        (hasOwner ? '- Owner\n' : '') +
        (hasAccessories ? '- Accessories\n' : '');
    }

    Swal.fire({
      title: 'Confirm deletion',
      text: message,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Delete',
      cancelButtonText: 'Cancel',
    }).then((result) => {
      if (result.isConfirmed) {
        this.deleteCar(car);
      }
    });
  }

  private deleteCar(car: Car): void {
    if (!car.id) return;

    this.carService.deleteCar(car.id).subscribe({
      next: () => {
        this.list = this.list.filter((c) => c.id !== car.id);
        Swal.fire('Deleted!', 'Car deleted successfully.', 'success');
      },
      error: (err) => {
        const msg =
          err.error?.message ||
          'This car cannot be deleted because it has related data.';
        Swal.fire('Deletion blocked', msg, 'error');
      },
    });
  }
}

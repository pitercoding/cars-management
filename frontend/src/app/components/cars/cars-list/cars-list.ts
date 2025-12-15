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

  @ViewChild('modalCarsList') modalCarsList!: TemplateRef<any>;
  modalRef!: MdbModalRef<any>;

  private modalService = inject(MdbModalService);
  private carService = inject(CarService);

  constructor() {
    this.getAllCars();
  }

  getAllCars(): void {
    this.carService.getAllCars().subscribe({
      next: (cars) => (this.list = cars),
      error: () => Swal.fire('Failed to retrieve the car list.', '', 'error'),
    });
  }

  new(): void {
    this.carEdit = new Car({
      name: '',
      model: '',
      manufactureYear: new Date().getFullYear(),
      brand: undefined,
    });

    setTimeout(() => {
      this.modalRef = this.modalService.open(this.modalCarsList, { modalClass: 'modal-lg' });
    });
  }

  edit(car: Car): void {
    this.carEdit = new Car({ ...car });

    setTimeout(() => {
      this.modalRef = this.modalService.open(this.modalCarsList, { modalClass: 'modal-lg' });
    });
  }

  deleteById(car: Car): void {
    if (!car.id) return;

    Swal.fire({
      title: 'Are you sure?',
      icon: 'warning',
      showConfirmButton: true,
      showCancelButton: true,
      confirmButtonText: 'Yes',
    }).then((result) => {
      if (result.isConfirmed) {
        this.carService.deleteCar(car.id!).subscribe({
          next: () => {
            this.list = this.list.filter((c) => c.id !== car.id);
            Swal.fire('Successfully deleted!', '', 'success');
          },
          error: () => Swal.fire('Failed to delete this car.', '', 'error'),
        });
      }
    });
  }

  returnDetails(): void {
    this.getAllCars();
    this.modalRef.close();
  }
}

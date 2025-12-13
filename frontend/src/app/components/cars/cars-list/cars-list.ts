import { Component, inject, TemplateRef, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Car } from './../../../models/car';
import Swal from 'sweetalert2';

import { MdbModalModule, MdbModalRef, MdbModalService } from 'mdb-angular-ui-kit/modal';
import { MdbRippleModule } from 'mdb-angular-ui-kit/ripple';
import { MdbFormsModule } from 'mdb-angular-ui-kit/forms';
import { MdbValidationModule } from 'mdb-angular-ui-kit/validation';
import { CarsDetails } from '../cars-details/cars-details';
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
  styleUrl: './cars-list.scss',
})
export class CarsList {
  list: Car[] = [];
  carEdit: Car = new Car(0, '');

  @ViewChild('modalCarsList') modalCarsList!: TemplateRef<any>;
  modalRef!: MdbModalRef<any>;

  private modalService = inject(MdbModalService);
  carService = inject(CarService);

  constructor() {
    this.getAllCars();

    let carNew = history.state.carNew;
    let carEdited = history.state.carEdited;

    if (carNew) {
      carNew.id = 555;
      this.list.push(carNew);
    }

    if (carEdited) {
      let index = this.list.findIndex((x) => x.id === carEdited.id);
      if (index >= 0) this.list[index] = carEdited;
    }
  }

  getAllCars() {
    this.carService.getAllCars().subscribe({
      next: (carList) => { // Success
        this.list = carList;
      },
      error: (err) => { // Fail
        Swal.fire({
              title: 'Failed to retrieve the car list.',
              icon: 'error',
              confirmButtonText: 'Ok',
            });
        alert('');
      },
    });
  }

  deleteById(car: Car) {
    Swal.fire({
      title: 'Are you sure that you want to delete this record?',
      icon: 'warning',
      showConfirmButton: true,
      showDenyButton: true,
      confirmButtonText: 'Yes',
      cancelButtonText: 'No',
    }).then((result) => {
      if (result.isConfirmed) {
        this.carService.deleteCar(car.id).subscribe({
          next: () => { // Success
            this.list = this.list.filter(c => c.id !== car.id);
            Swal.fire({
              title: 'Successfully deleted!',
              icon: 'success',
              confirmButtonText: 'Ok',
            });
          },
          error: (err) => { // Fail
            Swal.fire({
              title: 'Failed to delete this car.',
              icon: 'error',
              confirmButtonText: 'Ok',
            });
          },
        });
      }
    });
  }

  new() {
    this.carEdit = new Car(0, '');
    this.modalRef = this.modalService.open(this.modalCarsList);
  }

  edit(car: Car) {
    this.carEdit = Object.assign({}, car);
    this.modalRef = this.modalService.open(this.modalCarsList);
  }

  returnDetails(car: Car) {
    if (car.id > 0) {
      let index = this.list.findIndex((x) => {
        return x.id == car.id;
      });
      this.list[index] = car;
    } else {
      car.id = 55;
      this.list.push(car);
    }

    this.modalRef.close();
  }
}

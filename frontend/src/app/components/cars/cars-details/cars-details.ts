import { CarService } from './../../../services/car.service';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MdbFormsModule } from 'mdb-angular-ui-kit/forms';
import { Car } from '../../../models/car';
import { ActivatedRoute, Router } from '@angular/router';
import { inject } from '@angular/core';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-cars-details',
  imports: [MdbFormsModule, FormsModule],
  templateUrl: './cars-details.html',
  styleUrl: './cars-details.scss',
})
export class CarsDetails {
  @Input() car: Car = new Car(0, '');
  @Output() return = new EventEmitter<Car>();

  carService = inject(CarService);

  saveCar() {
    if (this.car.id > 0) {
      this.carService.updateCar(this.car, this.car.id).subscribe({
        next: () => {
          Swal.fire({
            title: 'Successfully edited!',
            icon: 'success',
            confirmButtonText: 'Ok',
          });
          this.return.emit(this.car);
        },
        error: () => {
          Swal.fire({
            title: 'Failed to update this car.',
            icon: 'error',
            confirmButtonText: 'Ok',
          });
        },
      });
    } else {
      this.carService.postCar(this.car).subscribe({
        next: () => {
          Swal.fire({
            title: 'Saved successfully!',
            icon: 'success',
            confirmButtonText: 'Ok',
          });
          this.return.emit(this.car);
        },
        error: () => {
          Swal.fire({
            title: 'Failed to save this car.',
            icon: 'error',
            confirmButtonText: 'Ok',
          });
        },
      });
    }
  }
}

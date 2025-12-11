import { Component } from '@angular/core';
import { Car } from './../../../models/car';
import { RouterLink } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-cars-list',
  imports: [RouterLink],
  templateUrl: './cars-list.html',
  styleUrl: './cars-list.scss',
})
export class CarsList {
  list: Car[] = [];

  constructor() {
    const car1: Car = { id: 1, name: 'Fiesta' };
    const car2: Car = { id: 2, name: 'Uno' };
    const car3: Car = { id: 3, name: 'Monza' };

    this.list.push(car1, car2, car3);

    let carNew = history.state.carNew;
    let carEdited = history.state.carEdited;

    if (carNew) {
      carNew.id = 555;
      this.list.push(carNew);
    }

    if (carEdited) {
      let index = this.list.findIndex((x) => {
        return x.id == carEdited.id;
      });
      this.list[index] = carEdited;
    }
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
        let index = this.list.findIndex((x) => {
          return x.id == car.id;
        });
        this.list.splice(index, 1);

        Swal.fire({
          title: 'Successfully deleted!',
          icon: 'success',
          confirmButtonText: 'Ok',
        });
      }
    });
  }
}

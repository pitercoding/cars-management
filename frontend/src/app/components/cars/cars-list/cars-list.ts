import { Component } from '@angular/core';
import { Car } from './../../../models/car';
import { RouterLink } from "@angular/router";

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
  }

  deleteCar() {
    //
  }
}

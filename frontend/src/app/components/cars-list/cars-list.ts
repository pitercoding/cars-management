import { Component } from '@angular/core';
import { Car } from '../../models/car';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-cars-list',
  imports: [CommonModule],
  templateUrl: './cars-list.html',
  styleUrl: './cars-list.scss',
})
export class CarsList {

  list: Car[] = [];

  constructor() {
  this.list = [
    { id: 1, name: "Fiesta", brand: "Ford" },
    { id: 2, name: "Uno", brand: "Fiat" },
    { id: 3, name: "Monza", brand: "Chevrolet" },
    { id: 4, name: "Jeep Compass", brand: "Jeep"}
  ];
}

}

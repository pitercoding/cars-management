import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MdbFormsModule } from 'mdb-angular-ui-kit/forms';
import { Car } from '../../../models/car';

@Component({
  selector: 'app-cars-details',
  imports: [MdbFormsModule, FormsModule],
  templateUrl: './cars-details.html',
  styleUrl: './cars-details.scss',
})
export class CarsDetails {

  car: Car = new Car(0, "");

  saveChanges() {
    alert('Saved successfully!')
  }
}

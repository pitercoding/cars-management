import { Component } from '@angular/core';
import { CarsList } from '../cars-list/cars-list';

@Component({
  selector: 'app-dashboard',
  imports: [CarsList],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.scss',
})
export class Dashboard {

}

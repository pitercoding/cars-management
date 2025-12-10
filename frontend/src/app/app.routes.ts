import { Routes } from '@angular/router';
import { CarsList } from './components/cars-list/cars-list';
import { Dashboard } from './components/dashboard/dashboard';

export const routes: Routes = [
  { path: "", redirectTo: "dashboard", pathMatch: 'full' },
  { path: "dashboard", component: Dashboard },
  { path: "cars", component: CarsList }
];

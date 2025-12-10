import { Routes } from '@angular/router';
import { Login } from './components/layout/login/login';
import { MainContent } from './components/layout/main-content/main-content';
import { CarsList } from './components/cars/cars-list/cars-list';
import { CarsDetails } from './components/cars/cars-details/cars-details';

export const routes: Routes = [
  { path: "", redirectTo: "login", pathMatch: 'full' },
  { path: "login", component: Login },
  { path: "admin", component: MainContent, children: [
    { path: "cars", component: CarsList },
    { path: "cars/new", component: CarsDetails },
    { path: "cars/edit/:id", component: CarsDetails }
  ]}
];

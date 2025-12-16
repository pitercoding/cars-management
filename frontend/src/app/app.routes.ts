import { Routes } from '@angular/router';
import { Login } from './components/layout/login/login';
import { MainContent } from './components/layout/main-content/main-content';
import { CarsList } from './components/cars/cars-list/cars-list';
import { CarsDetails } from './components/cars/cars-details/cars-details';
import { BrandsList } from './components/brands/brands-list/brands-list';
import { BrandsDetails } from './components/brands/brands-details/brands-details';
import { AccessoriesList } from './components/accessories/accessories-list/accessories-list';
import { AccessoriesDetails } from './components/accessories/accessories-details/accessories-details';

export const routes: Routes = [
  { path: "", redirectTo: "login", pathMatch: 'full' },
  { path: "login", component: Login },
  { path: "admin", component: MainContent, children: [
    { path: "cars", component: CarsList },
    { path: "cars/new", component: CarsDetails },
    { path: "cars/edit/:id", component: CarsDetails },
    { path: "brands", component: BrandsList },
    { path: "brands/new", component: BrandsDetails },
    { path: "brands/edit/:id", component: BrandsDetails },
    { path: "accessories", component: AccessoriesList },
    { path: "accessories/new", component: AccessoriesDetails },
    { path: "accessories/edit/:id", component: AccessoriesDetails }
  ]}
];

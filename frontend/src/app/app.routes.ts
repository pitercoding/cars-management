import { Routes } from '@angular/router';
import { LoginComponent } from './components/layout/login/login.component';
import { MainContent } from './components/layout/main-content/main-content';
import { CarsList } from './components/cars/cars-list/cars-list';
import { CarsDetails } from './components/cars/cars-details/cars-details';
import { BrandsList } from './components/brands/brands-list/brands-list';
import { BrandsDetails } from './components/brands/brands-details/brands-details';
import { AccessoriesList } from './components/accessories/accessories-list/accessories-list';
import { AccessoriesDetails } from './components/accessories/accessories-details/accessories-details';
import { loginGuard } from './auth/guards/login-guard';
import { UsersList } from './auth/components/users-list/users-list';
import { UsersDetails } from './auth/components/users-details/users-details';
import { OwnersList } from './components/owners/owners-list/owners-list';
import { OwnersDetails } from './components/owners/owners-details/owners-details';

export const routes: Routes = [
  { path: "", redirectTo: "login", pathMatch: 'full' },
  { path: "login", component: LoginComponent },
  { path: "admin", component: MainContent, canActivate: [loginGuard], children: [
    { path: "cars", component: CarsList },
    { path: "cars/new", component: CarsDetails },
    { path: "cars/edit/:id", component: CarsDetails },
    { path: "brands", component: BrandsList },
    { path: "brands/new", component: BrandsDetails },
    { path: "brands/edit/:id", component: BrandsDetails },
    { path: "accessories", component: AccessoriesList },
    { path: "accessories/new", component: AccessoriesDetails },
    { path: "accessories/edit/:id", component: AccessoriesDetails },
    { path: "users", component: UsersList },
    { path: "users/new", component: UsersDetails },
    { path: "users/edit/:id", component: UsersDetails },
    { path: "owners", component: OwnersList },
    { path: "owners/new", component: OwnersDetails },
    { path: "owners/edit/:id", component: OwnersDetails }
  ]}
];

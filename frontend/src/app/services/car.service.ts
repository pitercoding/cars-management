import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Car } from '../models/car';

@Injectable({
  providedIn: 'root',
})
export class CarService {
  http = inject(HttpClient);

  API = 'http://localhost:8080/api/cars';

  constructor() {}

  getAllCars(): Observable<Car[]> {
    return this.http.get<Car[]>(this.API + '/getAllCars');
  }

  deleteCar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.API}/deleteCar/${id}`);
  }

  postCar(car: Car): Observable<Car> {
    return this.http.post<Car>(this.API + '/postCar', car);
  }

  updateCar(car: Car, id: number): Observable<Car> {
    return this.http.put<Car>(`${this.API}/updateCar/${id}`, car);
  }

  getCarById(id: number): Observable<Car> {
    return this.http.get<Car>(`${this.API}/getCarById/${id}`);
  }
}

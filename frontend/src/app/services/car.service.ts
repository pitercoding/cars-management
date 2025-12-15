import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Car } from '../models/car';

@Injectable({
  providedIn: 'root',
})
export class CarService {
  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080/api/cars';

  getAllCars(): Observable<Car[]> {
    return this.http.get<Car[]>(`${this.apiUrl}/getAllCars`);
  }

  getCarById(id: number): Observable<Car> {
    return this.http.get<Car>(`${this.apiUrl}/getCarById/${id}`);
  }

  postCar(car: Car): Observable<Car> {
    return this.http.post<Car>(`${this.apiUrl}/postCar`, car);
  }

  updateCar(car: Car, id: number): Observable<Car> {
    return this.http.put<Car>(`${this.apiUrl}/updateCar/${id}`, car);
  }

  deleteCar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/deleteCar/${id}`);
  }
}

import { inject, Injectable } from '@angular/core';
import { Brand } from '../models/brand';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class BrandService {
  http = inject(HttpClient);

  private apiUrl = 'http://localhost:8080/api/brands';

  constructor() {}

  getAllBrands(): Observable<Brand[]> {
    return this.http.get<Brand[]>(`${this.apiUrl}/getAllBrands`);
  }

  postBrand(brand: Brand): Observable<Brand> {
    return this.http.post<Brand>(`${this.apiUrl}/postBrand`, brand);
  }

  updateBrand(brand: Brand, id: number): Observable<Brand> {
    return this.http.put<Brand>(`${this.apiUrl}/updateBrand/${id}`, brand);
  }

  deleteBrand(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/deleteBrand/${id}`);
  }

  getBrandById(id: number): Observable<Brand> {
      return this.http.get<Brand>(`${this.apiUrl}/getBrandById/${id}`);
    }
}

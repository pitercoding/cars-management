import { User } from '../user';
import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { jwtDecode, JwtPayload } from 'jwt-decode';
import { Login } from '../login';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  private http = inject(HttpClient);
  private API = environment.SERVER + '/api/login';

  login(credentials: Login): Observable<string> {
    return this.http.post<string>(this.API, credentials, {
      responseType: 'text' as 'json',
    });
  }

  updatePassword(newPassword: string): Observable<void> {
    return this.http.put<void>(
      `${environment.SERVER}/api/users/me/password`,
      { password: newPassword }
    );
  }

  addToken(token: string): void {
    localStorage.setItem('token', token);
  }

  removeToken(): void {
    localStorage.removeItem('token');
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  getCurrentUser(): User | null {
    const token = this.getToken();
    if (!token) return null;

    const decoded = jwtDecode<JwtPayload & any>(token);

    return {
      id: decoded.id,
      username: decoded.username,
      role: decoded.role,
    } as User;
  }

  hasRole(role: string): boolean {
    const user = this.getCurrentUser();
    return !!user && user.role === role;
  }
}

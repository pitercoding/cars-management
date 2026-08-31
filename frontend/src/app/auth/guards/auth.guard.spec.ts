import { TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { authGuard } from './auth.guard';
import { LoginService } from '../services/login.service';

describe('authGuard', () => {
  let loginServiceSpy: jasmine.SpyObj<LoginService>;
  let routerSpy: jasmine.SpyObj<Router>;

  beforeEach(() => {
    loginServiceSpy = jasmine.createSpyObj('LoginService', ['isLoggedIn']);
    routerSpy = jasmine.createSpyObj('Router', ['navigate']);

    TestBed.configureTestingModule({
      providers: [
        { provide: LoginService, useValue: loginServiceSpy },
        { provide: Router, useValue: routerSpy },
      ],
    });

    spyOn(Swal, 'fire').and.returnValue(Promise.resolve({} as any));
  });

  function runGuard() {
    return TestBed.runInInjectionContext(() => authGuard({} as any, {} as any));
  }

  it('allows navigation when the user is logged in', () => {
    loginServiceSpy.isLoggedIn.and.returnValue(true);

    expect(runGuard()).toBeTrue();
    expect(Swal.fire).not.toHaveBeenCalled();
  });

  it('blocks navigation and redirects to /login when the user is not logged in', async () => {
    loginServiceSpy.isLoggedIn.and.returnValue(false);

    expect(runGuard()).toBeFalse();
    expect(Swal.fire).toHaveBeenCalled();

    await Promise.resolve();
    expect(routerSpy.navigate).toHaveBeenCalledWith(['/login']);
  });
});

import { TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { adminGuard } from './role.guard';
import { LoginService } from '../services/login.service';

describe('adminGuard', () => {
  let loginServiceSpy: jasmine.SpyObj<LoginService>;
  let routerSpy: jasmine.SpyObj<Router>;

  beforeEach(() => {
    loginServiceSpy = jasmine.createSpyObj('LoginService', ['hasRole']);
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
    return TestBed.runInInjectionContext(() => adminGuard({} as any, {} as any));
  }

  // Regression test for the bug where hasRole('ADMIN') was checked instead
  // of hasRole('ROLE_ADMIN'), which always evaluated to false and locked
  // real admins out of /admin/users.
  it('allows a user with role ROLE_ADMIN to proceed', () => {
    loginServiceSpy.hasRole.and.callFake((role: string) => role === 'ROLE_ADMIN');

    expect(runGuard()).toBeTrue();
    expect(loginServiceSpy.hasRole).toHaveBeenCalledWith('ROLE_ADMIN');
    expect(Swal.fire).not.toHaveBeenCalled();
  });

  it('blocks a non-admin user and redirects to /admin/cars', async () => {
    loginServiceSpy.hasRole.and.returnValue(false);

    expect(runGuard()).toBeFalse();
    expect(Swal.fire).toHaveBeenCalled();

    await Promise.resolve();
    expect(routerSpy.navigate).toHaveBeenCalledWith(['/admin/cars']);
  });
});

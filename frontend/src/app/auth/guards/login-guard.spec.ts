import { TestBed } from '@angular/core/testing';
import { Router, RouterStateSnapshot } from '@angular/router';
import Swal from 'sweetalert2';
import { loginGuard } from './login-guard';
import { LoginService } from '../services/login.service';

describe('loginGuard', () => {
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

  function runGuard(url: string) {
    return TestBed.runInInjectionContext(() =>
      loginGuard({} as any, { url } as RouterStateSnapshot)
    );
  }

  // Regression test: hasRole('STANDARD_USER') used to be checked instead of
  // hasRole('ROLE_STANDARD_USER'), which always evaluated to false.
  it('blocks a standard user (ROLE_STANDARD_USER) from reaching /admin/users', async () => {
    loginServiceSpy.hasRole.and.callFake((role: string) => role === 'ROLE_STANDARD_USER');

    expect(runGuard('/admin/users')).toBeFalse();
    expect(loginServiceSpy.hasRole).toHaveBeenCalledWith('ROLE_STANDARD_USER');
    expect(Swal.fire).toHaveBeenCalled();

    await Promise.resolve();
    expect(routerSpy.navigate).toHaveBeenCalledWith(['/admin/cars']);
  });

  it('allows a standard user to reach routes other than /admin/users', () => {
    loginServiceSpy.hasRole.and.returnValue(true);

    expect(runGuard('/admin/cars')).toBeTrue();
    expect(Swal.fire).not.toHaveBeenCalled();
  });

  it('allows an admin to reach /admin/users', () => {
    loginServiceSpy.hasRole.and.returnValue(false);

    expect(runGuard('/admin/users')).toBeTrue();
  });
});

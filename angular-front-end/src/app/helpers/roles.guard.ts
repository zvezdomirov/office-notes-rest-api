import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import {Observable} from 'rxjs';
import {AuthenticationService} from '../services';
import {User} from '../models/user';

@Injectable({
  providedIn: 'root'
})
export class RolesGuard implements CanActivate {

  constructor(private authenticationService: AuthenticationService, private router: Router) {
  }

  canActivate(
    route: ActivatedRouteSnapshot, state: RouterStateSnapshot):
    Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    const expectedRole: [] = route.data.expectedRole;
    const user: User = JSON.parse(localStorage.getItem('currentUser'));
    let roleMatch = false;

    expectedRole.forEach((role: string) => {
      if (
        user.role.toUpperCase() === role.toUpperCase()) {
        roleMatch = true;
      }
    });
    if (!roleMatch) {
      this.router.navigate(['/login']);
      return false;
    }
    return true;
  }

}

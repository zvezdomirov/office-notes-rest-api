import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {AuthenticationService} from './services';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  currentUser: any;

  constructor(
    private router: Router,
    private authenticationService: AuthenticationService
  ) {
    this.authenticationService.currentUser.subscribe(foundUser => this.currentUser = foundUser);
  }

  logout() {
    this.authenticationService.logout();
    this.router.navigate(['/login']);
  }
}

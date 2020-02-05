import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';

import {AuthenticationService} from '../services';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {
  constructor(private authenticationService: AuthenticationService) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // add authorization header with jwt token if available
    const currentUser = JSON.parse(localStorage.getItem('currentUser'));
    // tslint:disable-next-line:no-conditional-assignment
    if (currentUser && (currentUser.token = localStorage.getItem('ACCESS_TOKEN'))) {
      request = request.clone({
        setHeaders: {
          Authorization: `Basic ${currentUser.token}`
        }, withCredentials: true
      });
    }

    return next.handle(request);
  }
}

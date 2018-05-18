import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { filter } from 'rxjs/operators';
import * as auth0 from 'auth0-js';

@Injectable()
export class AuthService {

  auth0 = new auth0.WebAuth({
    clientID: 'Fm291VvLyWt5V48H5OQCUzn4dKO7NSVA',
    domain: 'isis2503-jdtrujillom.auth0.com',
    responseType: 'token id_token',
    audience: 'https://isis2503-jdtrujillom.auth0.com/userinfo',
    redirectUri: 'http://localhost:3000/callback',
    scope: 'openid'
  });

  constructor(public router: Router) {}

  public login(): void {
    this.auth0.authorize();
  }

}
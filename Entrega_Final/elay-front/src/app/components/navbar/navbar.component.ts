import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  constructor(private as: AuthService) { }

  ngOnInit() {
  }

  login(){
  	this.as.login();
  }

  logout(){
    this.as.logout();
  }

  isAuthenticated(){
    return this.as.isAuthenticated();
  }

}

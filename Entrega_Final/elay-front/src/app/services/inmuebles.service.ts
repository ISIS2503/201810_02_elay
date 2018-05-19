import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions } from '@angular/http';
import { AuthService} from '../services/auth.service';
import 'rxjs/add/operator/map';

@Injectable()
export class InmueblesService {

  options;
  token;


  constructor(
    private http: Http,
    private authService: AuthService
  ) { }



  createAuthenticationHeaders() {
    this.token = this.authService.loadToken();
    this.options = new RequestOptions({
      headers: new Headers({
        'Authorization': 'Bearer ' + this.token
      })
    });
  }

  

  getInmuebles() {
    this.createAuthenticationHeaders();
    return this.http.get('http://172.24.42.67:53385/ELAY_BASHV2/service/users/auth0|5aff5d29e1fee066700b1a9e/unidadResidencial', this.options).map(res => res.json());
  }

  getAlarmas(inmueble, torre, apartamento) {
    this.createAuthenticationHeaders();
    var ruta = 'http://172.24.42.67:53385/ELAY_BASHV2/service/inmuebles/'+inmueble+'/alarmas?torre=' + torre + '&apartamento='+apartamento;
    console.log(ruta);
    return this.http.get(ruta, this.options).map(res => res.json());
  }

}

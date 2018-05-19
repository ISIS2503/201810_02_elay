import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions } from '@angular/http';
import { AuthService} from '../services/auth.service';
import 'rxjs/add/operator/map';

@Injectable()
export class InmueblesService {

  options;


  constructor(
    private http: Http
  ) { }

  createAuthenticationHeaders() {
    // this.authService.loadToken();
    this.options = new RequestOptions({
      headers: new Headers({
        'Authorization': 'Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6Ik5VSkNRVUkyUmpOQk9UZzRNekk0TkRNelFUY3hNamhGT0VVek5UWTJOREl4TUVZMFJUZ3pPUSJ9.eyJodHRwOi8vZWxheS9yb2xlcyI6WyJzZWd1cmlkYWRfcHJpdmFkYSJdLCJuaWNrbmFtZSI6InZpZ2lsYW50ZTEiLCJuYW1lIjoidmlnaWxhbnRlMUBzZWd1cmlkYWQuY29tLmNvIiwicGljdHVyZSI6Imh0dHBzOi8vcy5ncmF2YXRhci5jb20vYXZhdGFyLzcxZDU4NjU2NDg0YjZmMTVmN2NlYjNmYzI4MGIyYTgxP3M9NDgwJnI9cGcmZD1odHRwcyUzQSUyRiUyRmNkbi5hdXRoMC5jb20lMkZhdmF0YXJzJTJGdmkucG5nIiwidXBkYXRlZF9hdCI6IjIwMTgtMDUtMTlUMDI6NTE6NDEuNTQwWiIsImVtYWlsIjoidmlnaWxhbnRlMUBzZWd1cmlkYWQuY29tLmNvIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJpc3MiOiJodHRwczovL2lzaXMyNTAzLWpkdHJ1amlsbG9tLmF1dGgwLmNvbS8iLCJzdWIiOiJhdXRoMHw1YWZmNWQyOWUxZmVlMDY2NzAwYjFhOWUiLCJhdWQiOiJGbTI5MVZ2THlXdDVWNDhINU9RQ1V6bjRkS083TlNWQSIsImlhdCI6MTUyNjY5ODMwMywiZXhwIjoxNTI2NzM0MzAzfQ.qax_KgdiIm_djCmF4P2YySSo9k-NmZO4_TWl4xlexAs8F7d55qvUMZOxDCsqKlSRUZXEz7xO-UUssbhEswAE7nAhSQuATZc0QvBs8P47flucp8uk9Tmw1VUo3nNBwkuRS1KyFunCTgjYjCiSCWbMYxTHkfXTAy2g5HL99N_wXBkTJN13R555OWdyja8bxyyoqdhVfmw4BNSxwwW4qWGYdVagg-gM3shRSJhLhnGAmLEN7iAGVR26EuPLeQKCJEefYM7v57mRReeu_Fp-vACk-yQNEFmZY12fOmClD1orAmYYHydGzT2aCfAa8cD__bw6DjYtVfKmKyIrNlNJ02hOrg'
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

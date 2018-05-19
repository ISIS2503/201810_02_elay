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
        'Authorization': 'Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6Ik5VSkNRVUkyUmpOQk9UZzRNekk0TkRNelFUY3hNamhGT0VVek5UWTJOREl4TUVZMFJUZ3pPUSJ9.eyJodHRwOi8vZWxheS9yb2xlcyI6WyJzZWd1cmlkYWRfcHJpdmFkYSJdLCJodHRwOi8vZWxheS91c2VyX21ldGFkYXRhIjp7ImlkVW5pZGFkUmVzaWRlbmNpYWwiOiJiMzU2YTE0Mi01MWU3LTQxNTQtOWU1YS1iMmU4ZGE0NDhkYzUifSwibmlja25hbWUiOiJ2aWdpbGFudGUxIiwibmFtZSI6InZpZ2lsYW50ZTFAc2VndXJpZGFkLmNvbS5jbyIsInBpY3R1cmUiOiJodHRwczovL3MuZ3JhdmF0YXIuY29tL2F2YXRhci83MWQ1ODY1NjQ4NGI2ZjE1ZjdjZWIzZmMyODBiMmE4MT9zPTQ4MCZyPXBnJmQ9aHR0cHMlM0ElMkYlMkZjZG4uYXV0aDAuY29tJTJGYXZhdGFycyUyRnZpLnBuZyIsInVwZGF0ZWRfYXQiOiIyMDE4LTA1LTE5VDE2OjI2OjQ0LjQ5NFoiLCJlbWFpbCI6InZpZ2lsYW50ZTFAc2VndXJpZGFkLmNvbS5jbyIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwiaXNzIjoiaHR0cHM6Ly9pc2lzMjUwMy1qZHRydWppbGxvbS5hdXRoMC5jb20vIiwic3ViIjoiYXV0aDB8NWFmZjVkMjllMWZlZTA2NjcwMGIxYTllIiwiYXVkIjoiRm0yOTFWdkx5V3Q1VjQ4SDVPUUNVem40ZEtPN05TVkEiLCJpYXQiOjE1MjY3NDcyMDYsImV4cCI6MTUyNjc4MzIwNn0.QQ43QTyh_JyURhXkRnCFpamkNQokp-6KLqgWQ3ksAy4qN0sxOsW8xyDo3vyAspRR9LmoSzRlfownN0kgD85tz3x4fNf2RHqgUomd_mp0n76rbJNq84uEy7UhUw-YloVubDdowW3j7OvZDUBJMv_R0oj_LRkKkit8BYA3-P7MeYQphxf-dtfOeFKB46AXBYbquRYlPrJ8FSArbJkDs8WB-QZarcc0GUKtIGoraRXww5KmYNbCI0ic9JjyeHf3PRgeeeuTaq2fXZdV7oCROUdg-4ohYwG-ZGGAf_dyQW3vB3OlsnbP8W3E-M0mJLM8XeJ11O4cJMeuLaa04snpaCYYuA'     })
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

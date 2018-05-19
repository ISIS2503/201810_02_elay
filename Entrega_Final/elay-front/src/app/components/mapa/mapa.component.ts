import { Component, OnInit } from '@angular/core';
import { InmueblesService } from '../../services/inmuebles.service';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-mapa',
  templateUrl: './mapa.component.html',
  styleUrls: ['./mapa.component.css']
})
export class MapaComponent implements OnInit {

  inmuebles;
  torre;
  tipo = -1;
  alarmas;
  totalAlarmas;
  inmu;

  constructor(

    private inmueblesService: InmueblesService,
    private as: AuthService

  ) { }

  getInmuebles() {
    this.inmueblesService.getInmuebles().subscribe(data => {
      this.inmuebles = data.inmuebles.sort(function (a, b) { return (a.torre + a.apartamento) - (b.torre + b.apartamento) });
      this.totalAlarmas = data.alarmas;
    });
  }

  cambiarTipo(nuevo) {
    this.tipo;

  }

  generarId(inmueble) {
    return inmueble.torre + inmueble.apartamento;
  }

  imprimir(a, b) {
    console.log(a);
    console.log(b);
  }

  isAuthenticated(){
    return this.as.isAuthenticated();
  }

  public actual = 'mapa';

  show(tab, el) {

    if (tab != this.actual) {

      this.actual = tab;

      this.actual = tab;
      if (screen.width <= 991) {
        el.scrollIntoView({ behavior: "smooth" });
      }

    }

  }

  alertasData = [{
    tipo: 'Puerta abierta por un largo tiempo',
    id: 1,
    alerta: 'alerta1'
  },
  {
    tipo: 'Número de intentos excedido',
    id: 2,
    alerta: 'alerta2'
  },
  {
    tipo: 'Presencia detectada',
    id: 3,
    alerta: 'alerta3'
  },
  {
    tipo: 'Acceso en horario no permitido',
    id: 4,
    alerta: 'alerta4'
  },
  {
    tipo: 'Nivel de batería crítico',
    id: 5,
    alerta: 'alerta5'
  },
  {
    tipo: 'Cerradura desconectada',
    id: 7,
    alerta: 'alerta7'
  },
  {
    tipo: 'Hub desconectado',
    id: 8,
    alerta: 'alerta8'
  }
  ];

  interval;

  ngOnInit() {
    window.scrollTo(0, 0);
    this.getInmuebles();

    this.interval = setInterval(() => { 
      this.getInmuebles();
      console.log("actualizo"); 
  }, 5000);

  }
}

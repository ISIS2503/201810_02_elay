import { Component, OnInit, Input } from '@angular/core';
import { InmueblesService } from '../../services/inmuebles.service';

@Component({
  selector: 'app-inmueble',
  templateUrl: './inmueble.component.html',
  styleUrls: ['./inmueble.component.css']
})
export class InmuebleComponent implements OnInit {
  
  profile:any;
  @Input() torre;
  @Input() apto;

  alarmas;

  constructor(private inmueblesService: InmueblesService) { }

  getAlarmas() {
    this.inmueblesService.getAlarmas(123, this.torre, this.apto).subscribe(data => {
      this.alarmas = data;
    });
  }


  ngOnInit() {
    this.getAlarmas
  }

}

import { Component, OnInit, Input } from '@angular/core';
import { InmueblesService } from '../../services/inmuebles.service';
import { Location } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-inmueble',
  templateUrl: './inmueble.component.html',
  styleUrls: ['./inmueble.component.css']
})
export class InmuebleComponent implements OnInit {
  
  profile:any;

  alarmas;
  currentUrl;
  torre;
  apto;
  inmue;

  getAlarmas() {
    this.inmueblesService.getAlarmas(this.inmue, this.torre, this.apto).subscribe(data => {
      this.alarmas = data;
    });
  }


  constructor(private inmueblesService: InmueblesService, private location: Location, private activatedRoute: ActivatedRoute) { }

  
  ngOnInit() {
    this.currentUrl = this.activatedRoute.snapshot.params;
    this.inmue = this.currentUrl.inmu;
    this.torre = this.currentUrl.tor;
    this.apto = this.currentUrl.apt;
    this.getAlarmas();
  }

}

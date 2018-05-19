import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-alarmas',
  templateUrl: './alarmas.component.html',
  styleUrls: ['./alarmas.component.css']
})
export class AlarmasComponent implements OnInit {

  @Input() tipo;
  @Input() id;
  @Input() alarmas;
  constructor() { }

  ngOnInit() {
    console.log(this.alarmas);
  }

}

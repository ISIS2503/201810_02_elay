import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module'
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { LoginComponent } from './components/login/login.component';
import { MapaComponent } from './components/mapa/mapa.component';
import { InmuebleComponent } from './components/inmueble/inmueble.component';
import { AuthService} from './services/auth.service';
import { InmueblesService} from './services/inmuebles.service';
import { FilterPipe } from './pipes/filter.pipe';
import { AlarmasComponent } from './components/alarmas/alarmas/alarmas.component';
import { HomeComponent } from './components/home/home.component';
import { CallbackComponent} from './callback/callback.component';


@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    NavbarComponent,
    LoginComponent,
    MapaComponent,
    InmuebleComponent,
    FilterPipe,
    AlarmasComponent,
    CallbackComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    HttpClientModule,
    AppRoutingModule
  ],
  providers: [AuthService, InmueblesService],
  bootstrap: [AppComponent]
})
export class AppModule { }

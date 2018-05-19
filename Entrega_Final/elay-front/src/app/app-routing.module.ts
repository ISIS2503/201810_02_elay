import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';
import { HomeComponent } from './components/home/home.component';
import { InmuebleComponent } from './components/inmueble/inmueble.component';
import { CallbackComponent} from './callback/callback.component';
import { MapaComponent } from './components/mapa/mapa.component';

const appRoutes: Routes = [
    { path: '', component: HomeComponent },
    { path: 'mapa/inmueble/:inmu/torre/:tor/apto/:apt', component: InmuebleComponent},
    { path: 'callback', component: CallbackComponent },
    { path: 'mapa', component: MapaComponent},
    { path: '**', redirectTo: '' }
];

@NgModule({
    declarations: [],
    imports: [RouterModule.forRoot(appRoutes)],
    providers: [],
    bootstrap: [],
    exports: [RouterModule]
})

export class AppRoutingModule { }
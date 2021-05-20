import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { InfoComponent } from './components/info/info.component';
import { PreferenceComponent } from './components/preference/preference.component';
import { SubpageComponent } from './components/subpage/subpage.component';

const routes: Routes = [
  {path: '**', component: SubpageComponent, pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

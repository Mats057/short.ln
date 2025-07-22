import { Routes } from '@angular/router';
import { Home } from './pages/home/home';
import { Redirect } from './pages/redirect/redirect';

export const routes: Routes = [
    { path: '', component: Home },
    { path: 'rdr/:shortCode', component: Redirect}

];

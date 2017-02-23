import {Routes} from "@angular/router";
import {NoContentComponent} from "./no-content/no-content.component";
import {HomeComponent} from "./home/home.component";
import {ProfileComponent} from "./profile/profile.component";
import {PrivatePageGuard} from "./service/private-page.guard";

export const appRoutes: Routes = [
  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full'
  },
  {
    path: 'login',
    component: HomeComponent
  },
  {
    path: 'profile',
    component: ProfileComponent
  },
  {
    path: '**',
    component: NoContentComponent
  }
];
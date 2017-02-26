import {Routes} from "@angular/router";
import {WelcomeComponent} from "./pages/welcome/welcome.component";
import {NoContentComponent} from "./components/no-content/no-content.component";
import {PublicPageGuard} from "./service/public-page.guard";
import {HomeComponent} from "./pages/home/home.component";
import {PrivatePageGuard} from "./service/private-page.guard";
import {UserTable} from "./components/userTable/table.component";

export const appRoutes: Routes = [
  {
    path: '',
    redirectTo: 'overseer',
    pathMatch: 'full'
  },
  {
    path: 'overseer',
    component: WelcomeComponent,
    canActivate: [PublicPageGuard]
  },
  {
    path: 'login',
    loadChildren: './pages/login/login.module#LoginModule',
    canActivate: [PublicPageGuard]
  },
  {
    path: 'table',
    component: UserTable
  },
  {
    path: 'signup',
    loadChildren: './pages/signup/signup.module#SignupModule',
    canActivate: [PublicPageGuard]
  },
  {
    path: 'recover',
    loadChildren: './pages/recover/recover.module#RecoverModule',
    canActivate: [PublicPageGuard]
  },
  {
    path: 'home',
    component: HomeComponent,
    canActivate: [PrivatePageGuard]
  },
  {
    path: '**',
    component: NoContentComponent
  }
];

import {Routes} from "@angular/router";
import {NoContentComponent, LoginComponent, SignupComponent, RecoverComponent} from "./components/barrel";
import {PublicPageGuard} from "./service/public-page.guard";
import {PrivatePageGuard} from "./service/private-page.guard";
import {AdminPageGuard} from "./service/admin-page.guard";

export const appRoutes: Routes = [
  {
    path: '',
    redirectTo: 'authentication',
    pathMatch: 'full'
  },
  // Available for unregistered user
  {
    path: 'authentication',
    loadChildren: './pages/authentication/authentication.module#AuthenticationModule',
    canActivate: [PublicPageGuard]
  },
  // Available for registered user
  {
    path: 'home',
    loadChildren: './pages/home/home.module#HomeModule',
    canActivate: [PrivatePageGuard]
  },
  {
    path: 'profile',
    loadChildren: './pages/profile/profile.module#ProfileModule',
    canActivate: [PrivatePageGuard]
  },
  {
    path: 'forum',
    loadChildren: './pages/forum/forum.module#ForumModule',
    canActivate: [PrivatePageGuard]
  },
  // Available for admin
  {
    path: 'users',
    loadChildren: './pages/user-table/user-table.module#UserTableModule',
    canActivate: [PrivatePageGuard, AdminPageGuard]
  },
  {
    path: 'requests',
    loadChildren: './pages/request-table/request-table.module#RequestTableModule',
    canActivate: [PrivatePageGuard, AdminPageGuard]
  },
  // If route does not match any previous ones
  {
    path: '**',
    component: NoContentComponent
  }
];

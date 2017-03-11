import {Routes} from "@angular/router";
import {NoContentComponent} from "./components/barrel";
import {PublicPageGuard} from "./service/guard/public-page.guard";
import {PrivatePageGuard} from "./service/guard/private-page.guard";
import {AdminPageGuard} from "./service/guard/admin-page.guard";

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
  {
    path: 'error',
    loadChildren: './pages/error/error.module#ErrorModule',
    canActivate: [PrivatePageGuard]
  },
  // If route does not match any previous ones
  {
    path: '**',
    component: NoContentComponent
  }
];

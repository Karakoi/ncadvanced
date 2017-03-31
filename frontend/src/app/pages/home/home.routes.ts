import {Routes} from "@angular/router";
import {AdminComponent} from "./admin/admin.component";
import {AdminPageGuard} from "../../service/guard/admin-page.guard";
import {UserPageGuard} from "../../service/guard/user-page.guard";
import {ManagerPageGuard} from "../../service/guard/manager-page.guard";
import {ManagerComponent} from "./manager/manager.component";

export const homeRoutes: Routes = [
  {
    path: '',
    redirectTo: 'user',
    pathMatch: 'full'
  },
  {
    path: 'user',
    loadChildren: './user/user.module#UserModule',
    canActivate: [UserPageGuard]
  },
  {
    path: 'manager',
    component: ManagerComponent,
    canActivate: [ManagerPageGuard]
  },
  {
    path: 'admin',
    component: AdminComponent,
    canActivate: [AdminPageGuard]
  }
];

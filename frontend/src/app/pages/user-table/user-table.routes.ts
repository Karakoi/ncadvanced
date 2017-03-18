import {Routes} from "@angular/router";
import {UserPageGuard} from "../../service/guard/user-page.guard";
import {UserTableComponent} from "./all/user-table.component";
import {UserTableDeactivatedComponent} from "./deactivated/user-table-deactivated.component";
import {AdminPageGuard} from "../../service/guard/admin-page.guard";

export const usersTableRoutes: Routes = [
  {
    path: '',
    component: UserTableComponent,
    canActivate: [AdminPageGuard]
  },
  {
    path: 'all',
    component: UserTableComponent,
  },
  {
    path: 'deactivated',
    component: UserTableDeactivatedComponent,
  },
];

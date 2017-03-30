import {ManagerComponent} from "./manager.component";
import {ManagerPageGuard} from "../../../service/guard/manager-page.guard";
import {Routes} from "@angular/router";

export const ManagerRoutes: Routes = [
  {
    path: '',
    component: ManagerComponent,
    canActivate: [ManagerPageGuard]
  }
];

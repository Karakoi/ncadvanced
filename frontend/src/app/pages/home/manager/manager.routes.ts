import {ManagerComponent} from "./manager.component";
import {Routes} from "@angular/router";

export const ManagerRoutes: Routes = [
  {
    path: '',
    component: ManagerComponent
  },
  {
    path: 'manager',
    component: ManagerComponent,
  },
];

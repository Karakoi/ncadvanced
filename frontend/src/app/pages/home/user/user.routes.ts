import {Routes} from "@angular/router";
import {ActiveRequest} from "./userRequests/active-request.component";
import {UserPageGuard} from "../../../service/guard/user-page.guard";
import {ClosedRequest} from "./userClosedRequests/closed-request.component";

export const userRouts: Routes = [
  {
    path: '',
    component: ActiveRequest,
    canActivate: [UserPageGuard]
  },
  {
    path: 'active',
    component: ActiveRequest,
  },
  {
    path: 'closed',
    component: ClosedRequest,
  },
];


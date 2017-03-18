import {Pipe} from "@angular/core";
import {Request} from "../model/request.model";

@Pipe({
  name: "sort"
})
export class UserSortPipe {

  transform(array: Array<Request>, args: any): Array<Request> {
    let field = args.field;
    let order: boolean = args.order;

    if (field == 'role') {
      this.roleSort(array, order);
    } else {
      this.shallowSort(array, field, order);
    }
    return array;
  }

  roleSort(array, order) {
    array.sort((a: any, b: any) => {
      if (a['role']['name'].toLowerCase() < b['role']['name'].toLowerCase()) {
        return order ? -1 : 1;
      } else if (a['role']['name'].toLowerCase() > b['role']['name'].toLowerCase()) {
        return order ? 1 : -1;
      } else {
        return 0;
      }
    });
  }

  shallowSort(array, field, order) {
    array.sort((a: any, b: any) => {
      if (a[field].toLowerCase() < b[field].toLowerCase()) {
        return order ? -1 : 1;
      } else if (a[field].toLowerCase() > b[field].toLowerCase()) {
        return order ? 1 : -1;
      } else {
        return 0;
      }
    });
  }
}

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
    } else if (field == 'dateOfDeactivation') {
      this.shallowDateSort(array, field, order)
    } else {
      this.shallowSort(array, field, order);
    }
    return array;
  }

  shallowDateSort(array, field, order) {
    array.sort((a: any, b: any) => {
      let aDate = new Date(a[field][1] + "-" + a[field][2]+ "-" + a[field][0] + " " + + a[field][3] + ":" + a[field][4]);
      let bDate = new Date(b[field][1] + "-" + b[field][2]+ "-" + b[field][0] + " " + + b[field][3] + ":" + b[field][4]);

      if (aDate < bDate) {
        return order ? -1 : 1;
      } else if (aDate > bDate) {
        return order ? 1 : -1;
      } else {
        return 0;
      }
    });
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

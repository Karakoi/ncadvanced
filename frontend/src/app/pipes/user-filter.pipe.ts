import {Pipe, PipeTransform} from "@angular/core";
import {User} from "../model/user.model";
@Pipe ({
  name: 'filter'
})
export class UserFilterPipe implements PipeTransform {

  transform(array: Array<User>, searchTypes: any): any {
    return array.filter(function (item) {
      let res: boolean = true;
      if (searchTypes.firstName != '') {
        res = item.firstName.toLowerCase().includes(searchTypes.firstName.toLowerCase());
      }
      if (searchTypes.lastName != '') {
        res = res && item.lastName.toLowerCase().includes(searchTypes.lastName.toLowerCase());
      }
      if (searchTypes.email != '') {
        res = res && item.email.toLowerCase().includes(searchTypes.email.toLowerCase());
      }
      if (searchTypes.role != '') {
        res = res && item.role.name.toLowerCase().includes(searchTypes.role.toLowerCase());
      }
      if (searchTypes.dateOfDeactivation != '') {
        let dateStr = item.dateOfDeactivation[0] + "-" +
          (item.dateOfDeactivation[1] > 9 ? item.dateOfDeactivation[1] : "0" + item.dateOfDeactivation[1]) + "-" +
          (item.dateOfDeactivation[2] > 9 ? item.dateOfDeactivation[2] : "0" + item.dateOfDeactivation[2]);
        res = res && dateStr.includes(searchTypes.dateOfDeactivation);
      }
      return res;
    });
  }
}

import {Pipe, PipeTransform} from "@angular/core";
@Pipe ({
  name: 'filter'
})
export class UserFilterPipe implements PipeTransform {

  transform(items: any, term: any): any {
    if (term === undefined) return items;

    return items.filter(function (item) {
      return item.role.name.toLowerCase().includes(term.toLowerCase())
        || (item.firstName != null && item.firstName.toLowerCase().includes(term.toLowerCase()))
        || (item.lastName != null && item.lastName.toLowerCase().includes(term.toLowerCase()));
    });
  }
}

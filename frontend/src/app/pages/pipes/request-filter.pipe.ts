import {Pipe, PipeTransform} from "@angular/core";
@Pipe ({
  name: 'filter'
})
export class RequestFilterPipe implements PipeTransform {

  transform(items: any, term: any): any {
    if (term === undefined) return items;

    return items.filter(function (item) {
      return item.title.toLowerCase().includes(term.toLowerCase())
        || (item.priorityStatus.name != null && item.priorityStatus.name.toLowerCase().includes(term.toLowerCase()))
        || (item.progressStatus.name != null && item.progressStatus.name.toLowerCase().includes(term.toLowerCase()));
    });
  }
}

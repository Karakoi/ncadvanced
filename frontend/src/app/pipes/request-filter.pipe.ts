import {Pipe, PipeTransform} from "@angular/core";
import {Request} from "../model/request.model";
@Pipe({
  name: 'filter'
})
export class RequestFilterPipe implements PipeTransform {


  transform(array: Array<Request>, searchTypes: any): any {

    return array.filter(function (item) {
      let res: boolean = true;
      if (searchTypes.title != '') {
        res = item.title.toLowerCase().includes(searchTypes.title.toLowerCase());
      }
      if (searchTypes.priorityStatus != '') {
        res = res && item.priorityStatus.name.toLowerCase().includes(searchTypes.priorityStatus.toLowerCase());
      }
      if (searchTypes.progressStatus != '') {
        res = res && item.progressStatus.name.toLowerCase().includes(searchTypes.progressStatus.toLowerCase());
      }
      if (searchTypes.estimateTime != '') {
        res = res && item.estimateTimeInDays == searchTypes.estimateTime;
      }
      if (searchTypes.reporterName != '') {
        res = res && item.reporter.firstName.toLowerCase().includes(searchTypes.reporterName.toLowerCase());
      }
      if (searchTypes.assigneeName != '') {
        res = res && item.assignee.firstName!= null && item.assignee.firstName.toLowerCase().includes(searchTypes.assigneeName.toLowerCase());
      }
      if (searchTypes.date != '') {
        let dateStr = item.dateOfCreation[0] + "-" +
                     (item.dateOfCreation[1] > 9 ? item.dateOfCreation[1] : "0" + item.dateOfCreation[1]) + "-" +
                     (item.dateOfCreation[2] > 9 ? item.dateOfCreation[2] : "0" + item.dateOfCreation[2]);
        res = res && dateStr.includes(searchTypes.date);
      }
      return res;
    });
  }
}

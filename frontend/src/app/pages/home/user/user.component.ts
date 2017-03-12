import {Component, OnInit} from "@angular/core";
import {RequestService} from "../../../service/request.service";


@Component({
  selector: 'user-home',
  templateUrl: 'user.component.html',
})
export class UserComponent implements OnInit {
  private data:Array<any> = new Array();
  private request:any[];

  constructor(private requestService: RequestService) {
  }

  ngOnInit() {
    this.requestService.getAll(1).subscribe(requests => {
      this.request = requests;
      requests.forEach(r => {
        if(r.assignee.firstName === null) {
          r.assignee.firstName = "";
          r.assignee.lastName = "";
        }

        this.data.push({
          "title": r.title,
          "description" : r.description,
          "estimateTimeInDays": r.estimateTimeInDays,
          "dateOfCreation" : r.dateOfCreation,
          "assignee": r.assignee.firstName + " " + r.assignee.lastName,
          "priorityStatus" : r.priorityStatus.name,
          "progressStatus" : r.progressStatus.name
        })
      })
      this.onChangeTable(this.config)
    })

  }

  init(){
    this.data.forEach(r => console.log(r))
  }


  public rows: Array<any> = [];

  public columns: Array<any> = [
    {
      title: 'Title',
      name: 'title',
      sort: '',
      filtering: {filterString: '', placeholder: 'Filter by title'}
    },
    {
      title: 'Progress status',
      name: 'progressStatus',
      sort: '',
      filtering: {filterString: '', placeholder: 'Filter by dateOfCreation'}
    },
    {
      title: 'Priority status',
      name: 'priorityStatus',
      sort: false,
      filtering: {filterString: '', placeholder: 'Filter by dateOfCreation'}
    },
    {
      title: 'Date of creation',
      className: ['office-header'],
      name: 'dateOfCreation',
      sort: 'asc'
    },
    {title: 'Progress status', name: 'priorityStatus', sort: ''},
    {
      title: 'Assignee', name: 'assignee',sort: '',
      filtering: {filterString: '', placeholder: 'Filter by dateOfCreation'}},
  ];

  public page: number = 1;
  public itemsPerPage: number = 20;
  public maxSize: number = 5;
  public numPages: number = 1;
  public length: number = 5;

  public config: any = {
    paging: true,
    sorting: {columns: this.columns},
    filtering: {filterString: ''},
    className: ['table-striped', 'table-bordered']
  };


  public changePage(page: any, data: Array<any> = this.data): Array<any> {
    let start = (page.page - 1) * page.itemsPerPage;
    let end = page.itemsPerPage > -1 ? (start + page.itemsPerPage) : data.length;
    return data.slice(start, end);
  }

  public changeSort(data: any, config: any): any {
    if (!config.sorting) {
      return data;
    }

    let columns = this.config.sorting.columns || [];
    let columnName: string = void 0;
    let sort: string = void 0;

    for (let i = 0; i < columns.length; i++) {
      if (columns[i].sort !== '' && columns[i].sort !== false) {
        columnName = columns[i].name;
        sort = columns[i].sort;
      }
    }

    if (!columnName) {
      return data;
    }

    // simple sorting
    return data.sort((previous: any, current: any) => {
      if (previous[columnName].toLowerCase() > current[columnName].toLowerCase()) {
        return sort === 'desc' ? -1 : 1;
      } else if (previous[columnName] < current[columnName]) {
        return sort === 'asc' ? -1 : 1;
      }
      return 0;
    });
  }

  public changeFilter(data: any, config: any): any {
    let filteredData: Array<any> = data;
    this.columns.forEach((column: any) => {
      if (column.filtering) {
        filteredData = filteredData.filter((item: any) => {
          return item[column.name].match(column.filtering.filterString);
        });
      }
    });

    if (!config.filtering) {
      return filteredData;
    }

    if (config.filtering.columnName) {
      return filteredData.filter((item: any) =>
        item[config.filtering.columnName].match(this.config.filtering.filterString));
    }

    let tempArray: Array<any> = [];
    filteredData.forEach((item: any) => {
      let flag = false;
      this.columns.forEach((column: any) => {
        if (item[column.name].toString().match(this.config.filtering.filterString)) {
          flag = true;
        }
      });
      if (flag) {
        tempArray.push(item);
      }
    });
    filteredData = tempArray;

    return filteredData;
  }


  public onChangeTable(config: any, page: any = {page: this.page, itemsPerPage: this.itemsPerPage}): any {
    if (config.filtering) {
      Object.assign(this.config.filtering, config.filtering);
    }

    if (config.sorting) {
      Object.assign(this.config.sorting, config.sorting);
    }

    let filteredData = this.changeFilter(this.data, this.config);
    let sortedData = this.changeSort(filteredData, this.config);
    this.rows = page && config.paging ? this.changePage(page, sortedData) : sortedData;
    this.length = sortedData.length;
  }

  public onCellClick(data: any): any {
    console.log(data);
  }
}

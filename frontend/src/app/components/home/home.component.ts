import {Component} from "@angular/core";

class Requests{

  constructor(public name,
              public priority_status,
              public data_of_creation,
              public estimate_time) {}
}

const requests: Requests[] = [];

@Component({
  selector: 'overseer-home',
  templateUrl: 'home.component.html',
  styleUrls: ['home.component.css']

})
export class HomeComponent {

  request: Requests[] = requests;

  emptyValueForName: string = '';
  emptyValueForPriority: string = '';
  emptyValueForData: string = '';
  emptyValueForTime: string = '';

  createRequest(){

    event.preventDefault();

    let request: Requests = new Requests(this.emptyValueForName,this.emptyValueForPriority, this.emptyValueForData,this.emptyValueForTime);

    this.request.push(request);

    this.emptyValueForName = '';
    this.emptyValueForPriority = '';
    this.emptyValueForData = '';
    this.emptyValueForTime = '';

  }
}

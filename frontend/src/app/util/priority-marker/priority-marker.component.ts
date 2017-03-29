import {Component, OnInit, Input} from "@angular/core";

@Component({
  selector: 'priority-marker',
  templateUrl: 'priority-marker.component.html',
  styleUrls: ['priority-marker.component.css']
})
export class PriorityMarkerComponent {

  @Input('value') value: string;

}


import {Component, OnInit, Input} from "@angular/core";

@Component({
  selector: 'progress-marker',
  templateUrl: 'progress-marker.component.html',
  styleUrls: ['progress-marker.component.css']
})
export class ProgressMarkerComponent {

  @Input('value') value: string;

}


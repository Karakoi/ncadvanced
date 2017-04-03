import {Component, OnInit} from "@angular/core";
import {FormGroup, FormBuilder, Validators} from "@angular/forms";

@Component({
  selector: 'overseer-request-form',
  templateUrl: 'request-form.component.html',
  styleUrls: ['request-form.component.css']
})
export class RequestFormComponent implements OnInit {
  requestForm: FormGroup;

  constructor(private formBuilder: FormBuilder) {
  }

  ngOnInit(): void {
    this.requestForm = this.formBuilder.group({
      title: ['', Validators.required]
    });
  }

  createNewRequest(params): void {
    //console.log(params);
  }

  validateField(field: string): boolean {
    return this.requestForm.get(field).valid || !this.requestForm.get(field).dirty;
  }
}

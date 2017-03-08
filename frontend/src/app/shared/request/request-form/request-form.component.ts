import {Component, OnInit, ViewChild} from "@angular/core";
import {FormGroup, FormBuilder, Validators} from "@angular/forms";
import {ModalComponent} from "ng2-bs3-modal/components/modal";

@Component({
  selector: 'request-form',
  templateUrl: 'request-form.component.html',
  styleUrls: ['request-form.component.css']
})
export class RequestFormComponent implements OnInit {
  requestForm: FormGroup;

  @ViewChild('requestFormModal')
  modal: ModalComponent;

  constructor(private formBuilder: FormBuilder) {
  }

  ngOnInit(): void {
    this.requestForm = this.formBuilder.group({
      title: ['', Validators.required]
    });
  }

  createNewRequest(params): void {
    console.log(params);
  }

  validateField(field: string): boolean {
    return this.requestForm.get(field).valid || !this.requestForm.get(field).dirty;
  }
}

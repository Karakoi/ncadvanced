"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var core_1 = require("@angular/core");
var ReportComponent = (function () {
    function ReportComponent(formBuilder, userService, authService, router, toastr) {
        this.formBuilder = formBuilder;
        this.userService = userService;
        this.authService = authService;
        this.router = router;
        this.toastr = toastr;
    }
    ReportComponent.prototype.ngOnInit = function () {
    };
    ReportComponent.prototype.handleError = function (error) {
        switch (error.status) {
            case 400:
                this.toastr.error('This email is already taken.', 'Error.');
        }
    };
    ReportComponent = __decorate([
        core_1.Component({
            selector: 'report',
            templateUrl: 'report.component.html',
            styleUrls: ['report.component.css']
        })
    ], ReportComponent);
    return ReportComponent;
}());
exports.ReportComponent = ReportComponent;

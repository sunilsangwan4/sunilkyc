import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPersonalInformation } from 'app/shared/model/personal-information.model';

@Component({
    selector: 'jhi-personal-information-detail',
    templateUrl: './personal-information-detail.component.html'
})
export class PersonalInformationDetailComponent implements OnInit {
    personalInformation: IPersonalInformation;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ personalInformation }) => {
            this.personalInformation = personalInformation;
        });
    }

    previousState() {
        window.history.back();
    }
}

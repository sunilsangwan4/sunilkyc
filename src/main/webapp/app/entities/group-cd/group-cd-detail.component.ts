import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGroupCd } from 'app/shared/model/group-cd.model';

@Component({
    selector: 'jhi-group-cd-detail',
    templateUrl: './group-cd-detail.component.html'
})
export class GroupCdDetailComponent implements OnInit {
    groupCd: IGroupCd;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ groupCd }) => {
            this.groupCd = groupCd;
        });
    }

    previousState() {
        window.history.back();
    }
}

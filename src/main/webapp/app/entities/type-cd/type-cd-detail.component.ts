import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypeCd } from 'app/shared/model/type-cd.model';

@Component({
    selector: 'jhi-type-cd-detail',
    templateUrl: './type-cd-detail.component.html'
})
export class TypeCdDetailComponent implements OnInit {
    typeCd: ITypeCd;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ typeCd }) => {
            this.typeCd = typeCd;
        });
    }

    previousState() {
        window.history.back();
    }
}

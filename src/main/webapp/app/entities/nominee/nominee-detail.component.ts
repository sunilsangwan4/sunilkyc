import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INominee } from 'app/shared/model/nominee.model';

@Component({
    selector: 'jhi-nominee-detail',
    templateUrl: './nominee-detail.component.html'
})
export class NomineeDetailComponent implements OnInit {
    nominee: INominee;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ nominee }) => {
            this.nominee = nominee;
        });
    }

    previousState() {
        window.history.back();
    }
}

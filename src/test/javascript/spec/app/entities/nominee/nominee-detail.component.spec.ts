/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Kyc5TestModule } from '../../../test.module';
import { NomineeDetailComponent } from 'app/entities/nominee/nominee-detail.component';
import { Nominee } from 'app/shared/model/nominee.model';

describe('Component Tests', () => {
    describe('Nominee Management Detail Component', () => {
        let comp: NomineeDetailComponent;
        let fixture: ComponentFixture<NomineeDetailComponent>;
        const route = ({ data: of({ nominee: new Nominee(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Kyc5TestModule],
                declarations: [NomineeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(NomineeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(NomineeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.nominee).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

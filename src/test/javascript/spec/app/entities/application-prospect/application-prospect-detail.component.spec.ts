/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Kyc5TestModule } from '../../../test.module';
import { ApplicationProspectDetailComponent } from 'app/entities/application-prospect/application-prospect-detail.component';
import { ApplicationProspect } from 'app/shared/model/application-prospect.model';

describe('Component Tests', () => {
    describe('ApplicationProspect Management Detail Component', () => {
        let comp: ApplicationProspectDetailComponent;
        let fixture: ComponentFixture<ApplicationProspectDetailComponent>;
        const route = ({ data: of({ applicationProspect: new ApplicationProspect(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Kyc5TestModule],
                declarations: [ApplicationProspectDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ApplicationProspectDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ApplicationProspectDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.applicationProspect).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

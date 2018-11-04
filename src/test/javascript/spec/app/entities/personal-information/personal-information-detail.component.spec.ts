/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Kyc5TestModule } from '../../../test.module';
import { PersonalInformationDetailComponent } from 'app/entities/personal-information/personal-information-detail.component';
import { PersonalInformation } from 'app/shared/model/personal-information.model';

describe('Component Tests', () => {
    describe('PersonalInformation Management Detail Component', () => {
        let comp: PersonalInformationDetailComponent;
        let fixture: ComponentFixture<PersonalInformationDetailComponent>;
        const route = ({ data: of({ personalInformation: new PersonalInformation(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Kyc5TestModule],
                declarations: [PersonalInformationDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PersonalInformationDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PersonalInformationDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.personalInformation).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
